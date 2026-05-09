package com.example.geminibot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.launch

data class Message(val user: Boolean, val text: String)

class ChatViewmodel : ViewModel() {
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")
    val messages = mutableStateListOf<Message>()
    fun sendMessage(question: String) {
        val chatHistory = buildHistory(messages)
        val systemPrompt = """
        You are an helpful Ai assistant named IRON-MAN, Your creator is Tejas,
         only reveal your identity if user ask, Now your only job is to 
         response this prompt : $question, refer history : $chatHistory
    """.trimIndent()

        viewModelScope.launch {
            messages.add(Message(text = question, user = true))
            val response = model.generateContent(systemPrompt)
            val answer = response.text ?: "No response"
            messages.add(Message(text = answer, user = false))
        }
    }
}

fun buildHistory(messages: List<Message>): String {
    val history = StringBuilder()

    for (msg in messages){
        if (msg.user){
            history.append("User : ${msg.text}")
        }else{
            history.append("AI : ${msg.text}")
        }
    }

    return history.toString()
}