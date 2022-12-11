package com.vuhm.note_app.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.vuhm.note_app.data.local.DatabaseDriverFactory
import com.vuhm.note_app.data.note.SqlDelightNoteDataSource
import com.vuhm.note_app.domain.note.NoteDataSource
import com.vuhm.noteappkmm.database.NoteDatabse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabse(driver))
    }
}