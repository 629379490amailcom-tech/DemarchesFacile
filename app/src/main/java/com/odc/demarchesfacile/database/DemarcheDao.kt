package com.odc.demarchesfacile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.model.DemarcheAvecPieces

/**
 * Un DAO (Data Access Object) contient uniquement des requêtes SQL.
 * Room génère le code pour nous à partir des annotations @Query, @Insert, etc.
 *
 * Toutes les fonctions renvoient un Flow<...> : cela veut dire que Room va
 * automatiquement renvoyer une nouvelle liste à chaque fois que la table change,
 * sans qu'on ait besoin de recharger nous-mêmes. C'est ce qui permet à
 * l'interface de se mettre à jour toute seule (Compose observe ce Flow).
 */
@Dao
interface DemarcheDao {

    @Query("SELECT * FROM demarches ORDER BY titre ASC")
    fun observerToutes(): Flow<List<Demarche>>

    @Query(
        """
        SELECT * FROM demarches
        WHERE (:categorie IS NULL OR categorie = :categorie)
        AND (:motCle = '' OR titre LIKE '%' || :motCle || '%')
        ORDER BY titre ASC
        """
    )
    fun rechercher(motCle: String, categorie: String?): Flow<List<Demarche>>

    @Transaction
    @Query("SELECT * FROM demarches WHERE id = :demarcheId")
    fun observerAvecPieces(demarcheId: Long): Flow<DemarcheAvecPieces?>

    @Query("SELECT * FROM demarches WHERE id = :demarcheId")
    suspend fun getById(demarcheId: Long): Demarche?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insererTout(demarches: List<Demarche>)

    @Query("SELECT COUNT(*) FROM demarches")
    suspend fun compter(): Int
}
