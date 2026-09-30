package com.odc.demarchesfacile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.repository.DemarcheRepository
import com.odc.demarchesfacile.database.SeedData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * Ce que l'écran Catalogue a besoin d'afficher, à un instant donné.
 * Regrouper toutes les infos dans un seul "UiState" est une bonne pratique
 * MVVM : l'écran n'a qu'UNE seule chose à observer.
 */
data class CatalogueUiState(
    val demarches: List<Demarche> = emptyList(),
    val motCle: String = "",
    val categorieSelectionnee: String? = null
) {
    val categoriesDisponibles: List<String> = listOf(
        SeedData.CAT_IDENTITE,
        SeedData.CAT_VOYAGE,
        SeedData.CAT_TRANSPORT,
        SeedData.CAT_ENTREPRISE,
        SeedData.CAT_EDUCATION,
        SeedData.CAT_JUSTICE,
        SeedData.CAT_FONCIER,
        SeedData.CAT_FISCALITE,
        SeedData.CAT_EMPLOI,
        SeedData.CAT_SANTE,
        SeedData.CAT_MAIRIE,
        SeedData.CAT_EN_LIGNE
    )
}

/**
 * NOTE IMPORTANTE (architecture MVVM) : ce ViewModel ne contient AUCUN import
 * android.widget / android.view / Compose. Il ne connaît que le Repository.
 * C'est ce qui permet de dire "la logique métier n'est pas dans l'interface".
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CatalogueViewModel(private val repository: DemarcheRepository) : ViewModel() {

    private val _motCle = MutableStateFlow("")
    private val _categorieSelectionnee = MutableStateFlow<String?>(null)

    // flatMapLatest : à chaque fois que motCle OU categorie change, on relance
    // automatiquement une nouvelle requête Room et on annule l'ancienne.
    private val demarchesFiltrees = combine(_motCle, _categorieSelectionnee) { motCle, categorie ->
        Pair(motCle, categorie)
    }.flatMapLatest { (motCle, categorie) ->
        repository.rechercherDemarches(motCle, categorie)
    }

    val uiState: StateFlow<CatalogueUiState> = combine(
        demarchesFiltrees, _motCle, _categorieSelectionnee
    ) { demarches, motCle, categorie ->
        CatalogueUiState(demarches = demarches, motCle = motCle, categorieSelectionnee = categorie)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CatalogueUiState()
    )

    fun onMotCleChange(nouveauMotCle: String) {
        _motCle.value = nouveauMotCle
    }

    fun onCategorieChange(nouvelleCategorie: String?) {
        // Si on reclique sur la catégorie déjà sélectionnée, on la désélectionne
        _categorieSelectionnee.value =
            if (_categorieSelectionnee.value == nouvelleCategorie) null else nouvelleCategorie
    }

    /** Fabrique du ViewModel : nécessaire car ce ViewModel a un paramètre (repository). */
    class Factory(private val repository: DemarcheRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CatalogueViewModel(repository) as T
        }
    }
}
