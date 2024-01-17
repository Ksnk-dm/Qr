package com.ksnk.qr

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val scanResult = mutableStateOf<String?>(null)
}