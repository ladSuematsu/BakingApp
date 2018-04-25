package com.ladsoft.bakingapp.entity

import android.os.Parcel
import android.os.Parcelable


class Ingredient(val id: Long, val recipeId: Long, val quantity: Double, val measure: String, val description: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString())

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient {
            return Ingredient(parcel)
        }

        override fun newArray(size: Int): Array<Ingredient?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(recipeId)
        parcel.writeDouble(quantity)
        parcel.writeString(measure)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }


}
