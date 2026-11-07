package com.baladi.app.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baladi.app.R
import com.baladi.app.data.model.Category
import com.baladi.app.data.model.Governorate
import com.baladi.app.data.model.Listing
import com.baladi.app.ui.components.CategoryTile
import com.baladi.app.ui.components.EmptyState
import com.baladi.app.ui.components.ListingCard
import com.baladi.app.ui.components.ListingCardSkeleton
import com.baladi.app.ui.components.SectionHeader
import com.baladi.app.ui.components.SkeletonBox
import com.baladi.app.ui.components.categoryTint
import com.baladi.app.ui.theme.Spacing
import java.util.Calendar

private const val GRID_COLUMNS = 4
private const val HOME_CATEGORY_COUNT = 7

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenSearch: () -> Unit,
    onOpenMap: () -> Unit,
    onOpenCategory: (Category) -> Unit,
    onOpenAllCategories: () -> Unit,
    onOpenListing: (Listing) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showGovernorateSheet by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeHeader(
            governorate = state.governorate,
            onGovernorateClick = { showGovernorateSheet = true },
            onMapClick = onOpenMap,
            onSearchClick = onOpenSearch,
        )

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.fillMaxSize(),
        ) {
            AnimatedContent(
                targetState = state.isLoading,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "homeContent",
            ) { loading ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(GRID_COLUMNS),
                    contentPadding = PaddingValues(
                        start = Spacing.lg,
                        end = Spacing.lg,
                        top = Spacing.sm,
                        bottom = Spacing.xl,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (loading) {
                        homeSkeleton()
                    } else {
                        homeContent(
                            state = state,
                            onOpenCategory = onOpenCategory,
                            onOpenAllCategories = onOpenAllCategories,
                            onOpenListing = onOpenListing,
                            onToggleFavorite = viewModel::toggleFavorite,
                            onChangeArea = { showGovernorateSheet = true },
                        )
                    }
                }
            }
        }
    }

    if (showGovernorateSheet) {
        GovernorateSheet(
            selected = state.governorate,
            onSelect = {
                viewModel.selectGovernorate(it)
                showGovernorateSheet = false
            },
            onDismiss = { showGovernorateSheet = false },
        )
    }
}

@Composable
private fun HomeHeader(
    governorate: Governorate?,
    onGovernorateClick: () -> Unit,
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = Spacing.lg)
            .padding(top = Spacing.sm, bottom = Spacing.md)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Brand mark
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Outlined.Eco,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(Modifier.width(Spacing.sm))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.weight(1f))

            // Governorate selector
            Surface(
                onClick = onGovernorateClick,
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, MaterialTheme.colorScheme.outlineVariant
                ),
            ) {
                Row(
                    Modifier.padding(start = Spacing.md, end = Spacing.sm, top = Spacing.sm, bottom = Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.width(Spacing.xs))
                    Text(
                        text = stringResource(governorate?.nameRes ?: R.string.all_of_jordan),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                    )
                    Icon(
                        Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Spacer(Modifier.width(Spacing.xs))
            IconButton(onClick = onMapClick) {
                Icon(
                    Icons.Outlined.Map,
                    contentDescription = stringResource(R.string.cd_open_map),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Spacer(Modifier.height(Spacing.md))

        // Search bar
        Surface(
            onClick = onSearchClick,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                Modifier.padding(horizontal = Spacing.lg, vertical = Spacing.md + Spacing.xxs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.width(Spacing.md))
                Text(
                    text = stringResource(R.string.search_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    Icons.Outlined.Tune,
                    contentDescription = stringResource(R.string.cd_filters),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun LazyGridScope.fullWidth(key: String, content: @Composable () -> Unit) {
    item(key = key, span = { GridItemSpan(maxLineSpan) }) { content() }
}

private fun LazyGridScope.homeContent(
    state: HomeUiState,
    onOpenCategory: (Category) -> Unit,
    onOpenAllCategories: () -> Unit,
    onOpenListing: (Listing) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onChangeArea: () -> Unit,
) {
    fullWidth("season") { SeasonBanner() }

    fullWidth("categoriesHeader") {
        SectionHeader(
            title = stringResource(R.string.section_categories),
            actionLabel = stringResource(R.string.action_see_all),
            onAction = onOpenAllCategories,
        )
    }
    items(
        items = Category.entries.take(HOME_CATEGORY_COUNT),
        key = { "cat_${it.id}" },
    ) { category ->
        val (bg, fg) = categoryTint(category)
        CategoryTile(
            label = stringResource(category.nameRes),
            icon = category.icon,
            background = bg,
            iconTint = fg,
            onClick = { onOpenCategory(category) },
        )
    }
    item(key = "cat_more") {
        CategoryTile(
            label = stringResource(R.string.cat_more),
            icon = Icons.Outlined.Apps,
            background = MaterialTheme.colorScheme.surfaceContainerHigh,
            iconTint = MaterialTheme.colorScheme.onSurface,
            onClick = onOpenAllCategories,
        )
    }

    if (state.fresh.isEmpty()) {
        fullWidth("emptyArea") {
            EmptyState(
                icon = Icons.Outlined.SearchOff,
                title = stringResource(R.string.empty_area_title),
                message = stringResource(R.string.empty_area_message),
                actionLabel = stringResource(R.string.action_change_area),
                onAction = onChangeArea,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        return
    }

    fullWidth("nearbyHeader") {
        SectionHeader(title = stringResource(R.string.section_near_you))
    }
    fullWidth("nearbyRow") {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
            items(state.nearby, key = { "near_${it.id}" }) { listing ->
                ListingCard(
                    listing = listing,
                    isFavorite = listing.id in state.favoriteIds,
                    onToggleFavorite = { onToggleFavorite(listing.id) },
                    onClick = { onOpenListing(listing) },
                    modifier = Modifier.width(168.dp),
                )
            }
        }
    }

    fullWidth("freshHeader") {
        SectionHeader(title = stringResource(R.string.section_fresh_today))
    }
    items(
        items = state.fresh,
        key = { "fresh_${it.id}" },
        span = { GridItemSpan(2) },
    ) { listing ->
        ListingCard(
            listing = listing,
            isFavorite = listing.id in state.favoriteIds,
            onToggleFavorite = { onToggleFavorite(listing.id) },
            onClick = { onOpenListing(listing) },
        )
    }
}

private fun LazyGridScope.homeSkeleton() {
    fullWidth("s_banner") {
        SkeletonBox(
            Modifier
                .fillMaxWidth()
                .height(112.dp)
        )
    }
    fullWidth("s_header") {
        SkeletonBox(
            Modifier
                .fillMaxWidth(0.35f)
                .height(18.dp)
        )
    }
    items(8, key = { "s_cat_$it" }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SkeletonBox(
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            )
            Spacer(Modifier.height(Spacing.sm))
            SkeletonBox(
                Modifier
                    .fillMaxWidth(0.7f)
                    .height(10.dp)
            )
        }
    }
    items(4, key = { "s_list_$it" }, span = { GridItemSpan(2) }) {
        ListingCardSkeleton()
    }
}

/** "In season now" banner — changes with the month of the year. */
@Composable
private fun SeasonBanner() {
    val months = stringArrayResource(R.array.season_by_month)
    val month = remember { Calendar.getInstance().get(Calendar.MONTH) }
    val produce = months.getOrElse(month) { months.first() }
    val primary = MaterialTheme.colorScheme.primary
    val deep = MaterialTheme.colorScheme.onPrimaryContainer

    Box(
        Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Brush.linearGradient(listOf(primary, deep)))
            .padding(Spacing.lg)
    ) {
        Icon(
            Icons.Outlined.Eco,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f),
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.CenterEnd),
        )
        Column(Modifier.fillMaxWidth(0.82f)) {
            Text(
                text = stringResource(R.string.season_now),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = produce,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = stringResource(R.string.season_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            )
        }
    }
}
