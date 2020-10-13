package com.example.materialcomponentstest

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_card.container_card

class BottomSheetActivity : AppCompatActivity() {

    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<View>

    private val startColor = Color.parseColor("#00FFFFFF")
    private val endColor = Color.parseColor("#FFFFFFFF")
    private val textColor = Color.parseColor("#FF000000")

    private var modalDismissWithAnimation = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        setupButtons()
        setupStandardBottomSheet()
        //animateStandardBottomSheetStates()

        setStatusBarColorIcons()
        setInsets()
    }

    private fun setupButtons() {
        standardBottomSheetButton.setOnClickListener {
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        modalBottomSheetButton.setOnClickListener {
            showModalBottomSheet()
        }
        toggleModalDismissAnimationButton.setOnClickListener {
            modalDismissWithAnimation = !modalDismissWithAnimation
        }
    }

    private fun setupStandardBottomSheet() {
        standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                textView.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "STATE_EXPANDED"
                    BottomSheetBehavior.STATE_COLLAPSED -> "STATE_COLLAPSED"
                    BottomSheetBehavior.STATE_DRAGGING -> "STATE_DRAGGING"
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> "STATE_HALF_EXPANDED"
                    BottomSheetBehavior.STATE_HIDDEN -> "STATE_HIDDEN"
                    BottomSheetBehavior.STATE_SETTLING -> "STATE_SETTLING"
                    else -> null
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val fraction = (slideOffset + 1f) / 2f
                val color = ArgbEvaluatorCompat.getInstance().evaluate(fraction, startColor, endColor)
                slideView.setBackgroundColor(color)
            }
        }
        standardBottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
        standardBottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL
        textView.setTextColor(textColor)
    }

    private fun showModalBottomSheet() {
        val modalBottomSheet = ModalBottomSheet.newInstance(modalDismissWithAnimation)
        modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
    }

    private fun animateStandardBottomSheetStates() {
        standardBottomSheet.postDelayed({
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }, 1000L)
        standardBottomSheet.postDelayed({
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 2000L)
        standardBottomSheet.postDelayed({
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }, 3000L)
        standardBottomSheet.postDelayed({
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }, 4000L)
        standardBottomSheet.postDelayed({
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }, 5000L)
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
                .setOnApplyWindowInsetsListener(container_bottomsheet) { view, insets ->
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
                .setOnApplyWindowInsetsListener(container_bottomsheet) { view, insets ->
                    view.updatePadding(
                        top = insets.systemWindowInsetTop + container_bottomsheet.paddingTop,
                        bottom = insets.systemWindowInsetBottom + container_bottomsheet.paddingBottom
                    )
                    insets
                }

        }
    }
}