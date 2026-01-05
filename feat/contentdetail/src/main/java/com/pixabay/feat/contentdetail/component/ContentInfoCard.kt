package com.pixabay.feat.contentdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StatInfo(
    val label: String,
    val value: String
)

@Composable
fun ContentInfoCard(
    stats: List<StatInfo>,
    tags: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stats.forEach { stat ->
                StatItem(label = stat.label, value = stat.value)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tags",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = tags,
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewContentInfoCard() {
    ContentInfoCard(
        stats = listOf(
            StatInfo(label = "Type", value = "Photo"),
            StatInfo(label = "Views", value = "2,781"),
            StatInfo(label = "Likes", value = "9"),
            StatInfo(label = "Downloads", value = "9")
        ),
        tags = "bejaia • algerie • mediterranean • blue • sea • nature • water • bay • coast • clouds • heaven • summer"
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewContentInfoCardVideo() {
    ContentInfoCard(
        stats = listOf(
            StatInfo(label = "Type", value = "Video"),
            StatInfo(label = "Views", value = "15,432"),
            StatInfo(label = "Likes", value = "256"),
            StatInfo(label = "Downloads", value = "89")
        ),
        tags = "nature • travel • landscape • mountain • adventure • outdoor"
    )
}