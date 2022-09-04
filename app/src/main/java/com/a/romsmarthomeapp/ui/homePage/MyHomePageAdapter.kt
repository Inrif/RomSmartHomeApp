package com.a.romsmarthomeapp.ui.homePage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.a.romsmarthomeapp.R
import com.a.romsmarthomeapp.databinding.HeaterItemBinding
import com.a.romsmarthomeapp.databinding.LightItemBinding
import com.a.romsmarthomeapp.databinding.RollershutterItemBinding
import com.a.romsmarthomeapp.model.*
import com.a.romsmarthomeapp.ui.myAccount.MyAccountFragment
import com.google.android.material.switchmaterial.SwitchMaterial


/**
 * Created by HOUNSA ROMUALD on 07/08/22.
 */
open class MyHomePageAdapter(
    private val listener: HomePageFragment?,
    private val lstn: MyAccountFragment?,
    context: Context,
    list: ArrayList<Device>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {

    companion object {
        const val VIEW_TYPE_LIGHT = 1
        const val VIEW_TYPE_HEATER = 2
        const val VIEW_TYPE_ROLLER_SHUTTER = 3
    }

    interface DeviceItemListener {
        fun onClickedDevice(deviceId: Int)
    }


    private val context: Context = context
    var list: ArrayList<Device> = list

    private inner class LightViewHolder(itemView: View, private val listener: HomePageFragment?, private val lstn: MyAccountFragment?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {



        private lateinit var device: Device

        init {
            itemView.setOnClickListener(this)
        }

        var deviceName: TextView = itemView.findViewById(R.id.product_name)
        var intensite: ProgressBar = itemView.findViewById(R.id.intensite)
        var onOff: SwitchMaterial = itemView.findViewById(R.id.onOff)
        var lightImageView: ImageView = itemView.findViewById(R.id.lightImageView)


        var contraintLayout: ConstraintLayout = itemView.findViewById(R.id.light)
        var intensiteTv: TextView = itemView.findViewById(R.id.intensiteTv)


        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            deviceName.text = recyclerViewModel.deviceName
            device = recyclerViewModel
            intensiteTv.text = " Intensité :  " +  recyclerViewModel.intensity.toString() + "%"
            intensite.progress = Integer.parseInt(recyclerViewModel.intensity)

            if (recyclerViewModel.mode == "ON" && recyclerViewModel.intensity.toString().toInt() > 0) {
                onOff.isChecked = true;
            } else {
                onOff.isChecked = false;
//                intensite.setProgress(0)
                lightImageView.setColorFilter(ContextCompat.getColor(context, R.color.bleu_gris_40), android.graphics.PorterDuff.Mode.SRC_IN);
            }

        }

        override fun onClick(p0: View?) {
            listener?.onClickedDevice(device.id)
            lstn?.onClickedDevice(device.id)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Device>) {
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    private inner class HeaterViewHolder(itemView: View, private val listener: HomePageFragment?, private val lstn: MyAccountFragment?,) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var device: Device

        init {
            itemView.setOnClickListener(this)
        }

        var deviceName: TextView = itemView.findViewById(R.id.product_name)
        var temperature: TextView = itemView.findViewById(R.id.temperature)
        var onOff: SwitchMaterial = itemView.findViewById(R.id.onOff)
        var heaterImageView: ImageView = itemView.findViewById(R.id.hearterImg)

        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            deviceName.text = recyclerViewModel.deviceName
            device = recyclerViewModel
            temperature.text = "Température : " + recyclerViewModel.temperature + "°C"
            if (recyclerViewModel.mode == "ON") {
                onOff.isChecked = true;
            } else {
                onOff.isChecked = false;
                heaterImageView.setColorFilter(ContextCompat.getColor(context, R.color.bleu_gris_40), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }

        override fun onClick(p0: View?) {
            listener?.onClickedDevice(device.id)
            lstn?.onClickedDevice(device.id)
        }

    }

    private inner class RollerShutterViewHolder(
        itemView: View,
        private val listener: HomePageFragment?,
        private val lstn: MyAccountFragment?
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var device: Device

        init {
            itemView.setOnClickListener(this)
        }

        var deviceName: TextView = itemView.findViewById(R.id.product_name)
        var positionTv: TextView = itemView.findViewById(R.id.position)

        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            deviceName.text = recyclerViewModel.deviceName
            positionTv.text = "Position : " + recyclerViewModel.position
            device = recyclerViewModel
        }

        override fun onClick(p0: View?) {
            listener?.onClickedDevice(device.id)
            lstn?.onClickedDevice(device.id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_LIGHT) {
            return LightViewHolder(
                LayoutInflater.from(context).inflate(R.layout.light_item, parent, false),
                listener,
                lstn
            )
        } else if (viewType == VIEW_TYPE_HEATER) {
            return HeaterViewHolder(
                LayoutInflater.from(context).inflate(R.layout.heater_item, parent, false),
                listener,
                lstn

            )
        }
        return RollerShutterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rollershutter_item, parent, false),
            listener,
            lstn
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var viewType = getTypeInt(list[position].productType)


        if (viewType == VIEW_TYPE_LIGHT) {
            (holder as LightViewHolder).bind(position)
        } else if (viewType == Companion.VIEW_TYPE_HEATER) {
            (holder as HeaterViewHolder).bind(position)
        } else {
            (holder as RollerShutterViewHolder).bind(position)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return getTypeInt(list[position].productType)
    }

    fun getTypeInt(productType: String?): Int {

        if (productType == "Light") {
            return VIEW_TYPE_LIGHT
        } else if (productType == "Heater") {
            return VIEW_TYPE_HEATER
        } else {
            return VIEW_TYPE_ROLLER_SHUTTER
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}

