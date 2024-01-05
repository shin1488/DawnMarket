package kr.co.teamfresh.syc.dawnmarket.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    fun showDevelopmentToast(context: Context) {
        Toast.makeText(context, "개발 예정", Toast.LENGTH_SHORT).show()
    }

    fun showAddedToCartToast(context: Context, productName: String) {
        Toast.makeText(context, "${productName}이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
    }
}