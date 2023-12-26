package kr.co.teamfresh.syc.dawnmarket.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.teamfresh.syc.dawnmarket.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val i = Intent(this@SplashActivity, CategoryActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}