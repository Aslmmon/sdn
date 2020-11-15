package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.sdn.aivimapandroid.map.AiviUtils
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclerModel
import kotlinx.android.synthetic.main.vehicle_item_layout.view.*

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
                R.layout.vehicle_item_layout,
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
            itemView.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                interaction?.onItemSelected(adapterPosition, item, isChecked)

            }
            itemView.tv_location.text = item.locationLng?.let {
                item.locationLat?.let { it1 ->
                    AiviUtils.getCompleteAddressString(
                        itemView.context, it1,
                        it
                    )
                }
            }
            itemView.tv_vehicle_name.text = item.vehicleName
//            itemView.tv_plate.text = resources.getString(R.string.plate_number) + item.plateNo
//            itemView.tv_license_start.text =
//                resources.getString(R.string.liscense_start) + item.licenseStart
//            itemView.tv_license_end.text =
//                resources.getString(R.string.liscense_end) + item.licenseEnd

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: VehiclerModel, isChecked: Boolean)
    }


}
