# Démarches Facile — Guide complet du projet

Bienvenue ! Ce document explique **tout ce qu'il faut savoir** pour installer,
comprendre et présenter ce projet, même si vous débutez en développement
Android. Lisez-le dans l'ordre.

---

## 1. Créer le projet dans Android Studio

On part du modèle officiel d'Android Studio (garanti de fonctionner), puis on
ajoute notre code par-dessus. Ne créez PAS un projet vide : suivez ces étapes.

1. Ouvrez Android Studio → **New Project**.
2. Choisissez le modèle **"Empty Activity"** (dans la catégorie Phone and Tablet).
   Vérifiez bien que c'est celui avec le logo Compose (pas "Empty Views Activity").
3. Remplissez :
   - **Name** : `Démarches Facile`
   - **Package name** : `com.odc.demarchesfacile` (important : gardez exactement
     ce nom, sinon il faudra changer le mot `package` en haut de tous nos fichiers)
   - **Minimum SDK** : API 24 (Android 7.0)
   - **Build configuration language** : Kotlin DSL (build.gradle.kts)
4. Cliquez sur **Finish** et attendez la fin du premier "Gradle Sync"
   (barre de progression en bas). Vérifiez que le projet généré se lance bien
   sur un émulateur avant de continuer (bouton ▶ vert) — cela confirme que la
   base est saine.

## 2. Ajouter les dépendances (Room, Navigation...)

Ouvrez le fichier **`DEPENDANCES_A_AJOUTER.md`** fourni à côté de ce README et
suivez-le pas à pas. C'est une étape obligatoire : sans elle, notre code ne
compilera pas car Room et Navigation ne sont pas inclus par défaut.

## 3. Copier notre code dans le projet

Dans le dossier généré par Android Studio, vous devez remplacer/ajouter :

| Élément fourni dans ce zip | Où le mettre dans votre projet Android Studio |
|---|---|
| `app/src/main/java/com/odc/demarchesfacile/` (tous les sous-dossiers `data`, `ui`, `util`, et les fichiers `MainActivity.kt`, `DemarchesFacileApplication.kt`) | Remplace entièrement le dossier `app/src/main/java/com/odc/demarchesfacile/` généré par Android Studio (qui ne contient au départ que `MainActivity.kt`) |
| `app/src/main/AndroidManifest.xml` | Remplace le fichier généré |
| `app/src/main/res/values/strings.xml` | Remplace le fichier généré |

**Comment faire concrètement (le plus simple) :**
1. Dans Android Studio, passez la vue du panneau de gauche de "Android" à
   "Project" (menu déroulant en haut du panneau).
2. Supprimez le dossier `com/odc/demarchesfacile` existant sous
   `app/src/main/java/`.
3. Avec votre explorateur de fichiers (Windows/Mac/Linux), copiez-collez le
   dossier `com` fourni dans ce zip à la même place.
4. Faites de même pour `AndroidManifest.xml` et `strings.xml`.
5. Retournez dans Android Studio, clic droit sur le projet → **Sync Project
   with Gradle Files**.

## 4. Lancer l'application

Cliquez sur le bouton ▶ (Run) en haut d'Android Studio, avec un émulateur ou
un vrai téléphone branché en USB (mode débogage USB activé). L'application
doit s'ouvrir sur l'écran **Catalogue**, déjà rempli de 8 démarches (aucune
connexion internet nécessaire : les données sont pré-enregistrées dans Room
au premier lancement, voir `AppDatabase.kt`).

## 5. Tester le mode "offline-first" (obligatoire pour l'évaluation)

1. Activez le **mode avion** sur votre téléphone/émulateur.
2. Parcourez : Catalogue → cliquez une démarche → Détail → "Démarrer le
   suivi" → cochez des pièces → changez le statut → écrivez une note →
   revenez en arrière → allez sur "Tableau de bord".
3. Tout doit fonctionner sans le moindre message d'erreur réseau, car
   l'application n'appelle jamais internet.
4. Fermez complètement l'application (pas juste "revenir en arrière" :
   swipez-la hors des apps récentes) puis rouvrez-la : vos données doivent
   toujours être là. C'est la preuve que Room fonctionne.

---

## 6. Comprendre l'architecture MVVM (pour l'expliquer à l'oral)

Le projet est organisé en **5 dossiers**, un par rôle, ce qui rend chaque
couche immédiatement identifiable :

```
com.odc.demarchesfacile/
│
├── model/          → LE MODÈLE : les classes de données pures (@Entity, enum)
│                      Demarche.kt, Piece.kt, SuiviDemarche.kt, PieceCochee.kt,
│                      StatutSuivi.kt, DemarcheAvecPieces.kt
│                      Aucune de ces classes ne sait qu'un écran existe.
│
├── database/       → L'ACCÈS AUX DONNÉES (Room)
│                      DemarcheDao.kt, PieceDao.kt, SuiviDao.kt, PieceCocheeDao.kt
│                      (les requêtes SQL), AppDatabase.kt (la base elle-même),
│                      SeedData.kt (le catalogue pré-rempli).
│
├── repository/     → LA FAÇADE entre données et logique
│                      DemarcheRepository.kt (interface) +
│                      DemarcheRepositoryImpl.kt (implémentation avec Room).
│
├── viewmodel/      → LES VIEWMODELS : la logique et l'état de chaque écran
│                      CatalogueViewModel.kt, DetailViewModel.kt,
│                      SuiviViewModel.kt, DashboardViewModel.kt.
│                      AUCUN code Compose ici — uniquement StateFlow et règles métier.
│
├── view/           → LES VUES : tout ce qui dessine l'écran (Jetpack Compose)
│   ├── catalogue/CatalogueScreen.kt
│   ├── detail/DetailScreen.kt
│   ├── suivi/SuiviScreen.kt
│   ├── dashboard/DashboardScreen.kt
│   ├── components/EtatVide.kt   (composant réutilisé sur plusieurs écrans)
│   └── theme/Color.kt, Theme.kt, Type.kt
│
├── navigation/     → AppNavGraph.kt : relie les écrans entre eux
│                      (ni Modèle, ni Vue, ni ViewModel : c'est la "colle")
│
├── util/           → Fonctions utilitaires sans état (formaterGnf, formaterDate)
│
├── MainActivity.kt              → point de démarrage de l'app
└── DemarchesFacileApplication.kt → fabrique le Repository une seule fois
```

Le sens de circulation est **toujours le même**, quelle que soit la couche
où vous êtes en train de lire le code :

```
view/  --observe-->  viewmodel/  --appelle-->  repository/  --appelle-->  database/  --lit/écrit-->  model/
(écran)               (état + logique)          (façade)                  (DAO/Room)                 (données)
```

- **`model/`** répond à la question *"à quoi ressemble une donnée ?"*
- **`database/`** répond à la question *"comment je lis/écris cette donnée sur l'appareil ?"*
- **`repository/`** répond à la question *"quelle est LA porte d'entrée unique vers les données pour le reste de l'app ?"*
- **`viewmodel/`** répond à la question *"que doit afficher l'écran, et que se passe-t-il quand l'utilisateur clique ?"*
- **`view/`** répond à la question *"à quoi ressemble l'écran, pixel par pixel ?"*

### Comment retrouver un fichier en 2 secondes

Demandez-vous : **"Est-ce que ce fichier dessine quelque chose à l'écran ?"**
- **Oui** → dossier `view/`
- **Non, mais il prépare les données pour l'écran** → dossier `viewmodel/`
- **Non, il ne connaît même pas l'existence d'un écran** → dossiers
  `model/`, `database/` ou `repository/`

### Pourquoi StateFlow ?

`StateFlow` est un "flux" qui contient toujours une valeur, et qui prévient
automatiquement l'écran quand cette valeur change. Exemple concret dans
`CatalogueViewModel` : dès qu'on tape dans la barre de recherche, une
nouvelle requête Room part automatiquement, son résultat traverse le
ViewModel, et l'écran se redessine tout seul — sans qu'on ait écrit une
seule ligne de code pour "rafraîchir l'écran à la main".

---

## 7. Où sont les 4 écrans demandés ?

| Écran exigé | Fichier | Ce qu'il fait |
|---|---|---|
| Catalogue (liste, recherche, filtre) | `view/catalogue/CatalogueScreen.kt` | Liste des démarches, barre de recherche, filtres par catégorie |
| Détail | `view/detail/DetailScreen.kt` | Pièces à fournir, coût (GNF), délai, lieu, bouton "Démarrer le suivi" |
| Formulaire de suivi | `view/suivi/SuiviScreen.kt` | Cases à cocher des pièces, choix du statut, notes personnelles |
| Tableau de bord | `view/dashboard/DashboardScreen.kt` | Démarches en cours, progression globale, prochaine action |

## 8. Le modèle de données Room (pour le dossier technique)

```
Demarche (id, titre, categorie, coutGnf, delaiJours, lieu)
   │ 1---N
   ▼
Piece (id, demarcheId, libelle)

SuiviDemarche (id, demarcheId, dateDebut, statut, notes)
   │ 1---N
   ▼
PieceCochee (suiviId, pieceId, cochee)   [clé primaire composée]
```

Vous pouvez recopier ce schéma dans votre dossier technique (3 à 5 pages).

---

## 9. Pour aller plus loin (améliorations possibles, sans casser la base)

Le cahier des charges autorise à améliorer la base sans la casser. Quelques
idées simples à ajouter une fois que tout fonctionne :
- Un écran "Ajouter une démarche personnalisée" (formulaire + `DemarcheDao.inserer`).
- Une notification locale de rappel pour les démarches "en préparation" depuis
  longtemps.
- Un bouton "Partager mon suivi" (texte récapitulatif via `Intent.ACTION_SEND`).
- Un mode sombre déjà préparé dans `ui/theme/Theme.kt` (`ColorSchemeSombre`) —
  testez-le en changeant le thème système du téléphone.
- Des tests unitaires simples sur les fonctions de `Formatters.kt` (aucune
  dépendance Android, donc faciles à tester).

## 10. Répartition suggérée entre les 4 membres du groupe

D'après les rôles définis par ODC :

- **Responsable données** → dossiers `model/` et `database/` : entités, DAO,
  `SeedData.kt`. Peut ajouter de nouvelles démarches au catalogue, ou une
  migration Room si le schéma change plus tard.
- **Responsable interface** → dossier `view/` (tous les `xxxScreen.kt`,
  `view/theme/`, `view/components/`) et `navigation/`. Peut retravailler les
  couleurs, ajouter des icônes, améliorer les états vides.
- **Responsable logique et qualité** → dossiers `viewmodel/` et `repository/`.
  Peut ajouter des validations (ex : empêcher de démarrer un suivi deux fois),
  écrire le dossier technique.
- **Chef de projet / intégration** → dépôt Git, fusion des branches (chaque
  membre peut travailler sur une branche par écran puisque les couches sont
  bien séparées), préparation de l'APK final (`Build > Generate Signed
  Bundle / APK` dans Android Studio).

Comme les couches sont dans des dossiers séparés (`model/`, `database/`,
`repository/`, `viewmodel/`, `view/`), vous pouvez vraiment travailler en
parallèle sans trop de conflits Git : la personne sur `database/` ne touche
presque jamais aux mêmes lignes que la personne sur `view/`.

---

## 11. En cas de problème

- **Erreur Room au moment de la compilation ("cannot find symbol
  AppDatabase_Impl")** : vérifiez que le plugin `ksp` est bien ajouté (étape 2)
  et relancez **Build > Rebuild Project**.
- **"Unresolved reference: viewModel"** : vérifiez que la dépendance
  `androidx.lifecycle:lifecycle-viewmodel-compose` a bien été ajoutée.
- **Le catalogue reste vide** : désinstallez complètement l'application de
  l'émulateur/téléphone puis relancez (le pré-remplissage ne se fait qu'à la
  toute première création de la base).
- **Package name différent** : si vous avez choisi un autre nom de package
  qu'`com.odc.demarchesfacile`, remplacez ce texte par le vôtre en haut de
  CHAQUE fichier `.kt` fourni (ligne `package ...`), et déplacez les fichiers
  dans le dossier correspondant à votre package.
