package com.softwareDrivingNetwork.sdn.features.drawer_tabs.drivers.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.drivers.driversModel
import kotlinx.android.synthetic.main.driver_item_layout.view.*

class DriverAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<driversModel>() {

        override fun areItemsTheSame(oldItem: driversModel, newItem: driversModel) = oldItem == newItem

        override fun areContentsTheSame(oldItem: driversModel, newItem: driversModel) = oldItem==newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DriverViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.driver_item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DriverViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<driversModel>) {

        differ.submitList(list)
    }

    fun addItems(list: List<driversModel>) {

        val newList = mutableListOf<driversModel>()
        newList.addAll(differ.currentList)
        newList.addAll(list)
        differ.submitList(newList)
    }

    class DriverViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: driversModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_driver_name.text = item.driver_name

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: driversModel)
    }
}