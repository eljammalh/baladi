package com.baladi.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.baladi.app.R
import java.util.Locale

/** 7.5 -> "7.50", 3.0 -> "3" */
fun formatPrice(price: Double): String =
    if (price % 1.0 == 0.0) price.toLong().toString()
    else String.format(Locale.US, "%.2f", price)

fun formatDistance(km: Double): String =
    if (km < 10) String.format(Locale.US, "%.1f", km)
    else km.toLong().toString()

@Composable
fun relativeTime(hoursAgo: Int): String = when {
    hoursAgo <= 0 -> stringResource(R.string.time_just_now)
    hoursAgo < 24 -> pluralStringResource(R.plurals.time_hours_ago, hoursAgo, hoursAgo)
    else -> {
        val days = hoursAgo / 24
        pluralStringResource(R.plurals.time_days_ago, days, days)
    }
}
