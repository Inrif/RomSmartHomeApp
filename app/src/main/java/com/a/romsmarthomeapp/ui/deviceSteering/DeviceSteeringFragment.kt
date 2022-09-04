package com.a.romsmarthomeapp.ui.deviceSteering

import android.content.Context
import android.graphics.PorterDuff.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.a.romsmarthomeapp.R
import com.a.romsmarthomeapp.databinding.FragmentDeviceSteeringBinding
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.utils.Resource
import com.a.romsmarthomeapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class DeviceSteeringFragment : Fragment() {

    private var binding: FragmentDeviceSteeringBinding by autoCleared()
    private val viewModel: DeviceSteeringViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceSteeringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments ?.getInt("id")?.let { viewModel.start(it) }
        val context: Context = view.context;
        setupObservers(context)
    }
    private fun setupObservers(context: Context) {
        viewModel.device.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindDevice(it.data!!, context)
                }
                Resource.Status.ERROR ->{}
                Resource.Status.LOADING -> {
                }
            }
        })
    }

    private fun bindDevice(device: Device, context:Context) {

        when (device.productType) {
            "Light" -> {

                binding.lightView.visibility = View.VISIBLE
                binding.heaterView.visibility = View.GONE
                binding.rollerShutterView.visibility = View.GONE
                bindLight(device, context)



            }
            "Heater" -> {

                binding.lightView.visibility = View.GONE
                binding.heaterView.visibility = View.VISIBLE
                binding.rollerShutterView.visibility = View.GONE
                bindHeater(device, context)

            }
            else -> {

                binding.lightView.visibility = View.GONE
                binding.heaterView.visibility = View.GONE
                binding.rollerShutterView.visibility = View.VISIBLE
                rollerShutter(device)
            }
        }
    }


    private fun bindLight(device: Device, context:Context){
        binding.devicename.text = device.deviceName
        setProgress(device.intensity.toString().toFloat(),context)
        binding.intensiteProgressSetter.progress = device.intensity.toString().toInt()


        binding.onOff.setOnClickListener { // Do some work here
            setOnOff(binding.onOff, binding.light, context, true)
        }

        switchOnOff(device, binding.onOff, binding.light, context)

        binding.intensiteProgressSetter.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                setProgress(progress.toString().toFloat(),context)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })

    }


    private fun bindHeater(device: Device, context:Context){
        binding.heaterName.text = device.deviceName
        setTempProgress(device.temperature.toString().toFloat(),context)
        binding.temperatureProgressSetter.progress = device.temperature.toString().toInt()

        binding.mode.setOnClickListener { // Do some work here
            setOnOff(binding.mode, binding.heaterImg, context, true)
        }

        switchOnOff(device, binding.mode, binding.heaterImg, context)

        binding.temperatureProgressSetter.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                setTempProgress(progress.toString().toFloat(),context)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }

    private fun rollerShutter(device: Device){
        binding.rollername.text = device.deviceName
        binding.seekbar?.progress = device.position.toString().toInt()

    }


    private fun setTempProgress(value:Float, context: Context){
        binding.temperatureTv.text = "$value°C"
        binding.temperatureCircularProgressBar.setProgressWithAnimation(value, 1000)
        binding.heaterImg.alpha = (value / 28)


        if (value.toInt() == 0){
            setHeaterGray(context, false)
        }else{
            setHeaterGray(context, true)
        }

    }
    private fun setHeaterGray(context:Context, on:Boolean){
        viewModel.start(id)
        if (on){
            binding.heaterImg.colorFilter = null;
        }else{
            binding.heaterImg.alpha = (1f)
            binding.heaterImg.setColorFilter(getColor(context, R.color.bleu_gris_40), Mode.SRC_IN);
        }

    }

    private fun setProgress(value:Float, context: Context){
        binding.intensiteTv.text = "$value%"
        with(binding) {
            intensiteCircularProgressBar.setProgressWithAnimation(value, 1000)
            light.alpha = (value / 100)
        }

        if (value.toInt() == 0) setGray(context, false) else setGray(context, true)

    }

    private fun setGray(context:Context, on:Boolean) = if (on){
        binding.light.colorFilter = null;
    }else{
        binding.light.alpha = (1f)

    }

    private fun switchOnOff(device: Device, checkBox: CheckBox, imageView: ImageView, context: Context){

        checkBox.isChecked = device.mode == "ON"

        if (device.mode == "ON"){

            checkBox.isChecked = true
            checkBox.text = "MODE ON"
            imageView.colorFilter = null;

        }else{
            checkBox.isChecked = false
            checkBox.text = "MODE OFF"
            imageView.alpha = (1f)
            with(imageView) { setColorFilter(getColor(context, R.color.bleu_gris_40), Mode.SRC_IN) };
        }
    }

    private fun setOnOff(checkBox: CheckBox, imageView: ImageView, context: Context, click:Boolean){

        if (click){
            checkBox.isChecked = !checkBox.isChecked
        }

        if (checkBox.isChecked){
            checkBox.isChecked = false
            checkBox.text = "MODE OFF"
            imageView.alpha = (1f)
            imageView.setColorFilter(getColor(context, R.color.bleu_gris_40), Mode.SRC_IN);
        }else{
            checkBox.isChecked = true
            checkBox.text = "MODE ON"
            imageView.colorFilter = null;

        }

    }

}

