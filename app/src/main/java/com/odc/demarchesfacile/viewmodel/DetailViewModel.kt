package com.odc.demarchesfacile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.odc.demarchesfacile.model.DemarcheAvecPieces
import com.odc.demarchesfacile.repository.DemarcheRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetailUiState(
    val demarcheAvecPieces: DemarcheAvecPieces? = null,
    // id du suivi déjà existant pour cette démarche, ou null si pas encore démarré
    val suiviExistantId: Long? = null,
    val chargementEnCours: Boolean = true
)

class DetailViewModel(
    private val repository: DemarcheRepository,
    private val demarcheId: Long
) : ViewModel() {

    private val _suiviExistantId = MutableStateFlow<Long?>(null)
    private val _chargementInitialFait = MutableStateFlow(false)

    init {
        // On vérifie une fois, au chargement de l'écran, si l'utilisateur a
        // déjà commencé à suivre cette démarche (pas besoin d'observer en
        // continu : on rafraîchit nous-même juste après avoir créé un suivi).
        viewModelScope.launch {
            val suiviExistant = repository.getSuiviExistant(demarcheId)
            _suiviExistantId.value = suiviExistant?.id
            _chargementInitialFait.value = true
        }
    }

    val uiState: StateFlow<DetailUiState> = combine(
        repository.observerDemarcheAvecPieces(demarcheId),
        _suiviExistantId,
        _chargementInitialFait
    ) { demarcheAvecPieces, suiviId, chargementFait ->
        DetailUiState(
            demarcheAvecPieces = demarcheAvecPieces,
            suiviExistantId = suiviId,
            chargementEnCours = !chargementFait || demarcheAvecPieces == null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailUiState()
    )

    /**
     * Démarre le suivi de cette démarche puis appelle [onSuiviCree] avec l'id
     * du nouveau suivi, pour que l'écran puisse naviguer vers le formulaire.
     */
    fun demarrerSuivi(onSuiviCree: (Long) -> Unit) {
        val pieces = uiState.value.demarcheAvecPieces?.pieces ?: return
        viewModelScope.launch {
            val nouvelId = repository.demarrerSuivi(demarcheId, pieces)
            _suiviExistantId.value = nouvelId
            onSuiviCree(nouvelId)
        }
    }

    class Factory(
        private val repository: DemarcheRepository,
        private val demarcheId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository, demarcheId) as T
        }
    }
}
