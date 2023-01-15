package com.example.doughcalculator.data

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class RatioModel : ViewModel() {
    /*
        private val _flourGram: MutableLiveData<Short?> = MutableLiveData(null)
        private val _waterGram: MutableLiveData<Short?> = MutableLiveData(null)
        private val _saltGram: MutableLiveData<Short?> = MutableLiveData(null)
        private val _sugarGram: MutableLiveData<Short?> = MutableLiveData(null)
        private val _butterGram: MutableLiveData<Short?> = MutableLiveData(null)

        private val _flourGramCorrection: MutableLiveData<Short?> = MutableLiveData(null)
        private val _waterGramCorrection: MutableLiveData<Short?> = MutableLiveData(null)
        private val _saltGramCorrection: MutableLiveData<Short?> = MutableLiveData(null)
        private val _sugarGramCorrection: MutableLiveData<Short?> = MutableLiveData(null)
        private val _butterGramCorrection: MutableLiveData<Short?> = MutableLiveData(null)

        private val _waterPercent: MutableLiveData<Double?> = MutableLiveData(null)
        private val _saltPercent: MutableLiveData<Double?> = MutableLiveData(null)
        private val _sugarPercent: MutableLiveData<Double?> = MutableLiveData(null)
        private val _butterPercent: MutableLiveData<Double?> = MutableLiveData(null)

        val flourGram: MutableLiveData<Short?> = _flourGram
        val waterGram: MutableLiveData<Short?> = _waterGram
        val saltGram: MutableLiveData<Short?> = _saltGram
        val sugarGram: MutableLiveData<Short?> = _sugarGram
        val butterGram: MutableLiveData<Short?> = _butterGram

        val flourGramCorrection: MutableLiveData<Short?> = _flourGramCorrection
        val waterGramCorrection: MutableLiveData<Short?> = _waterGramCorrection
        val saltGramCorrection: MutableLiveData<Short?> = _saltGramCorrection
        val sugarGramCorrection: MutableLiveData<Short?> = _sugarGramCorrection
        val butterGramCorrection: MutableLiveData<Short?> = _butterGramCorrection

        var waterPercent: MutableLiveData<Double?> = _waterPercent
        val saltPercent: MutableLiveData<Double?> = _saltPercent
        val sugarPercent: MutableLiveData<Double?> = _sugarPercent
        val butterPercent: MutableLiveData<Double?> = _butterPercent
    */

    // Grams
    var flourGram: Short? = null
    var flourGramBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                flourGram = value?.toShortOrNull() ?: flourGram
            }
        }

    var waterGram: Short? = null
    var waterGramBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                waterGram = value?.toShortOrNull() ?: waterGram
            }
        }

    var saltGram: Short? = null
    var saltGramBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                saltGram = value?.toShortOrNull() ?: saltGram
            }
        }

    var sugarGram: Short? = null
    var sugarGramBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                sugarGram = value?.toShortOrNull() ?: sugarGram
            }
        }

    var butterGram: Short? = null
    var butterGramBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                butterGram = value?.toShortOrNull() ?: butterGram
            }
        }

    // Gram corrections
    var flourGramCorrection: Short? = null
    var flourGramCorrectionBindingVariable =
        object: ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                flourGramCorrection = value?.toShortOrNull() ?: flourGramCorrection
            }
        }

    var waterGramCorrection=
        object: ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                waterGramCorrectionBindingVariable.set(value.toString())
            }
        }
    var waterGramCorrectionBindingVariable = ObservableField("")

    var saltGramCorrection=
        object: ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                saltGramCorrectionBindingVariable.set(value.toString())
            }
        }
    var saltGramCorrectionBindingVariable = ObservableField("")

    var sugarGramCorrection=
        object: ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                sugarGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var sugarGramCorrectionBindingVariable = ObservableField("")

    var butterGramCorrection=
        object: ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                butterGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var butterGramCorrectionBindingVariable = ObservableField("")

    // Percents
    var waterPercent =
        object: ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                waterPercentBindingVariable.set(value.toString())
            }
        }
    var waterPercentBindingVariable = ObservableField("")

    var saltPercent=
        object: ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                saltPercentBindingVariable.set(value.toString())
            }
        }
    var saltPercentBindingVariable = ObservableField("")

    var sugarPercent=
        object: ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                sugarPercentBindingVariable.set(value?.toString() ?: "")
            }
        }
    var sugarPercentBindingVariable = ObservableField("")

    var butterPercent=
        object: ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                butterPercentBindingVariable.set(value?.toString() ?: "")
            }
        }
    var butterPercentBindingVariable = ObservableField("")

}
