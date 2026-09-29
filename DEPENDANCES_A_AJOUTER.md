# Dépendances à ajouter au projet

Après avoir créé votre projet dans Android Studio (voir README.md, étape 1),
il faut ajouter Room, Navigation et les icônes Material. Voici exactement quoi
faire.

## 1. Fichier `build.gradle.kts` du PROJET (celui à la racine, pas celui du dossier `app`)

Dans le bloc `plugins { ... }`, ajoutez la ligne KSP (sert à générer le code de Room) :

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
}
```

> Si votre projet utilise une version de Kotlin différente (regardez dans
> `gradle/libs.versions.toml`, ligne `kotlin = "..."`), remplacez `2.0.21` par
> cette version exacte. Le suffixe `-1.0.28` peut rester tel quel dans la
> plupart des cas ; si Android Studio affiche une erreur de version au sync,
> il vous proposera généralement la bonne valeur automatiquement.

## 2. Fichier `app/build.gradle.kts`

En haut du fichier, dans le bloc `plugins { ... }`, ajoutez :

```kotlin
plugins {
    // ... plugins déjà présents ...
    id("com.google.devtools.ksp")
}
```

Dans le bloc `dependencies { ... }`, ajoutez ces lignes (elles peuvent
cohabiter avec ce qui existe déjà) :

```kotlin
dependencies {
    // ... dépendances déjà présentes (compose, etc.) ...

    // Room (base de données locale)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Navigation entre les écrans Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // ViewModel + StateFlow dans Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Icônes utilisées dans la barre de navigation (Home, ArrowBack, CheckCircle)
    implementation("androidx.compose.material:material-icons-core")
}
```

> Remarque : `material-icons-core` n'a pas besoin de numéro de version car il
> est géré par le BOM Compose déjà présent dans le projet
> (ligne `implementation(platform(libs.androidx.compose.bom))`).

## 3. Synchroniser

Cliquez sur **"Sync Now"** en haut de l'écran quand Android Studio vous le
propose (ou menu File > Sync Project with Gradle Files).

Si une popup de version Kotlin/KSP incompatible apparaît, laissez Android
Studio appliquer sa suggestion automatique de correction.
