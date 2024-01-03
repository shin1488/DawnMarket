package kr.co.teamfresh.syc.dawnmarket.utils

import android.content.Context
import android.widget.Toast

object DevelopmentToast {
    fun showToast(context: Context) {
        Toast.makeText(context, "개발 예정", Toast.LENGTH_SHORT).show()
    }
}