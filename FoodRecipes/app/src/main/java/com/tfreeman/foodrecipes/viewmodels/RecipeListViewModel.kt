package com.tfreeman.foodrecipes.viewmodels

import com.tfreeman.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {
    private val mRecipeRepository: RecipeRepository?
    var isViewingRecipes = false
    private var mIsPerformingQuery: Boolean
    val recipes: LiveData<MutableList<Recipe?>?>?
        get() = mRecipeRepository.getRecipes()

    val isQueryExhausted: LiveData<Boolean?>?
        get() = mRecipeRepository.isQueryExhausted()

    fun searchRecipesApi(query: String?, pageNumber: Int) {
        isViewingRecipes = true
        mIsPerformingQuery = true
        mRecipeRepository.searchRecipesApi(query, pageNumber)
    }

    fun searchNextPage() {
        if (!mIsPerformingQuery
            && isViewingRecipes
            && !isQueryExhausted.getValue()
        ) {
            mRecipeRepository.searchNextPage()
        }
    }

    var isPerformingQuery: Boolean?
        get() = mIsPerformingQuery
        set(isPerformingQuery) {
            mIsPerformingQuery = isPerformingQuery!!
        }

    fun onBackPressed(): Boolean {
        if (mIsPerformingQuery) { // cancel the query
            mRecipeRepository.cancelRequest()
            mIsPerformingQuery = false
        }
        if (isViewingRecipes) {
            isViewingRecipes = false
            return false
        }
        return true
    }

    init {
        mRecipeRepository = RecipeRepository.getInstance()
        mIsPerformingQuery = false
    }
}