package com.example.udmnewsapiclient.data.repository

import com.example.udmnewsapiclient.data.model.Article
import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.example.udmnewsapiclient.data.util.ApiResource
import com.example.udmnewsapiclient.domain.repository.NewsRepository
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
) : NewsRepository {

    // TODO:【エラーハンドリング：方法１】【kotlin-result"不"使用の場合】：getTopHeadlinesの戻り値をRetrofitの”Response＜T＞”でそのまま受け取る場合
    override suspend fun getNewsHeadlines(
        country: String,
        page: Int,
    ): ApiResource<NewsResponse> {
        // resultの型が、「Response<NewsResponse>」の場合：kotlin-result不使用
        return newsRemoteDataSource.getTopHeadlines(country, page)
    }

    // TODO:【エラーハンドリング：方法2】【kotlin-result使用の場合（getTopHeadlinesの戻り値が、Result<Response<API取得データ>>型）】：
//    override suspend fun getNewsHeadlines(
//        country: String,
//        page: Int,
//    ): Result<NewsResponse, String>{
//        return newsRemoteDataSource.getTopHeadlines(country, page)
//    }

    override suspend fun getSearchedNews(searchQuery: String): ApiResource<NewsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNewsList(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

}
