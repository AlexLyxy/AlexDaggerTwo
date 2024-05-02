package com.alexlyxy.alexretrofitlessontwo.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackoverflowApi {
    @GET("/questions?key=" +  "ZiXCZbWaOwnDgpVT9Hx8IA((" + "&order=desc&sort=activity&site=stackoverflow")
    suspend fun lastActiveQuestions(@Query("pagesize") pageSize: Int?): Response<QuestionsListResponseSchema>

    @GET("/questions/{questionId}?key=" + "ZiXCZbWaOwnDgpVT9Hx8IA(("  + "&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Response<SingleQuestionResponseSchema>
}