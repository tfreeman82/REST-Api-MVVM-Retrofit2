package com.tfreeman.foodrecipes.repositories

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tfreeman.foodrecipes.models.Recipe
import com.tfreeman.foodrecipes.network.RecipeApiClient

class RecipeRepository private constructor() {
    private val mRecipeApiClient: RecipeApiClient
    private var mQuery: String? = null
    private var mPageNumber = 0
    private val mIsQueryExhausted: MutableLiveData<Boolean> = MutableLiveData()
    private val mRecipes: MediatorLiveData<List<Recipe>> = MediatorLiveData()

    private fun initMediators() {
        val recipeListApiSource: LiveData<List<Recipe>> =
            mRecipeApiClient.getRecipes()
        mRecipes.addSource(
            recipeListApiSource,
            object : Observer<List<Recipe?>?>() {
                fun onChanged(@Nullable recipes: List<Recipe>?) {
                    if (recipes != null) {
                        mRecipes.setValue(recipes)
                        doneQuery(recipes)
                    } else { // search database cache
                        doneQuery(null)
                    }
                }
            })
    }
    fun getRecipe(): LiveData<Recipe?>? {
        return mRecipeApiClient.getRecipe()
    }
    private fun doneQuery(list: List<Recipe>?) {
        if (list != null) {
            if (list.size % 30 != 0) {
                mIsQueryExhausted.value = true
            }
        } else {
            mIsQueryExhausted.setValue(true)
        }
    }

    val isQueryExhausted: LiveData<Boolean>
        get() = mIsQueryExhausted

    val recipes: LiveData<List<Recipe>>
        get() = mRecipes



    fun searchRecipeById(recipeId: String?) {
        mRecipeApiClient.searchRecipeById(recipeId)
    }

    fun searchRecipesApi(query: String?, pageNumber: Int) {
        var pageNumber = pageNumber
        if (pageNumber == 0) {
            pageNumber = 1
        }
        mQuery = query
        mPageNumber = pageNumber
        mIsQueryExhausted.value = false
        mRecipeApiClient.searchRecipesApi(query, pageNumber)
    }

    fun searchNextPage() {
        searchRecipesApi(mQuery, mPageNumber + 1)
    }

    fun cancelRequest() {
        mRecipeApiClient.cancelRequest()
    }

    val isRecipeRequestTimedOut: LiveData<Boolean>
        get() = mRecipeApiClient.isRecipeRequestTimedOut

    companion object {
        var instance: RecipeRepository? = null
            get() {
                if (field == null) {
                    field = RecipeRepository()
                }
                return field
            }
            private set
    }

    init {
        mRecipeApiClient = RecipeApiClient.instance!!
        initMediators()
    }
}