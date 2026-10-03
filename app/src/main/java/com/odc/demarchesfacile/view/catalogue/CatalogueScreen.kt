package com.odc.demarchesfacile.view.catalogue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.odc.demarchesfacile.R
import com.odc.demarchesfacile.model.Demarche
import com.odc.demarchesfacile.util.formaterGnf
import com.odc.demarchesfacile.view.components.EtatVide
import com.odc.demarchesfacile.viewmodel.CatalogueViewModel

/**
 * Écran "Catalogue" : le point d'entrée de l'application.
 * Il n'a AUCUNE logique métier : il se contente d'afficher ce que le
 * CatalogueViewModel lui donne, et de transmettre les clics/saisies au ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen(
    viewModel: CatalogueViewModel,
    onDemarcheClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(36.dp)
                        )
                        Text("Démarches Facile")
                    }
                }
            )
        }
    ) { paddingInterne ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingInterne)) {

            OutlinedTextField(
                value = uiState.motCle,
                onValueChange = viewModel::onMotCleChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Rechercher une démarche...") },
                singleLine = true
            )

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.categoriesDisponibles) { categorie ->
                    FilterChip(
                        selected = uiState.categorieSelectionnee == categorie,
                        onClick = { viewModel.onCategorieChange(categorie) },
                        label = { Text(categorie) }
                    )
                }
            }

            if (uiState.demarches.isEmpty()) {
                EtatVide(message = "Aucune donnée pour le moment")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.demarches, key = { it.id }) { demarche ->
                        CarteDemarche(demarche = demarche, onClick = { onDemarcheClick(demarche.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CarteDemarche(demarche: Demarche, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = demarche.titre, style = MaterialTheme.typography.titleMedium)
            Text(
                text = demarche.categorie,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${formaterGnf(demarche.coutGnf)} · ${demarche.delaiJours} jours",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

