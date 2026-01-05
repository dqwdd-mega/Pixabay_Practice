package com.pixabay.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pixabay.core.designsystem.R
import com.pixabay.core.designsystem.theme.ColorTokens.Black
import com.pixabay.core.designsystem.theme.ColorTokens.Black212121
import com.pixabay.core.designsystem.theme.ColorTokens.White

@Composable
fun PixabayInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "입력해 주세요",
    placeholderTextColor: Color = Black212121,
    textAlign: TextAlign = TextAlign.Start,
    singleLine: Boolean = true,
    strokeColor: Color = Black212121,
    background: Color = White,
    shape: Shape = RoundedCornerShape(8.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    hasLeftContent: Boolean = false,
    hasRightContent: Boolean = false,
    leftContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
) {
    val isValueEmpty = value.isEmpty()

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, strokeColor, shape)
                    .background(background, shape)
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isFocused = interactionSource.collectIsFocusedAsState().value

                if (hasLeftContent) {
                    leftContent()
                }
                Box(
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 10.dp)
                        .defaultMinSize(minHeight = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isValueEmpty && !isFocused) {
                        Text(
                            text = placeholderText,
                            color = placeholderTextColor,
                            textAlign = textAlign
                        )
                    }
                    innerTextField()
                }

                if (hasRightContent) {
                    Spacer(modifier = Modifier.weight(1f))
                    rightContent()
                }
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewPixabayInputTextField() {
    var text by remember { mutableStateOf("") }
    PixabayInputTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSearchInputTextField() {
    var text by remember { mutableStateOf("") }
    PixabayInputTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        placeholderText = "Search videos and images...",
        hasLeftContent = true,
        hasRightContent = true,
        leftContent = {
            Image(
                modifier = Modifier
                    .padding(start = 10.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
            )
        },
        rightContent = {
            Image(
                modifier = Modifier
                    .padding(end = 20.dp),
                painter = painterResource(id = R.drawable.ic_close_circle),
                contentDescription = "close button",
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Black212121)
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Cancel",
                color = Black,
            )
        }
    )
}