package com.behnamuix.persianM3Calendar.data.remote.downloader.repository

import android.util.Log
import com.behnamuix.persianM3Calendar.data.remote.errorHandler.DownloadResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.ensureActive
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class DownloaderRepository(private val client: OkHttpClient) {
    fun downloadFile(outputFile: File?, url: String): Flow<DownloadResult> {
        var total = 0L
        var downloadedBytes = 0L
        return flow {
            val req = Request.Builder().apply {
                url(url)
            }.build()
            try {
                client.newCall(req).execute().use { resp ->
                    if (resp.isSuccessful) {
                        resp.body?.let { body ->
                            total = body.contentLength()
                            body.byteStream().use { input ->
                                FileOutputStream(outputFile).use { file ->
                                    val buffer = ByteArray(8192)
                                    var readByte: Int
                                    while (input.read(buffer).also { readByte = it } != -1) {
                                        currentCoroutineContext().ensureActive()
                                        file.write(buffer, 0, readByte)
                                        downloadedBytes += readByte

                                        emit(
                                            DownloadResult.Progress(
                                                downloadedBytes.toFloat() / total,
                                                code = resp.code,
                                                total,
                                                downloadedBytes,
                                            )
                                        )
                                    }
                                    emit(DownloadResult.Success)
                                }
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
                emit(DownloadResult.Error(e.message.toString()))
                e.printStackTrace()
            }

        }


    }


}