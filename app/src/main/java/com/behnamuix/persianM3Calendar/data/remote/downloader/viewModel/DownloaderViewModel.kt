package com.behnamuix.persianM3Calendar.viewModel

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.persianM3Calendar.data.remote.downloader.repository.DownloaderRepository
import com.behnamuix.persianM3Calendar.data.remote.errorHandler.DownloadResult
import com.behnamuix.persianM3Calendar.model.DownloadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File

class DownloaderViewModel(val downloaderRepo: DownloaderRepository) : ViewModel() {
    var progress = MutableStateFlow<Float>(0f)
        private set

    var state = MutableStateFlow<DownloadState>(DownloadState.Idle)
        private set

    var errorCount = MutableStateFlow(0)
        private set

    var total = MutableStateFlow(0L)
        private set
    var downloaded = MutableStateFlow(0L)
        private set

    private var job: Job? = null

    fun startDownload(context: Context) {
        job?.cancel()
        if (state.value == DownloadState.Downloading) {
            Log.d("LOG", "download is running now")
        } else {
            state.value = DownloadState.Downloading
            progress.value = 0f
            job = viewModelScope.launch(Dispatchers.IO) {
                var dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                var file = File(dir, "calendar.apk")

                downloaderRepo.downloadFile(
                    file,
                    "https://github.com/behnamUix/PersianM3Calendar/releases/download/v1.0.0/calendar.apk"
                ).collect {
                    when (it) {
                        is DownloadResult.Progress -> {

                            progress.value = it.percent
                            downloaded.value = it.downloaded
                            total.value = it.total
                        }

                        is DownloadResult.Success -> {
                            state.value = DownloadState.Completed
                        }

                        is DownloadResult.Error -> {
                            state.value = DownloadState.Failed
                            errorCount.value += 1
                            Log.d("ERROR", it.message)
                        }
                    }
                }

            }
        }
    }

    fun cancelDownload() {
        job?.cancel()
        viewModelScope.launch {

            state.emit(DownloadState.Cancelled)
        }
    }


}