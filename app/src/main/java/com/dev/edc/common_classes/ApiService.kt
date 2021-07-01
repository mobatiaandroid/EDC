package com.dev.edc.common_classes

import android.provider.ContactsContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("countries")
    fun languagesListCall(): Call<ResponseBody>

    @GET("branches")
    fun branchesListCall(): Call<ResponseBody>

    @GET("countries")
    fun countriesListCall(): Call<ResponseBody>

    @GET("educationLevel")
    fun educationListCall(): Call<ResponseBody>

    @GET("courses")
    fun coursesListCall(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("validateStudent")
    fun validateStudentCall(
        @Field("StudentNo") studentNumber: String,
        @Field("TrafficNo") trafficNumber: String,
        @Field("TryFileNo") tryFileNumber: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("signin")
    fun loginCall(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("signup")
    fun signUpCall(
        @Field("StudentNo") studentNumber: String,
        @Field("UnifiedID") unifiedID: String,
        @Field("TrafficNo") trafficNumber: String,
        @Field("TryFileNo") tryFileNumber: String,
        @Field("Branch") branch: String,
        @Field("TrainingLanguage") trainingLanguage: String,
        @Field("Nationality") nationality: String,
        @Field("MotherTongue") motherTongue:  String,
        @Field("LevelOfEducation") levelOfEducation: String,
        @Field("EmailAddress") email: String,
        @Field("password") password: String,
        @Field("PhoneNumber") phoneNumber: String,
        @Field("LicenseTypeCode") licenseTypeCode: String,
        @Field("FullNameArabic") fullNameArabic: String,
        @Field("FullName") fullName: String,
        @Field("Gender") gender: String,
        @Field("dob") dob: String,
        @Field("EmiratesID") emiratesID: String
        ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("sendConfirmEmail")
    fun sendConfirmEmailCall(
        @Field("email") email: String,
        @Field("FullNameEn") fullName: String,
        @Field("FullNameAr") fullNameArabic: String,
        @Field("dob") dob: String
    ): Call<ResponseBody>
}
