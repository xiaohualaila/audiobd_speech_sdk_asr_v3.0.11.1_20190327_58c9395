package com.aier.speech.recognizer.util

import android.widget.Toast
import es.dmoral.toasty.Toasty


object ToastyUtil {

    fun showError(msg: String) {
        Toasty.error(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }

    fun showSuccess(msg: String) {
        Toasty.success(Utils.getContext(), msg, Toast.LENGTH_SHORT, true).show()
    }

    fun showNormal(msg: String) {
        Toasty.normal(Utils.getContext(), msg, Toast.LENGTH_SHORT).show()
    }
    fun showInfo(msg: String) {
        Toasty.normal(Utils.getContext(), msg, Toast.LENGTH_SHORT).show()
    }
}
