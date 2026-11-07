package com.baladi.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.baladi.app.R
import com.baladi.app.data.model.Category
import com.baladi.app.data.model.Listing
import com.baladi.app.ui.theme.Spacing

/** Soft background colours that give each category its own look. */
@Composable
fun categoryTint(category: Category): Pair<Color, Color> {
    val c = MaterialTheme.colorScheme
    return when (category.ordinal % 4) {
        0 -> c.primaryContainer to c.onPrimaryContainer
        1 -> c.secondaryContainer to c.onSecondaryContainer
        2 -> c.tertiaryContainer to c.onTertiaryContainer
        else -> c.surfaceVariant to c.primary
    }
}

/** Placeholder artwork until real product photos arrive in Phase 3. */
@Composable
fun ListingImage(category: Category, modifier: Modifier = Modifier) {
    val (bg, fg) = categoryTint(category)
    Box(
        modifier = modifier.background(
            Brush.linearGradient(listOf(bg, bg.copy(alpha = 0.55f)))
        ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = null,
            tint = fg.copy(alpha = 0.55f),
            modifier = Modifier.size(52.dp),
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "favScale",
    )
    val tint by animateColorAsState(
        targetValue = if (isFavorite) MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "favTint",
    )
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
        shadowElevation = 1.dp,
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                onToggle()
            },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(
                    if (isFavorite) R.string.cd_remove_favorite else R.string.cd_add_favorite
                ),
                tint = tint,
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale),
            )
        }
    }
}

@Composable
fun PriceText(listing: Listing, modifier: Modifier = Modifier) {
    val priceColor = MaterialTheme.colorScheme.primary
    val unitColor = MaterialTheme.colorScheme.onSurfaceVariant
    val currency = stringResource(R.string.currency_jod)
    val perUnit = stringResource(R.string.price_per_unit, stringResource(listing.unit.labelRes))
    val price = formatPrice(listing.price)
    Text(
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = priceColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
            ) { append(price) }
            withStyle(SpanStyle(color = priceColor, fontWeight = FontWeight.Bold)) {
                append(" ")
                append(currency)
            }
            withStyle(SpanStyle(color = unitColor)) {
                append(" ")
                append(perUnit)
            }
        },
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun TagBadge(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.92f))
            .padding(horizontal = Spacing.sm, vertical = Spacing.xxs),
    )
}

@Composable
fun ListingCard(
    listing: Listing,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.15f)
        ) {
            ListingImage(listing.category, Modifier.fillMaxSize())
            FavoriteButton(
                isFavorite = isFavorite,
                onToggle = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Spacing.sm),
            )
            listing.tags.firstOrNull()?.let { tag ->
                TagBadge(
                    text = stringResource(tag.labelRes),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(Spacing.sm),
                )
            }
            if (listing.offersDelivery) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(Spacing.sm)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .padding(horizontal = Spacing.sm, vertical = Spacing.xxs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.LocalShipping,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(Spacing.xs))
                    Text(
                        stringResource(R.string.label_delivery),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Column(Modifier.padding(horizontal = Spacing.md, vertical = Spacing.md)) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = stringResource(listing.titleRes),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                if (listing.sellerVerified) {
                    Spacer(Modifier.width(Spacing.xs))
                    Icon(
                        Icons.Filled.Verified,
                        contentDescription = stringResource(R.string.cd_verified_seller),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            Spacer(Modifier.height(Spacing.xs))
            PriceText(listing)
            Spacer(Modifier.height(Spacing.sm))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Icon(
                    Icons.Outlined.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(14.dp),
                )
                Text(
                    text = stringResource(
                        R.string.location_and_distance,
                        stringResource(listing.governorate.nameRes),
                        formatDistance(listing.distanceKm),
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(Spacing.xxs))
            Text(
                text = relativeTime(listing.postedHoursAgo),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Composable
fun ListingCardSkeleton(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        SkeletonBox(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.15f)
        )
        Spacer(Modifier.height(Spacing.md))
        SkeletonBox(
            Modifier
                .fillMaxWidth(0.9f)
                .height(14.dp)
        )
        Spacer(Modifier.height(Spacing.sm))
        SkeletonBox(
            Modifier
                .fillMaxWidth(0.5f)
                .height(14.dp)
        )
        Spacer(Modifier.height(Spacing.sm))
        SkeletonBox(
            Modifier
                .fillMaxWidth(0.7f)
                .height(10.dp)
        )
    }
}
