package com.a20210501_victorlekweuwa_nycschools

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.retrofit_activity)

        addFontToAppBarTitle(supportActionBar!!, this)

    }


    // Adds font to the AppBar Title using R.font.dancingscriptvariablefontwght
    private fun addFontToAppBarTitle(supportActionBar: ActionBar, applicationContext: Context) {

        val actionBar: ActionBar = supportActionBar
        val tv: TextView = TextView(applicationContext)
        val typeface: Typeface = ResourcesCompat.getFont(applicationContext, R.font.dancingscriptvariablefontwght)!!
        val lp: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ); // Height of TextView
        tv.layoutParams = lp
        tv.text = "NYCSchools" // ActionBar title text
        tv.textSize = 30f
        tv.setTextColor(Color.parseColor("#e64a19"))
        tv.setTypeface(typeface, Typeface.BOLD)
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.customView = tv

    }

}