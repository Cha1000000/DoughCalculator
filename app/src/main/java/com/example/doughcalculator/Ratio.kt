package com.example.doughcalculator

import androidx.databinding.ObservableField

data class Ratio (
    var flourGram: String? = null,
    var flourGramCorrection: String? = null,

    var waterGram: String? = null,
    var waterPercent: ObservableField<String> = ObservableField<String>(),
    var waterGramCorrection: ObservableField<String> = ObservableField<String>(),

    var saltGram: String? = null,
    var saltPercent: ObservableField<String> = ObservableField<String>(),
    var saltGramCorrection: ObservableField<String> = ObservableField<String>(),

    var sugarGram: String? = null,
    var sugarPercent: ObservableField<String> = ObservableField<String>(),
    var sugarGramCorrection: ObservableField<String> = ObservableField<String>(),

    var butterGram: String? = null,
    var butterPercent: ObservableField<String> = ObservableField<String>(),
    var butterGramCorrection: ObservableField<String> = ObservableField<String>(),
)
