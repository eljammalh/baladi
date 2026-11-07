package com.baladi.app.data.sample

import com.baladi.app.R
import com.baladi.app.data.model.Category
import com.baladi.app.data.model.Governorate
import com.baladi.app.data.model.Listing
import com.baladi.app.data.model.ListingTag
import com.baladi.app.data.model.PriceUnit

/** Demo products used only until real listings come from the database (Phase 3–4). */
object SampleListings {
    val all: List<Listing> = listOf(
        Listing(
            id = "s1", titleRes = R.string.sample_olive_oil, category = Category.OLIVE_OIL,
            price = 7.5, unit = PriceUnit.LITER, governorate = Governorate.AJLOUN,
            distanceKm = 18.2, postedHoursAgo = 2,
            tags = listOf(ListingTag.ORGANIC), sellerVerified = true, offersDelivery = true,
        ),
        Listing(
            id = "s2", titleRes = R.string.sample_labneh, category = Category.DAIRY,
            price = 3.0, unit = PriceUnit.KG, governorate = Governorate.IRBID,
            distanceKm = 1.4, postedHoursAgo = 1,
            tags = listOf(ListingTag.HOMEMADE),
        ),
        Listing(
            id = "s3", titleRes = R.string.sample_eggs, category = Category.EGGS_POULTRY,
            price = 2.25, unit = PriceUnit.DOZEN, governorate = Governorate.IRBID,
            distanceKm = 3.8, postedHoursAgo = 5,
            tags = listOf(ListingTag.PESTICIDE_FREE), sellerVerified = true,
        ),
        Listing(
            id = "s4", titleRes = R.string.sample_honey, category = Category.HONEY,
            price = 18.0, unit = PriceUnit.KG, governorate = Governorate.KARAK,
            distanceKm = 142.0, postedHoursAgo = 20,
            tags = listOf(ListingTag.ORGANIC), offersDelivery = true,
        ),
        Listing(
            id = "s5", titleRes = R.string.sample_pomegranate, category = Category.FRUITS,
            price = 1.0, unit = PriceUnit.KG, governorate = Governorate.BALQA,
            distanceKm = 64.5, postedHoursAgo = 8,
        ),
        Listing(
            id = "s6", titleRes = R.string.sample_taboon, category = Category.BREAD,
            price = 0.5, unit = PriceUnit.BUNDLE, governorate = Governorate.AMMAN,
            distanceKm = 82.0, postedHoursAgo = 0,
            tags = listOf(ListingTag.HOMEMADE),
        ),
        Listing(
            id = "s7", titleRes = R.string.sample_makdous, category = Category.PICKLES_PRESERVES,
            price = 6.0, unit = PriceUnit.JAR, governorate = Governorate.AMMAN,
            distanceKm = 79.3, postedHoursAgo = 30,
            tags = listOf(ListingTag.HOMEMADE), sellerVerified = true, offersDelivery = true,
        ),
        Listing(
            id = "s8", titleRes = R.string.sample_zaatar, category = Category.HERBS_SPICES,
            price = 5.0, unit = PriceUnit.KG, governorate = Governorate.JERASH,
            distanceKm = 28.6, postedHoursAgo = 12,
            tags = listOf(ListingTag.PESTICIDE_FREE),
        ),
        Listing(
            id = "s9", titleRes = R.string.sample_tomatoes, category = Category.VEGETABLES,
            price = 0.6, unit = PriceUnit.KG, governorate = Governorate.IRBID,
            distanceKm = 6.1, postedHoursAgo = 3,
            tags = listOf(ListingTag.PESTICIDE_FREE),
        ),
        Listing(
            id = "s10", titleRes = R.string.sample_freekeh, category = Category.GRAINS_LEGUMES,
            price = 2.5, unit = PriceUnit.KG, governorate = Governorate.MADABA,
            distanceKm = 101.0, postedHoursAgo = 48,
        ),
    )

    fun byId(id: String): Listing? = all.firstOrNull { it.id == id }
}
