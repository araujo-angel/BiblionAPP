package com.example.biblion.Activity.Dashboard

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.biblion.Activity.DetailEachBook.DetailEachBookActivity
import com.example.biblion.R
import com.example.biblion.Room.FavoriteBookEntity
import com.example.biblion.ViewModel.FavoriteUiState
import com.example.biblion.ViewModel.FavoriteViewModel
import com.example.biblion.ui.theme.BiblionTheme
import org.koin.core.parameter.parametersOf
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavoriteBooksScreen(
    userEmail: String,
    onBackClick: () -> Unit,
    viewModel: FavoriteViewModel = koinViewModel { parametersOf(userEmail) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BiblionTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = colorResource(R.color.pink)
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            "Meus Favoritos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                }

                when (uiState) {
                    is FavoriteUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = colorResource(R.color.pink))
                        }
                    }
                    is FavoriteUiState.Empty -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Nenhum livro favorito encontrado", color = Color.Black)
                        }
                    }
                    is FavoriteUiState.Success -> {
                        val favoriteBooks = (uiState as FavoriteUiState.Success).books
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(favoriteBooks) { book ->
                                FavoriteBookItem(
                                    book = book,
                                    onRemoveClick = { viewModel.removeFavorite(book.bookId) },
                                    onBookClick = {
                                        val intent = Intent(context, DetailEachBookActivity::class.java)
                                        intent.putExtra("bookId", book.bookId)
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                    is FavoriteUiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Erro ao carregar favoritos", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteBookItem(
    book: FavoriteBookEntity,
    onRemoveClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onBookClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(model = book.imagePath),
                contentDescription = "Capa do livro",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(book.title, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text("R$ ${book.price}", color = colorResource(R.color.pink))
            }
            IconButton(onClick = onRemoveClick) {
                Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "Remover", tint = colorResource(R.color.pink))
            }
        }
    }
}
