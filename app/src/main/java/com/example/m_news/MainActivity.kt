package com.example.m_news

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.m_news.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home || destination.id == R.id.navigation_bookmark) {

                if (binding.navView.visibility != View.VISIBLE) {
                    binding.navView.visibility = View.VISIBLE

                    val fadeIn = ObjectAnimator.ofFloat(binding.navView, "alpha", 0f, 1f).apply {
                        duration = 300
                    }
                    val slideIn = ObjectAnimator.ofFloat(binding.navView, "translationY", binding.navView.height.toFloat(), 0f).apply {
                        duration = 300
                    }

                    fadeIn.start()
                    slideIn.start()
                }
            } else {

                val fadeOut = ObjectAnimator.ofFloat(binding.navView, "alpha", 1f, 0f).apply {
                    duration = 300
                }
                val slideOut = ObjectAnimator.ofFloat(binding.navView, "translationY", 0f, binding.navView.height.toFloat()).apply {
                    duration = 300
                }

                fadeOut.start()
                slideOut.start()

                slideOut.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        binding.navView.visibility = View.GONE
                    }
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
        }
    }
}
