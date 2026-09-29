package com.odc.demarchesfacile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.model.PieceCochee

@Dao
interface PieceCocheeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserer(pieceCochee: PieceCochee)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insererTout(pieces: List<PieceCochee>)

    @Query("SELECT * FROM pieces_cochees WHERE suiviId = :suiviId")
    fun observerPourSuivi(suiviId: Long): Flow<List<PieceCochee>>

    @Query("UPDATE pieces_cochees SET cochee = :cochee WHERE suiviId = :suiviId AND pieceId = :pieceId")
    suspend fun definirCochee(suiviId: Long, pieceId: Long, cochee: Boolean)

    @Query("SELECT COUNT(*) FROM pieces_cochees WHERE suiviId = :suiviId AND cochee = 1")
    suspend fun compterCocheesPourSuivi(suiviId: Long): Int
}
