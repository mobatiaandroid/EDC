package com.dev.edc.activities.main.model

import com.google.gson.annotations.SerializedName

data class Courses(
    @SerializedName("vcFeeCd") var vcFeeCd : String,
    @SerializedName("vcFeeDesc") var vcFeeDesc : String,
    @SerializedName("decAmount") var decAmount : String,
    @SerializedName("chStatus") val chStatus : String,
    @SerializedName("chOrderFlg") val chOrderFlg : String,
    @SerializedName("chTestFlg") val chTestFlg : String,
    @SerializedName("chParPayFlg") val chParPayFlg : String,
    @SerializedName("intLicTypeCd") val intLicTypeCd : Int,
    @SerializedName("course") val course : String
)
