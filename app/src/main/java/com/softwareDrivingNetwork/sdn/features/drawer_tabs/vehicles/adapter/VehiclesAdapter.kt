package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sdn.aivimapandroid.map.AiviUtils
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclerModel
import kotlinx.android.synthetic.main.vehicle_item.view.*

class VehiclesAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VehiclerModel>() {

        override fun areItemsTheSame(oldItem: VehiclerModel, newItem: VehiclerModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: VehiclerModel, newItem: VehiclerModel) =
            oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VehicleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.vehicle_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VehicleViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<VehiclerModel>) {
        differ.submitList(list)
    }

    class VehicleViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: VehiclerModel) = with(itemView) {
            if(item.locationLat != null)
                itemView.tv_location_vehicle.text = item.locationLng?.let {
                item.locationLat?.let { it1 ->
                    AiviUtils.getCompleteAddressString(
                        itemView.context, it1,
                        it
                    )
                }
            }
            else itemView.tv_location_vehicle.visibility = View.GONE

            itemView.tv_vehicle_name_vehicle.text = item.vehicleName
            if (item.driverName == null) itemView.tv_driver_name_in_vehicle.text =
                resources.getString(R.string.drivername_title) + "No Driver Name"
            else itemView.tv_driver_name_in_vehicle.text =
                resources.getString(R.string.drivername_title) + item.driverName


            if (item.plateNo == null) itemView.tv_plate_number_in_vehicle.text =
                resources.getString(R.string.plate_number) + "No Plate Number"
            else itemView.tv_plate_number_in_vehicle.text =
                resources.getString(R.string.plate_number) + item.plateNo


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: VehiclerModel, isChecked: Boolean)
    }


}
