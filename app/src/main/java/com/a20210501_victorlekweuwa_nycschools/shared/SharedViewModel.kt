package com.a20210501_victorlekweuwa_nycschools.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.a20210501_victorlekweuwa_nycschools.data.School
import com.a20210501_victorlekweuwa_nycschools.data.SchoolRepository
import com.a20210501_victorlekweuwa_nycschools.data.SelectedSchool
import com.a20210501_victorlekweuwa_nycschools.data.SelectedSchoolRepository

// SharedViewModel allows both Home Fragment and Detail Fragment to share the same view model
public class SharedViewModel(val app: Application) : AndroidViewModel(app) {

    // Init data repositories for all schools
    private val dataRepo = SchoolRepository(app)

    // Init data repositories for schools with SAT scores
    private val selectedDataRepo = SelectedSchoolRepository(app)

    // Get's all school data
    val schoolData = dataRepo.schoolData

    // Get's data of schools with SAT scores
    val selectedSchoolData = selectedDataRepo.selectedSchoolData

    // Observable variable that holds list of all NYC schools
    val currentSchool = MutableLiveData<School>()

    // Observable variable that holds list of all NYC schools who have SAT scores
    val selectedSchool = MutableLiveData<SelectedSchool>()

    // If I had more time, I would change the appBar title when a school is selected.
    // This variable is unused.
    val activityTitle = MutableLiveData<String>()

    // Holds value of the selected school's unique dbn value
    var selectedSchoolDbn: String = ""

    // Holds all the schools that have SAT scores
    var selectedSchools: MutableList<SelectedSchool> = mutableListOf()

    // No need to init when data can be retrieved in instantiation
    init {

    }

    // Called when user starts activity as well as gets
    // Data Remote When User Swipes to Refresh
    fun refreshData() {

        selectedDataRepo.refreshDataFromWeb()
        dataRepo.refreshDataFromWeb()

    }


}
