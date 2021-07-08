package com.dev.edc.activities.main.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("status") val status : String,
    @SerializedName("courses") val courses : List<Courses>

)
