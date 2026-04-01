package com.kohala.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kohala.android.ui.theme.*

/**
 * Top bar with user profile and quick-access icons.
 */
@Composable
fun DashboardTopBar(
    username: String = "Player1",
    score: String = "2,450",
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.85f))
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Home indicator
        Icon(
            imageVector = Icons.Default.Gamepad,
            contentDescription = "Home",
            modifier = Modifier.size(24.dp),
            tint = AccentGreen,
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Profile avatar placeholder
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(AccentGreen),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(20.dp),
                tint = Color.White,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Username and score
        Column {
            Text(
                text = username,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Score",
                    modifier = Modifier.size(12.dp),
                    tint = TextSecondary,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = score,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextSecondary,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Right-side icons
        TopBarIcon(Icons.Default.People, "Friends", badgeCount = 12)
        Spacer(modifier = Modifier.width(20.dp))
        TopBarIcon(Icons.Default.Email, "Messages", badgeCount = 3)
        Spacer(modifier = Modifier.width(20.dp))
        TopBarIcon(Icons.Default.Notifications, "Notifications", badgeCount = 1)
        Spacer(modifier = Modifier.width(20.dp))
        TopBarIcon(Icons.Default.Settings, "Settings")
    }
}

@Composable
private fun TopBarIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    badgeCount: Int? = null,
) {
    Box(
        modifier = Modifier
            .clickable { /* TODO: navigation */ }
            .padding(4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp),
            tint = TextSecondary,
        )
        if (badgeCount != null && badgeCount > 0) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .clip(CircleShape)
                    .background(AccentGreen),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (badgeCount > 9) "9+" else badgeCount.toString(),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }
    }
}
