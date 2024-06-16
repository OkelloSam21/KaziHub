package com.samuelokello.kazihub.presentation.shared.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.presentation.destinations.ChatScreenDestination
import com.samuelokello.kazihub.ui.theme.KaziHubTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Destination
@Composable
fun MessagesScreen(viewModel: MessageViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    Surface (modifier = Modifier.fillMaxSize()){
        KaziHubTheme {

            val messages = viewModel.messageList
            MessageScreenContent(
                navigator = navigator,
                messageList = messages,
                onEvent =  {
                MessageEvent.OnMessageItemClick(messages.first().id)

            })
        }
    }

}

@Composable
fun MessageScreenContent(
    messageList: List<MessageUiState>,
    onEvent:(MessageEvent) -> Unit,
    navigator: DestinationsNavigator
) {
    Column (
        Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn {
            items(messageList) {
                MessageListItem(message = it,
                    onClick = {
                        onEvent(MessageEvent.OnMessageItemClick(messageList.indexOf(it)))
                        navigator.navigate(ChatScreenDestination)
                    }
                )

                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageListItem(
    message: MessageUiState,
    onClick: () -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { onClick() }
    ){
        Image(
            painterResource(message.image),
            contentDescription = null,
            Modifier
                .clip(CircleShape)
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = message.sender)
            Spacer(modifier = Modifier.height(9.dp))
            Text(text = message.message, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
        }
    }
}

data class MessageUiState(
    val id: Int = 0,
    val message: String = "",
    val sender: String = "",
    val image: Int = 0
)


@HiltViewModel
class MessageViewModel @Inject constructor(): ViewModel() {

    val messageList = listOf<MessageUiState>(
        MessageUiState (id = 1, message = "Hello", sender = "John", image = R.drawable.male_user),
        MessageUiState (id = 2, message = "Hi", sender = "Jane", image = R.drawable.male_user_2),
        MessageUiState(id = 3, message = "How are you", sender = "John", image = R.drawable.male_user),
        MessageUiState(id = 4, message = "I'm fine", sender = "Jane", image = R.drawable.male_user_2),
        MessageUiState(id = 5, message = "I'm good", sender = "John", image = R.drawable.male_user),
        MessageUiState (id = 1, message = "Hello", sender = "John", image = R.drawable.male_user),
        MessageUiState (id = 2, message = "Hi", sender = "Jane", image = R.drawable.male_user_2),
        MessageUiState(id = 3, message = "How are you", sender = "John", image = R.drawable.male_user),
        MessageUiState(id = 4, message = "I'm fine", sender = "Jane", image = R.drawable.male_user_2),
        MessageUiState(id = 5, message = "I'm good", sender = "John", image = R.drawable.male_user),
    )
}





