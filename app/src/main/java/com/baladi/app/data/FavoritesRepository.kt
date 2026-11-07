package com.baladi.app.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Favorites kept in memory for Phase 1 so the heart button works across screens.
 * Phase 7 moves this to the user's account in the database.
 */
@Singleton
class FavoritesRepository @Inject constructor() {
    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    fun toggle(listingId: String) {
        _favoriteIds.update { current ->
            if (listingId in current) current - listingId else current + listingId
        }
    }
}
