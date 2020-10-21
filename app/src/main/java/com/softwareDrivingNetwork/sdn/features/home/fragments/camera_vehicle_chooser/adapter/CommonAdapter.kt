package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.common.CommonModel
import kotlinx.android.synthetic.main.camera_item_chooser_layout.view.*

class CommonAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastCheckedPosition = -1


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommonModel>() {

        override fun areItemsTheSame(oldItem: CommonModel, newItem: CommonModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommonModel, newItem: CommonModel) =
            oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VehiclesViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.camera_item_chooser_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VehiclesViewModel -> {
                holder.bind(differ.currentList[position],lastCheckedPosition)
                holder.itemView.checkbox_camera.setOnClickListener {
                    val copyOfLastCheckedPosition = lastCheckedPosition
                    lastCheckedPosition = position
                    notifyItemChanged(copyOfLastCheckedPosition)
                    notifyItemChanged(lastCheckedPosition)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CommonModel>) {

        differ.submitList(list)
    }

    fun addItems(list: List<CommonModel>) {

        val newList = mutableListOf<CommonModel>()
        newList.addAll(differ.currentList)
        newList.addAll(list)
        differ.submitList(newList)
    }

    class VehiclesViewModel
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: CommonModel,
            lastCheckedPosition: Int
        ) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.checkbox_camera.isChecked = position == lastCheckedPosition;

            itemView.checkbox_camera.setOnCheckedChangeListener { buttonView, isChecked ->
                interaction?.onItemSelected(position = adapterPosition, item = item)
            }
            itemView.checkbox_camera.text = item.name


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CommonModel)
    }
}