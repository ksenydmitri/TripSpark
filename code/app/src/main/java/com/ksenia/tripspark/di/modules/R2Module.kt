package com.ksenia.tripspark.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object R2Module {

    private fun loadProperties(context: Context): Map<String, String> {
        return context.assets.open("local.properties").bufferedReader().use { reader ->
            reader.readLines().associate {
                val (key, value) = it.split("=")
                key.trim() to value.trim()
            }
        }
    }

    @Provides
    @Named("workerEndpoint")
    fun provideWorkerEndpoint(@ApplicationContext context: Context): String {
        val props = loadProperties(context)
        return props["WORKER_ENDPOINT"]
            ?: error("Missing WORKER_ENDPOINT in local.properties")
    }
}
