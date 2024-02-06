package com.example.udmnewsapiclient.data.util

// TODO【エラーハンドリング：方法２】：kotlin-resultを使用”しない”なら、下記クラスを利用する
sealed class ApiResource<T>(
    val data: T? = null,
    val meessage: String? = null,
) {
    class Success<T>(data: T): ApiResource<T>(data)
    class Loading<T>(data: T? = null): ApiResource<T>(data)
    class Error<T>(message: String, data: T? = null): ApiResource<T>(data, message)
}
