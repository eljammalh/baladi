package com.baladi.app.data.model

import androidx.annotation.StringRes
import com.baladi.app.R

enum class PriceUnit(@StringRes val labelRes: Int) {
    KG(R.string.unit_kg),
    PIECE(R.string.unit_piece),
    DOZEN(R.string.unit_dozen),
    BOX(R.string.unit_box),
    LITER(R.string.unit_liter),
    JAR(R.string.unit_jar),
    BUNDLE(R.string.unit_bundle),
}

enum class ListingTag(@StringRes val labelRes: Int) {
    ORGANIC(R.string.tag_organic),
    HOMEMADE(R.string.tag_homemade),
    PESTICIDE_FREE(R.string.tag_pesticide_free),
}

/**
 * A product a seller is offering.
 * In Phase 1 the title is a string resource (sample data). From Phase 3 it becomes
 * real text typed by the seller and loaded from the database.
 */
data class Listing(
    val id: String,
    @StringRes val titleRes: Int,
    val category: Category,
    val price: Double,
    val unit: PriceUnit,
    val governorate: Governorate,
    val distanceKm: Double,
    val postedHoursAgo: Int,
    val tags: List<ListingTag> = emptyList(),
    val sellerVerified: Boolean = false,
    val offersDelivery: Boolean = false,
)
