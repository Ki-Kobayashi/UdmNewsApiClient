package com.example.udmnewsapiclient.data.repository.dataSourceImpl

import com.example.udmnewsapiclient.data.api.NewsApiService
import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.example.udmnewsapiclient.data.util.ApiResource
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NewsRemoteDataSource {

    // TODO:【エラーハンドリング：方法１】【kotlin-result"不"使用の場合】：getTopHeadlinesの戻り値をRetrofitの”Response＜T＞”でそのまま受け取る場合
    override suspend fun getTopHeadlines(
        country: String,
        page: Int,
    ): ApiResource<NewsResponse> {
        return withContext(ioDispatcher) {
            val response = newsApiService.getTopHeadlines(country, page)
            return@withContext resourceFromResponse(response)
        }
    }

    // TODO【エラーハンドリング：方法２】：kotlin-resultを使用”しない”なら、下記クラスを利用する
//    override suspend fun getTopHeadlines(
//        country: String,
//        page: Int,
//    ): Result<NewsResponse, String> {
//        return withContext(ioDispatcher) {
//            val response = newsApiService.getTopHeadlines(country, page)
//            if (response.isSuccessful) {
//                response.body()?.let { result ->
//                    return@withContext Ok(result)
//                }
//            }
//            return@withContext Err("API通信に失敗しました。")
//        }
//    }


    // kotlin-resultを使用しない場合：以下のように独自ApiResourceを作成し、それに変換するようにする
    // kotlin-result使用の場合：　以下Factory不要（※詳細はDataSourceImpl参照。）
    private fun resourceFromResponse(response: Response<NewsResponse>): ApiResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ApiResource.Success(result)
            }
        }
        return ApiResource.Error(response.message())
    }
}
