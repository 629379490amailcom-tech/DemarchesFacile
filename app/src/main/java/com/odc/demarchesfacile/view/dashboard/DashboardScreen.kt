package com.odc.demarchesfacile.view.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.view.components.EtatVide
import com.odc.demarchesfacile.viewmodel.DashboardViewModel
import com.odc.demarchesfacile.viewmodel.SuiviAffichable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onSuiviClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tableau de bord") }) }
    ) { paddingInterne ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingInterne)) {

            Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Progression globale", style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(
                        progress = { uiState.progressionGlobalePourcentage / 100f },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    Text(
                        text = "${uiState.progressionGlobalePourcentage}% des pièces réunies au total",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = uiState.prochaineAction,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Text(
                text = "Démarches en cours",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            if (!uiState.chargementEnCours && uiState.suivisEnCours.isEmpty()) {
                EtatVide(message = "Aucune donnée pour le moment.\nDémarrez le suivi d'une démarche depuis le catalogue.")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.suivisEnCours, key = { it.suivi.id }) { ligne ->
                        CarteSuiviEnCours(ligne = ligne, onClick = { onSuiviClick(ligne.suivi.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CarteSuiviEnCours(ligne: SuiviAffichable, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = ligne.demarche?.titre ?: "Démarche",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = StatutSuivi.fromDb(ligne.suivi.statut).libelle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            LinearProgressIndicator(
                progress = { ligne.progressionPourcentage / 100f },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            Text(
                text = "${ligne.piecesFaites}/${ligne.piecesTotal} pièces réunies (${ligne.progressionPourcentage}%)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
