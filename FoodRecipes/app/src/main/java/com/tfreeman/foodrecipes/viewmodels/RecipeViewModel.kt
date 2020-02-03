package com.tfreeman.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tfreeman.foodrecipes.models.Recipe
import com.tfreeman.foodrecipes.repositories.RecipeRepository

class RecipeViewModel : ViewModel() {

    private val mRecipeRepository: RecipeRepository = RecipeRepository.instance!!

    var recipeId: String? = null
        private set
    private var mDidRetrieveRecipe: Boolean
    val recipe: LiveData<Recipe>
        get() = mRecipeRepository.getRecipe()

    val isRecipeRequestTimedOut: LiveData<Boolean>
        get() = mRecipeRepository.isRecipeRequestTimedOut

    fun searchRecipeById(recipeId: String?) {
        this.recipeId = recipeId
        mRecipeRepository.searchRecipeById(recipeId)
    }

    fun setRetrievedRecipe(retrievedRecipe: Boolean) {
        mDidRetrieveRecipe = retrievedRecipe
    }

    fun didRetrieveRecipe(): Boolean {
        return mDidRetrieveRecipe
    }

    init {
        mDidRetrieveRecipe = false
    }
}