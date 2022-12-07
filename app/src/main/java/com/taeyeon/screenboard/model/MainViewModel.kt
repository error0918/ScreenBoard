package com.taeyeon.screenboard.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel() {
    private var _time = MutableLiveData(Calendar.getInstance())
    val time: LiveData<Calendar> get() = _time

    init {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(50)
                _time.value = Calendar.getInstance()
            }
        }
    }
}