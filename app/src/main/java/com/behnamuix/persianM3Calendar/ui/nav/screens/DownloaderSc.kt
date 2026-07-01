package com.behnamuix.persianM3Calendar.ui.nav.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.behnamuix.persianM3Calendar.model.DownloadState
import com.behnamuix.persianM3Calendar.viewModel.DownloaderViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showSystemUi = true)
@Composable
fun DownloaderSc(
    downloaderVm: DownloaderViewModel = koinViewModel()
) {
    val progress by downloaderVm.progress.collectAsState()
    val state by downloaderVm.state.collectAsState()
    val error by downloaderVm.errorCount.collectAsState()
    val total by downloaderVm.total.collectAsState()
    val downloaded by downloaderVm.downloaded.collectAsState()
    val context = LocalContext.current

    val isDownloading = state == DownloadState.Downloading

    val statusColor = when (state) {
        DownloadState.Downloading -> Color(0xFF2196F3)
        DownloadState.Completed -> Color(0xFF4CAF50)
        DownloadState.Failed -> Color(0xFFF44336)
        DownloadState.Cancelled -> Color(0xFFFF9800)
        else -> Color(0xFF9E9E9E)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1020)),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF11182E)
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🔵 ICON + STATUS
                Box(contentAlignment = Alignment.Center) {

                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        color = statusColor,
                        trackColor = Color(0xFF2A3350)
                    )

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 📌 TITLE
                Text(
                    text = "دانلود آخرین نسخه تقویم",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // 🟡 STATUS CHIP
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = when (state) {
                            DownloadState.Idle -> "آماده"
                            DownloadState.Downloading -> "در حال دانلود"
                            DownloadState.Completed -> "تکمیل شد"
                            DownloadState.Cancelled -> "لغو شد"
                            DownloadState.Failed -> "خطا"
                        },
                        color = statusColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                // ❌ ERROR
                if (error > 0) {
                    Text(
                        text = "تعداد خطا: $error",
                        color = Color(0xFFF44336),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                // 📊 PROGRESS BAR
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50)),
                    color = statusColor,
                    trackColor = Color(0xFF2A3350)
                )
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(0.5f))
                        .clip(RoundedCornerShape(8.dp))
                ) {

                    val downloadedMB = downloaded / (1024f * 1024f)
                    val totalMB = total / (1024f * 1024f)
                    val remainingMB = (total - downloaded) / (1024f * 1024f)

                    Text(
                        "${"%.2f".format(downloadedMB)} MB / " +
                                "${"%.2f".format(totalMB)} MB | " +
                                "${"%.2f".format(remainingMB)} MB"
                    )

                }

                // 🎯 ACTION BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    if (isDownloading) {
                        OutlinedButton(
                            onClick = { downloaderVm.cancelDownload() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("لغو")
                        }
                    }

                    Button(
                        onClick = { downloaderVm.startDownload(context) },
                        enabled = !isDownloading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = statusColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = when (state) {
                                DownloadState.Completed -> "دانلود مجدد"
                                DownloadState.Failed -> "تلاش دوباره"
                                else -> "شروع دانلود"
                            }
                        )
                    }
                }
            }
        }
    }
}