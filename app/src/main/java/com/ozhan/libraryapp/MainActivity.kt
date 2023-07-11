package com.ozhan.libraryapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ozhan.libraryapp.repository.Repository
import com.ozhan.libraryapp.room.BookEntity
import com.ozhan.libraryapp.room.BooksDB
import com.ozhan.libraryapp.screens.UpdateScreen
import com.ozhan.libraryapp.ui.theme.LibraryAppTheme
import com.ozhan.libraryapp.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    val db = BooksDB.getInstance(context)
                    val repository = Repository(db)
                    val viewModel = BookViewModel(repository)

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable("MainScreen") {
                            MainScreen(viewModel, navController = navController)

                        }
                        composable("UpdateScreen/{bookId}") {
                            UpdateScreen(
                                viewModel = viewModel,
                                bookId = it.arguments?.getString("bookId"),
                                navController = navController
                            )

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel, navController: NavHostController) {
    var inputBook by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 22.dp, start = 6.dp, end = 6.dp)
    ) {
        Text(text = "Insert Books in ROOM DB", fontSize = 22.sp)
        OutlinedTextField(value = inputBook,
            onValueChange = { enteredText -> inputBook = enteredText },
            label = { Text(text = "Book Name") },
            placeholder = { Text(text = "Enter Your Book Name") })
        Button(
            onClick = { viewModel.addBook(BookEntity(0, inputBook)) },
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )
        ) {
            Text(text = "Insert Book Into DB")

        }
        BooksList(viewModel = viewModel, navController)
    }
}


@Composable
fun BookCard(viewModel: BookViewModel, book: BookEntity, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "" + book.id,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                color = Color.Blue
            )
            Text(
                text = book.title,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                color = Color.Blue
            )
            Row(horizontalArrangement = Arrangement.End) {

                IconButton(onClick = { viewModel.deleteBook(book) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")

                }
                IconButton(onClick = { navController.navigate("UpdateScreen/${book.id}") }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")

                }
            }
        }
    }
}

@Composable
fun BooksList(viewModel: BookViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    Column(Modifier.padding(16.dp)) {

        Text(text = "My Library: ", fontSize = 24.sp, color = Color.Red)
        LazyColumn() {
            items(items = books) { item ->
                BookCard(viewModel = viewModel, book = item, navController)
            }
        }
    }
}