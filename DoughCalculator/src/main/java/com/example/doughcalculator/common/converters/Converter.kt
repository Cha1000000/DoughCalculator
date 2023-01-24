@file:JvmName("Converter")
package com.example.doughcalculator.common.converters

import androidx.databinding.InverseMethod

object Converter {

    @InverseMethod("stringToShort")
    fun shortToString(value: Short?): String? {
        if (value == null)
            return null
        return value.toString()
    }

    fun stringToShort(value: String?): Short? {
        if (value.isNullOrEmpty())
            return null
        return value.toShortOrNull()
    }

    @InverseMethod("stringToDouble")
    fun doubleToString(value: Double?): String? {

        if (value == null) {
            return null
        }
        return value.toString()
    }

    fun stringToDouble(value: String?): Double? {

        if (value == null) {
            return null
        }
        return value.toDoubleOrNull()
    }
}