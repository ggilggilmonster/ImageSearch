package com.example.imagesearch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.fragment.MyStorageFragment
import com.example.imagesearch.fragment.SearchFragment
import com.example.imagesearch.retrofit.SearchData

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragment = mutableListOf<SearchData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setFragment(SearchFragment())

        binding.apply {
            btnSearch.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnMyStorage.setOnClickListener {
                setFragment(MyStorageFragment())
            }
        }
    }

    private fun setFragment(fragment : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    fun addLikeItem(image: String, site: String, date: String) {
        val item = SearchData(image, site, date)
        fragment.add(item)
    }

    fun deleteLikeItem(image: String, site: String, date: String) {
        val item = SearchData(image, site, date)
        fragment.remove(item)
    }
}