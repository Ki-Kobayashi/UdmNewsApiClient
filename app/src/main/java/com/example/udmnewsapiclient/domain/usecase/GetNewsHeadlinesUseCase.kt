package com.example.udmnewsapiclient.domain.usecase

import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.util.ApiResource
import com.example.udmnewsapiclient.domain.repository.NewsRepository
import com.github.michaelbull.result.Result
import retrofit2.Response

class GetNewsHeadlinesUseCase(
    private val newsRepository: NewsRepository,
) {
    suspend fun execute(
        country: String,
        page: Int,
    ): ApiResource<NewsResponse> {
        // TODO:本来Repositoryでは、ビジネスロジックをここに記載する（例：　取得データを変更して、別タイプに変換して返すなど）
        return newsRepository.getNewsHeadlines(country, page)
    }
}
