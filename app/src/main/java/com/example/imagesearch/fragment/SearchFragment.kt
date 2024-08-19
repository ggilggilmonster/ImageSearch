package com.example.imagesearch.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.Constants
import com.example.imagesearch.adapter.SearchAdapter
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.retrofit.ImageResponse
import com.example.imagesearch.retrofit.NetWorkClient.apiService
import com.example.imagesearch.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mContext: Context
    private lateinit var adapter: SearchAdapter
    private lateinit var gridLayoutManager: StaggeredGridLayoutManager
//    private lateinit var callback: OnBackPressedCallback

    private var resItems: ArrayList<SearchItemModel> = ArrayList()

//        override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                requireActivity().recreate()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setupViews()    //  뷰 초기 설정
        setupListeners()    //  리스너 설정

        return binding.root
    }

    private fun setupViews() {
        //  RecyclerView 설정
        gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearch.layoutManager = gridLayoutManager

        adapter = SearchAdapter(mContext)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.itemAnimator = null

        // 최근 검색어를 가져와 EditText에 설정
        val getHistory = Utils.getHistory(requireContext())
        binding.etSearch.setText(getHistory)
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.rvSearch.layoutManager = GridLayoutManager(context, 2)
//        adapter = SearchAdapter(mutableListOf())
//        binding.rvSearch.adapter = adapter
//
//        binding.etSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//                val search = binding.etSearch.text.toString()
//                communicateNetWork(search)
//
//                softkeyboardHide()
//                return@OnKeyListener true
//            }
//            false
//        })
//
//        loadWord()
//
//        binding.btnSearch.setOnClickListener {
//            val search = binding.etSearch.text.toString()
//            communicateNetWork(search)
//            softkeyboardHide()
//            saveWord(search)
//        }
//        binding.rvSearch.setOnClickListener {
//            Log.d("imageClick", "Clicked")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }

    private fun setupListeners() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                Utils.saveHistory(requireContext(), query)
                adapter.clearItem()
                fetchImageResults(query)
            } else {
                Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            // 키보드 숨기기 따로 안 빼줘도 됨.
            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
    }

    private fun fetchImageResults(query: String) {
        apiService.getImgData(Constants.AUTH_HEADER, query, "recency", 1, 80)
            ?.enqueue(object : Callback<ImageResponse?> {
                override fun onResponse(
                    call: Call<ImageResponse?>,
                    response: Response<ImageResponse?>
                ) {
                    response.body()?.meta?.let { meta ->
                        if (meta.totalCount > 0) {
                            response.body()!!.documents.forEach { documents ->
                                val site = documents.siteName
                                val date = documents.dateTime
                                val url = documents.thumbnailUrl
                                resItems.add(SearchItemModel(site, date, url))
                            }
                        }
                    }
                    adapter.items = resItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
                    Log.e("#ggilmon", "onFailure: ${t.message}")
                }

            })
    }
}

//    private fun communicateNetWork(query: String) {
//        val call = NetWorkClient.apiService.getImgData(query)
//
//        call.enqueue(object : Callback<ImageResponse> {
//            override fun onResponse(
//                call: Call<ImageResponse>,
//                response: Response<ImageResponse>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let { searchResponse ->
//                        adapter.mItems.clear()
//                        adapter.mItems.addAll(searchResponse.documents)
//                        adapter.notifyDataSetChanged()
//                    }
//
//                } else {
//                    Log.e("SearchFragment", "Error: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
//                Log.e("SearchFragment", "Request failed", t)
//            }
//        })
//    }

//    private fun saveWord(word: String) {
//        val sharedPref = requireContext().getSharedPreferences("pref", 0)
//        val edit = sharedPref.edit()
//        edit.putString("savedSearch", word)
//        edit.apply()
//    }
//
//    private fun loadWord() {
//        val sharedPref = requireContext().getSharedPreferences("pref", 0)
//        binding.etSearch.setText(sharedPref.getString("savedSearch", ""))
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

//    private fun softkeyboardHide() {
//        val hide = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        hide.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
//    }
//}