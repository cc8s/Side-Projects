package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitinterface {
    @POST("/users")
    fun signUp(@Body user: User): Call<AuthResponse>
}