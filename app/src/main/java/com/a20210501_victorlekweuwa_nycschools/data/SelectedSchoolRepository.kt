package com.a20210501_victorlekweuwa_nycschools.data


import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.a20210501_victorlekweuwa_nycschools.LOG_TAG
import com.a20210501_victorlekweuwa_nycschools.WEB_SERVICE_URL
import com.app.ej.cs.ui.practise.retrofit.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Similar class to SchoolRepository so no need to duplicate comments
class SelectedSchoolRepository(val app: Application) {

    val selectedSchoolData = MutableLiveData<List<SelectedSchool>>()

    private val selectedSchoolDao = SelectedSchoolDatabase.getDatabase(app).selectedSchoolDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {

            val data = selectedSchoolDao.getAll()

            if (data.isEmpty()) {

                callWebService()

            }
            else {

                selectedSchoolData.postValue(data)

                withContext(Dispatchers.Main) {

//                    KToasty.info(app, "Using local data", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    @WorkerThread
    suspend fun callWebService() {

        if (networkAvailable()) {

            withContext(Dispatchers.Main) {

//                KToasty.info(app, "Using remote data", Toast.LENGTH_SHORT).show()

            }

            Log.i(LOG_TAG, "Calling web service")

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(SelectedSchoolService::class.java)

            val serviceData = service.getSelectedSchoolData().body() ?: emptyList()

            selectedSchoolData.postValue(serviceData)

            selectedSchoolDao.deleteAll()

            selectedSchoolDao.insertSelectedSchools(serviceData)

        }

    }

    @SuppressLint("MissingPermission")
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

    private fun saveDataToCache(monsterData: List<SelectedSchool>) {

        if (ContextCompat.checkSelfPermission(app,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {

            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, SelectedSchool::class.java)
            val adapter: JsonAdapter<List<SelectedSchool>> = moshi.adapter(listType)
            val json = adapter.toJson(monsterData)
            FileHelper.saveTextToFile(app, json)

        }

    }

    private fun readDataFromCache(): List<SelectedSchool> {

        val json = FileHelper.readTextFile(app)

        if (json == null) {

            return emptyList()

        }

        val moshi = Moshi.Builder().build()

        val listType = Types.newParameterizedType(List::class.java, SelectedSchool::class.java)

        val adapter: JsonAdapter<List<SelectedSchool>> = moshi.adapter(listType)

        return adapter.fromJson(json) ?: emptyList()

    }

}