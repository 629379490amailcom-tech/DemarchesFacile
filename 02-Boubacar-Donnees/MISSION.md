# Votre mission — Boubacar Siddy Diallo (Responsable données)

## Ce que contient ce dossier
Uniquement **vos fichiers**, rangés exactement au même endroit que dans le
projet complet :
```
app/src/main/java/com/odc/demarchesfacile/model/       (6 fichiers)
app/src/main/java/com/odc/demarchesfacile/database/    (6 fichiers)
```
Ce sont les entités (Demarche, Piece, SuiviDemarche, PieceCochee...), les
DAO Room (les requêtes) et le catalogue de démarches (`SeedData.kt`).

⚠️ Ce dossier seul ne compile pas : il vous manque tout le reste du projet.
C'est normal, voir "Comment procéder" ci-dessous.

## Comment procéder

1. Attendez que Mamadou vous confirme que le dépôt GitHub existe et qu'il
   vous a invité.
2. Clonez le dépôt complet :
   ```
   git clone https://github.com/VOTRE-NOM/DemarchesFacile.git
   cd DemarchesFacile
   git checkout -b feature-donnees
   ```
3. Copiez les fichiers de CE dossier par-dessus ceux du dépôt cloné (mêmes
   emplacements exacts) — normalement ils remplacent des fichiers déjà
   identiques, sauf celui que vous allez modifier à l'étape suivante.
4. Ouvrez le projet dans Android Studio et faites votre tâche (ci-dessous).
5. Vérifiez que l'application compile toujours (bouton ▶).
6. Committez et poussez :
   ```
   git add .
   git commit -m "Ajout de nouvelles démarches au catalogue"
   git push -u origin feature-donnees
   ```
7. Sur GitHub, ouvrez une **Pull Request** vers `main`.

## Votre tâche concrète

Ajoutez **2 à 3 nouvelles démarches** dans `database/SeedData.kt`. Le
dernier identifiant utilisé est **21** : continuez à partir de 22.

Exemple à adapter (copiez ce modèle dans la liste `demarches`, puis ajoutez
les pièces correspondantes dans la liste `pieces`) :
```kotlin
Demarche(id = 22, titre = "Duplicata de carte d'identité", categorie = CAT_IDENTITE, coutGnf = 30000, delaiJours = 10, lieu = "Poste de police / Mairie"),
```
```kotlin
Piece(demarcheId = 22, libelle = "Déclaration de perte ou de vol"),
Piece(demarcheId = 22, libelle = "2 photos d'identité"),
Piece(demarcheId = 22, libelle = "Copie de l'acte de naissance"),
```
Utilisez l'une des catégories déjà définies en haut du fichier (CAT_IDENTITE,
CAT_VOYAGE, CAT_TRANSPORT, CAT_ENTREPRISE, CAT_EDUCATION, CAT_JUSTICE,
CAT_FONCIER, CAT_FISCALITE, CAT_EMPLOI, CAT_SANTE, CAT_MAIRIE, CAT_EN_LIGNE).

## Rappel pour l'oral
Vous devrez expliquer le modèle de données Room : les 4 tables, leurs
relations (1 démarche → plusieurs pièces, 1 suivi → plusieurs pièces
cochées), et pourquoi `PieceCochee` a une clé primaire composée.
