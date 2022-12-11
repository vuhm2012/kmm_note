package com.vuhm.note_app.data.note

import com.vuhm.note_app.domain.note.Note
import com.vuhm.note_app.domain.note.NoteDataSource
import com.vuhm.note_app.domain.time.DateTimeUtil
import com.vuhm.noteappkmm.database.NoteDatabse

class SqlDelightNoteDataSource(db: NoteDatabse) : NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNote(id)
    }
}