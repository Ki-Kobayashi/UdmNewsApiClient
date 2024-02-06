package com.example.udmnewsapiclient.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udmnewsapiclient.R
import com.example.udmnewsapiclient.data.model.NewsResponse
import com.example.udmnewsapiclient.data.util.ApiResource
import com.example.udmnewsapiclient.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val newsRepository: NewsRepository,
) : ViewModel() {
    // 【LiveData使用の場合】監視したい値の定義（変更はこのクラスでのみ行うようにする）
    private val _newsHeadLines = MutableLiveData<ApiResource<NewsResponse>>()
    val newsHeadLines: LiveData<ApiResource<NewsResponse>> get() = _newsHeadLines

    fun fetchNewsHeadLines(country: String, page: Int) {
        val testString = appContext.getString(R.string.app_name)
        // 読み込み中
        _newsHeadLines.value = ApiResource.Loading()
        Log.d("AAAAAAAAAAA", testString)
        try {

            if (isNetworkAvailable(appContext)) {
                // 【viewModelScope.launch】無指定の場合は、メインスレッド実行
                viewModelScope.launch {
                    // Repositoryでの取得処理は、ワーカースレッドで実行されている（※withContext(）で切り替えてる）
                    val newsResponse = newsRepository.getNewsHeadlines(country, page)
                    // 上の取得をワーカースレッド実行しているため、値の代入は、MainでもOK、
                    // 取得できたら、結果（ApiResource.Sccess or Error）をセット
                    _newsHeadLines.value = newsResponse
//                _newsHeadLines.postValue(newsResponse) // ワーカスレッドで代入
                }
            } else {
                _newsHeadLines.value = ApiResource.Error(message = "Internet not availabel")
            }
        } catch (e: Exception) {
            // ネットワーク関連以外のエラーのキャッチ
            _newsHeadLines.value = ApiResource.Error(e.message.toString())
        }
    }


    // TODO:https://developer.android.com/training/basics/network-ops/managing?hl=ja
    /**
     * ネットワーク状態の監視
     */
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    // モバイル通信接続中
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    // Wifiに接続中
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {

            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        // インターネットなし
        return false

    }

}
