package com.example.materialcomponentstest

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.updatePadding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_button.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class ButtonActivity : AppCompatActivity() {

    private var badgingEnabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        initialTextInputLayout1()
        setupBottomNavigationMenu()
        setStatusBarColorIcons()
        setInsets()
    }

    fun navigationViewLabelVisible(view: View) {
        val labelVisibilityMode = when (bottom_navigation.labelVisibilityMode) {
            LabelVisibilityMode.LABEL_VISIBILITY_AUTO -> LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
            LabelVisibilityMode.LABEL_VISIBILITY_SELECTED -> LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED -> LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
            else -> LabelVisibilityMode.LABEL_VISIBILITY_AUTO
        }
        bottom_navigation.labelVisibilityMode = labelVisibilityMode
    }

    fun navigationViewBadgingEnabled(view: View) {
        if (!badgingEnabled) {
            bottom_navigation.menu.forEachIndexed { index, item ->
                val badgeDrawable = bottom_navigation.getOrCreateBadge(item.itemId)
                if (index > 0) {
                    val number = 10f.pow(index * 2).toInt()
                    badgeDrawable.number = number
                }
                // Alternatively init once and use badgeDrawable#setVisible(true, false)
            }
            badgingEnabled = true
        } else {
            bottom_navigation.menu.forEach { item ->
                bottom_navigation.removeBadge(item.itemId)
                // Alternatively init once and use badgeDrawable#setVisible(false, false)
            }
            badgingEnabled = false
        }
    }

    fun navigationViewBadgeGravityButton(view: View) {
        val badgeGravity = when (bottom_navigation.getBadge(R.id.item1)?.badgeGravity) {
            BadgeDrawable.TOP_END -> BadgeDrawable.TOP_START
            BadgeDrawable.TOP_START -> BadgeDrawable.BOTTOM_START
            BadgeDrawable.BOTTOM_START -> BadgeDrawable.BOTTOM_END
            else -> BadgeDrawable.TOP_END
        }
        Log.d("TAG", "dabgeGravity: $badgeGravity : ${bottom_navigation.getBadge(R.id.item1)?.badgeGravity}")
        bottom_navigation.menu.forEachIndexed { _, item ->
            val badgeDrawable = bottom_navigation.getBadge(item.itemId)
            badgeDrawable?.badgeGravity = badgeGravity
        }
    }

    private fun setupBottomNavigationMenu() {
        bottom_navigation.inflateMenu(R.menu.menu_bottom_navigation_2)
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

    private fun setInsets() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            ViewCompat
                .setOnApplyWindowInsetsListener(container_button) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_button) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_button.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_button.paddingBottom
                    )
                    insets
                }

        }

    }

    fun callSnackbar(view: View) {
        Snackbar.make(view, "Simple snackbar", Snackbar.LENGTH_SHORT).setAction("Repeat") {
            callSnackbar(it)
        }
            .setActionTextColor(Color.RED)
            .show()


    }

    private fun initialTextInputLayout1() {
        tIEt.setOnFocusChangeListener { _, _ ->
            if (tIEt.text.isNullOrEmpty()) {
                tILbl1.error = "Input text"
            } else {
                tILbl1.error = null
            }
        }
    }
}