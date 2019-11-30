package fr.br.calc.utils

import fr.br.calc.model.OperatorElement
import fr.br.calc.services.RpnListComputer
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


/**
 * Utils functions
 */
object Utils {

    /**
     * Simplify a query string
     */
    fun simplifyQuery( query : String ) : String{

        // Avoid complexity by replacing contigous operators
        return query.replace(" ","")
                .replace("--","+")
                .replace("++","+")
                .replace("+-","-")
                .replace("-+","-")
                .replace("*+","*")

    }

    /**
     * Convert double to string and suppress useless decimal
     * Round to one decimal
     */
    fun doubleToString(d: Double): String {

        val otherSymbols = DecimalFormatSymbols(Locale.FRANCE)
        otherSymbols.decimalSeparator = '.'

        val dec = DecimalFormat("####.#", otherSymbols)
        dec.roundingMode = RoundingMode.CEILING
        return dec.format( d )
    }
 
}