package com.odc.demarchesfacile.view.suivi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.odc.demarchesfacile.model.StatutSuivi
import com.odc.demarchesfacile.viewmodel.SuiviViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuiviScreen(
    viewModel: SuiviViewModel,
    onRetour: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Quand l'utilisateur quitte l'écran (retour arrière, navigation), on
    // enregistre les notes tapées. DisposableEffect s'exécute quand le
    // composable disparaît de l'écran.
    DisposableEffect(Unit) {
        onDispose { viewModel.enregistrerNotes() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.titreDemarche.ifBlank { "Mon suivi" }) },
                navigationIcon = {
                    IconButton(onClick = onRetour) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { paddingInterne ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterne)
                .padding(16.dp)
        ) {
            Text(
                text = "Progression : ${uiState.progressionPourcentage}% (${uiState.nombreCoche}/${uiState.nombreTotal} pièces)",
                style = MaterialTheme.typography.bodyLarge
            )
            LinearProgressIndicator(
                progress = { uiState.progressionPourcentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Text(text = "Statut de la démarche", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                StatutSuivi.entries.forEach { statut ->
                    val statutActuel = uiState.suivi?.let { StatutSuivi.fromDb(it.statut) }
                    FilterChip(
                        modifier = Modifier.padding(end = 8.dp),
                        selected = statutActuel == statut,
                        onClick = { viewModel.onStatutChoisi(statut) },
                        label = { Text(statut.libelle) }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Pièces à réunir", style = MaterialTheme.typography.titleMedium)

            if (uiState.pieces.isEmpty()) {
                Text(
                    text = "Aucune donnée pour le moment",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(uiState.pieces, key = { it.id }) { piece ->
                        val cochee = uiState.estCochee(piece.id)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = cochee,
                                onCheckedChange = { nouvelEtat ->
                                    viewModel.basculerPiece(piece.id, nouvelEtat)
                                }
                            )
                            Text(
                                text = piece.libelle,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Notes personnelles", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = uiState.notesSaisies,
                onValueChange = viewModel::onNotesChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex : dossier déposé le matin, agent m'a dit de revenir jeudi...") },
                minLines = 3
            )
        }
    }
}
