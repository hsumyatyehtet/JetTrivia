package com.hmyh.jettrivia.network

import com.hmyh.jettrivia.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question

}