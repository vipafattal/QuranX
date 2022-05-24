package com.abedfattal.quranx.ui.library.ui.read.processor.tajweed

import androidx.annotation.IdRes
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors.Companion.idgham
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors.Companion.mimm
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors.Companion.mudud
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors.Companion.noon
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors.Companion.qalaqah
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ReadLibrary.tempPreferences

class TajweedColorsHelper(isDarkThemeOn: Boolean) {
    var tajweedColors = MetaColors(defaultColor = if (isDarkThemeOn) "#FFFFFF" else "#000000")
        private set

    init {
        tajweedColors = getMududColors()
        tajweedColors = getNoonColors()
        tajweedColors = getMimmColors()
        tajweedColors = getQalaqahColors()
        tajweedColors = getIdghamColors()
    }

    companion object {
        private const val mududKey = "mudud-rules"
        private const val noonKey = "noon-rules"
        private const val mimmKey = "mimm-rules"
        private const val qalaqahKey = "qalaqah-rules"
        private const val idghamKey = "idgham-rules"
    }

    private var mudud: Boolean
        get() = tempPreferences.getBoolean(mududKey, true)
        set(value) = tempPreferences.put(mududKey, value)

    private var noon: Boolean
        get() = tempPreferences.getBoolean(noonKey, true)
        set(value) = tempPreferences.put(noonKey, value)

    private var mimm: Boolean
        get() = tempPreferences.getBoolean(mimmKey, true)
        set(value) = tempPreferences.put(mimmKey, value)

    private var qalaqah: Boolean
        get() = tempPreferences.getBoolean(qalaqahKey, true)
        set(value) = tempPreferences.put(qalaqahKey, value)

    private var idgham: Boolean
        get() = tempPreferences.getBoolean(idghamKey, true)
        set(value) = tempPreferences.put(idghamKey, value)


    fun getCheckBoxState(@IdRes checkBoxId: Int): Boolean {
        return when (checkBoxId) {
            R.id.mududCheckBox -> mudud
            R.id.noonCheckBox -> noon
            R.id.mimmCheckBox -> mimm
            R.id.qalaqahCheckBox -> qalaqah
            R.id.idghamCheckBox -> idgham
            else -> throw IllegalArgumentException("Unknown checkbox id")
        }
    }

    fun updateTajweedColor(checkBoxRuleId: Int) {
        tajweedColors = when (checkBoxRuleId) {
            R.id.mududCheckBox -> {
                mudud = !mudud
                getMududColors()
            }
            R.id.noonCheckBox -> {
                noon = !noon
                getNoonColors()
            }
            R.id.mimmCheckBox -> {
                mimm = !mimm
                getMimmColors()
            }
            R.id.qalaqahCheckBox -> {
                qalaqah = !qalaqah
                getQalaqahColors()

            }
            R.id.idghamCheckBox -> {
                idgham = !idgham
                getIdghamColors()
            }
            else -> throw IllegalArgumentException("Unknown checkbox id")
        }
    }

    private fun getMududColors() =
        if (mudud) tajweedColors.mudud()
        else
            tajweedColors.mudud(
                tajweedColors.defaultColor,
                tajweedColors.defaultColor,
                tajweedColors.defaultColor,
                tajweedColors.defaultColor,
                tajweedColors.defaultColor
            )

    private fun getNoonColors() =
        if (noon) tajweedColors.noon()
        else
            tajweedColors.noon(
                tajweedColors.defaultColor,
                tajweedColors.defaultColor,
                tajweedColors.defaultColor,
                tajweedColors.defaultColor
            )


    private fun getMimmColors() =
        if (mimm) tajweedColors.mimm()
        else
            tajweedColors.mimm(
                tajweedColors.defaultColor,
                tajweedColors.defaultColor
            )

    private fun getQalaqahColors() =
        if (qalaqah) tajweedColors.qalaqah()
        else
            tajweedColors.qalaqah(
                tajweedColors.defaultColor
            )

    private fun getIdghamColors() =
        if (idgham) tajweedColors.idgham()
        else
            tajweedColors.idgham(
                tajweedColors.defaultColor,
                tajweedColors.defaultColor
            )

}