package com.example.materialcomponentstest

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_button.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_selection.*
import java.text.NumberFormat
import java.util.*

class SelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        initialParentChildCheckboxes()
        initialRadioGroup()

        initialSlider()
        setStatusBarColorIcons()
        setInsets()
    }

    private fun setStatusBarColorIcons() {
        val currentNightMode = (resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK)
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = 0
        }

    }

    private fun initialSlider() {
        slider2.setLabelFormatter {
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("RUB")
            format.format(it.toDouble())

        }
    }

    private fun setInsets() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            ViewCompat
                .setOnApplyWindowInsetsListener(container_selection) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop,
                        bottom = insets.systemWindowInsetBottom
                    )
                    insets
                }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            ViewCompat
                .setOnApplyWindowInsetsListener(container_selection) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_selection.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_selection.paddingBottom
                    )
                    insets
                }

        }
    }

    private fun initialParentChildCheckboxes() {
        val childCheckBoxes = listOf(chchbox1)
        pchbox.setOnCheckedChangeListener { _, isChecked ->
            childCheckBoxes.forEach {
                it.isChecked = isChecked
            }
        }
    }

    private fun initialRadioGroup() {
        rbGr.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                rbt_lightmode.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
                rbt_darkmode.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                rbt_defaultmode.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                }
            }
        }
    }


}