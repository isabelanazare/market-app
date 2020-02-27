package com.example.market.utils

import android.util.Log
//i - information
fun Any.logi(message: Any? = "no message!") {
    Log.i(this.javaClass.simpleName, message.toString())
}
// d - debug
fun Any.logd(message: Any? = "no message!", cause: Throwable? = null) {
    Log.d(this.javaClass.simpleName, message.toString(), cause)
}
//w - warning
fun Any.logw(message: Any? = "no message!", cause: Throwable? = null) {
    Log.w(this.javaClass.simpleName, message.toString(), cause)
}
//e - error
fun Any.loge(message: Any? = "no message!", cause: Throwable? = null) {
    Log.e(this.javaClass.simpleName, message.toString(), cause)
}