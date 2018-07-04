package ru.tvhelp.akruglov.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_nerd_launcher.*

class NerdLauncherFragment(): Fragment() {
    companion object {
        const val TAG = "NerdLauncherFragment"
        fun newInstance() = NerdLauncherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nerd_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appRecyclerView.layoutManager = LinearLayoutManager(activity!!)
        setupAdapter()
    }

    private fun setupAdapter() {
        val startupIntent = Intent(Intent.ACTION_MAIN)
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pm = activity!!.packageManager
        val activities = pm.queryIntentActivities(startupIntent, 0)
        activities.sortWith(object: Comparator<ResolveInfo> {
            override fun compare(a: ResolveInfo, b: ResolveInfo) = String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm).toString(), b.loadLabel(pm).toString())
        });

        Log.i(TAG, "Found " + activities.size + " activities.")

        appRecyclerView.adapter = ActivityAdapter(activities)
    }

    private inner class ActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var resolveInfo: ResolveInfo

        init {
            itemView.setOnClickListener {
                val activityInfo = resolveInfo.activityInfo
                val i = Intent(Intent.ACTION_MAIN)
                        .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            }
        }

        fun bindActivity(resolveInfo: ResolveInfo) {
            this.resolveInfo = resolveInfo
            val pm = activity!!.packageManager
            val appName = resolveInfo.loadLabel(pm).toString()
            (itemView as TextView).text = appName
        }
    }

    private inner class ActivityAdapter(private val activities: List<ResolveInfo>): RecyclerView.Adapter<ActivityHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val layoutInflater = LayoutInflater.from(activity!!)
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            holder.bindActivity(activities[position])
        }

        override fun getItemCount(): Int {
            return activities.size
        }
    }
}