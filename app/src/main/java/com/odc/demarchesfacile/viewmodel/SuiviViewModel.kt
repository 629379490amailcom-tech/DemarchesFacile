package com.odc.demarchesfacile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.odc.demarchesfacile.repository.DemarcheRepository
import com.odc.demarchesfacile.model.Piece
import com.odc.demarchesfacile.model.PieceCochee
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.model.SuiviDemarche
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SuiviUiState(
    val suivi: SuiviDemarche? = null,
    val titreDemarche: String = "",
    val pieces: List<Piece> = emptyList(),
    val piecesCochees: List<PieceCochee> = emptyList(),
    val notesSaisies: String = ""
) {
    val nombreCoche: Int get() = piecesCochees.count { it.cochee }
    val nombreTotal: Int get() = pieces.size
    val progressionPourcentage: Int
        get() = if (nombreTotal == 0) 0 else (nombreCoche * 100) / nombreTotal

    fun estCochee(pieceId: Long): Boolean =
        piecesCochees.firstOrNull { it.pieceId == pieceId }?.cochee ?: false
}

@OptIn(ExperimentalCoroutinesApi::class)
class SuiviViewModel(
    private val repository: DemarcheRepository,
    private val suiviId: Long
) : ViewModel() {

    // On garde une copie locale du texte des notes pendant que l'utilisateur tape,
    // pour ne pas ré-écrire dans la base à chaque lettre (on enregistre à la fin).
    private val _notesSaisies = MutableStateFlow<String?>(null)

    private val suiviFlow = repository.observerSuivi(suiviId)

    // flatMapLatest : dès qu'on connaît le suivi, on va chercher les pièces
    // de SA démarche (demarcheId). Tant que suivi == null, on renvoie une liste vide.
    private val piecesFlow = suiviFlow.flatMapLatest { suivi ->
        if (suivi == null) flowOf(emptyList<Piece>())
        else repository.observerPiecesPourDemarche(suivi.demarcheId)
    }

    private val titreFlow = suiviFlow.flatMapLatest { suivi ->
        flow {
            if (suivi == null) {
                emit("")
            } else {
                val demarche = repository.getDemarcheParId(suivi.demarcheId)
                emit(demarche?.titre ?: "")
            }
        }
    }

    val uiState: StateFlow<SuiviUiState> = combine(
        suiviFlow,
        titreFlow,
        piecesFlow,
        repository.observerPiecesCocheesPourSuivi(suiviId),
        _notesSaisies
    ) { suivi, titre, pieces, piecesCochees, notesLocales ->
        SuiviUiState(
            suivi = suivi,
            titreDemarche = titre,
            pieces = pieces,
            piecesCochees = piecesCochees,
            notesSaisies = notesLocales ?: suivi?.notes ?: ""
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SuiviUiState()
    )

    fun basculerPiece(pieceId: Long, cochee: Boolean) {
        viewModelScope.launch {
            repository.basculerPieceCochee(suiviId, pieceId, cochee)
        }
    }

    fun onStatutChoisi(nouveauStatut: StatutSuivi) {
        val suivi = uiState.value.suivi ?: return
        viewModelScope.launch {
            repository.changerStatut(suivi, nouveauStatut)
        }
    }

    fun onNotesChange(nouvellesNotes: String) {
        _notesSaisies.value = nouvellesNotes
    }

    /** À appeler quand l'utilisateur quitte l'écran, pour sauvegarder les notes tapées. */
    fun enregistrerNotes() {
        val suivi = uiState.value.suivi ?: return
        val notes = _notesSaisies.value ?: return
        viewModelScope.launch {
            repository.enregistrerNotes(suivi, notes)
        }
    }

    class Factory(
        private val repository: DemarcheRepository,
        private val suiviId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SuiviViewModel(repository, suiviId) as T
        }
    }
}
