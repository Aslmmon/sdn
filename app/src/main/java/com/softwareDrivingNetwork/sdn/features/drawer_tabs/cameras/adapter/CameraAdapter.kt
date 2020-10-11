package com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraModel
import kotlinx.android.synthetic.main.camera_item_layout.view.*

class CameraAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CameraModel>() {

        override fun areItemsTheSame(oldItem: CameraModel, newItem: CameraModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CameraModel, newItem: CameraModel) =
            oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CameraViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.camera_item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CameraViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CameraModel>) {

        differ.submitList(list)
    }

    fun addItems(list: List<CameraModel>) {

        val newList = mutableListOf<CameraModel>()
        newList.addAll(differ.currentList)
        newList.addAll(list)
        differ.submitList(newList)
    }

    class CameraViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CameraModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_camera.text = item.sensorName

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CameraModel)
    }
}