package com.tfreeman.foodrecipes.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Recipe() : Parcelable {
    private var title: String? = null
    private var publisher: String? = null
    private var ingredients: Array<String>? = null
    private var recipe_id: String? = null
    private var image_url: String? = null
    private var social_rank = 0f

    fun Recipe(
        title: String?,
        publisher: String?,
        ingredients: Array<String>?,
        recipe_id: String?,
        image_url: String?,
        social_rank: Float
    ) {
        this.title = title
        this.publisher = publisher
        this.ingredients = ingredients
        this.recipe_id = recipe_id
        this.image_url = image_url
        this.social_rank = social_rank
    }

    fun Recipe() {}

    protected fun Recipe(`in`: Parcel) {
        title = `in`.readString()
        publisher = `in`.readString()
        ingredients = `in`.createStringArray()
        recipe_id = `in`.readString()
        image_url = `in`.readString()
        social_rank = `in`.readFloat()
    }



    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        publisher = parcel.readString()
        ingredients = parcel.createStringArray()
        recipe_id = parcel.readString()
        image_url = parcel.readString()
        social_rank = parcel.readFloat()
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPublisher(): String? {
        return publisher
    }

    fun setPublisher(publisher: String?) {
        this.publisher = publisher
    }

    fun getIngredients(): Array<String>? {
        return ingredients
    }

    fun setIngredients(ingredients: Array<String>?) {
        this.ingredients = ingredients
    }

    fun getRecipe_id(): String? {
        return recipe_id
    }

    fun setRecipe_id(recipe_id: String?) {
        this.recipe_id = recipe_id
    }

    fun getImage_url(): String? {
        return image_url
    }

    fun setImage_url(image_url: String?) {
        this.image_url = image_url
    }

    fun getSocial_rank(): Float {
        return social_rank
    }

    fun setSocial_rank(social_rank: Float) {
        this.social_rank = social_rank
    }

    override fun toString(): String {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipe_id='" + recipe_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(publisher)
        dest.writeStringArray(ingredients)
        dest.writeString(recipe_id)
        dest.writeString(image_url)
        dest.writeFloat(social_rank)
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}