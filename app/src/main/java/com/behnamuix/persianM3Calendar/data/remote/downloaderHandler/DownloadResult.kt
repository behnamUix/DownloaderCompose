package com.behnamuix.persianM3Calendar.data.remote.errorHandler

sealed class DownloadResult() {
    data class Progress(val percent: Float, val code: Int, val total: Long, val downloaded: Long) :
        DownloadResult()

    object Success : DownloadResult()
    data class Error(val message: String) : DownloadResult()

}