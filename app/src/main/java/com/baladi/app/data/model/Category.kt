package com.baladi.app.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BakeryDining
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.EmojiNature
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import com.baladi.app.R

/** Product categories shown on the home grid. The [id] is what will be stored in the database. */
enum class Category(
    val id: String,
    @StringRes val nameRes: Int,
    val icon: ImageVector,
) {
    VEGETABLES("vegetables", R.string.cat_vegetables, Icons.Outlined.Eco),
    FRUITS("fruits", R.string.cat_fruits, Icons.Outlined.Park),
    EGGS_POULTRY("eggs_poultry", R.string.cat_eggs_poultry, Icons.Outlined.Egg),
    DAIRY("dairy", R.string.cat_dairy, Icons.Outlined.LocalDrink),
    HONEY("honey", R.string.cat_honey, Icons.Outlined.EmojiNature),
    OLIVE_OIL("olive_oil", R.string.cat_olive_oil, Icons.Outlined.WaterDrop),
    HERBS_SPICES("herbs_spices", R.string.cat_herbs_spices, Icons.Outlined.Spa),
    GRAINS_LEGUMES("grains_legumes", R.string.cat_grains_legumes, Icons.Outlined.Grain),
    BREAD("bread", R.string.cat_bread, Icons.Outlined.BakeryDining),
    HOME_MEALS("home_meals", R.string.cat_home_meals, Icons.Outlined.Restaurant),
    PICKLES_PRESERVES("pickles_preserves", R.string.cat_pickles_preserves, Icons.Outlined.Kitchen),
    SWEETS("sweets", R.string.cat_sweets, Icons.Outlined.Cake),
    PLANTS("plants", R.string.cat_plants, Icons.Outlined.LocalFlorist),
    OTHER("other", R.string.cat_other, Icons.Outlined.Category);

    companion object {
        fun fromId(id: String?): Category? = entries.firstOrNull { it.id == id }
    }
}
