package com.example.biblion

import androidx.room.Room
import com.example.biblion.Repository.MainRepository
import com.example.biblion.Repository.UserRepository
import com.example.biblion.Repository.BookRepository
import com.example.biblion.Room.AppDatabase
import com.example.biblion.ViewModel.DeliveryViewModel
import com.example.biblion.ViewModel.FavoriteViewModel
import com.example.biblion.ViewModel.UserViewModel
import com.example.biblion.ViewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "biblion_db"
        ).fallbackToDestructiveMigration(true) //
            .build()
    }
    single { get<AppDatabase>().favoriteBookDao() }

    single { MainRepository() }
    single { UserRepository() }
    single { BookRepository(get(), get()) }

    viewModel { (userEmail: String) -> FavoriteViewModel(get(), userEmail) }
    viewModel { UserViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DeliveryViewModel() }
}