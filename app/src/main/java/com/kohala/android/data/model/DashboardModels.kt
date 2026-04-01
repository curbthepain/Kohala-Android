package com.kohala.android.data.model

import androidx.compose.ui.graphics.Color
import com.kohala.android.ui.theme.*

/**
 * Represents a tile on the Xbox-style dashboard.
 * Tiles can be different sizes like the original Xbox One dashboard.
 */
data class DashboardTile(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val backgroundColor: Color,
    val size: TileSize = TileSize.MEDIUM,
    val icon: TileIcon? = null,
    val badge: String? = null,
)

enum class TileSize {
    SMALL,   // 1x1 - small square
    MEDIUM,  // 2x1 - standard rectangle
    LARGE,   // 2x2 - large featured tile
    WIDE,    // 3x1 - wide banner tile
}

enum class TileIcon {
    GAME, STORE, SETTINGS, FRIENDS, MESSAGES, MUSIC, VIDEO, ACHIEVEMENTS, PROFILE, APP
}

data class DashboardSection(
    val title: String,
    val tiles: List<DashboardTile>,
)

// Sample data representing the Xbox One 2013 dashboard layout
object SampleDashboard {

    val featuredTile = DashboardTile(
        id = "featured",
        title = "Halo: The Master Chief Collection",
        subtitle = "Play now",
        backgroundColor = TileBlue,
        size = TileSize.LARGE,
        icon = TileIcon.GAME,
    )

    val pins = DashboardSection(
        title = "pins",
        tiles = listOf(
            DashboardTile("pin1", "Forza 5", "Racing", TileRed, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("pin2", "Netflix", "Entertainment", TileRed, TileSize.SMALL, TileIcon.APP),
            DashboardTile("pin3", "Dead Rising 3", "Action", TileDarkGreen, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("pin4", "Killer Instinct", "Fighting", TilePurple, TileSize.SMALL, TileIcon.GAME),
            DashboardTile("pin5", "Skype", "Communication", TileBlue, TileSize.SMALL, TileIcon.APP),
            DashboardTile("pin6", "Ryse", "Action", TileOrange, TileSize.MEDIUM, TileIcon.GAME),
        )
    )

    val recentItems = DashboardSection(
        title = "recent",
        tiles = listOf(
            DashboardTile("rec1", "Titanfall", "Last played 2h ago", TileOrange, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("rec2", "Xbox Music", "Now playing", XboxGreen, TileSize.SMALL, TileIcon.MUSIC),
            DashboardTile("rec3", "Upload Studio", "1 new clip", TilePurple, TileSize.MEDIUM, TileIcon.VIDEO),
            DashboardTile("rec4", "Battlefield 4", "Last played yesterday", TileTeal, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("rec5", "Twitch", "Live now", TilePurple, TileSize.SMALL, TileIcon.APP),
        )
    )

    val store = DashboardSection(
        title = "store",
        tiles = listOf(
            DashboardTile("store1", "Games", "New releases", XboxGreen, TileSize.MEDIUM, TileIcon.STORE),
            DashboardTile("store2", "Apps", "Popular", TileBlue, TileSize.MEDIUM, TileIcon.STORE),
            DashboardTile("store3", "Movies & TV", "Featured", TileRed, TileSize.MEDIUM, TileIcon.VIDEO),
            DashboardTile("store4", "Music", "Top albums", TilePurple, TileSize.MEDIUM, TileIcon.MUSIC),
        )
    )

    val social = DashboardSection(
        title = "friends",
        tiles = listOf(
            DashboardTile("soc1", "Friends", "12 online", XboxGreen, TileSize.MEDIUM, TileIcon.FRIENDS),
            DashboardTile("soc2", "Messages", "3 new", TileBlue, TileSize.SMALL, TileIcon.MESSAGES),
            DashboardTile("soc3", "Achievements", "2,450 G", TileTeal, TileSize.MEDIUM, TileIcon.ACHIEVEMENTS),
            DashboardTile("soc4", "Party", "Join a party", TilePurple, TileSize.SMALL, TileIcon.FRIENDS),
        )
    )

    val allSections = listOf(pins, recentItems, store, social)
}
