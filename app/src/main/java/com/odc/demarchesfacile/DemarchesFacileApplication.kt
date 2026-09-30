package com.odc.demarchesfacile

import android.app.Application
import com.odc.demarchesfacile.database.AppDatabase
import com.odc.demarchesfacile.repository.DemarcheRepository
import com.odc.demarchesfacile.repository.DemarcheRepositoryImpl

/**
 * On n'utilise pas de librairie d'injection de dépendances (Hilt/Koin) pour
 * rester simple pour un MVP. À la place, cette classe Application fabrique
 * UNE SEULE FOIS la base de données et le Repository, au démarrage de l'app,
 * et les rend disponibles à tous les écrans via "application.repository".
 *
 * N'oubliez pas de déclarer cette classe dans AndroidManifest.xml
 * avec android:name=".DemarchesFacileApplication"
 */
class DemarchesFacileApplication : Application() {

    lateinit var repository: DemarcheRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getInstance(this)
        repository = DemarcheRepositoryImpl(
            demarcheDao = database.demarcheDao(),
            pieceDao = database.pieceDao(),
            suiviDao = database.suiviDao(),
            pieceCocheeDao = database.pieceCocheeDao()
        )
    }
}
