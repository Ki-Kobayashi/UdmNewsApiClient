package com.example.udmnewsapiclient.domain.repository

import com.example.udmnewsapiclient.data.model.Article
import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.util.ApiResource
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    // 【エラーハンドリング：方法１】【kotlin-result"不"使用の場合】：getTopHeadlinesの戻り値をRetrofitの”Response＜T＞”でそのまま受け取る場合
    suspend fun getNewsHeadlines(
        country: String,
        page: Int,
    ): ApiResource<NewsResponse>

    // 【エラーハンドリング：方法２】：【kotlin-result使用の場合（getTopHeadlinesの戻り値が、Result<Response<API取得データ>>型）】：
//    suspend fun getNewsHeadlines(
//        country: String,
//        page: Int,
//    ): Result<NewsResponse, String>


    suspend fun getSearchedNews(searchQuery: String): ApiResource<NewsResponse>

    // ローカルDB
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)

    //TODO: 【Flow】DB（Room）からリアルタイムで変更後のデータを受け取りたいからFlow:　RoomはFlowを返す（suspendで中断すべきでない）
    fun getSavedNewsList(): Flow<List<Article>>
}
