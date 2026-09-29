package com.odc.demarchesfacile.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Représente le fait qu'UN utilisateur a commencé à suivre UNE démarche
 * (créé quand on clique sur "Démarrer le suivi" dans l'écran Détail).
 *
 * - dateDebut : date de début du suivi, stockée en millisecondes (System.currentTimeMillis()).
 * - statut : stocké en String ("EN_PREPARATION", "DEPOSEE", "TERMINEE") -> voir StatutSuivi.kt.
 * - notes : notes libres de l'utilisateur.
 */
@Entity(
    tableName = "suivis_demarche",
    foreignKeys = [
        ForeignKey(
            entity = Demarche::class,
            parentColumns = ["id"],
            childColumns = ["demarcheId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SuiviDemarche(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val demarcheId: Long,

    val dateDebut: Long,

    val statut: String = StatutSuivi.EN_PREPARATION.name,

    val notes: String = ""
)
