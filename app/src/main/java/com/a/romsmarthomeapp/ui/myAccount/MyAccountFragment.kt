package com.a.romsmarthomeapp.ui.myAccount

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.a.romsmarthomeapp.R
import com.a.romsmarthomeapp.databinding.EditProfilBinding
import com.a.romsmarthomeapp.databinding.FragmentMyAccountBinding
import com.a.romsmarthomeapp.db.database.Database
import com.a.romsmarthomeapp.model.Address
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.ui.homePage.DevicesViewModel
import com.a.romsmarthomeapp.ui.homePage.MyHomePageAdapter
import com.a.romsmarthomeapp.utils.Resource
import com.a.romsmarthomeapp.utils.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MyAccountFragment : Fragment(), MyHomePageAdapter.DeviceItemListener {

    private var binding: FragmentMyAccountBinding by autoCleared()
    private val userViewModel: UserViewModel by viewModels()
    private val deviceViewModel: DevicesViewModel by viewModels()
    private lateinit var adapter: MyHomePageAdapter
    var deviceList = ArrayList<Device>()
    var userList = ArrayList<User>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        val mContext = inflater.context

        setupRecyclerView(mContext, deviceList)
        setupObservers()
        setupUser()


        binding.editprofile.setOnClickListener {
            showDialogOne(mContext)
        }

        return binding.root
    }

    private fun setupRecyclerView(context: Context, deviceList: ArrayList<Device>) {
        adapter = MyHomePageAdapter(null, this, context, deviceList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        deviceViewModel.devices.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    if (!it.data.isNullOrEmpty()) {
                        deviceList = (ArrayList(it.data));
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


    private fun setupUser() {
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            userList.clear()
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    Log.e("TAG", "userViewModel.user:" + it.data)
                    userList.add(it.data!!)
                    bindUser(it.data!!)
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


    private fun bindUser(user: User) {

        var birthday = user?.birthDate.toString().toLong()
        binding.username.text = user?.firstName + " " + user?.lastName
        Log.e("TAG", "bindUser:" + user?.lastName)
        binding.birthday.text = "Anniversaire : " + getDateTime(birthday)
        binding.country.text = user.address?.country
        binding.city.text = user.address?.city + ", " +  user.address?.postalCode
        binding.street.text = user.address?.street + ", " +  user.address?.streetCode

    }

    private fun getDateTime(stringDate: Long): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM")
            val netDate = Date(stringDate * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun showDialogOne(mContext: Context) {

        val dialog = BottomSheetDialog(mContext)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindDialog:EditProfilBinding = EditProfilBinding.inflate(inflater)
        dialog.setContentView(bindDialog.root)


        val firstNameEditText = bindDialog.textInputEditTextFirstName
        val lastnameEditText = bindDialog.textInputEditTextLastname
        val birthdayEditText = bindDialog.textInputEditTextBirthday
        val countryEditText = bindDialog.textInputEditTextCountry
        val cityEditText = bindDialog.textInputEditTextTown
        val postalCodeEditText = bindDialog.textInputEditTextPostalCode
        val streetEditText = bindDialog.textInputEditTextStreet
        val streetCodeEditText = bindDialog.textInputEditTextStreetCode

        val validerBtn = bindDialog.valider


        validerBtn?.setOnClickListener {

            val firstName = firstNameEditText?.text.toString()
            val lastName = lastnameEditText?.text.toString()
            val birthday = birthdayEditText?.text.toString()
            val covertedBirthday = SimpleDateFormat("dd/MM/yyyy").parse(birthday)

            val postalCodeNew = postalCodeEditText!!.text.toString().toInt()
            val countryNew = countryEditText?.text.toString()
            val streetNew = streetEditText?.text.toString()
            val streetCodeNew = streetCodeEditText?.text.toString()
            val cityNew = cityEditText?.text.toString()




            userViewModel.userUpdate.observe(viewLifecycleOwner, Observer {
//                userList.clear()
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE

                        binding.username.text = "$firstName  $lastName"
                        try {
                            binding.birthday.text = "Anniversaire :"+ birthday.getDateInAnotherFormat("dd/MM/yyy","dd/MM/yyyy")
                        }catch (e: Exception) {
                            Toast.makeText(activity?.applicationContext,"Le format de la date est jj/mm/année en chiffre",Toast.LENGTH_SHORT).show()
                        }
                        binding.city.text = "$cityNew , $postalCodeNew"
                        binding.country.text = countryNew
                        binding.street.text = "$streetNew, $streetCodeNew"

                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            })
            dialog.dismiss()

        }


        dialog.show()
    }


    private fun String.getDateInAnotherFormat(inputFormat: String, outputFormat: String):String = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)?.let { SimpleDateFormat(outputFormat,Locale.getDefault()).format(it) }?:""


    @SuppressLint("LogNotTimber")
    override fun onClickedDevice(deviceId: Int) {

        Log.e("TAG", "onClickedDevice: $deviceId", )
        findNavController().navigate(
            R.id.action_myAccountFragment_to_deviceSteeringFragment,
            bundleOf("id" to deviceId)
        )
    }


    }










