package ru.tvhelp.akruglov.nerdlauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import ru.tvhelp.akruglov.criminalintent.SingleFragmentActivity

class NerdLauncherActivity : SingleFragmentActivity() {

    override fun createFragment() = NerdLauncherFragment.newInstance()
}
