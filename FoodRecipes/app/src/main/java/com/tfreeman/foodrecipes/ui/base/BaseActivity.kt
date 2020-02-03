package com.tfreeman.foodrecipes.ui.base

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tfreeman.foodrecipes.R


abstract class BaseActivity : AppCompatActivity() {
    var mProgressBar: ProgressBar? = null
    override fun setContentView(layoutResID: Int) {
        val constraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout =
            constraintLayout.findViewById<FrameLayout>(R.id.activity_content)
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar)
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
    }

    fun showProgressBar(visible: Boolean) {
        mProgressBar!!.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}