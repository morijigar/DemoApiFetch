package com.example.apifetch.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apifetch.BR
import com.example.apifetch.R
import com.example.apifetch.adapter.PostAdapter
import com.example.apifetch.base.BaseFragment
import com.example.apifetch.databinding.FragmentMainBinding
import com.example.apifetch.viewmodel.MainFragmentViewModel
import com.example.myapplication.databse.Database

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {

    var adapter: PostAdapter? = null
    //var loadmore = true
    //var offset = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_main
    override val viewModel: MainFragmentViewModel
        get() = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    fun initData() {
        viewModel.callApi()
        showData()

        viewDataBinding.swipeRefresh.setOnRefreshListener {
            viewModel.callApi()
        }

        val layoutManager = LinearLayoutManager(baseActivity)
        viewDataBinding.recycler.layoutManager = layoutManager


        /* For lOAD MORE
        viewDataBinding?.recycler?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) { // super.onScrolled(recyclerView, dx, dy);
                val lastVisiblePosition: Int = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == recyclerView.childCount) {
                    if (loadmore) {
                        val data1 = viewModel.getDatabaseList(offset, this@MainActivity)
                        loadmore = data1.size == 10
                        offset += 10

                        viewModel.listDataRecycle.value?.addAll(data1)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
        })*/
    }

    private fun showData() {

        viewModel.listData.observe(viewLifecycleOwner, Observer {
            viewDataBinding.swipeRefresh.isRefreshing = false
            Database.getInstance(baseActivity!!).appDatabase.PostDao().deleteAllRow()
            Database.getInstance(baseActivity!!).appDatabase.PostDao().save(it)
            viewModel.listDataRecycle.value = viewModel.getDatabaseList(0, baseActivity!!)
            /*  if (viewModel.listDataRecycle.value!!.size == 10) {
                  loadmore = true
                  offset += 10
              }*/
        })

        viewModel.listDataRecycle.observe(viewLifecycleOwner, Observer {
            if (null == adapter) {
                adapter = PostAdapter(baseActivity!!, it)
                viewDataBinding.recycler.adapter = adapter
            } else {
                //adapter?.updateList(it)
                adapter?.notifyDataSetChanged()
            }
        })


    }
}