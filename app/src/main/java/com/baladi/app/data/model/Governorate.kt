package com.baladi.app.data.model

import androidx.annotation.StringRes
import com.baladi.app.R

/** The 12 governorates of Jordan. Cities/areas inside each one are added with the database in Phase 2. */
enum class Governorate(val id: String, @StringRes val nameRes: Int) {
    AMMAN("amman", R.string.gov_amman),
    IRBID("irbid", R.string.gov_irbid),
    ZARQA("zarqa", R.string.gov_zarqa),
    BALQA("balqa", R.string.gov_balqa),
    MAFRAQ("mafraq", R.string.gov_mafraq),
    JERASH("jerash", R.string.gov_jerash),
    AJLOUN("ajloun", R.string.gov_ajloun),
    MADABA("madaba", R.string.gov_madaba),
    KARAK("karak", R.string.gov_karak),
    TAFILAH("tafilah", R.string.gov_tafilah),
    MAAN("maan", R.string.gov_maan),
    AQABA("aqaba", R.string.gov_aqaba);

    companion object {
        fun fromId(id: String?): Governorate? = entries.firstOrNull { it.id == id }
    }
}
