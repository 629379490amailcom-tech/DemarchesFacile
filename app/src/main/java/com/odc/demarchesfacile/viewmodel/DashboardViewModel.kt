package com.odc.demarchesfacile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.repository.DemarcheRepository
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.model.SuiviDemarche
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Une ligne affichée dans le tableau de bord : le suivi + sa démarche + sa progression. */
data class SuiviAffichable(
    val suivi: SuiviDemarche,
    val demarche: Demarche?,
    val piecesFaites: Int,
    val piecesTotal: Int
) {
    val progressionPourcentage: Int
        get() = if (piecesTotal == 0) 0 else (piecesFaites * 100) / piecesTotal
}

data class DashboardUiState(
    val suivisEnCours: List<SuiviAffichable> = emptyList(),
    val progressionGlobalePourcentage: Int = 0,
    val prochaineAction: String = "Aucune démarche en cours pour le moment",
    val chargementEnCours: Boolean = true
)

class DashboardViewModel(private val repository: DemarcheRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // On observe en continu la liste des suivis en cours. Pour chaque
            // changement, on recalcule les progressions (nombre de pièces
            // cochées vs total) puis on met à jour l'état affiché.
            repository.observerSuivisEnCours().collect { suivis ->
                val toutesLesDemarches = repository.observerToutesLesDemarches().first()

                val lignes = suivis.map { suivi ->
                    val demarche = toutesLesDemarches.firstOrNull { it.id == suivi.demarcheId }
                    val total = repository.compterPiecesDemarche(suivi.demarcheId)
                    val faites = repository.compterPiecesCocheesSuivi(suivi.id)
                    SuiviAffichable(suivi, demarche, faites, total)
                }

                val totalPiecesGlobal = lignes.sumOf { it.piecesTotal }
                val totalFaitesGlobal = lignes.sumOf { it.piecesFaites }
                val progressionGlobale = if (totalPiecesGlobal == 0) 0
                else (totalFaitesGlobal * 100) / totalPiecesGlobal

                // Prochaine action = la démarche la moins avancée, en préparation
                val prochaine = lignes
                    .filter { StatutSuivi.fromDb(it.suivi.statut) != StatutSuivi.TERMINEE }
                    .minByOrNull { it.progressionPourcentage }

                _uiState.value = DashboardUiState(
                    suivisEnCours = lignes,
                    progressionGlobalePourcentage = progressionGlobale,
                    prochaineAction = if (prochaine == null) {
                        "Aucune démarche en cours pour le moment"
                    } else {
                        "Continuer : ${prochaine.demarche?.titre ?: "une démarche"} " +
                            "(${prochaine.progressionPourcentage}% des pièces réunies)"
                    },
                    chargementEnCours = false
                )
            }
        }
    }

    class Factory(private val repository: DemarcheRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
    }
}
