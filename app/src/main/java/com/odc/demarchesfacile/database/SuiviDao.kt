package com.odc.demarchesfacile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.model.SuiviDemarche

@Dao
interface SuiviDao {

    @Insert
    suspend fun inserer(suivi: SuiviDemarche): Long

    @Update
    suspend fun mettreAJour(suivi: SuiviDemarche)

    @Query("SELECT * FROM suivis_demarche WHERE id = :suiviId")
    fun observerParId(suiviId: Long): Flow<SuiviDemarche?>

    @Query("SELECT * FROM suivis_demarche WHERE id = :suiviId")
    suspend fun getParId(suiviId: Long): SuiviDemarche?

    // Un suivi existe déjà pour cette démarche ? (utile pour l'écran Détail :
    // afficher "Démarrer le suivi" ou "Voir mon suivi")
    @Query("SELECT * FROM suivis_demarche WHERE demarcheId = :demarcheId LIMIT 1")
    suspend fun getPourDemarche(demarcheId: Long): SuiviDemarche?

    // Tous les suivis PAS encore terminés, pour le tableau de bord
    @Query("SELECT * FROM suivis_demarche WHERE statut != 'TERMINEE' ORDER BY dateDebut DESC")
    fun observerEnCours(): Flow<List<SuiviDemarche>>

    @Query("SELECT * FROM suivis_demarche ORDER BY dateDebut DESC")
    fun observerTous(): Flow<List<SuiviDemarche>>
}
