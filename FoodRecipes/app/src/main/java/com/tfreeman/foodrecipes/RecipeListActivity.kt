package com.tfreeman.foodrecipes


import android.os.Bundle
import android.view.View
import com.tfreeman.foodrecipes.ui.base.BaseActivity


class RecipeListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        findViewById<View>(R.id.test).setOnClickListener {
            if (mProgressBar!!.visibility == View.VISIBLE) {
                showProgressBar(false)
            } else {
                showProgressBar(true)
            }
        }
    }
}
