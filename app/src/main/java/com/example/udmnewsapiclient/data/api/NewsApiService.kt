package com.example.udmnewsapiclient.data.api

import com.example.udmnewsapiclient.BuildConfig
import com.example.udmnewsapiclient.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY,
    ): Response<NewsResponse>
}
