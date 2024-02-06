package com.example.udmnewsapiclient.data.repository.dataSource

import com.example.udmnewsapiclient.BuildConfig
import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.util.ApiResource
import retrofit2.Response

import com.github.michaelbull.result.Result

/**
 * APIと通信するための抽象関数を定義する
 */
interface NewsRemoteDataSource {

// TODO:【エラーハンドリング：方法１】【kotlin-result"不"使用の場合】：getTopHeadlinesの戻り値をRetrofitの”Response＜T＞”でそのまま受け取る場合
    suspend fun getTopHeadlines(
        country: String,
        page: Int,
    ): ApiResource<NewsResponse>


    // TODO【エラーハンドリング：方法２】：kotlin-resultを使用”しない”なら、下記クラスを利用する
//    suspend fun getTopHeadlines(
//        country: String,
//        page: Int,
//    ): Result<NewsResponse, String>
}
