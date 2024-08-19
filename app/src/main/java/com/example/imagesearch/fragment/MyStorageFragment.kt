package com.example.imagesearch.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.MainActivity
import com.example.imagesearch.adapter.MyStorageAdapter
import com.example.imagesearch.databinding.FragmentMyStorageBinding
import com.example.imagesearch.model.SearchItemModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyStorageFragment : Fragment() {

    private lateinit var mContext: Context

    private var binding: FragmentMyStorageBinding? = null
    private lateinit var adapter: MyStorageAdapter

    private var likes : List<SearchItemModel> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        likes = mainActivity.likedItems

        Log.d("MyStorageFragment", "#ggilmon likes size = ${likes.size}")

        adapter = MyStorageAdapter(mContext).apply {
            items = likes.toMutableList()
        }

        binding = FragmentMyStorageBinding.inflate(inflater, container, false).apply {
            rvMyStorage.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvMyStorage.adapter = adapter
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수 방지 위해 뷰가 파괴될 때 바인딩 객체를 null로
        binding = null
    }

//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_storage, container, false)
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MyStorageFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}