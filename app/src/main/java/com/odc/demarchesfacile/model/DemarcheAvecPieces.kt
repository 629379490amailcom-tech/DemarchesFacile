package com.odc.demarchesfacile.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Une "vue" pratique qui regroupe une Demarche et la liste de ses Pieces.
 * @Relation dit à Room : "va chercher automatiquement toutes les Piece
 * dont demarcheId == id de cette Demarche".
 * Ce n'est pas une table, juste un objet Kotlin pratique pour l'écran Détail.
 */
data class DemarcheAvecPieces(
    @Embedded
    val demarche: Demarche,

    @Relation(
        parentColumn = "id",
        entityColumn = "demarcheId"
    )
    val pieces: List<Piece>
)
