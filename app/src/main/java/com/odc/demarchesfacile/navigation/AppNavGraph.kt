package com.odc.demarchesfacile.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.odc.demarchesfacile.repository.DemarcheRepository
import com.odc.demarchesfacile.view.catalogue.CatalogueScreen
import com.odc.demarchesfacile.viewmodel.CatalogueViewModel
import com.odc.demarchesfacile.view.dashboard.DashboardScreen
import com.odc.demarchesfacile.viewmodel.DashboardViewModel
import com.odc.demarchesfacile.view.detail.DetailScreen
import com.odc.demarchesfacile.viewmodel.DetailViewModel
import com.odc.demarchesfacile.view.suivi.SuiviScreen
import com.odc.demarchesfacile.viewmodel.SuiviViewModel
import com.odc.demarchesfacile.view.welcome.WelcomeScreen

/**
 * Toutes les "adresses" (routes) de l'application sont rassemblées ici,
 * pour éviter d'écrire des chaînes de caractères un peu partout dans le code.
 */
object Destinations {
    const val WELCOME = "welcome"
    const val CATALOGUE = "catalogue"
    const val DASHBOARD = "dashboard"
    const val DETAIL = "detail/{demarcheId}"
    const val SUIVI = "suivi/{suiviId}"

    fun detail(demarcheId: Long) = "detail/$demarcheId"
    fun suivi(suiviId: Long) = "suivi/$suiviId"
}

// Les deux onglets principaux, affichés dans la barre de navigation du bas
private data class OngletPrincipal(val route: String, val label: String, val icone: androidx.compose.ui.graphics.vector.ImageVector)

private val onglets = listOf(
    OngletPrincipal(Destinations.CATALOGUE, "Catalogue", Icons.Filled.Home),
    OngletPrincipal(Destinations.DASHBOARD, "Tableau de bord", Icons.Filled.CheckCircle)
)

@Composable
fun AppNavGraph(repository: DemarcheRepository) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BarreDeNavigationBasse(navController) }
    ) { paddingInterne ->
        NavHost(
            navController = navController,
            startDestination = Destinations.WELCOME,
            modifier = androidx.compose.ui.Modifier.padding(paddingInterne)
        ) {
            composable(Destinations.WELCOME) {
                WelcomeScreen(
                    onCommencerClick = {
                        navController.navigate(Destinations.CATALOGUE) {
                            popUpTo(Destinations.WELCOME) { inclusive = true }
                        }
                    }
                )
            }

            composable(Destinations.CATALOGUE) {
                val viewModel: CatalogueViewModel = viewModel(
                    factory = CatalogueViewModel.Factory(repository)
                )
                CatalogueScreen(
                    viewModel = viewModel,
                    onDemarcheClick = { demarcheId ->
                        navController.navigate(Destinations.detail(demarcheId))
                    }
                )
            }

            composable(Destinations.DASHBOARD) {
                val viewModel: DashboardViewModel = viewModel(
                    factory = DashboardViewModel.Factory(repository)
                )
                DashboardScreen(
                    viewModel = viewModel,
                    onSuiviClick = { suiviId ->
                        navController.navigate(Destinations.suivi(suiviId))
                    }
                )
            }

            composable(
                route = Destinations.DETAIL,
                arguments = listOf(navArgument("demarcheId") { type = NavType.LongType })
            ) { backStackEntry ->
                val demarcheId = backStackEntry.arguments?.getLong("demarcheId") ?: 0L
                val viewModel: DetailViewModel = viewModel(
                    factory = DetailViewModel.Factory(repository, demarcheId)
                )
                DetailScreen(
                    viewModel = viewModel,
                    onRetour = { navController.popBackStack() },
                    onVoirSuivi = { suiviId ->
                        navController.navigate(Destinations.suivi(suiviId))
                    }
                )
            }

            composable(
                route = Destinations.SUIVI,
                arguments = listOf(navArgument("suiviId") { type = NavType.LongType })
            ) { backStackEntry ->
                val suiviId = backStackEntry.arguments?.getLong("suiviId") ?: 0L
                val viewModel: SuiviViewModel = viewModel(
                    factory = SuiviViewModel.Factory(repository, suiviId)
                )
                SuiviScreen(
                    viewModel = viewModel,
                    onRetour = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun BarreDeNavigationBasse(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destinationActuelle = navBackStackEntry?.destination

    // On n'affiche la barre du bas que si on est sur un des 2 onglets principaux
    val doitAfficher = onglets.any { onglet ->
        destinationActuelle?.hierarchy?.any { it.route == onglet.route } == true
    }

    if (doitAfficher) {
        NavigationBar {
            onglets.forEach { onglet ->
                val selectionne = destinationActuelle?.hierarchy?.any { it.route == onglet.route } == true
                NavigationBarItem(
                    selected = selectionne,
                    onClick = {
                        navController.navigate(onglet.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(onglet.icone, contentDescription = onglet.label) },
                    label = { Text(onglet.label) }
                )
            }
        }
    }
}
