package com.ozhan.libraryapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDAO {

    @Insert
    suspend fun addBook(bookEntity : BookEntity)

    @Query("Select * from BookEntity")
    fun getAllBooks():Flow<List<BookEntity>>

    @Delete
    suspend fun deleteBook(bookEntity: BookEntity)

    @Update
    suspend fun updateBook(bookEntity: BookEntity)


}