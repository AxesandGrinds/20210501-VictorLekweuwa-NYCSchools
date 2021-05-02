package com.a20210501_victorlekweuwa_nycschools.data


import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.a20210501_victorlekweuwa_nycschools.LOG_TAG
import com.a20210501_victorlekweuwa_nycschools.WEB_SERVICE_URL
import com.app.ej.cs.ui.practise.retrofit.utilities.FileHelper
import com.droidman.ktoasty.KToasty

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Repository that fetches the data remote
class SchoolRepository(val app: Application) {

    val schoolData = MutableLiveData<List<School>>()

    private val schoolDao = SchoolDatabase.getDatabase(app).schoolDao()

    init {

        // Get's data in a background thread remote or local
        CoroutineScope(Dispatchers.IO).launch {

            val data = schoolDao.getAll()

            if (data.isEmpty()) {

                callWebService()

            }
            else {

                schoolData.postValue(data)

                withContext(Dispatchers.Main) {

                    KToasty.info(app, "Using local data", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    // If internet access is available, try to load the data remote
    @WorkerThread
    suspend fun callWebService() {

        if (networkAvailable()) {

            withContext(Dispatchers.Main) {

                KToasty.info(app, "Using remote data", Toast.LENGTH_SHORT).show()

            }

            Log.i(LOG_TAG, "Calling web service")

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(SchoolService::class.java)

            val serviceData = service.getSchoolData().body() ?: emptyList()

            schoolData.postValue(serviceData)

            schoolDao.deleteAll()

            schoolDao.insertSchools(serviceData)

        }

    }

    // If I had more time, I would check why ".activeNetworkInfo" and ".isConnectedOrConnecting"
    // is deprecated and would use alternative method.
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {

        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager


        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting ?: false

    }

    fun refreshDataFromWeb() {

        CoroutineScope(Dispatchers.IO).launch {

            callWebService()

        }

    }

    // If I had more time, would implement these methods
    private fun saveDataToCache(monsterData: List<School>) {

        if (ContextCompat.checkSelfPermission(app,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {

            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, School::class.java)
            val adapter: JsonAdapter<List<School>> = moshi.adapter(listType)
            val json = adapter.toJson(monsterData)
            FileHelper.saveTextToFile(app, json)

        }

    }

    // If I had more time, would implement these methods
    private fun readDataFromCache(): List<School> {

        val json = FileHelper.readTextFile(app)

        if (json == null) {

            return emptyList()

        }

        val moshi = Moshi.Builder().build()

        val listType = Types.newParameterizedType(List::class.java, School::class.java)

        val adapter: JsonAdapter<List<School>> = moshi.adapter(listType)

        return adapter.fromJson(json) ?: emptyList()

    }

}