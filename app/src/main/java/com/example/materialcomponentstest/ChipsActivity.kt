package com.example.materialcomponentstest

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.activity_button.*
import kotlinx.android.synthetic.main.activity_button.container_button
import kotlinx.android.synthetic.main.activity_chips.*

class ChipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chips)

        setupChipDrawable()

        setStatusBarColorIcons()
        setInsets()
    }

    private fun setupChipDrawable() {
        val chip = ChipDrawable.createFromResource(this, R.xml.chip)
        chip.setBounds(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)
        val span = ImageSpan(chip)
        val text = editText.text!!
        text.setSpan(span, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
                .setOnApplyWindowInsetsListener(container_chips) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_chips) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_chips.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_chips.paddingBottom
                    )
                    insets
                }

        }

    }
}