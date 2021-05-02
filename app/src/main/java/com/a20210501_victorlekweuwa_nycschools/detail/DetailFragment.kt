package com.a20210501_victorlekweuwa_nycschools.detail


import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.a20210501_victorlekweuwa_nycschools.R
import com.a20210501_victorlekweuwa_nycschools.databinding.RetrofitDetailFragmentBinding
import com.a20210501_victorlekweuwa_nycschools.shared.SharedViewModel
//import com.nick.mowen.linkpreview.view.LinkPreview
import com.overflowarchives.linkpreview.ViewListener
import com.overflowarchives.linkpreview.WhatsappPreview

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).run {

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }

        setHasOptionsMenu(true)

        navController = Navigation.findNavController(

            requireActivity(), R.id.nav_host

        )

        val binding = RetrofitDetailFragmentBinding.inflate(
            inflater, container, false)

        // Although databinding is available, want to use different names and access
        // information that doesn't load directly from SharedViewModel.

        val schoolName = binding.root.findViewById<TextView>(R.id.schoolNameText)
        val schoolBorough = binding.root.findViewById<TextView>(R.id.boroughText)
        val schoolWebsite = binding.root.findViewById<TextView>(R.id.schoolWebsiteText)
        val schoolWebsitePreview = binding.root.findViewById<WhatsappPreview>(R.id.link_preview)
        val schoolDescription = binding.root.findViewById<TextView>(R.id.descriptionText)

        val satClassSize = binding.root.findViewById<TextView>(R.id.numberOfStudents)
        val criticalReadingAverage = binding.root.findViewById<TextView>(R.id.criticalReadingAverage)
        val mathAverage = binding.root.findViewById<TextView>(R.id.mathAverage)
        val writingAverage = binding.root.findViewById<TextView>(R.id.writingAverage)


        // ViewModelProviders.of is deprecated
        //viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        // Accepted answer but not working
        //viewModel = ViewModelProvider.NewInstanceFactory().create(SharedViewModel::class.java)

        viewModel = ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        ).get(SharedViewModel::class.java)

        // Observe when SelectedSchoolDao loads data from remote or locally and use in adapter
        viewModel.selectedSchool.observe(viewLifecycleOwner, Observer {

            selectedSchool ->

            schoolName.text = viewModel.currentSchool.value?.school_name
            schoolBorough.text = viewModel.currentSchool.value?.borough

            val website: String? = viewModel.currentSchool.value?.website
            var finalWebsite: String = ""

            // Some of the websites does not have the correct structure so
            // check to confirm website is appropriate before loading
            if (website != null) {

                if (website.contains("www.")) {

                    if (website.contains("https://")) {

                        finalWebsite = website

                    }
                    else {

                        val link: String = "https://${website}"
                        finalWebsite = link

                    }

                }
                else {

                    val link: String = "https://www.${website}"
                    finalWebsite = link

                }

                schoolWebsite.text = finalWebsite
                Linkify.addLinks(schoolWebsite, Linkify.WEB_URLS) // Linkify Website URL so that is is clickable

                schoolWebsite.visibility = View.VISIBLE
                schoolWebsitePreview.visibility = View.VISIBLE

                // Load Website URL for Preview using WhatsApp Style
                schoolWebsitePreview.loadUrl(finalWebsite, object : ViewListener { // was https://stackoverflow.com for testing

                    override fun onPreviewSuccess(status: Boolean) {

                    }

                    // If website does not load, hide the website views
                    override fun onFailedToLoad(e: Exception?) {

                        schoolWebsite.visibility = View.GONE
                        schoolWebsitePreview.visibility = View.GONE

                    }

                })

            }
            else {

                // If website is null, hide the views
                schoolWebsite.visibility = View.GONE
                schoolWebsitePreview.visibility = View.GONE

            }

            schoolDescription.text = viewModel.currentSchool.value?.overview_paragraph

            schoolDescription.visibility = View.VISIBLE

            satClassSize.text = selectedSchool.num_of_sat_test_takers

            criticalReadingAverage.text = selectedSchool.sat_critical_reading_avg_score

            mathAverage.text = selectedSchool.sat_math_avg_score

            writingAverage.text = selectedSchool.sat_writing_avg_score

            // Log.e is used for only visibility in the Run Console so that it can be seen in red.
            // I would use Log.i if working on client app.
            Log.e("ATTENTION ATTENTION","Selected School")
            Log.e("ATTENTION ATTENTION","Number of Students: ${selectedSchool.num_of_sat_test_takers}")
            Log.e("ATTENTION ATTENTION","Critical Reading Average: ${selectedSchool.sat_critical_reading_avg_score}")
            Log.e("ATTENTION ATTENTION","Math Average: ${selectedSchool.sat_math_avg_score}")
            Log.e("ATTENTION ATTENTION","Writing Average: ${selectedSchool.sat_writing_avg_score}")

        })


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            // Return to home screen when user clicks on back button
            navController.navigateUp()

        }

        return super.onOptionsItemSelected(item)

    }


}
