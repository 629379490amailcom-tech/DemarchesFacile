package com.odc.demarchesfacile.view.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.odc.demarchesfacile.view.components.EtatVide
import com.odc.demarchesfacile.util.formaterGnf
import com.odc.demarchesfacile.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onRetour: () -> Unit,
    onVoirSuivi: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détail de la démarche") },
                navigationIcon = {
                    IconButton(onClick = onRetour) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { paddingInterne ->
        when {
            uiState.chargementEnCours -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingInterne),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            uiState.demarcheAvecPieces == null -> {
                Box(modifier = Modifier.padding(paddingInterne)) {
                    EtatVide(message = "Cette démarche est introuvable")
                }
            }

            else -> {
                val demarcheAvecPieces = uiState.demarcheAvecPieces!!
                val demarche = demarcheAvecPieces.demarche

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingInterne)
                        .padding(16.dp)
                ) {
                    Text(text = demarche.titre, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = demarche.categorie,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    LigneInfo(libelle = "Coût estimé", valeur = formaterGnf(demarche.coutGnf))
                    LigneInfo(libelle = "Délai indicatif", valeur = "${demarche.delaiJours} jours")
                    LigneInfo(libelle = "Lieu de dépôt", valeur = demarche.lieu)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    Text(text = "Pièces à fournir", style = MaterialTheme.typography.titleMedium)

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(demarcheAvecPieces.pieces) { piece ->
                            Text(
                                text = "• ${piece.libelle}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val suiviId = uiState.suiviExistantId
                            if (suiviId != null) {
                                onVoirSuivi(suiviId)
                            } else {
                                viewModel.demarrerSuivi(onSuiviCree = onVoirSuivi)
                            }
                        }
                    ) {
                        Text(
                            if (uiState.suiviExistantId != null) "Voir mon suivi"
                            else "Démarrer le suivi"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LigneInfo(libelle: String, valeur: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = libelle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        Text(text = valeur, style = MaterialTheme.typography.bodyLarge)
    }
}

