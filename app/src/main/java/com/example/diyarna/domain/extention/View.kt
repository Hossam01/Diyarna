package com.example.diyarna.domain.extention

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.diyarna.base.BaseActivity

inline fun <reified T : BaseActivity> Fragment.castToActivity(
    callback: (T?) -> Unit,
): T? {
    return if (requireActivity() is T) {
        callback(requireActivity() as T)
        requireActivity() as T
    } else {
        callback(null)
        null
    }

}

fun Context.showAppToast(message: String) {
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}
