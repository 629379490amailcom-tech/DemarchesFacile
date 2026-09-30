# Votre mission — Mamadou Lamarana Diallo (Chef de projet et intégration)

## Ce que contient ce dossier
Le **projet complet**, prêt à être poussé sur GitHub : tout le code source,
le système de build Gradle, le `.gitignore`, le `README.md` et le fichier
`REPARTITION_EQUIPE.md`. C'est vous qui initialisez le dépôt.

## Vos fichiers à maîtriser
- `app/src/main/java/com/odc/demarchesfacile/navigation/AppNavGraph.kt`
- `app/src/main/java/com/odc/demarchesfacile/MainActivity.kt`
- `app/src/main/java/com/odc/demarchesfacile/DemarchesFacileApplication.kt`

Vous êtes le seul à toucher `AppNavGraph.kt` : c'est le fichier qui relie le
travail de tout le monde (écrans + ViewModels), donc personne d'autre n'y
touche pour éviter les conflits.

## Étape par étape

### 1. Créer le dépôt GitHub
Sur github.com → **New repository** → nom `DemarchesFacile` → ne cochez
aucune case (pas de README, pas de .gitignore : vous les avez déjà) →
**Create repository**. Copiez l'URL affichée.

### 2. Pousser le projet initial
Ouvrez un terminal dans ce dossier (celui qui contient `app/`, `gradlew`,
`README.md`...) et tapez :
```
git init
git add .
git commit -m "Projet initial Démarches Facile"
git branch -M main
git remote add origin https://github.com/VOTRE-NOM/DemarchesFacile.git
git push -u origin main
```

### 3. Inviter les 4 autres membres
Sur GitHub : **Settings → Collaborators → Add people**, avec leur nom
d'utilisateur GitHub ou leur email. Chacun doit accepter l'invitation.

### 4. Attendre les Pull Requests
Chaque membre va cloner ce dépôt, travailler sur sa branche, puis ouvrir
une Pull Request. Votre tâche : les relire et les fusionner (**Merge pull
request**) une par une dans `main`. En cas de conflit signalé par GitHub,
demandez à la personne concernée de le résoudre avant de fusionner.

### 5. Préparer l'APK final
Une fois toutes les Pull Requests fusionnées, dans Android Studio :
**Build → Generate Signed Bundle / APK → APK**. Déposez le fichier obtenu
dans une **Release** GitHub (onglet *Releases* → *Draft a new release*),
pas directement dans le code source (voir `.gitignore`).

### 6. Compléter le README
Ajoutez au `README.md` : description du projet, instructions d'installation,
et quelques captures d'écran de l'application (dossier `screenshots/`).

## Rappel pour l'oral
Vous devrez présenter le planning, l'organisation Git de l'équipe et faire
la démonstration. Soyez prêt à expliquer pourquoi le projet est organisé en
5 dossiers (model, database, repository, viewmodel, view).
