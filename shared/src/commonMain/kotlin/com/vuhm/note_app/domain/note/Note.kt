package com.vuhm.note_app.domain.note

import com.vuhm.note_app.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(redOrangeHex, redPinkHex, lightGreenHex, babyBlueHex, violetHex)

        fun generateRandomColor() = colors.random()
    }
}
