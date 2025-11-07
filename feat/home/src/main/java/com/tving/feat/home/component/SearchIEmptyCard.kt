package com.tving.feat.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.home.R

@Composable
fun SearchEmptyCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(52.dp),
            painter = painterResource(id = R.drawable.ic_empty),
            contentDescription = "buttonClose",
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.text_empty),
            color = Black,
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
fun PreviewSearchEmptyCard() {
    SearchEmptyCard()
}