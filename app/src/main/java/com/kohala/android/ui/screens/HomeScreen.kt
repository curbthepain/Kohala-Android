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
import com.kohala.android.ui.components.DashboardTopBar
import com.kohala.android.ui.theme.DashboardBackground

/**
 * Main homescreen with a tile-based dashboard layout.
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBackground)
    ) {
        // Fixed top bar
        DashboardTopBar()

        // Scrollable dashboard content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Featured hero tile
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
