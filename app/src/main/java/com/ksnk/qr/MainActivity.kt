package com.ksnk.qr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.ksnk.qr.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) viewModel.scanResult.value = result.contents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ResultText(result = viewModel.scanResult.value ?: "I'm waiting for the scan")
                ScanButton(onScanClick = ::scan)
            }
        }
    }

    private fun scan() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR Code")
        options.setOrientationLocked(true)
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        scanLauncher.launch(options)
    }
}

@Composable
fun ResultText(result: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = result)
    }
}

@Composable
fun ScanButton(onScanClick: (() -> Unit)?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(modifier = Modifier.padding(15.dp), onClick = { onScanClick?.invoke() }) {
            Text(text = "Scan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScannerPreview() {
    MyApplicationTheme {
        ResultText(result = "result")
        ScanButton(onScanClick = null)
    }
}