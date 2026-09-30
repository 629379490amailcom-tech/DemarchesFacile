package com.odc.demarchesfacile.repository

import kotlinx.coroutines.flow.Flow
import com.odc.demarchesfacile.database.DemarcheDao
import com.odc.demarchesfacile.database.PieceCocheeDao
import com.odc.demarchesfacile.database.PieceDao
import com.odc.demarchesfacile.database.SuiviDao
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.model.DemarcheAvecPieces
import com.odc.demarchesfacile.model.Piece
import com.odc.demarchesfacile.model.PieceCochee
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.model.SuiviDemarche

/**
 * Implémentation concrète du Repository, basée sur Room (les 4 DAO).
 * C'est la SEULE classe de l'application qui parle directement aux DAO.
 */
class DemarcheRepositoryImpl(
    private val demarcheDao: DemarcheDao,
    private val pieceDao: PieceDao,
    private val suiviDao: SuiviDao,
    private val pieceCocheeDao: PieceCocheeDao
) : DemarcheRepository {

    override fun rechercherDemarches(motCle: String, categorie: String?): Flow<List<Demarche>> =
        demarcheDao.rechercher(motCle, categorie)

    override fun observerDemarcheAvecPieces(demarcheId: Long): Flow<DemarcheAvecPieces?> =
        demarcheDao.observerAvecPieces(demarcheId)

    override suspend fun demarrerSuivi(demarcheId: Long, pieces: List<Piece>): Long {
        val nouveauSuivi = SuiviDemarche(
            demarcheId = demarcheId,
            dateDebut = System.currentTimeMillis(),
            statut = StatutSuivi.EN_PREPARATION.name,
            notes = ""
        )
        val suiviId = suiviDao.inserer(nouveauSuivi)

        // On crée une ligne "non cochée" pour chaque pièce requise : cela permet
        // ensuite d'afficher directement toutes les cases à cocher dans le formulaire.
        val lignesPieceCochee = pieces.map { piece ->
            PieceCochee(suiviId = suiviId, pieceId = piece.id, cochee = false)
        }
        pieceCocheeDao.insererTout(lignesPieceCochee)

        return suiviId
    }

    override suspend fun getSuiviExistant(demarcheId: Long): SuiviDemarche? =
        suiviDao.getPourDemarche(demarcheId)

    override suspend fun getSuiviParId(suiviId: Long): SuiviDemarche? =
        suiviDao.getParId(suiviId)

    override fun observerSuivi(suiviId: Long): Flow<SuiviDemarche?> =
        suiviDao.observerParId(suiviId)

    override fun observerPiecesPourDemarche(demarcheId: Long): Flow<List<Piece>> =
        pieceDao.observerPourDemarche(demarcheId)

    override fun observerPiecesCocheesPourSuivi(suiviId: Long): Flow<List<PieceCochee>> =
        pieceCocheeDao.observerPourSuivi(suiviId)

    override suspend fun basculerPieceCochee(suiviId: Long, pieceId: Long, cochee: Boolean) {
        pieceCocheeDao.definirCochee(suiviId, pieceId, cochee)
    }

    override suspend fun changerStatut(suivi: SuiviDemarche, nouveauStatut: StatutSuivi) {
        suiviDao.mettreAJour(suivi.copy(statut = nouveauStatut.name))
    }

    override suspend fun enregistrerNotes(suivi: SuiviDemarche, notes: String) {
        suiviDao.mettreAJour(suivi.copy(notes = notes))
    }

    override fun observerSuivisEnCours(): Flow<List<SuiviDemarche>> =
        suiviDao.observerEnCours()

    override fun observerToutesLesDemarches(): Flow<List<Demarche>> =
        demarcheDao.observerToutes()

    override suspend fun getDemarcheParId(id: Long): Demarche? =
        demarcheDao.getById(id)

    override suspend fun compterPiecesDemarche(demarcheId: Long): Int =
        pieceDao.compterPourDemarche(demarcheId)

    override suspend fun compterPiecesCocheesSuivi(suiviId: Long): Int =
        pieceCocheeDao.compterCocheesPourSuivi(suiviId)
}
