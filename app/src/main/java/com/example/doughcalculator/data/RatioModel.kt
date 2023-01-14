package com.example.doughcalculator.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatioModel: ViewModel() {

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

}
