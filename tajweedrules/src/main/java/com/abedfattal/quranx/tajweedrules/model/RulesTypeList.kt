package com.abedfattal.quranx.tajweedrules.model

import com.abedfattal.quranx.tajweedparser.model.MetaColors
import com.abedfattal.quranx.tajweedrules.R


data class RulesTypeList internal constructor(private val meta: MetaColors) {
    @JvmField
    val rulesMap = mapOf(
        'h' to TajweedRuleType(R.string.general_rules, R.string.tajweed_hamza_wasl, meta.hsl),
        'l' to TajweedRuleType(
            R.string.general_rules,
            R.string.tajweed_lam_shamsiyyah,
            meta.hsl
        ),
        's' to TajweedRuleType(R.string.general_rules, R.string.tajweed_silent, meta.hsl),

        'n' to TajweedRuleType(
            R.string.mududd_rules,
            R.string.tajweed_madda_normal,
            meta.madda_normal
        ),
        'p' to TajweedRuleType(
            R.string.mududd_rules,
            R.string.tajweed_madda_permissible,
            meta.madda_permissible
        ),
        'm' to TajweedRuleType(
            R.string.mududd_rules,
            R.string.tajweed_madda_necesssary,
            meta.madda_necesssary
        ),
        'o' to TajweedRuleType(
            R.string.mududd_rules,
            R.string.tajweed_madda_obligatory,
            meta.madda_obligatory
        ),
        'g' to TajweedRuleType(
            R.string.mududd_rules,
            R.string.tajweed_madda_ghunnah,
            meta.ghunnah
        ),

        'f' to TajweedRuleType(R.string.noon_rules, R.string.ikhafa, meta.ikhafa),
        'i' to TajweedRuleType(R.string.noon_rules, R.string.iqlab, meta.iqlab),
        'a' to TajweedRuleType(
            R.string.noon_rules,
            R.string.tajweed_idgham_with_ghunnah,
            meta.idgham_with_ghunnah
        ),
        'u' to TajweedRuleType(
            R.string.noon_rules,
            R.string.tajweed_idgham_without_ghunnah,
            meta.idgham_without_ghunnah
        ),

        'w' to TajweedRuleType(
            R.string.mimm_rules,
            R.string.idgham_shafawi,
            meta.idgham_shafawi
        ),
        'c' to TajweedRuleType(
            R.string.mimm_rules,
            R.string.ikhafa_shafawi,
            meta.ikhafa_shafawi
        ),

        'd' to TajweedRuleType(
            R.string.idgham_rules,
            R.string.tajweed_idgham_mutajanisayn,
            meta.idgham_mutajanisayn
        ),
        'b' to TajweedRuleType(
            R.string.idgham_rules,
            R.string.tajweed_idgham_mutaqaribayn,
            meta.idgham_mutaqaribayn
        ),

        'q' to TajweedRuleType(R.string.qalaqah_rules, R.string.qalaqah, meta.qalaqah),
    )
}