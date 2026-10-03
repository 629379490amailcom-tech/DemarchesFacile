package com.odc.demarchesfacile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.odc.demarchesfacile.navigation.AppNavGraph
import com.odc.demarchesfacile.view.theme.DemarchesFacileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // On récupère le Repository unique créé dans DemarchesFacileApplication
        // (voir ce fichier pour comprendre le "pourquoi").
        val repository = (application as DemarchesFacileApplication).repository

        setContent {
            DemarchesFacileTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(repository = repository)
                }
            }
        }
    }
}
