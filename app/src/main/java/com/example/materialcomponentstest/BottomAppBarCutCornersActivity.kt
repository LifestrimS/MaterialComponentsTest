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
import kotlinx.android.synthetic.main.activity_bottom_app_bar.bottomAppBar
import kotlinx.android.synthetic.main.activity_bottom_app_bar.decreaseFabCradleCornerRadiusButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.decreaseFabCradleMarginButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.decreaseFabCradleVerticalOffsetButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.fabAlignmentModeButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.fabAnimationModeButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.increaseFabCradleCornerRadiusButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.increaseFabCradleMarginButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar.increaseFabCradleVerticalOffsetButton
import kotlinx.android.synthetic.main.activity_bottom_app_bar_cut_corners.*
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_card.container_card

class BottomAppBarCutCornersActivity : AppCompatActivity() {
    private val oneDp by lazy { 1.0f }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_app_bar_cut_corners)

        initButtons()
        initBottomAppBarMenuAndNavigation()
        setupBottomAppBarCutCornersBackground()

        setStatusBarColorIcons()
        setInsets()
    }

    private fun initButtons() {
        fabAlignmentModeButton.setOnClickListener {
            val alignmentMode =
                if (bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
                    BottomAppBar.FAB_ALIGNMENT_MODE_END else BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            bottomAppBar.fabAlignmentMode = alignmentMode
        }
        fabAnimationModeButton.setOnClickListener {
            val animationMode =
                if (bottomAppBar.fabAnimationMode == BottomAppBar.FAB_ANIMATION_MODE_SCALE) {
                    BottomAppBar.FAB_ANIMATION_MODE_SLIDE
                } else {
                    BottomAppBar.FAB_ANIMATION_MODE_SCALE
                }
            bottomAppBar.fabAnimationMode = animationMode
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

    private fun setupBottomAppBarCutCornersBackground() {
        val topEdge = BottomAppBarCutCornersTopEdge(
            bottomAppBar.fabCradleMargin,
            bottomAppBar.fabCradleRoundedCornerRadius,
            bottomAppBar.cradleVerticalOffset
        )
        val background = bottomAppBar.background as MaterialShapeDrawable
        background.shapeAppearanceModel =
            background.shapeAppearanceModel.toBuilder().setTopEdge(topEdge).build()
        background.invalidateSelf()
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
                .setOnApplyWindowInsetsListener(container_bottom_cut_corners) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_bottom_cut_corners) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_bottom_cut_corners.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_bottom_cut_corners.paddingBottom
                    )
                    insets
                }

        }
    }
}