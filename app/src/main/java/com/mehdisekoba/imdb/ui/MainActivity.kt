package com.mehdisekoba.imdb.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.databinding.ActivityMainBinding
import com.mehdisekoba.imdb.utils.base.BaseActivity
import com.mehdisekoba.imdb.utils.extensions.setStatusBarIconsColor
import dagger.hilt.android.AndroidEntryPoint

@Suppress("ktlint:standard:backing-property-naming")
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    // Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // other
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Transparent status bar
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            // Change color of status bar icons
            setStatusBarIconsColor(true)
        }
        // Init nav host
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)
        // corner bottom navigation
        val radius = resources.getDimension(R.dimen.cornerSize)
        val shapeDrawable: MaterialShapeDrawable =
            binding.bottomNav.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel =
            shapeDrawable.shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build()
        // Gone bottom menu
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.apply {
                when (destination.id) {
                    R.id.homeFragment -> bottomNav.isVisible = true
                    R.id.searchFragment -> bottomNav.isVisible = true
                    R.id.videoFragment -> bottomNav.isVisible = true
                    R.id.profileFragment -> bottomNav.isVisible = true
                    else -> bottomNav.isVisible = false
                }
            }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        binding.bottomNav.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
