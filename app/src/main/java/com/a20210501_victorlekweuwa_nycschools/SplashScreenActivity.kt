package com.a20210501_victorlekweuwa_nycschools

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

// Splash Screen runs for 1 second before routing to the Main Activity
class SplashScreenActivity : AppCompatActivity() {

  private fun init() {

    hideSystemUI()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      window.attributes.layoutInDisplayCutoutMode =
        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }

  }

  private fun runSplash() {

    val handler = Handler()

    val r = Runnable {

      startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

      // close splash activity
      finish()

    }

    handler.postDelayed(r, 1000)

  }

  // Make screen full screen.
  // If I had more time, I would find out the un-deprecated alternatives
  private fun hideSystemUI() {

    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LOW_PROFILE
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_IMMERSIVE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

  }

  override fun onStart() {
    super.onStart()

    init()

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    runSplash()

  }

}