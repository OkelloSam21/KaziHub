package com.samuelokello.kazihub.presentation.shared.message

import androidx.compose.foundation.Image
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.samuelokello.kazihub.R
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@Destination
@Composable
fun MessagesScreen() {
    Surface (modifier = Modifier.fillMaxSize()){
        KaziHubTheme {
            MessageScreenContent()
        }
    }

}

@Composable
fun MessageScreenContent() {
    Column (
        Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn {
            items(messageList) {
                MessageListItem(message = it, onClick = {})
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageListItem(
    message: Message,
    onClick: () -> Unit,
) {
    Row (modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)){
        Image(
            painterResource(message.Imgae),
            contentDescription = null,
            Modifier.clip(CircleShape).size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = message.sender)
            Spacer(modifier = Modifier.height(9.dp))
            Text(text = message.message, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
        }
    }
}

data class Message(
    val id: Int,
    val message: String,
    val sender: String,
    val Imgae: Int
)

val messageList = listOf<Message>(
    Message (id = 1, message = "Hello", sender = "John", Imgae = R.drawable.male_user),
    Message (id = 2, message = "Hi", sender = "Jane", Imgae = R.drawable.male_user_2),
    Message(id = 3, message = "How are you", sender = "John", Imgae = R.drawable.male_user),
    Message(id = 4, message = "I'm fine", sender = "Jane", Imgae = R.drawable.male_user_2),
    Message(id = 5, message = "I'm good", sender = "John", Imgae = R.drawable.male_user),
    Message (id = 1, message = "Hello", sender = "John", Imgae = R.drawable.male_user),
    Message (id = 2, message = "Hi", sender = "Jane", Imgae = R.drawable.male_user_2),
    Message(id = 3, message = "How are you", sender = "John", Imgae = R.drawable.male_user),
    Message(id = 4, message = "I'm fine", sender = "Jane", Imgae = R.drawable.male_user_2),
    Message(id = 5, message = "I'm good", sender = "John", Imgae = R.drawable.male_user),
    )