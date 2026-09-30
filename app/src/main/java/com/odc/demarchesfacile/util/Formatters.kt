package com.odc.demarchesfacile.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Formate un montant en GNF avec séparateur de milliers (espace),
 * exemple : 150000 -> "150 000 GNF" (exigence du cahier des charges).
 */
fun formaterGnf(montant: Long): String {
    val texte = montant.toString()
    val avecEspaces = StringBuilder()
    for ((index, caractere) in texte.reversed().withIndex()) {
        if (index != 0 && index % 3 == 0) {
            avecEspaces.append(' ')
        }
        avecEspaces.append(caractere)
    }
    return avecEspaces.reverse().toString() + " GNF"
}

/** Formate une date (millisecondes) en "jj/MM/aaaa" pour l'affichage. */
fun formaterDate(timestampMillis: Long): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    return format.format(timestampMillis)
}
