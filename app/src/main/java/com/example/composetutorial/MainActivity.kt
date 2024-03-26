package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    MessageCard(Message("Android", "Jetpack compose"))
                }
            }
        }
    }
}

data class Message(val author : String, val body: String)

@Composable
fun MessageCard(msg : Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription ="Contact profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.5.dp, MaterialTheme.colorScheme.primary)

        )
        Spacer(modifier = Modifier.width(10.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "",
        )


        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = "Hello ${msg.author}!",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium

                )

            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark mode"
)
@Composable
fun MessageCardPreview() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(msg = Message(author = "João", body = "Hey, take a look to jetpack compose "))
        }

    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message -> MessageCard(msg = message) }
    }
}

@Preview(name = "Conversation")
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}

