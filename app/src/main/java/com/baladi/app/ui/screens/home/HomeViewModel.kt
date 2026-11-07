package com.baladi.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baladi.app.data.FavoritesRepository
import com.baladi.app.data.SettingsRepository
import com.baladi.app.data.model.Governorate
import com.baladi.app.data.model.Listing
import com.baladi.app.data.sample.SampleListings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val governorate: Governorate? = null,
    val nearby: List<Listing> = emptyList(),
    val fresh: List<Listing> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val settings: SettingsRepository,
    private val favorites: FavoritesRepository,
) : ViewModel() {

    private val loading = MutableStateFlow(true)
    private val refreshing = MutableStateFlow(false)

    val uiState: StateFlow<HomeUiState> = combine(
        loading,
        refreshing,
        settings.governorate,
        favorites.favoriteIds,
    ) { isLoading, isRefreshing, governorate, favoriteIds ->
        val inArea = SampleListings.all.filter { governorate == null || it.governorate == governorate }
        HomeUiState(
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            governorate = governorate,
            nearby = inArea.sortedBy { it.distanceKm }.take(6),
            fresh = inArea.sortedBy { it.postedHoursAgo },
            favoriteIds = favoriteIds,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    init {
        // Simulated first load so the skeleton screen can be seen; replaced by real loading in Phase 4.
        viewModelScope.launch {
            delay(900)
            loading.value = false
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshing.value = true
            delay(800)
            refreshing.value = false
        }
    }

    fun selectGovernorate(governorate: Governorate?) = settings.setGovernorate(governorate)

    fun toggleFavorite(listingId: String) = favorites.toggle(listingId)
}
