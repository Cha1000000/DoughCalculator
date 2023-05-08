package com.example.doughcalculator.data

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class RatioModel : ViewModel(), BaseRatioModel, Serializable {

    override var recipeId: Long = 0L
    override var title = MutableLiveData("")
    override var description = MutableLiveData("")
    override var isFavorite: Boolean = false
    override var hasUnsavedDate: Boolean = false

    // Grams
    override var flourGram: Short? = null
        set(value) {
            field = value
            flourGramBindingVariable.notifyChange()
        }
    var flourGramBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                flourGram = value?.toShortOrNull()
            }

            override fun get(): String {
                return flourGram?.toString() ?: ""
            }
        }

    override var waterGram: Short? = null
        set(value) {
            field = value
            waterGramBindingVariable.notifyChange()
        }
    var waterGramBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                waterGram = value?.toShortOrNull()
            }

            override fun get(): String {
                return waterGram?.toString() ?: ""
            }
        }

    override var saltGram: Short? = null
        set(value) {
            field = value
            saltGramBindingVariable.notifyChange()
        }
    var saltGramBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                saltGram = value?.toShortOrNull()
            }

            override fun get(): String {
                return saltGram?.toString() ?: ""
            }
        }

    override var sugarGram: Short? = null
        set(value) {
            field = value
            sugarGramBindingVariable.notifyChange()
        }
    var sugarGramBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                sugarGram = value?.toShortOrNull()
            }

            override fun get(): String {
                return sugarGram?.toString() ?: ""
            }
        }

    override var butterGram: Short? = null
        set(value) {
            field = value
            butterGramBindingVariable.notifyChange()
        }
    var butterGramBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                butterGram = value?.toShortOrNull()
            }

            override fun get(): String {
                return butterGram?.toString() ?: ""
            }
        }

    // Gram corrections
    override var flourGramCorrection: Short? = null
        set(value) {
            field = value
            flourGramCorrectionBindingVariable.notifyChange()
        }
    var flourGramCorrectionBindingVariable =
        object : ObservableField<String>("") {
            override fun set(value: String?) {
                super.set(value)
                flourGramCorrection = value?.toShortOrNull() ?: flourGramCorrection
            }

            override fun get(): String {
                return flourGramCorrection?.toString() ?: ""
            }
        }

    override var waterGramCorrection =
        object : ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                waterGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var waterGramCorrectionBindingVariable = ObservableField("")

    override var saltGramCorrection =
        object : ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                saltGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var saltGramCorrectionBindingVariable = ObservableField("")

    override var sugarGramCorrection =
        object : ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                sugarGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var sugarGramCorrectionBindingVariable = ObservableField("")

    override var butterGramCorrection =
        object : ObservableField<Short?>() {
            override fun set(value: Short?) {
                super.set(value)
                butterGramCorrectionBindingVariable.set(value?.toString() ?: "")
            }
        }
    var butterGramCorrectionBindingVariable = ObservableField("")

    // Percents
    override var waterPercent =
        object : ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                waterPercentBindingVariable.set(String.format("%.0f", value))
            }
        }
    var waterPercentBindingVariable = ObservableField("")

    override var saltPercent =
        object : ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                saltPercentBindingVariable.set(String.format("%.0f", value))
            }
        }
    var saltPercentBindingVariable = ObservableField("")

    override var sugarPercent =
        object : ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                val strValue =
                    if (value == null || value < 0.1) "" else String.format("%.0f", value)
                sugarPercentBindingVariable.set(strValue)
            }
        }
    var sugarPercentBindingVariable = ObservableField("")

    override var butterPercent =
        object : ObservableField<Double?>() {
            override fun set(value: Double?) {
                super.set(value)
                val strValue =
                    if (value == null || value < 0.1) "" else String.format("%.0f", value)
                butterPercentBindingVariable.set(strValue)
            }
        }
    var butterPercentBindingVariable = ObservableField("")


    override fun isUpdate(): Boolean = recipeId > 0L

    override fun clone(): BaseRatioModel = this.memberwiseClone()

    private fun BaseRatioModel.memberwiseClone(): BaseRatioModel {
        val clone = this::class.java.newInstance()
        this::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            field.set(clone, field.get(this))
        }
        return clone as BaseRatioModel
    }

    /*
    constructor(parcel: Parcel) : this() {
        recipeId = parcel.readLong()
        isFavorite = parcel.readByte() != 0.toByte()
        hasUnsavedDate = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(recipeId)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeByte(if (hasUnsavedDate) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RatioModel> {
        override fun createFromParcel(parcel: Parcel): RatioModel {
            return RatioModel(parcel)
        }

        override fun newArray(size: Int): Array<RatioModel?> {
            return arrayOfNulls(size)
        }
    }
     */
}
