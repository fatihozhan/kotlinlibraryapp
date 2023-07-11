package com.ozhan.libraryapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ozhan.libraryapp.room.BookEntity
import com.ozhan.libraryapp.viewmodel.BookViewModel

@Composable
fun UpdateScreen(viewModel: BookViewModel, bookId: String?, navController: NavHostController) {
    var inputBook by remember {
        mutableStateOf("")
    }
    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Update The Existing Book ", fontSize = 24.sp)
        OutlinedTextField(
//            modifiler = Modifier.padding(16.dp),
            value = inputBook,
            onValueChange = { it ->
                inputBook = it
            },
            label = { Text(text = "Update Book Name") },
            placeholder = { Text(text = "New Book Name") })
        Button(
            onClick = {
                var newBook = BookEntity(bookId!!.toInt(), inputBook)
                viewModel.updateBook(newBook)
            },
            modifier = Modifier.padding(16.dp), colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Update Book")
        }
    }
}


