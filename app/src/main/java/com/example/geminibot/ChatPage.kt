package com.example.geminibot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {

    val viewmodel: ChatViewmodel = ChatViewmodel()
    val message = viewmodel.messages

    Column(modifier = modifier
        .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
                .weight(1f)
        ) {
            items(message) {txt ->
                MessageBubble(txt)
            }
        }

        MessageInput(
            onSend = {
                viewmodel.sendMessage(it)
            }
        )
    }
}

@Composable
fun MessageBubble(msg: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (msg.user) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = msg.text,
            fontSize = 20.sp
        )
    }
}

@Composable
fun MessageInput(onSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = {
                message = it
            },
            shape = RoundedCornerShape(15.dp),
            placeholder = {Text(text = "Ask anything...")},
            trailingIcon = {
                IconButton(
                    onClick = {
                        onSend(message)
                        message = ""
                    }
                ) {
                    Icon(Icons.Default.Send, contentDescription = "send")
                }
            }
        )
    }
}
