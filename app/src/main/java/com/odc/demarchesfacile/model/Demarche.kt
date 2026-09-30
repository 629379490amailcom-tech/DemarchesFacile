package com.odc.demarchesfacile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Une "Demarche" = une fiche du catalogue (ex: "Faire un passeport").
 * C'est une donnée de référence : elle est préremplie au premier lancement
 * de l'application (voir SeedData.kt) et ne change pas souvent.
 *
 * @Entity dit à Room : "crée une table SQL qui ressemble à cette classe".
 */
@Entity(tableName = "demarches")
data class Demarche(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val titre: String,

    // Catégorie parmi : "Etat civil", "Identite et voyage", "Justice", "Creation entreprise"
    val categorie: String,

    val coutGnf: Long,

    val delaiJours: Int,

    val lieu: String
)
