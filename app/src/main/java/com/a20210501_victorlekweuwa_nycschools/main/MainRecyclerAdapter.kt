package com.a20210501_victorlekweuwa_nycschools.main

import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.graphics.Point
import android.graphics.Rect
import android.text.util.Linkify
import android.util.Size
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.a20210501_victorlekweuwa_nycschools.R
import com.a20210501_victorlekweuwa_nycschools.data.School
import com.a20210501_victorlekweuwa_nycschools.utilities.PrefsHelper
import com.overflowarchives.linkpreview.ViewListener
import com.overflowarchives.linkpreview.WhatsappPreview


class MainRecyclerAdapter(
    val context: Context,
    val schools: List<School>,
    val itemListener: SchoolItemListener
):
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()

{

    var layoutStyle: String = ""

    override fun getItemCount() = schools.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        layoutStyle = PrefsHelper().getItemType(parent.context)

        // Checks if User selected list style  or grid style
        // List style is default
        val layoutId = if (layoutStyle == "grid") {

            R.layout.retrofit_grid_item

        }
        else {

            R.layout.retrofit_list_item

        }

        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val school = schools[position]

        var height: Int = 0
        var width: Int = 0
        var display: Display

        with(holder) {

            // If App in Grid mode, set the width and height dynamically
            if (layoutStyle == "grid") {

                // Get system display configuration for before and after Android R
                // If App is in grid mode, these values will be used to set height and width to half of the screen size
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

                    display = context.display!!

                    val metrics: WindowMetrics = (context as Activity).windowManager.currentWindowMetrics

                    // Gets all excluding insets
                    val windowInsets = metrics.windowInsets
                    val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                        WindowInsets.Type.navigationBars()
                                or WindowInsets.Type.displayCutout()
                    )

                    val insetsWidth: Int = insets.right + insets.left
                    val insetsHeight: Int = insets.top + insets.bottom

                    // Legacy size that Display#getSize reports
                    val bounds: Rect = metrics.bounds
                    val legacySize = Size(
                        bounds.width() - insetsWidth,
                        bounds.height() - insetsHeight
                    )
                    width = legacySize.width / 2
                    height = legacySize.height / 2

                }
                else {

                    display = (context as Activity).windowManager.defaultDisplay
                    height = display.height / 2
                    width = display.width / 2

                }

                // Final step is setting height and width
                schoolConstraintLayout.layoutParams =
                    FrameLayout.LayoutParams(width, height)

            }

            schoolName.text = school.school_name

            schoolBorough.text = school.borough

            schoolWebsite.text = school.website

            val website: String? = school.website
            var finalWebsite: String = ""

            // Checks to see if website url is proper for preview
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
                Linkify.addLinks(schoolWebsite, Linkify.WEB_URLS)

                schoolWebsite.visibility = View.VISIBLE
//                schoolWebsitePreview.visibility = View.VISIBLE

                // Loads website url using WhatsApp style
                schoolWebsitePreview.loadUrl(
                    finalWebsite,
                    object : ViewListener { // was https://stackoverflow.com for testing

                        override fun onPreviewSuccess(status: Boolean) {

                        }

                        // If website does not load, hide the website views
                        // This throws an error so commented out
                        override fun onFailedToLoad(e: Exception?) {

                            // schoolWebsite.visibility = View.GONE
                            // schoolWebsitePreview.visibility = View.GONE

                        }

                    })

            }
            else {

                // If website is null, hide the views
                schoolWebsite.visibility = View.GONE
                schoolWebsitePreview.visibility = View.GONE

            }

            schoolDescription.text = school.overview_paragraph

            // Listener selects the school on click which would be used to search
            // for Schools with SAT scores that match the same dbn
            holder.itemView.setOnClickListener{

                itemListener.onSchoolItemClick(school)

            }

        }

    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        val schoolConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.schoolConstraintLayout)!!
        val schoolName = itemView.findViewById<TextView>(R.id.schoolNameText)!!
        val schoolBorough = itemView.findViewById<TextView>(R.id.boroughText)!!
        val schoolWebsite = itemView.findViewById<TextView>(R.id.schoolWebsiteText)!!
        val schoolWebsitePreview = itemView.findViewById<WhatsappPreview>(R.id.link_preview)!!
        val schoolDescription = itemView.findViewById<TextView>(R.id.descriptionText)!!

    }

    interface SchoolItemListener {

        fun onSchoolItemClick(school: School)

    }

}