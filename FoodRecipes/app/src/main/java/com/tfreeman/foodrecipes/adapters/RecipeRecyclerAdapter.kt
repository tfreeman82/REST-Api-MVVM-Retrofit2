package com.tfreeman.foodrecipes.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tfreeman.foodrecipes.R
import com.tfreeman.foodrecipes.models.Recipe
import com.tfreeman.foodrecipes.util.Constants
import java.lang.String
import java.util.*

class RecipeRecyclerAdapter(private val mOnRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var mRecipes: MutableList<Recipe>? = null
    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        var view: View? = null
        return when (i) {
            RECIPE_TYPE -> {
                view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, mOnRecipeListener)
            }
            LOADING_TYPE -> {
                view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_loading_list_item, viewGroup, false)
                LoadingViewHolder(view)
            }
            EXHAUSTED_TYPE -> {
                view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_search_exhausted, viewGroup, false)
                SearchExhaustedViewHolder(view)
            }
            CATEGORY_TYPE -> {
                view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_category_list_item, viewGroup, false)
                CategoryViewHolder(view!!, mOnRecipeListener)
            }
            else -> {
                view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, mOnRecipeListener)
            }
        }
    }

    override fun onBindViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {
        val itemViewType = getItemViewType(i)
        if (itemViewType == RECIPE_TYPE) {
            val requestOptions: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
            Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mRecipes!![i].getImage_url())
                .into((viewHolder as RecipeViewHolder).image)
            (viewHolder as RecipeViewHolder).title.setText(mRecipes!![i].getTitle())
            (viewHolder as RecipeViewHolder).publisher.setText(mRecipes!![i].getPublisher())
            (viewHolder as RecipeViewHolder).socialScore.setText(
                String.valueOf(
                    Math.round(
                        mRecipes!![i].getSocial_rank()
                    )
                )
            )
        } else if (itemViewType == CATEGORY_TYPE) {
            val requestOptions: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
            val path = Uri.parse(
                "android.resource://com.codingwithmitch.foodrecipes/drawable/" + mRecipes!![i].getImage_url()
            )
            Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(path)
                .into((viewHolder as CategoryViewHolder).categoryImage)
            (viewHolder as CategoryViewHolder).categoryTitle.setText(mRecipes!![i].getTitle())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mRecipes!![position].getSocial_rank() == (-1).toFloat()) {
            CATEGORY_TYPE
        } else if (mRecipes!![position].getTitle().equals("LOADING...")) {
            LOADING_TYPE
        } else if (mRecipes!![position].getTitle().equals("EXHAUSTED...")) {
            EXHAUSTED_TYPE
        } else if (position == mRecipes!!.size - 1 && position != 0 && !mRecipes!![position].getTitle().equals(
                "EXHAUSTED..."
            )
        ) {
            LOADING_TYPE
        } else {
            RECIPE_TYPE
        }
    }

    fun setQueryExhausted() {
        hideLoading()
        val exhaustedRecipe = Recipe()
        exhaustedRecipe.setTitle("EXHAUSTED...")
        mRecipes!!.add(exhaustedRecipe)
        notifyDataSetChanged()
    }

    private fun hideLoading() {
        if (isLoading) {
            for (recipe in mRecipes!!) {
                if (recipe.getTitle().equals("LOADING...")) {
                    mRecipes!!.remove(recipe)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun displayLoading() {
        if (!isLoading) {
            val recipe = Recipe()
            recipe.setTitle("LOADING...")
            val loadingList: MutableList<Recipe> = ArrayList<Recipe>()
            loadingList.add(recipe)
            mRecipes = loadingList
            notifyDataSetChanged()
        }
    }

    private val isLoading: Boolean
        private get() {
            if (mRecipes != null) {
                if (mRecipes!!.size > 0) {
                    if (mRecipes!![mRecipes!!.size - 1].getTitle().equals("LOADING...")) {
                        return true
                    }
                }
            }
            return false
        }

    fun displaySearchCategories() {
        val categories: MutableList<Recipe> = ArrayList()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            val recipe = Recipe()
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i])
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i])
            recipe.setSocial_rank((-1).toFloat())
            categories.add(recipe)
        }
        mRecipes = categories
        notifyDataSetChanged()
    }

//    val itemCount: Int
//        get() = if (mRecipes != null) {
//            mRecipes!!.size
//        } else 0

    fun setRecipes(recipes: MutableList<Recipe>?) {
        mRecipes = recipes
        notifyDataSetChanged()
    }

    fun getSelectedRecipe(position: Int): Recipe? {
        if (mRecipes != null) {
            if (mRecipes!!.size > 0) {
                return mRecipes!![position]
            }
        }
        return null
    }

    companion object {
        private const val RECIPE_TYPE = 1
        private const val LOADING_TYPE = 2
        private const val CATEGORY_TYPE = 3
        private const val EXHAUSTED_TYPE = 4
    }

    override fun getItemCount(): Int {
        if (mRecipes != null) {
            return mRecipes!!.size
        }
        return 0
    }

}