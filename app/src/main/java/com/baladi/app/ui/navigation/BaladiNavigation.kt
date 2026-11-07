package com.baladi.app.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.baladi.app.R
import com.baladi.app.ui.screens.categories.AllCategoriesScreen
import com.baladi.app.ui.screens.categories.CategoryScreen
import com.baladi.app.ui.screens.favorites.FavoritesScreen
import com.baladi.app.ui.screens.home.HomeScreen
import com.baladi.app.ui.screens.map.MapScreen
import com.baladi.app.ui.screens.messages.MessagesScreen
import com.baladi.app.ui.screens.post.PostScreen
import com.baladi.app.ui.screens.profile.ProfileScreen
import com.baladi.app.ui.screens.search.SearchScreen
import com.baladi.app.ui.screens.settings.SettingsScreen

object Routes {
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val POST = "post"
    const val MESSAGES = "messages"
    const val PROFILE = "profile"
    const val MAP = "map"
    const val SEARCH = "search"
    const val ALL_CATEGORIES = "categories"
    const val CATEGORY = "category/{id}"
    const val SETTINGS = "settings"

    fun category(id: String) = "category/$id"
}

enum class TopLevelTab(
    val route: String,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(Routes.HOME, R.string.tab_home, Icons.Filled.Home, Icons.Outlined.Home),
    FAVORITES(Routes.FAVORITES, R.string.tab_favorites, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    POST(Routes.POST, R.string.tab_post, Icons.Filled.Add, Icons.Filled.Add),
    MESSAGES(Routes.MESSAGES, R.string.tab_messages, Icons.Filled.ChatBubble, Icons.Outlined.ChatBubbleOutline),
    PROFILE(Routes.PROFILE, R.string.tab_profile, Icons.Filled.Person, Icons.Outlined.Person),
}

private const val ANIM_MS = 280

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInForward() =
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(ANIM_MS)) + fadeIn(tween(ANIM_MS))

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutBack() =
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(ANIM_MS)) + fadeOut(tween(ANIM_MS))

/** App shell: bottom navigation + all screens. */
@Composable
fun BaladiRoot() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = TopLevelTab.entries.any { it.route == currentRoute }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BaladiBottomBar(currentRoute = currentRoute) { tab -> navController.navigateToTab(tab) }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(200)) },
            exitTransition = { fadeOut(tween(200)) },
            popEnterTransition = { fadeIn(tween(200)) },
            popExitTransition = { fadeOut(tween(200)) },
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onOpenSearch = { navController.navigate(Routes.SEARCH) },
                    onOpenMap = { navController.navigate(Routes.MAP) },
                    onOpenCategory = { navController.navigate(Routes.category(it.id)) },
                    onOpenAllCategories = { navController.navigate(Routes.ALL_CATEGORIES) },
                    onOpenListing = { /* Listing details arrive in Phase 4 */ },
                )
            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(onBrowse = { navController.navigateToTab(TopLevelTab.HOME) })
            }
            composable(Routes.POST) { PostScreen() }
            composable(Routes.MESSAGES) { MessagesScreen() }
            composable(Routes.PROFILE) {
                ProfileScreen(onOpenSettings = { navController.navigate(Routes.SETTINGS) })
            }

            composable(
                Routes.SEARCH,
                enterTransition = { fadeIn(tween(ANIM_MS)) },
                popExitTransition = { fadeOut(tween(ANIM_MS)) },
            ) {
                SearchScreen(
                    onBack = { navController.popBackStack() },
                    onOpenCategory = { navController.navigate(Routes.category(it.id)) },
                )
            }
            composable(
                Routes.MAP,
                enterTransition = { slideInForward() },
                popExitTransition = { slideOutBack() },
            ) {
                MapScreen(onBack = { navController.popBackStack() })
            }
            composable(
                Routes.ALL_CATEGORIES,
                enterTransition = { slideInForward() },
                popExitTransition = { slideOutBack() },
            ) {
                AllCategoriesScreen(
                    onBack = { navController.popBackStack() },
                    onOpenCategory = { navController.navigate(Routes.category(it.id)) },
                )
            }
            composable(
                Routes.CATEGORY,
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                enterTransition = { slideInForward() },
                popExitTransition = { slideOutBack() },
            ) {
                CategoryScreen(onBack = { navController.popBackStack() })
            }
            composable(
                Routes.SETTINGS,
                enterTransition = { slideInForward() },
                popExitTransition = { slideOutBack() },
            ) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

private fun NavHostController.navigateToTab(tab: TopLevelTab) {
    navigate(tab.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun BaladiBottomBar(
    currentRoute: String?,
    onTabSelected: (TopLevelTab) -> Unit,
) {
    Column {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {
            TopLevelTab.entries.forEach { tab ->
                val selected = currentRoute == tab.route
                val itemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (tab == TopLevelTab.POST) {
                    NavigationBarItem(
                        selected = selected,
                        onClick = { onTabSelected(tab) },
                        icon = {
                            Box(
                                Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.tertiary),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = stringResource(tab.labelRes),
                                    tint = MaterialTheme.colorScheme.onTertiary,
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surface,
                        ),
                    )
                } else {
                    NavigationBarItem(
                        selected = selected,
                        onClick = { onTabSelected(tab) },
                        icon = {
                            Icon(
                                if (selected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(stringResource(tab.labelRes), style = MaterialTheme.typography.labelMedium)
                        },
                        colors = itemColors,
                    )
                }
            }
        }
    }
}
