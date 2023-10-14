package com.fastaccess.data.dao

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.fastaccess.data.entity.Commit
import com.google.gson.annotations.SerializedName

/**
 * Created by Kosh on 03 Mar 2017, 9:08 PM
 */
class BranchesModel : Parcelable {
    var name: String? = null
    var commit: Commit? = null

    @SerializedName("protected")
    var protectedBranch = false
    var protectionUrl: String? = null
    var isTag = false

    constructor() {}

    override fun toString(): String {
        return name!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeParcelable(commit, flags)
        dest.writeByte(if (protectedBranch) 1.toByte() else 0.toByte())
        dest.writeString(protectionUrl)
        dest.writeByte(if (isTag) 1.toByte() else 0.toByte())
    }

    private constructor(parcel: Parcel) {
        name = parcel.readString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            commit = parcel.readParcelable(Commit::class.java.classLoader, Commit::class.java)
        } else {
            @Suppress("DEPRECATION")
            commit = parcel.readParcelable(Commit::class.java.classLoader)
        }
        protectedBranch = parcel.readByte().toInt() != 0
        protectionUrl = parcel.readString()
        isTag = parcel.readByte().toInt() != 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BranchesModel> =
            object : Parcelable.Creator<BranchesModel> {
                override fun createFromParcel(source: Parcel): BranchesModel {
                    return BranchesModel(source)
                }

                override fun newArray(size: Int): Array<BranchesModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
