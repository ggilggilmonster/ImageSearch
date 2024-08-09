package com.example.imagesearch.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.adapter.SearchAdapter
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.retrofit.ImageResponse
import com.example.imagesearch.retrofit.NetWorkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearch.layoutManager = GridLayoutManager(context, 2)
        adapter = SearchAdapter(mutableListOf())
        binding.rvSearch.adapter = adapter

        binding.etSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val search = binding.etSearch.text.toString()
                communicateNetWork(search)

                softkeyboardHide()
                return@OnKeyListener true
            }
            false
        })

        binding.btnSearch.setOnClickListener {
            val search = binding.etSearch.text.toString()
            communicateNetWork(search)
            softkeyboardHide()
        }
    }

    private fun communicateNetWork(query: String) {
        val call = NetWorkClient.apiService.getImgData(query)

        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(
                call: Call<ImageResponse>,
                response: Response<ImageResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { searchResponse ->
                        adapter.mItems.clear()
                        adapter.mItems.addAll(searchResponse.documents)
                        adapter.notifyDataSetChanged()
                    }

                } else {
                    Log.e("SearchFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.e("SearchFragment", "Request failed", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun softkeyboardHide() {
        val hide = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hide.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}