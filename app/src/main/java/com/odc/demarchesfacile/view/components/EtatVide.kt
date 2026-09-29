package com.odc.demarchesfacile.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Composant de VUE réutilisé sur plusieurs écrans (Catalogue, Détail,
 * Tableau de bord) pour l'exigence du cahier des charges : afficher un
 * message clair quand une liste est vide ("Aucune donnée pour le moment").
 *
 * Il vit dans view/components/ car il ne dépend d'aucun écran précis.
 */
@Composable
fun EtatVide(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
