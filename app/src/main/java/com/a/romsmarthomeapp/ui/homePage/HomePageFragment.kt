package com.a.romsmarthomeapp.ui.homePage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.romsmarthomeapp.R
import com.a.romsmarthomeapp.databinding.FragmentHomePageBinding
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.ui.deviceSteering.DeviceSteeringViewModel
import com.a.romsmarthomeapp.ui.myAccount.UserViewModel
import com.a.romsmarthomeapp.utils.Resource
import com.a.romsmarthomeapp.utils.Resource.*
import com.a.romsmarthomeapp.utils.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment(), MyHomePageAdapter.DeviceItemListener {



    private var binding: FragmentHomePageBinding  by autoCleared()
    private val viewModel: DevicesViewModel  by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter:MyHomePageAdapter



    var deviceList = ArrayList<Device>()
    var filterResult = ArrayList<Device>()
    private val searchList = mutableSetOf<String>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val mContext = inflater.context

        setupRecyclerView(mContext, deviceList)
        setupObservers()
        swipeDeleteRecyclerView()

    // Navigation to AccountFragment
        binding.userAvatar.setOnClickListener(to_myAccountFragmentListener)
        binding.toMyAccount.setOnClickListener(to_myAccountFragmentListener)

    // Filter settings
        binding.light.setOnClickListener(lightSelectListener)
        binding.heater.setOnClickListener(heaterSelectListener)
        binding.rollerShutter.setOnClickListener(rollerShutterSelectListener)


        adapter.notifyDataSetChanged()
        return binding.root
    }


    private val to_myAccountFragmentListener = View.OnClickListener { _ ->
    //Get user before showing his details
        try {
            setupUser()
            findNavController().navigate(
                R.id.action_homePageFragment_to_myAccountFragment
            )
        }catch (e: Exception) {
            Toast.makeText(activity?.applicationContext,"L'utilisateur n'est pas encore connu",Toast.LENGTH_SHORT).show()
        }

    }


    private fun setupUser() {

        return userViewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("TAG", "userViewModel.user:" + it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }


    private val lightSelectListener = View.OnClickListener { _ ->
       addFilter( binding.light, "Light")
    }

    private val heaterSelectListener = View.OnClickListener { _ ->
       addFilter( binding.heater, "Heater")
    }
    private val rollerShutterSelectListener  = View.OnClickListener { _ ->
       addFilter( binding.rollerShutter, "RollerShutter")
    }



    private fun addFilter(checkBox: CheckBox, deviceType: String){

        if (checkBox.isChecked){
            searchList.add(deviceType)
        }else{
            searchList.remove(deviceType)
        }
        filterResult.clear()
        if (searchList.isNotEmpty()){
            deviceList.forEach(){ device ->
                if (searchList.contains(device.productType)){
                    filterResult.add(device)
                }
            }
            adapter.setItems(filterResult)
        }else{
            adapter.setItems(deviceList)
        }
    }


    private fun swipeDeleteRecyclerView(){

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedDevice: Device = deviceList.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                deviceList.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)





//                adapter.notifyDataSetChanged()

                Snackbar.make(binding.recyclerView, "Device deleted : " + deletedDevice.deviceName, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Annuler",
                        View.OnClickListener {
                            deviceList.add(position, deletedDevice)
                            adapter.notifyItemInserted(position)
                            adapter.notifyDataSetChanged()
                        }).show()
            }
        }).attachToRecyclerView(binding.recyclerView)
        adapter.notifyDataSetChanged()
        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    private fun setupRecyclerView(context: Context,  deviceList:ArrayList<Device>) {
        adapter = MyHomePageAdapter(this, null, context, deviceList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.devices.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))

                    Log.e("TAG", "viewModel.devices.observe"+ it.data)
                    if (!it.data.isNullOrEmpty()) {
                        deviceList = (ArrayList(it.data));
                    }
                }
                Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


    @SuppressLint("LogNotTimber")
    override fun onClickedDevice(deviceId: Int) {

        Log.e("TAG", "onClickedDevice: $deviceId", )
        findNavController().navigate(
            R.id.action_homePageFragment_to_deviceSteeringFragment,
            bundleOf("id" to deviceId)
        )
    }

}




