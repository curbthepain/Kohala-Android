package com.kohala.android.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kohala.android.data.model.DashboardTile
import com.kohala.android.data.model.TileIcon
import com.kohala.android.data.model.TileSize
import com.kohala.android.ui.theme.*

@Composable
fun DashboardTileCard(
    tile: DashboardTile,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.95f
            isFocused || isHovered -> 1.03f
            else -> 1f
        },
        label = "tileScale"
    )

    val tileWidth: Dp
    val tileHeight: Dp
    when (tile.size) {
        TileSize.SMALL -> { tileWidth = 100.dp; tileHeight = 100.dp }
        TileSize.MEDIUM -> { tileWidth = 160.dp; tileHeight = 100.dp }
        TileSize.LARGE -> { tileWidth = 280.dp; tileHeight = 200.dp }
        TileSize.WIDE -> { tileWidth = 260.dp; tileHeight = 100.dp }
    }

    Box(
        modifier = modifier
            .width(tileWidth)
            .height(tileHeight)
            .scale(scale)
            .then(
                if (isFocused || isHovered) {
                    Modifier.border(2.dp, FocusBorder)
                } else {
                    Modifier
                }
            )
            .background(tile.backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .focusable(interactionSource = interactionSource),
    ) {
        // Subtle gradient overlay at the bottom for text readability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(tileHeight * 0.5f)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                )
        )

        // Icon in center (faded, large) - background icon
        tile.icon?.let { icon ->
            Icon(
                imageVector = getTileIconVector(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(if (tile.size == TileSize.LARGE) 64.dp else 36.dp)
                    .align(Alignment.Center)
                    .offset(y = (-8).dp),
                tint = Color.White.copy(alpha = 0.25f)
            )
        }

        // Badge (top-right corner)
        tile.badge?.let { badge ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .background(WindowsGreen)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = badge,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Title and subtitle at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 8.dp
                )
        ) {
            Text(
                text = tile.title,
                fontSize = if (tile.size == TileSize.LARGE) 16.sp else 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = if (tile.size == TileSize.LARGE) 2 else 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (tile.subtitle.isNotEmpty()) {
                Text(
                    text = tile.subtitle,
                    fontSize = if (tile.size == TileSize.LARGE) 13.sp else 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

fun getTileIconVector(icon: TileIcon): ImageVector {
    return when (icon) {
        TileIcon.GAME -> Icons.Default.SportsEsports
        TileIcon.STORE -> Icons.Default.Store
        TileIcon.SETTINGS -> Icons.Default.Settings
        TileIcon.FRIENDS -> Icons.Default.People
        TileIcon.MESSAGES -> Icons.Default.Email
        TileIcon.MUSIC -> Icons.Default.MusicNote
        TileIcon.VIDEO -> Icons.Default.VideoLibrary
        TileIcon.ACHIEVEMENTS -> Icons.Default.EmojiEvents
        TileIcon.PROFILE -> Icons.Default.Person
        TileIcon.APP -> Icons.Default.Apps
        TileIcon.MY_GAMES_AND_APPS -> Icons.Default.GridView
        TileIcon.ACTIVITY_FEED -> Icons.Default.DynamicFeed
        TileIcon.PARTY -> Icons.Default.Group
        TileIcon.SEARCH -> Icons.Default.Search
    }
}
