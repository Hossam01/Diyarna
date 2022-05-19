package com.example.diyarna.presentation.main.more

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.R
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.local.model.MoreModel
import com.example.diyarna.databinding.MoreFragmentBinding
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.more.adapter.MoreAdapter
import com.example.diyarna.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MoreFragment : BaseFragment<MoreFragmentBinding>(MoreFragmentBinding::inflate),MoreAdapter.ItemAdapterListener{

    val moreViewModel : MoreViewModel by viewModels()
    lateinit var bundle:Bundle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(resources.getString(R.string.more))


        var adapter=MoreAdapter()
        adapter.submitList(listOf(MoreModel(R.string.about_us,R.drawable.info,R.string.about_details),MoreModel(R.string.contact_us,R.drawable.call,R.string.contact_details),MoreModel(R.string.privacy,R.drawable.privacy,R.string.privacy_details),MoreModel(R.string.language,R.drawable.language,R.string.about_us)))
        binding.rcItems.adapter=adapter
        adapter.mListener=this
        bundle=Bundle()


        binding.logout.setOnClickListener {
            moreViewModel.logout()
            (requireActivity() as MainActivity).gotoLogin()
        }

        binding.login.setOnClickListener {
            (requireActivity() as MainActivity).gotoLogin()
        }


        lifecycleScope.launchWhenCreated {
            moreViewModel.getLoginorLogout().collect {
                when(it.status)
                {
                    Status.OK->{
                        binding.logout.visibility=View.VISIBLE
                        binding.login.visibility=View.INVISIBLE
                    }
                    Status.ERROR->{
                        binding.login.visibility=View.VISIBLE
                        binding.logout.visibility=View.INVISIBLE
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
        moreViewModel.getAction().collect {
         when(it.status)
         {
             Status.OK->{
                 binding.login.visibility=View.VISIBLE
                 binding.logout.visibility=View.INVISIBLE
             }
             Status.ERROR->{
             }
         }
        }
        }
    }

    override fun onClickItem(position: Int,itemDto: MoreModel) {
        if (position==3)
        {
            (requireActivity() as MainActivity).navToDestination(R.id.nav_language)
        }
        else {
            bundle.putParcelable("Setting", itemDto)
            (requireActivity() as MainActivity).navToDestination(R.id.nav_about, bundle)
        }

    }


}