package com.vuhm.note_app.android.note_list

import com.vuhm.note_app.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)