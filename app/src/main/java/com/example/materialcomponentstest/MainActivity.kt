package com.example.materialcomponentstest

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setStatusBarColorIcons()
        setInsets()
        initialSwitch()

    }

    fun toButtons(view: View) {
        startActivity(Intent(this, ButtonActivity::class.java))
    }

    fun toSelection(view: View) {
        startActivity(Intent(this, SelectionActivity::class.java))
    }

    fun toChips(view: View) {
        startActivity(Intent(this, ChipsActivity::class.java))
    }

    fun toCard(view: View) {
        startActivity(Intent(this, CardActivity::class.java))
    }

    fun toBottom(view: View) {
        startActivity(Intent(this, BottomAppBarActivity::class.java))
    }

    fun toBottomCutCorners(view: View) {
        startActivity(Intent(this, BottomAppBarCutCornersActivity::class.java))
    }

    fun toBottomSheet(view: View) {
        startActivity(Intent(this, BottomSheetActivity::class.java))
    }

    private fun initialSwitch() {
        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fab.shrink()
            } else {
                fab.extend()
            }
        }
    }


    private fun setInsets() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            ViewCompat
                .setOnApplyWindowInsetsListener(container_main) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_main) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_main.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_main.paddingBottom
                    )
                    insets
                }

        }

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


}