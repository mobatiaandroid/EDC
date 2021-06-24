package com.dev.edc.common_classes

import android.util.Patterns

class AppUtils {
    companion object{
        fun isValidEmail(string: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(string).matches()
        }

    }
}