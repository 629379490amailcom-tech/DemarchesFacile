package com.odc.demarchesfacile.database
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.model.Piece

/**
 * Catalogue de démarches pré-rempli avec les tarifs officiels en Guinée (GNF),
 * inséré une seule fois au premier lancement de l'application (voir AppDatabase.kt).
 */
object SeedData {

    const val CAT_IDENTITE = "🪪 Identité"
    const val CAT_VOYAGE = "🛂 Voyage"
    const val CAT_TRANSPORT = "🚗 Transport"
    const val CAT_ENTREPRISE = "🏢 Entreprise"
    const val CAT_EDUCATION = "🎓 Éducation"
    const val CAT_JUSTICE = "⚖️ Justice"
    const val CAT_FONCIER = "🏠 Foncier"
    const val CAT_FISCALITE = "💰 Fiscalité"
    const val CAT_EMPLOI = "👷 Emploi"
    const val CAT_SANTE = "🏥 Santé"
    const val CAT_MAIRIE = "🏛️ Mairie"
    const val CAT_EN_LIGNE = "💻 En ligne"

    val demarches = listOf(
        Demarche(id = 1, titre = "Acte de naissance (copie intégrale)", categorie = CAT_IDENTITE, coutGnf = 5000, delaiJours = 2, lieu = "Mairie de la commune de naissance"),
        Demarche(id = 2, titre = "Carte d'identité nationale (CNI)", categorie = CAT_IDENTITE, coutGnf = 50000, delaiJours = 15, lieu = "Poste de police / Mairie"),
        Demarche(id = 3, titre = "Certificat de nationalité", categorie = CAT_IDENTITE, coutGnf = 15000, delaiJours = 5, lieu = "Tribunal de première instance"),
        Demarche(id = 4, titre = "Passeport ordinaire", categorie = CAT_VOYAGE, coutGnf = 550000, delaiJours = 21, lieu = "Direction Générale de la Police (DGPN)"),
        Demarche(id = 5, titre = "Visa de voyage", categorie = CAT_VOYAGE, coutGnf = 850000, delaiJours = 10, lieu = "Ambassade / Centre de visa concerné"),
        Demarche(id = 6, titre = "Permis de conduire", categorie = CAT_TRANSPORT, coutGnf = 350000, delaiJours = 14, lieu = "Service des Transports Routiers"),
        Demarche(id = 7, titre = "Carte grise (Immatriculation véhicule)", categorie = CAT_TRANSPORT, coutGnf = 300000, delaiJours = 7, lieu = "Direction Nationale des Transports"),
        Demarche(id = 8, titre = "Vignette automobile", categorie = CAT_TRANSPORT, coutGnf = 150000, delaiJours = 1, lieu = "Centre des Impôts / Banques agréées"),
        Demarche(id = 9, titre = "Registre du commerce (RCCM)", categorie = CAT_ENTREPRISE, coutGnf = 300000, delaiJours = 10, lieu = "Guichet Unique (APIP)"),
        Demarche(id = 10, titre = "Numéro d'Identification Fiscale (NIF)", categorie = CAT_ENTREPRISE, coutGnf = 0, delaiJours = 5, lieu = "Direction Générale des Impôts"),
        Demarche(id = 11, titre = "Quitus fiscal", categorie = CAT_FISCALITE, coutGnf = 0, delaiJours = 3, lieu = "Direction Générale des Impôts"),
        Demarche(id = 12, titre = "Déclaration d'impôts sur le revenu", categorie = CAT_FISCALITE, coutGnf = 100000, delaiJours = 2, lieu = "Centre des Impôts rattaché"),
        Demarche(id = 13, titre = "Casier judiciaire (bulletin n°3)", categorie = CAT_JUSTICE, coutGnf = 10000, delaiJours = 7, lieu = "Tribunal de première instance"),
        Demarche(id = 14, titre = "Légalisation de document", categorie = CAT_JUSTICE, coutGnf = 5000, delaiJours = 1, lieu = "Mairie ou Préfecture"),
        Demarche(id = 15, titre = "Titre foncier", categorie = CAT_FONCIER, coutGnf = 1500000, delaiJours = 30, lieu = "Conservation Foncière"),
        Demarche(id = 16, titre = "Permis de construire", categorie = CAT_FONCIER, coutGnf = 750000, delaiJours = 20, lieu = "Ministère de l'Urbanisme / Mairie"),
        Demarche(id = 17, titre = "Immatriculation Sécurité Sociale (CNSS)", categorie = CAT_EMPLOI, coutGnf = 0, delaiJours = 5, lieu = "Caisse Nationale de Sécurité Sociale"),
        Demarche(id = 18, titre = "Certificat médical d'aptitude", categorie = CAT_SANTE, coutGnf = 20000, delaiJours = 1, lieu = "Centre de santé agréé / Hôpital"),
        Demarche(id = 19, titre = "Certificat de résidence", categorie = CAT_MAIRIE, coutGnf = 5000, delaiJours = 1, lieu = "Mairie du quartier / Commissariat"),
        Demarche(id = 20, titre = "Demande et suivi de documents en ligne", categorie = CAT_EN_LIGNE, coutGnf = 10000, delaiJours = 3, lieu = "Portail Web des Services Publics"),
        Demarche(id = 21, titre = "Authentification de diplômes", categorie = CAT_EDUCATION, coutGnf = 25000, delaiJours = 7, lieu = "Ministère de l'Enseignement Supérieur")
    )

    val pieces = listOf(
        // 1. Acte de naissance
        Piece(demarcheId = 1, libelle = "Formulaire de demande rempli"),
        Piece(demarcheId = 1, libelle = "Ancien acte de naissance ou déclaration"),
        Piece(demarcheId = 1, libelle = "Frais de timbre"),
        // 2. CNI
        Piece(demarcheId = 2, libelle = "2 photos d'identité"),
        Piece(demarcheId = 2, libelle = "Copie de l'acte de naissance"),
        Piece(demarcheId = 2, libelle = "Certificat de résidence"),
        Piece(demarcheId = 2, libelle = "Frais d'établissement"),
        // 3. Nationalité
        Piece(demarcheId = 3, libelle = "Copie de l'acte de naissance"),
        Piece(demarcheId = 3, libelle = "Acte de naissance des parents"),
        Piece(demarcheId = 3, libelle = "Timbre fiscal et timbre communal"),
        // 4. Passeport
        Piece(demarcheId = 4, libelle = "Copie de la CNI"),
        Piece(demarcheId = 4, libelle = "4 photos d'identité fond blanc"),
        Piece(demarcheId = 4, libelle = "Copie de l'acte de naissance"),
        Piece(demarcheId = 4, libelle = "Reçu de paiement des frais de passeport"),
        // 5. Visa
        Piece(demarcheId = 5, libelle = "Passeport valide (minimum 6 mois)"),
        Piece(demarcheId = 5, libelle = "Formulaire de demande de visa"),
        Piece(demarcheId = 5, libelle = "Photos d'identité récentes"),
        Piece(demarcheId = 5, libelle = "Justificatif de ressources financières"),
        // 6. Permis de conduire
        Piece(demarcheId = 6, libelle = "Attestation d'auto-école"),
        Piece(demarcheId = 6, libelle = "Copie de la CNI"),
        Piece(demarcheId = 6, libelle = "Certificat médical d'aptitude visuelle"),
        Piece(demarcheId = 6, libelle = "Photos d'identité"),
        // 7. Carte grise
        Piece(demarcheId = 7, libelle = "Certificat de vente ou dédouanement"),
        Piece(demarcheId = 7, libelle = "Copie de la CNI du propriétaire"),
        Piece(demarcheId = 7, libelle = "Procès-verbal de visite technique"),
        // 8. Vignette
        Piece(demarcheId = 8, libelle = "Copie de la carte grise"),
        Piece(demarcheId = 8, libelle = "Quittance de paiement des taxes"),
        // 9. RCCM
        Piece(demarcheId = 9, libelle = "Copie de la CNI du gérant"),
        Piece(demarcheId = 9, libelle = "Statuts notariés de l'entreprise"),
        Piece(demarcheId = 9, libelle = "Certificat de non-condamnation"),
        // 10. NIF
        Piece(demarcheId = 10, libelle = "Copie du RCCM"),
        Piece(demarcheId = 10, libelle = "Plan de localisation du siège social"),
        // 11. Quitus fiscal
        Piece(demarcheId = 11, libelle = "Déclarations fiscales des 3 dernières années"),
        Piece(demarcheId = 11, libelle = "Bilan comptable certifié"),
        // 12. Déclaration impôts
        Piece(demarcheId = 12, libelle = "Formulaire de déclaration fiscale"),
        Piece(demarcheId = 12, libelle = "États financiers"),
        // 13. Casier judiciaire
        Piece(demarcheId = 13, libelle = "Copie de la CNI"),
        Piece(demarcheId = 13, libelle = "Copie de l'acte de naissance"),
        Piece(demarcheId = 13, libelle = "Timbre fiscal"),
        // 14. Légalisation
        Piece(demarcheId = 14, libelle = "Document original à légaliser"),
        Piece(demarcheId = 14, libelle = "Pièce d'identité"),
        // 15. Titre foncier
        Piece(demarcheId = 15, libelle = "Plan topographique du terrain"),
        Piece(demarcheId = 15, libelle = "Acte de vente notarié"),
        Piece(demarcheId = 15, libelle = "Certificat de non-litige"),
        // 16. Permis de construire
        Piece(demarcheId = 16, libelle = "Plans architecturaux approuvés"),
        Piece(demarcheId = 16, libelle = "Titre de propriété du terrain"),
        // 17. CNSS
        Piece(demarcheId = 17, libelle = "Registre du commerce (RCCM)"),
        Piece(demarcheId = 17, libelle = "Liste des employés et salaires"),
        // 18. Certificat médical
        Piece(demarcheId = 18, libelle = "Pièce d'identité"),
        Piece(demarcheId = 18, libelle = "Examen clinique de base"),
        // 19. Certificat de résidence
        Piece(demarcheId = 19, libelle = "Facture d'eau ou d'électricité récente"),
        Piece(demarcheId = 19, libelle = "Attestation du chef de quartier"),
        // 20. En ligne
        Piece(demarcheId = 20, libelle = "Compte utilisateur actif sur le portail"),
        Piece(demarcheId = 20, libelle = "Pièces justificatives scannées en PDF"),
        // 21. Diplômes
        Piece(demarcheId = 21, libelle = "Diplôme original ou attestation de réussite"),
        Piece(demarcheId = 21, libelle = "Relevés de notes officiels")
    )
}
