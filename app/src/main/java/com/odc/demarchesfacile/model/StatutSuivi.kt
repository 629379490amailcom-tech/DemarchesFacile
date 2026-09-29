package com.odc.demarchesfacile.model

/**
 * Les 3 statuts possibles d'une démarche suivie par l'utilisateur.
 *
 * Astuce pédagogique : Room ne sait pas stocker un enum directement dans une
 * colonne de base de données. Plutôt que d'utiliser un "TypeConverter" (une
 * notion un peu avancée), on choisit ici la solution la plus simple :
 * - dans la table SQL (SuiviDemarche.kt), le statut est stocké comme un simple String.
 * - partout ailleurs dans le code (ViewModel, écrans), on manipule ce enum,
 *   qui est plus sûr et plus lisible qu'un String libre.
 * Les fonctions ci-dessous font la conversion entre les deux mondes.
 */
enum class StatutSuivi(val libelle: String) {
    EN_PREPARATION("En préparation"),
    DEPOSEE("Déposée"),
    TERMINEE("Terminée");

    companion object {
        fun fromDb(valeur: String): StatutSuivi =
            entries.firstOrNull { it.name == valeur } ?: EN_PREPARATION
    }
}
