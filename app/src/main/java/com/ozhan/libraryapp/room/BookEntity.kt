package com.ozhan.libraryapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey(true)
    val id: Int,
    val title: String,
)
