package com.ladsoft.bakingapp.entity

import android.os.Parcel
import android.os.Parcelable

class Recipe(var id: Long, var name: String, var servings: Int, var imageUrl: String,
             var ingredients: List<Ingredient> = emptyList(), var steps: List<Step> = emptyList()): Parcelable {

    fun getIngredientCount() = ingredients.size
    fun getStepCount() = steps.size

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.createTypedArrayList(Ingredient),
            parcel.createTypedArrayList(Step.CREATOR))
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(servings)
        parcel.writeString(imageUrl)
        parcel.writeTypedList(ingredients)
        parcel.writeTypedList(steps)
    }

    override fun describeContents(): Int {
        return 0
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
