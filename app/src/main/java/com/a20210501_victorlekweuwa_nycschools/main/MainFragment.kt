package com.a20210501_victorlekweuwa_nycschools.main

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.a20210501_victorlekweuwa_nycschools.LOG_TAG
import com.a20210501_victorlekweuwa_nycschools.R
import com.a20210501_victorlekweuwa_nycschools.data.School
import com.a20210501_victorlekweuwa_nycschools.data.SelectedSchool
import com.a20210501_victorlekweuwa_nycschools.shared.SharedViewModel
import com.a20210501_victorlekweuwa_nycschools.utilities.PrefsHelper
import com.a20210501_victorlekweuwa_nycschools.utilities.PrefsHelper2.Companion.getItemType
import com.droidman.ktoasty.KToasty

class MainFragment : Fragment(),

    MainRecyclerAdapter.SchoolItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var adapter: MainRecyclerAdapter

    var selectedSchools: List<SelectedSchool> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Remove the back button if visible
        (requireActivity() as AppCompatActivity).run {

            supportActionBar?.setDisplayHomeAsUpEnabled(false)

        }

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.retrofit_home_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val layoutStyle = PrefsHelper().getItemType(requireContext())

        // Check if RecyclerView is in grid mode or list mode chosen in app bar actions
        recyclerView.layoutManager =

            if (layoutStyle == "grid") {
                GridLayoutManager(requireContext(), 2)
            }
            else {
                LinearLayoutManager(requireContext())
            }

        // Telling App how to navigate between the two fragments
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

        // In case the user want's to reload the data for any reason
        swipeLayout = view.findViewById(R.id.swipeLayout)

        swipeLayout.setOnRefreshListener {

            viewModel.refreshData()

        }

        // ViewModelProviders.of is deprecated
        //viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        // Accepted answer but not working
        //viewModel = ViewModelProvider.NewInstanceFactory().create(SharedViewModel::class.java)

        viewModel = ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        ).get(SharedViewModel::class.java)


        // Observe when SchoolDao loads data from remote or locally and use in adapter
        viewModel.schoolData.observe(viewLifecycleOwner, Observer
        {

            adapter = MainRecyclerAdapter(requireContext(), it, this)
            val dividerItemDecoration = DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL)
            recyclerView.addItemDecoration(dividerItemDecoration)

            if (layoutStyle == "grid") {

                val horizontalDividerItemDecoration = DividerItemDecoration(
                    context, LinearLayoutManager.HORIZONTAL)
                recyclerView.addItemDecoration(horizontalDividerItemDecoration)

            }

            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false

        })

        // Observe when app has gotten a list of Schools with SAT scores
        viewModel.selectedSchoolData.observe(viewLifecycleOwner, Observer
        {

            selectedSchools = it

        })

        // Not used and not usedful unless App Bar wants to update toolbar text when clicks on school details
        viewModel.activityTitle.observe(viewLifecycleOwner, Observer
        {

            requireActivity().title = it

        })

        return view

    }

    // When user clicks on school, app checks if there exists a school
    // with the same dbn number that has SAT information
    // If such a school exists, will navigate into the detail fragment
    override fun onSchoolItemClick(school: School) {

        Log.e(LOG_TAG, "Selected school: ${school.school_name}")

        var testSelectedSchool: SelectedSchool? = null

        viewModel.selectedSchoolDbn = school.dbn

        for (selectedSchool in selectedSchools) {

            if (selectedSchool.dbn == school.dbn) {

                viewModel.selectedSchool.value = selectedSchool

                testSelectedSchool = selectedSchool

                viewModel.currentSchool.value = school

                navController.navigate(R.id.action_nav_detail)

            }

        }

        if (testSelectedSchool == null) {

            KToasty.info(requireContext(), "This school does not have SAT Scores", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.options_main, menu)

        super.onCreateOptionsMenu(menu, inflater)

    }

    // User can switch school styles from List Style (Default) to Grid Style
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_view_grid -> {

                PrefsHelper().setItemType(requireContext(), "grid")
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter

            }
            R.id.action_view_list -> {

                PrefsHelper().setItemType(requireContext(), "list")
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter

            }
//            R.id.action_settings -> {
//
//                navController.navigate(R.id.settingsActivity)
//
//            }

        }

        return true

    }

    override fun onResume() {
        super.onResume()

    }

}
