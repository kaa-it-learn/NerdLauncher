package ru.tvhelp.akruglov.criminalintent

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.tvhelp.akruglov.nerdlauncher.R

abstract class SingleFragmentActivity: AppCompatActivity() {
    abstract fun createFragment(): Fragment

    @LayoutRes
    protected open val layoutResId = R.layout.activity_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }
}