package com.kohala.android.data.model

import androidx.compose.ui.graphics.Color
import com.kohala.android.ui.theme.*

/**
 * Represents a tile on the dashboard.
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
    GAME, STORE, SETTINGS, FRIENDS, MESSAGES, MUSIC, VIDEO, ACHIEVEMENTS, PROFILE, APP,
    MY_GAMES_AND_APPS, ACTIVITY_FEED, PARTY, SEARCH
}

data class DashboardSection(
    val title: String,
    val tiles: List<DashboardTile>,
)

object SampleDashboard {

    val featuredTile = DashboardTile(
        id = "featured",
        title = "Space Odyssey: Remastered",
        subtitle = "Play now",
        backgroundColor = TileBlue,
        size = TileSize.LARGE,
        icon = TileIcon.GAME,
    )

    val pins = DashboardSection(
        title = "pins",
        tiles = listOf(
            DashboardTile("pin1", "Turbo Drift", "Racing", TileRed, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("pin2", "StreamBox", "Entertainment", TileRed, TileSize.SMALL, TileIcon.APP),
            DashboardTile("pin3", "Outbreak", "Action", TileDarkGreen, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("pin4", "Iron Fists", "Fighting", TilePurple, TileSize.SMALL, TileIcon.GAME),
            DashboardTile("pin5", "QuickChat", "Communication", TileBlue, TileSize.SMALL, TileIcon.APP),
            DashboardTile("pin6", "Centurion", "Action", TileOrange, TileSize.MEDIUM, TileIcon.GAME),
        )
    )

    val recentItems = DashboardSection(
        title = "recent",
        tiles = listOf(
            DashboardTile("rec1", "Mech Assault", "Last played 2h ago", TileOrange, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("rec2", "Music Player", "Now playing", AccentGreen, TileSize.SMALL, TileIcon.MUSIC),
            DashboardTile("rec3", "Clip Studio", "1 new clip", TilePurple, TileSize.MEDIUM, TileIcon.VIDEO),
            DashboardTile("rec4", "Warfront", "Last played yesterday", TileTeal, TileSize.MEDIUM, TileIcon.GAME),
            DashboardTile("rec5", "LiveStream", "Live now", TilePurple, TileSize.SMALL, TileIcon.APP),
        )
    )

    val store = DashboardSection(
        title = "store",
        tiles = listOf(
            DashboardTile("store1", "Games", "New releases", AccentGreen, TileSize.MEDIUM, TileIcon.STORE),
            DashboardTile("store2", "Apps", "Popular", TileBlue, TileSize.MEDIUM, TileIcon.STORE),
            DashboardTile("store3", "Movies & TV", "Featured", TileRed, TileSize.MEDIUM, TileIcon.VIDEO),
            DashboardTile("store4", "Music", "Top albums", TilePurple, TileSize.MEDIUM, TileIcon.MUSIC),
        )
    )

    val social = DashboardSection(
        title = "social",
        tiles = listOf(
            DashboardTile("soc1", "Friends", "12 online", AccentGreen, TileSize.MEDIUM, TileIcon.FRIENDS),
            DashboardTile("soc2", "Messages", "3 new", TileBlue, TileSize.SMALL, TileIcon.MESSAGES),
            DashboardTile("soc3", "Achievements", "2,450 pts", TileTeal, TileSize.MEDIUM, TileIcon.ACHIEVEMENTS),
            DashboardTile("soc4", "Party", "Join a party", TilePurple, TileSize.SMALL, TileIcon.PARTY),
            DashboardTile("soc5", "Activity Feed", "See what's new", DashboardSurfaceLight, TileSize.MEDIUM, TileIcon.ACTIVITY_FEED),
        )
    )

    val system = DashboardSection(
        title = "system",
        tiles = listOf(
            DashboardTile("sys1", "My Games & Apps", "24 installed", DashboardSurfaceLight, TileSize.MEDIUM, TileIcon.MY_GAMES_AND_APPS),
            DashboardTile("sys2", "Settings", "System", DashboardSurfaceLight, TileSize.SMALL, TileIcon.SETTINGS),
            DashboardTile("sys3", "Search", "Find games & apps", DashboardSurfaceLight, TileSize.SMALL, TileIcon.SEARCH),
            DashboardTile("sys4", "Profile", "Player1", AccentGreenDark, TileSize.MEDIUM, TileIcon.PROFILE),
        )
    )

    val allSections = listOf(pins, recentItems, store, social, system)
}
