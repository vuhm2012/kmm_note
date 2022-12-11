package com.vuhm.note_app.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vuhm.note_app.domain.note.Note
import com.vuhm.note_app.domain.note.NoteDataSource
import com.vuhm.note_app.domain.note.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    private val notes = saveStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = saveStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = saveStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

//    init {
//        viewModelScope.launch {
//            (1..100).forEach {
//                noteDataSource.insertNote(
//                    Note(
//                        id = null,
//                        title = "Note $it",
//                        content = "Content $it",
//                        colorHex = redOrangeHex,
//                        created = DateTimeUtil.now()
//                    )
//                )
//            }
//        }
//    }

    fun loadNotes() {
        viewModelScope.launch {
            saveStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchText(text: String) {
        saveStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        saveStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            saveStateHandle["searchText"] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}