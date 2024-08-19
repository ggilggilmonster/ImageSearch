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
import com.example.imagesearch.model.SearchItemModel

class MainActivity : AppCompatActivity() {

//    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//
//    val fragment = mutableListOf<SearchData>()

    private lateinit var binding: ActivityMainBinding

    var likedItems : ArrayList<SearchItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.run {
            btnSearch.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnMyStorage.setOnClickListener {
                setFragment(MyStorageFragment())
            }
        }

        setFragment(SearchFragment())

//        binding.apply {
//            btnSearch.setOnClickListener {
//                setFragment(SearchFragment())
//            }
//            btnMyStorage.setOnClickListener {
//                setFragment(MyStorageFragment())
//            }
//        }
    }

    private fun setFragment(fragment : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    fun addLikeItem(item: SearchItemModel) {
        if (!likedItems.contains(item)) {
            likedItems.add(item)
        }
    }

    fun deleteLikeItem(item: SearchItemModel) {
        likedItems.remove(item)
    }
}