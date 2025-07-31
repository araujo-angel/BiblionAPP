package com.example.biblion.Activity.Dashboard

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.biblion.Activity.ItemsList.ItemsListActivity
import com.example.biblion.Domain.CategoryModel
import com.example.biblion.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun SearchableCategorySection(
    categories: SnapshotStateList<CategoryModel>,
    showCategoryLoading: Boolean
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current // pega o gerenciador de foco

    Column(modifier = Modifier.fillMaxWidth()) {
        // Campo de busca
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = {
                Text(
                    text = "Buscar categoria...",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable {
                            focusManager.clearFocus()  // Remove o foco, teclado some
                        }
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(R.color.grey),
                focusedBorderColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                textColor = colorResource(R.color.black),
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
                .background(colorResource(R.color.grey), CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de categorias filtradas
        val filteredCategories = if (searchQuery.isEmpty()) {
            categories
        } else {
            categories.filter {
                it.CategoryName.contains(searchQuery, ignoreCase = true)
            }
        }

        CategorySection(
            categories = SnapshotStateList<CategoryModel>().apply { addAll(filteredCategories) },
            showCategoryLoading = showCategoryLoading
        )
    }
}
