package com.example.udmnewsapiclient.domain.usecase

import com.example.udmnewsapiclient.data.model.Article
import com.example.udmnewsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(
    private val  newsRepository: NewsRepository,
) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNewsList()
    }
}
