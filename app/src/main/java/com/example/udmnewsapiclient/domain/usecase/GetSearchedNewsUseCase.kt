package com.example.udmnewsapiclient.domain.usecase

import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.util.ApiResource
import com.example.udmnewsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(
    val newsRepository: NewsRepository,
) {
    suspend fun execute(searchQuery: String): ApiResource<NewsResponse> {
        // TODO:本来Repositoryでは、ビジネスロジックをここに記載する（例：　取得データを変更して、別タイプに変換して返すなど）
        return newsRepository.getSearchedNews(searchQuery)
    }
}
