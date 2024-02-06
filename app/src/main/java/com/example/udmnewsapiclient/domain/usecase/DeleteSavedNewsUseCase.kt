package com.example.udmnewsapiclient.domain.usecase

import com.example.udmnewsapiclient.data.model.Article
import com.example.udmnewsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(
    private val newsRepository: NewsRepository,
) {
    // TODO:本来Repositoryでは、ビジネスロジックをここに記載する（例：　取得データを変更して、別タイプに変換して返すなど）
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}
