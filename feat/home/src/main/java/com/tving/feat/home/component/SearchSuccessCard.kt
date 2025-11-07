package com.tving.feat.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tving.core.designsystem.component.ThumbnailComponent
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.home.HomeContract
import com.tving.feat.home.R

@Composable
fun SearchSuccessCard(
    modifier: Modifier = Modifier,
    state: HomeContract.HomeState,
) {
    Column(
        modifier = Modifier
            .background(color = White)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.text_search_results),
            color = Black,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.text_featured_video),
            color = Black,
        )

        ThumbnailComponent(
            modifier = Modifier.size(width = 300.dp, height = 240.dp),
            favoriteOnOff = true,
            showBottomFavoriteState = true
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFF212121
)
fun PreviewSearchSuccessCard() {
    SearchSuccessCard(
        state = HomeContract.HomeState()
    )
}