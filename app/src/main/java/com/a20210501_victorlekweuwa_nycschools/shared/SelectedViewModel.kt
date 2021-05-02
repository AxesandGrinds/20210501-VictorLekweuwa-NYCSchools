package com.a20210501_victorlekweuwa_nycschools.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.a20210501_victorlekweuwa_nycschools.data.SelectedSchool
import com.a20210501_victorlekweuwa_nycschools.data.SelectedSchoolRepository

// Class not needed because Both fragments share the same SharedViewModel
class SelectedViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepo = SelectedSchoolRepository(app)

    val selectedSchoolData = dataRepo.selectedSchoolData

    val selectedSchool = MutableLiveData<SelectedSchool>()

    val activityTitle = MutableLiveData<String>()

    init {

    }

    fun refreshData() {

        dataRepo.refreshDataFromWeb()

    }

}
