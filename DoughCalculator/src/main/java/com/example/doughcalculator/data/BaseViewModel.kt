package com.example.doughcalculator.data

import androidx.databinding.ObservableField

interface BaseViewModel {
    var flourGram: Short?
    var waterGram: Short?
    var saltGram: Short?
    var sugarGram: Short?
    var butterGram: Short?

    var flourGramCorrection: Short?
    var waterGramCorrection: ObservableField<Short?>
    var saltGramCorrection: ObservableField<Short?>
    var sugarGramCorrection: ObservableField<Short?>
    var butterGramCorrection: ObservableField<Short?>

    var waterPercent: ObservableField<Double?>
    var saltPercent: ObservableField<Double?>
    var sugarPercent: ObservableField<Double?>
    var butterPercent: ObservableField<Double?>
}