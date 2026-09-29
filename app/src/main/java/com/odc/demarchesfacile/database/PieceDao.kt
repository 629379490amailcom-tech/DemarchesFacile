package com.odc.demarchesfacile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.model.Piece

@Dao
interface PieceDao {

    @Query("SELECT * FROM pieces WHERE demarcheId = :demarcheId")
    fun observerPourDemarche(demarcheId: Long): Flow<List<Piece>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insererTout(pieces: List<Piece>)

    @Query("SELECT COUNT(*) FROM pieces WHERE demarcheId = :demarcheId")
    suspend fun compterPourDemarche(demarcheId: Long): Int
}
