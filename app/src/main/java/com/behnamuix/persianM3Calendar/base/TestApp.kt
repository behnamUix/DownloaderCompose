package com.behnamuix.persianM3Calendar.base

import android.app.Application
import com.behnamuix.persianM3Calendar.di.client
import com.behnamuix.persianM3Calendar.di.repositoryModule
import com.behnamuix.persianM3Calendar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    client
                )
            )
        }
    }
}
