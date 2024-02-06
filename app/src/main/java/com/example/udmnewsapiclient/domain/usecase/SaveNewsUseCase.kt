package com.example.udmnewsapiclient.domain.usecase

import com.example.udmnewsapiclient.data.model.Article
import com.example.udmnewsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(
    private val newsRepository: NewsRepository,
) {
    // TODO:　【Domain > Usecase】本来Repositoryでは、ビジネスロジックをここに記載する（例：　取得データを変更して、別タイプに変換して返すなど）
    // TODO: 戻り値がない時は、1行で表現できる
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}
