package com.example.udmnewsapiclient.module

import com.example.udmnewsapiclient.BuildConfig
import com.example.udmnewsapiclient.data.api.NewsApiService
import com.example.udmnewsapiclient.data.repository.NewsRepositoryImpl
import com.example.udmnewsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.example.udmnewsapiclient.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.udmnewsapiclient.domain.repository.NewsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by K.Kobayashi on 2022/05/19.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    /**
     * Retrofit2でデバッグ用にログ出力する設定
     *   https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
     *   https://square.github.io/okhttp/features/interceptors/
     *
     * ◆ログレベル
     *      logger.levelで渡しているlevelには4種あり、出力の詳細さが変わる
     *      ★ NONE
     *        ログを出力しない
     *      ★ BASIC
     *        リクエストラインとレスポンスラインのみを出力
     *      ★ HEADER
     *        BASICのログに加えてリクエストヘッダとレスポンスヘッダが出力される
     *      ★ BODY
     *        HEADERのログに加えて、クエストボディとレスポンスボディが出力される
     */
    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor {
                // 💡【下記処理について】
                // 　　　　特にリクエストに変更を加えたいが元のリクエストを変更せずに残したい場合に有用な処理

                // リクエストからHTTPのURLを取得
                val httpUrl = it.request().url
                // 元のリクエストからコピーを作成し、url(httpUrl)メソッドを使用して新しいURLに更新
                val requestBuilder = it.request().newBuilder().url(httpUrl)
                // 更新されたリクエストを使って処理を継続
                it.proceed(requestBuilder.build())
            }
            .addInterceptor(logInterceptor)
            // 以下3つのタイムアウト：定義なし＝デフォルト10秒がセットされる（※ほとんどの場合、デフォルトで問題ない＝定義不要）
            // "接続タイムアウト"の設定：
            .connectTimeout(30, TimeUnit.SECONDS)
            //　読み取りタイムアウトの設定：個々の結果取得到着時間
            .readTimeout(20, TimeUnit.SECONDS)
            // データ送信タイムアウト設定：個々の書き込み IO 操作時間
            .writeTimeout(25, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class MainModule {
        @Binds
        @Singleton
        abstract fun bindNewsRemoteDataSource(impl: NewsRemoteDataSourceImpl): NewsRemoteDataSource

        @Binds
        @Singleton
        abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
    }
}
