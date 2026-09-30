package com.odc.demarchesfacile.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Une pièce à fournir pour réaliser une démarche
 * (ex : "Photo d'identité", "Copie de la CNI").
 *
 * ForeignKey = on dit à Room "cette pièce appartient à UNE démarche précise".
 * onDelete = CASCADE : si on supprime la démarche, ses pièces sont supprimées aussi.
 */
@Entity(
    tableName = "pieces",
    foreignKeys = [
        ForeignKey(
            entity = Demarche::class,
            parentColumns = ["id"],
            childColumns = ["demarcheId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Piece(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val demarcheId: Long,

    val libelle: String
)
