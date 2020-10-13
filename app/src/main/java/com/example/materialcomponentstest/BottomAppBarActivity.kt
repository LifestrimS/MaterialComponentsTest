package com.example.materialcomponentstest

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.example.materialcomponentstest.material_layout.BottomAppBarCutCornersTopEdge
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.android.synthetic.main.activity_bottom_app_bar.*
import kotlinx.android.synthetic.main.activity_card.*

class BottomAppBarActivity : AppCompatActivity() {
    private val oneDp by lazy { 1.0f }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_app_bar)

        initButtons()
        initBottomAppBarMenuAndNavigation()
        //setupBottomAppBarCutCornersBackground()

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


    private fun setInsets() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            ViewCompat
                .setOnApplyWindowInsetsListener(container_bottom) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_bottom) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_bottom.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_bottom.paddingBottom
                    )
                    insets
                }

        }
    }

    private fun initButtons() {
        fabAlignmentModeButton.setOnClickListener {
            val alignmentMode = if (bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
                BottomAppBar.FAB_ALIGNMENT_MODE_END else BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            bottomAppBar.fabAlignmentMode = alignmentMode
        }
        fabAnimationModeButton.setOnClickListener {
            val animationMode = if (bottomAppBar.fabAnimationMode == BottomAppBar.FAB_ANIMATION_MODE_SCALE) {
                BottomAppBar.FAB_ANIMATION_MODE_SLIDE
            } else {
                BottomAppBar.FAB_ANIMATION_MODE_SCALE
            }
            bottomAppBar.fabAnimationMode = animationMode
        }
        increaseFabCradleMarginButton.setOnClickListener {
            val cradleMargin = (bottomAppBar.fabCradleMargin + oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.fabCradleMargin = cradleMargin
        }
        decreaseFabCradleMarginButton.setOnClickListener {
            val cradleMargin = (bottomAppBar.fabCradleMargin - oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.fabCradleMargin = cradleMargin
        }
        increaseFabCradleCornerRadiusButton.setOnClickListener {
            val cradleCornerRadius = (bottomAppBar.fabCradleRoundedCornerRadius + oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.fabCradleRoundedCornerRadius = cradleCornerRadius
        }
        decreaseFabCradleCornerRadiusButton.setOnClickListener {
            val cradleCornerRadius = (bottomAppBar.fabCradleRoundedCornerRadius - oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.fabCradleRoundedCornerRadius = cradleCornerRadius
        }
        increaseFabCradleVerticalOffsetButton.setOnClickListener {
            val cradleVerticalOffset = (bottomAppBar.cradleVerticalOffset + oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.cradleVerticalOffset = cradleVerticalOffset
        }
        decreaseFabCradleVerticalOffsetButton.setOnClickListener {
            val cradleVerticalOffset = (bottomAppBar.cradleVerticalOffset - oneDp).coerceIn(0f, 16 * oneDp)
            bottomAppBar.cradleVerticalOffset = cradleVerticalOffset
        }
    }

    private fun initBottomAppBarMenuAndNavigation() {
        bottomAppBar.replaceMenu(R.menu.menu_bottom_app_bar)
        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, "Clicked menu item 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    Toast.makeText(this, "Clicked menu item 2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3 -> {
                    Toast.makeText(this, "Clicked menu item 3", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        bottomAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Clicked navigation item", Toast.LENGTH_SHORT).show()
        }
    }

    // BUG: Shape theming is not supported by default for the BottomAppBar cradle
    // Use this function to enable cut corners for the BottomAppBar FAB cradle
    // Note: It does NOT respond to increasing/decreasing the FAB cradle margin, rounded corner radius or vertical offset
    // Copied from: https://github.com/material-components/material-components-android/blob/master/catalog/java/io/material/catalog/bottomappbar/BottomAppBarCutCornersTopEdge.java
    // See: https://issuetracker.google.com/issues/127454207
    private fun setupBottomAppBarCutCornersBackground() {
        val topEdge = BottomAppBarCutCornersTopEdge(
            bottomAppBar.fabCradleMargin,
            bottomAppBar.fabCradleRoundedCornerRadius,
            bottomAppBar.cradleVerticalOffset
        )
        val background = bottomAppBar.background as MaterialShapeDrawable
        background.shapeAppearanceModel = background.shapeAppearanceModel.toBuilder().setTopEdge(topEdge).build()
        background.invalidateSelf()
    }
}