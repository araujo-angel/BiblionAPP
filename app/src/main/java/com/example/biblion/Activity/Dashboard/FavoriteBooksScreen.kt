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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.biblion.Activity.DetailEachBook.DetailEachBookActivity
import com.example.biblion.Domain.BookModel
import com.example.biblion.Helper.FirebaseFavoritesHelper
import com.example.biblion.R
import com.example.biblion.Repository.BookRepository
import com.example.biblion.ui.theme.BiblionTheme
import com.example.biblion.ui.theme.Typography

@Composable
fun FavoriteBooksScreen(
    userId: String,
    onBackClick: () -> Unit,
    allBooks: List<BookModel> = BookRepository.allBooks
) {
    var favoriteIds by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    fun removeFavorite(bookId: String) {
        favoriteIds = favoriteIds.filter { it != bookId }
    }

    LaunchedEffect(Unit) {
        FirebaseFavoritesHelper.getUserFavorites(userId) {
            favoriteIds = it
            isLoading = false
        }
    }

    val favoriteBooks = allBooks.filter { favoriteIds.contains(it.Id.toString()) }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = colorResource(R.color.pink),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Meus Favoritos",
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(40.dp))
                }
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.pink),
                            strokeWidth = 4.dp
                        )
                    }
                } else if (favoriteBooks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Nenhum livro favorito encontrado",
                            style = MaterialTheme.typography.h6.copy(
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(favoriteBooks) { book ->
                            FavoriteBookItem(
                                book = book,
                                userId = userId,
                                onRemoveClick = {
                                    removeFavorite(book.Id.toString())
                                    FirebaseFavoritesHelper.toggleFavorite(
                                        userId,
                                        book.Id.toString(),
                                        false
                                    )
                                },
                                onBookClick = {
                                    val intent = Intent(context, DetailEachBookActivity::class.java)
                                    intent.putExtra("object", book)
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteBookItem(
    book: BookModel,
    userId: String,
    onRemoveClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onBookClick),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(R.color.white),
        elevation = 4.dp,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp)
        ) {
            val (image, title, price, removeButton) = createRefs()

            // Imagem do livro
            Image(
                painter = rememberAsyncImagePainter(model = book.ImagePath),
                contentDescription = "Capa do livro",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop
            )

            // Título do livro
            Text(
                text = book.Title,
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .constrainAs(title) {
                        top.linkTo(image.top)
                        start.linkTo(image.end)
                        end.linkTo(removeButton.start)
                    }
            )

            // Preço do livro
            Text(
                text = "R$ ${book.Price}",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.pink)
                ),
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .constrainAs(price) {
                        top.linkTo(title.bottom)
                        start.linkTo(image.end)
                    }
            )

            // Botão para remover dos favoritos
            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier.constrainAs(removeButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Remover dos favoritos",
                    tint = colorResource(R.color.pink)
                )
            }
        }
    }
}