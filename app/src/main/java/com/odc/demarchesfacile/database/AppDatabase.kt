package com.odc.demarchesfacile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.model.Piece
import com.odc.demarchesfacile.model.PieceCochee
import com.odc.demarchesfacile.model.SuiviDemarche

/**
 * Point d'entrée unique vers la base de données locale (SQLite via Room).
 * C'est ce fichier qui rend l'application "offline-first" : toutes les
 * données vivent ici, sur l'appareil, et survivent à la fermeture de l'app.
 */
@Database(
    entities = [Demarche::class, Piece::class, SuiviDemarche::class, PieceCochee::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun demarcheDao(): DemarcheDao
    abstract fun pieceDao(): PieceDao
    abstract fun suiviDao(): SuiviDao
    abstract fun pieceCocheeDao(): PieceCocheeDao

    companion object {
        // @Volatile + synchronized = un classique Kotlin pour garantir qu'une
        // seule instance de la base est créée, même si plusieurs écrans la
        // demandent en même temps (pattern "Singleton").
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }
        }

        private fun build(context: Context): AppDatabase {
            var instance: AppDatabase? = null
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "demarches_facile.db"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // onCreate n'est appelé qu'UNE SEULE FOIS : la toute première
                        // fois que la base est créée sur l'appareil. C'est le bon
                        // endroit pour insérer notre catalogue de démarrage.
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = instance ?: INSTANCE
                            database?.demarcheDao()?.insererTout(SeedData.demarches)
                            database?.pieceDao()?.insererTout(SeedData.pieces)
                        }
                    }
                })
                .build()
            return instance!!
        }
    }
}
