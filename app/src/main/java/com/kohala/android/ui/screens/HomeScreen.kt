package com.kohala.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kohala.android.data.model.SampleDashboard
import com.kohala.android.ui.components.DashboardTileCard
import com.kohala.android.ui.components.TileRow
import com.kohala.android.ui.components.XboxTopBar
import com.kohala.android.ui.theme.DashboardBackground

/**
 * Main homescreen that replicates the Xbox One 2013 dashboard layout.
 *
 * Layout structure (top to bottom):
 * - Top bar with profile, gamertag, and quick-access icons
 * - Featured/hero tile (large, prominent)
 * - Pins section (horizontal scrolling tiles)
 * - Recent section (horizontal scrolling tiles)
 * - Store section (horizontal scrolling tiles)
 * - Friends/Social section (horizontal scrolling tiles)
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBackground)
    ) {
        // Fixed top bar
        XboxTopBar()

        // Scrollable dashboard content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Featured hero tile - prominently displayed like the Xbox One main tile
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                DashboardTileCard(
                    tile = SampleDashboard.featuredTile,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* TODO: launch featured content */ },
                )
            }

            // Dashboard sections
            SampleDashboard.allSections.forEach { section ->
                TileRow(section = section)
            }
        }
    }
}
