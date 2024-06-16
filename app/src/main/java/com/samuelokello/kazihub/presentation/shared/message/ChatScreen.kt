package com.samuelokello.kazihub.presentation.shared.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@Preview(showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    KaziHubTheme {
        ChatScreen()
    }
}

@Destination
@Composable
fun ChatScreen() {
    var chatMessages by rememberSaveable { mutableStateOf(listOf<ChatUiModel.Message>()) }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (chatBox, chatList) = createRefs()

        val listState = rememberLazyListState()
        LaunchedEffect( Unit) {
            listState.animateScrollToItem(chatMessages.size)
        }
        ChatBox(
            onSendChatClickListener = {},
            modifier =
                Modifier
                    .constrainAs(chatBox) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
        )
        LazyColumn(
            state = listState,
            modifier =
                Modifier
                    .constrainAs(chatList) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(chatBox.top)
                        height = Dimension.fillToConstraints
                    },
        ) {
            items(chatMessages) { message ->
                ChatItem(message)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier,
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier.padding(16.dp)) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            modifier =
                Modifier
                    .weight(1f)
                    .padding(4.dp),
            shape = RoundedCornerShape(24.dp),
            colors =
                TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
            placeholder = {
                Text(text = "Type something")
            },
        )
        IconButton(
            onClick = {
                val msg = chatBoxValue.text
                if (msg.isBlank()) return@IconButton
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            modifier =
                Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    .align(Alignment.CenterVertically),
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send",
                tint = Color.White,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp),
            )
        }
    }
}

@Composable
fun ChatItem(message: ChatUiModel.Message) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = if (message.isFromMe) 48f else 0f,
                            bottomEnd = if (message.isFromMe) 0f else 48f,
                        ),
                    ).background(
                        if (message.isFromMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                    ).padding(16.dp),
        ) {
            Text(text = message.text)
        }
    }
}
