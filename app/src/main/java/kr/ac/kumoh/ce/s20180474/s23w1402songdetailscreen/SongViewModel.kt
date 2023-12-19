package kr.ac.kumoh.ce.s20180474.s23w1402songdetailscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongViewModel() : ViewModel() {
    private val SERVER_URL = "https://port-0-swp-1drvf2llomiqzf1.sel5.cloudtype.app/"
    private val songApi: SongApi
    private val _songList = MutableLiveData<List<Song>>()
    val songList: LiveData<List<Song>>
        get() = _songList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        songApi = retrofit.create(SongApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                Log.i("fetchData()","start")
                val response = songApi.getSongs()
                Log.i("fetchData()","end")
                _songList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}