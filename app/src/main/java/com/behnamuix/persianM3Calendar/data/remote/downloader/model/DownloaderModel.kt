package com.behnamuix.persianM3Calendar.model

class DownloaderModel(
    val name: String,
    val progress: Float,
    val state: DownloadState = DownloadState.Idle,
    val url: String,
    val size: Long,
    val downloadedBytes: Long
)

enum class DownloadState {
    Idle,
    Downloading,
    Completed,
    Failed,
    Cancelled

}