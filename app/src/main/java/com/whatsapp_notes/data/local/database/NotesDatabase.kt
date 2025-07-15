package com.whatsapp_notes.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.whatsapp_notes.data.local.converters.DateConverters
import com.whatsapp_notes.data.local.dao.NoteDao
import com.whatsapp_notes.data.local.dao.ThreadDao
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.data.local.entities.ThreadEntity

@Database(entities = [NoteEntity::class, ThreadEntity::class], version = 4, exportSchema = false)
// VERSION IS 3 NOW
@TypeConverters(DateConverters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun threadDao(): ThreadDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                )
                   // .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Add MIGRATION_2_3 here
                    .fallbackToDestructiveMigration() // THIS WILL WIPE ALL DATA ON SCHEMA CHANGE
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration from version 1 to 2 (e.g., adding index to ThreadEntity)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Room generally handles index creation when specified in @Entity if it's the only schema change.
                // If you had complex changes, you'd put SQL here.
                // For example: database.execSQL("CREATE INDEX index_threads_noteOwnerId ON threads (noteOwnerId)");
                // Or if you added a new column to an existing table:
                // database.execSQL("ALTER TABLE notes ADD COLUMN newColumn TEXT");
            }
        }

        // NEW MIGRATION from version 2 to 3
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add SQL commands for any changes made between version 2 and 3.
                // If you didn't explicitly add anything *new* to entities or tables
                // between incrementing from 2 to 3, then this migrate method can be empty,
                // but the Migration object still needs to be provided.
                // For example, if you just bumped the version without new schema changes:
                // No explicit SQL needed if the schema implicitly updated or no new changes.
                // If you added a column, for instance:
                // database.execSQL("ALTER TABLE NoteEntity ADD COLUMN newNoteField TEXT");
            }
        }
    }
}