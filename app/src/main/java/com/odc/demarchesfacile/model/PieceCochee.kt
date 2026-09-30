package com.odc.demarchesfacile.model

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Pour un suivi donné, dit si telle pièce a déjà été réunie (cochée) ou non.
 *
 * Cette table a une CLE PRIMAIRE COMPOSEE (suiviId + pieceId) : cela veut dire
 * qu'il ne peut jamais exister deux lignes avec la même paire (suiviId, pieceId).
 * C'est logique : une pièce donnée n'a qu'un seul état (cochée ou pas) pour un suivi donné.
 */
@Entity(
    tableName = "pieces_cochees",
    primaryKeys = ["suiviId", "pieceId"],
    foreignKeys = [
        ForeignKey(
            entity = SuiviDemarche::class,
            parentColumns = ["id"],
            childColumns = ["suiviId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Piece::class,
            parentColumns = ["id"],
            childColumns = ["pieceId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PieceCochee(
    val suiviId: Long,
    val pieceId: Long,
    val cochee: Boolean = false
)
