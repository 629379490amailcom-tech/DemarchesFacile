package com.odc.demarchesfacile.repository

import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.model.DemarcheAvecPieces
import com.odc.demarchesfacile.model.Piece
import com.odc.demarchesfacile.model.PieceCochee
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.model.SuiviDemarche

/**
 * Le Repository est LA porte d'entrée unique vers les données pour tout le
 * reste de l'application. Les ViewModels ne connaissent QUE cette interface,
 * jamais Room ou les DAO directement.
 *
 * Pourquoi c'est important (voir cahier des charges) : le jour où on veut
 * remplacer Room par un vrai serveur (API), on change seulement la classe
 * DemarcheRepositoryImpl ci-dessous. Les ViewModels et les écrans ne bougent pas.
 */
interface DemarcheRepository {

    // --- Catalogue ---
    fun rechercherDemarches(motCle: String, categorie: String?): Flow<List<Demarche>>
    fun observerDemarcheAvecPieces(demarcheId: Long): Flow<DemarcheAvecPieces?>

    // --- Suivi ---
    suspend fun demarrerSuivi(demarcheId: Long, pieces: List<Piece>): Long
    suspend fun getSuiviExistant(demarcheId: Long): SuiviDemarche?
    suspend fun getSuiviParId(suiviId: Long): SuiviDemarche?
    fun observerSuivi(suiviId: Long): Flow<SuiviDemarche?>
    fun observerPiecesPourDemarche(demarcheId: Long): Flow<List<Piece>>
    fun observerPiecesCocheesPourSuivi(suiviId: Long): Flow<List<PieceCochee>>
    suspend fun basculerPieceCochee(suiviId: Long, pieceId: Long, cochee: Boolean)
    suspend fun changerStatut(suivi: SuiviDemarche, nouveauStatut: StatutSuivi)
    suspend fun enregistrerNotes(suivi: SuiviDemarche, notes: String)

    // --- Tableau de bord ---
    fun observerSuivisEnCours(): Flow<List<SuiviDemarche>>
    fun observerToutesLesDemarches(): Flow<List<Demarche>>
    suspend fun getDemarcheParId(id: Long): Demarche?
    suspend fun compterPiecesDemarche(demarcheId: Long): Int
    suspend fun compterPiecesCocheesSuivi(suiviId: Long): Int
}
