package com.kohala.android.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kohala.android.data.model.DashboardSection
import com.kohala.android.ui.theme.TextSecondary

/**
 * A horizontal scrolling row of tiles with a section header.
 * Horizontal scrolling row of tiles for dashboard sections
 * like Pins, Recent, Store, etc.
 */
@Composable
fun TileRow(
    section: DashboardSection,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // Section header - uppercase, light-weight section labels
        Text(
            text = section.title.uppercase(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = TextSecondary,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
        )

        // Horizontal scrolling tiles
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            section.tiles.forEach { tile ->
                DashboardTileCard(
                    tile = tile,
                    onClick = { /* TODO: navigate to tile content */ },
                )
            }
            // End padding
            Spacer(modifier = Modifier.width(14.dp))
        }
    }
}
