package com.zx.marketnew_base.system.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.zx.zxutils.views.ZXStatusBarCompat

class FastSplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ZXStatusBarCompat.translucent(this)
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SplashActivity.startAction(this, false)
        overridePendingTransition(0, 0)
        finish()
    }
}
