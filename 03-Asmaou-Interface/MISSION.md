# Votre mission — Asmaou Diallo (Responsable interface, 1/2)

## Ce que contient ce dossier
Uniquement **vos fichiers** :
```
app/src/main/java/com/odc/demarchesfacile/view/welcome/    (WelcomeScreen.kt)
app/src/main/java/com/odc/demarchesfacile/view/catalogue/  (CatalogueScreen.kt)
app/src/main/java/com/odc/demarchesfacile/view/detail/     (DetailScreen.kt)
app/src/main/java/com/odc/demarchesfacile/view/theme/      (Color, Theme, Type)
```
L'écran d'accueil, le catalogue, le détail d'une démarche, et les couleurs /
la typographie de toute l'application.

⚠️ Ce dossier seul ne compile pas : il vous manque tout le reste du projet.
C'est normal, voir "Comment procéder" ci-dessous.

## Comment procéder

1. Attendez que Mamadou vous confirme que le dépôt GitHub existe et qu'il
   vous a invitée.
2. Clonez le dépôt complet :
   ```
   git clone https://github.com/VOTRE-NOM/DemarchesFacile.git
   cd DemarchesFacile
   git checkout -b feature-interface-accueil-catalogue
   ```
3. Copiez les fichiers de CE dossier par-dessus ceux du dépôt cloné (mêmes
   emplacements exacts).
4. Ouvrez le projet dans Android Studio et faites votre tâche (ci-dessous).
5. Vérifiez que l'application compile et s'affiche toujours (bouton ▶).
6. Committez et poussez :
   ```
   git add .
   git commit -m "Amélioration du texte d'accueil et des couleurs"
   git push -u origin feature-interface-accueil-catalogue
   ```
7. Sur GitHub, ouvrez une **Pull Request** vers `main`.

## Votre tâche concrète

Choisissez au moins une de ces deux améliorations :

**a) Le texte d'accroche de l'écran d'accueil** — dans
`view/welcome/WelcomeScreen.kt`, cherchez le texte affiché sous le logo et
proposez une phrase plus engageante (ex. "Vos démarches administratives,
simplifiées, où que vous soyez").

**b) Une couleur** — dans `view/theme/Color.kt`, essayez une nuance
différente pour `OrangePrincipal` ou `VertSucces`, puis relancez
l'application pour voir le rendu sur les écrans Catalogue et Détail.

## Rappel pour l'oral
Vous devrez présenter les écrans Accueil, Catalogue et Détail : montrez la
recherche, les filtres par catégorie, et le bouton qui devient "Voir mon
suivi" une fois une démarche démarrée.
