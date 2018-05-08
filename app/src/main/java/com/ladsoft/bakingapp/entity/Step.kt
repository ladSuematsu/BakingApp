package com.ladsoft.bakingapp.entity


import android.os.Parcel
import android.os.Parcelable

class Step : Parcelable {
    var id: Long
    var recipeId: Long
    var shortDescription: String
    var description: String
    var videoUrl: String
    var thumbnailUrl: String

    constructor(id: Long, recipeId: Long, shortDescription: String, description: String, videoUrl: String, thumbnailUrl: String) {
        this.id = id
        this.recipeId = recipeId
        this.shortDescription = shortDescription
        this.description = description
        this.videoUrl = videoUrl
        this.thumbnailUrl = thumbnailUrl
    }

    constructor(parcel: Parcel) {
        id = parcel.readLong()
        recipeId = parcel.readLong()
        shortDescription = parcel.readString()
        description = parcel.readString()
        videoUrl = parcel.readString()
        thumbnailUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(recipeId)
        parcel.writeString(shortDescription)
        parcel.writeString(description)
        parcel.writeString(videoUrl)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }


}
