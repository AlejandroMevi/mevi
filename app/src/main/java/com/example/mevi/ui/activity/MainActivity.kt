package com.example.mevi.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.mevi.R
import com.example.mevi.core.Utilities
import com.example.mevi.databinding.ActivityMainBinding
import com.example.mevi.ui.fragments.CategoriesFragment
import com.example.mevi.ui.fragments.FavFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainInterface {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initView()
        buttons()
        setContentView(binding.root)
    }

    private fun initView() {
        Utilities().loadFragment(this, CategoriesFragment(), "Books")
    }

    private fun buttons() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Utilities().loadFragment(this, CategoriesFragment(), "Books")
                    true
                }

                R.id.fav -> {
                    Utilities().loadFragment(this, FavFragment(), "Fav")
                    true
                }

                else -> false
            }
        }
    }

    override fun showLoading(isShowing: Boolean) {
        binding.progress.root.isVisible = isShowing
        if (isShowing) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}