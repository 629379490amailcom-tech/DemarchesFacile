# Répartition du travail — Démarches Facile

Ce document précise qui maîtrise quelle partie du projet, pour que chacun
puisse committer sa contribution sans conflit avec les autres, et pour que
chaque membre soit capable de défendre sa partie à l'oral.

## Rôles et zones de responsabilité

| Personne | Rôle (cahier des charges) | Fichiers / dossiers à maîtriser | Branche Git |
|---|---|---|---|
| Mamadou Lamarana Diallo | Chef de projet et intégration | `navigation/AppNavGraph.kt`, `MainActivity.kt`, `DemarchesFacileApplication.kt`, `.gitignore`, `README.md` | travaille sur `main` |
| Boubacar Siddy Diallo | Responsable données | `model/` (Demarche, Piece, SuiviDemarche, PieceCochee, StatutSuivi, DemarcheAvecPieces) et `database/` (AppDatabase, DemarcheDao, PieceDao, SuiviDao, PieceCocheeDao, SeedData) | `feature-donnees` |
| Asmaou Diallo | Responsable interface (1/2) | `view/welcome/`, `view/catalogue/`, `view/detail/`, `view/theme/` (Color, Theme, Type) | `feature-interface-accueil-catalogue` |
| Thierno Youssoufou Sow | Responsable interface (2/2) | `view/suivi/`, `view/dashboard/`, `view/components/` | `feature-interface-suivi-dashboard` |
| Mohamed Issiaga Touré | Responsable logique et qualité | `viewmodel/` (CatalogueViewModel, DetailViewModel, SuiviViewModel, DashboardViewModel), `repository/`, `util/Formatters.kt`, dossier technique | `feature-logique` |

Le rôle « interface » est partagé entre deux personnes, réparties par écran
plutôt que par fichier : Asmaou et Thierno ne modifient jamais les mêmes
fichiers, donc aucune fusion conflictuelle entre eux.

## Pourquoi ce découpage évite les conflits Git

`AppNavGraph.kt` est le seul fichier qui importe le travail de tout le
monde (les écrans et les ViewModels de chacun). C'est pourquoi **seul le
chef de projet y touche** : personne d'autre ne le modifie, donc aucune
fusion délicate dessus. Les autres dossiers ne se croisent jamais.

## Tâche concrète à committer par personne

Le projet fonctionne déjà : pour que chaque commit soit une vraie
contribution vérifiable (et pas une simple recopie), voici une tâche
précise par personne.

- **Boubacar** — ajouter 2 à 3 nouvelles démarches dans `SeedData.kt`, ou
  documenter (KDoc) les DAO.
- **Asmaou** — ajuster une couleur ou une taille de texte dans
  `Color.kt` / `Type.kt`, ou améliorer le texte d'accroche du
  `WelcomeScreen.kt`.
- **Thierno** — améliorer le message d'état vide du tableau de bord, ou
  ajouter un élément visuel sur le formulaire de suivi.
- **Mohamed** — ajouter une validation (ex. empêcher une note vide), et
  rédiger le dossier technique.
- **Mamadou** — créer le dépôt, écrire le `.gitignore`, rédiger le README
  final avec captures d'écran, fusionner les Pull Requests, générer l'APK.

## Marche à suivre, dans l'ordre

1. Mamadou crée le dépôt GitHub et pousse le projet actuel sur `main`.
2. Chacun des 4 autres fait `git clone`, puis `git checkout -b sa-branche`
   (voir la colonne « Branche Git » ci-dessus).
3. Chacun réalise sa tâche, puis :
   ```
   git add .
   git commit -m "message clair décrivant la tâche"
   git push -u origin sa-branche
   ```
4. Chacun ouvre une Pull Request sur GitHub vers `main`.
5. Mamadou relit et fusionne les Pull Requests une par une — c'est sa
   tâche d'intégration en tant que chef de projet.

## Rappel pour l'oral

Le jury attend que chaque membre prenne la parole et puisse expliquer sa
partie. Restez dans votre zone de maîtrise pendant la préparation, pour
être capable de répondre aux questions dessus le jour de la soutenance.
