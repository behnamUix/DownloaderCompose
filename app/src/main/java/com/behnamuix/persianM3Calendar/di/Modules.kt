package com.behnamuix.persianM3Calendar.di

import com.behnamuix.persianM3Calendar.data.remote.downloader.repository.DownloaderRepository
import com.behnamuix.persianM3Calendar.viewModel.DownloaderViewModel
import com.behnamuix.persianM3Calendar.viewModel.PersianCalendarViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val client = module {
    single {
        OkHttpClient.Builder()
            .build()
    }
}


val repositoryModule = module {
    single { DownloaderRepository(get()) }
}


val viewModelModule = module {
    viewModel { DownloaderViewModel(get()) }
}



