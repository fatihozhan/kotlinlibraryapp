package com.ozhan.libraryapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 2, exportSchema = false)
abstract class BooksDB : RoomDatabase() {
    abstract fun bookDao(): BookDAO

    //Companion Object
    companion object {
        @Volatile
        private var INSTANCE: BooksDB? = null

        fun getInstance(context: Context): BooksDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BooksDB::class.java,
                        "books_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}