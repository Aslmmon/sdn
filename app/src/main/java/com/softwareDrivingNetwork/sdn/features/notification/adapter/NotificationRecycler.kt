package com.softwareDrivingNetwork.sdn.features.notification.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.notification.NotificationModel
import kotlinx.android.synthetic.main.notification_item.view.*

class NotificationRecycler(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationModel>() {

        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ) = oldItem == newItem
        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notification_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NotificationViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<NotificationModel>) {

        differ.submitList(list)
    }

    fun addItems(list: List<NotificationModel>) {

        val newList = mutableListOf<NotificationModel>()
        newList.addAll(differ.currentList)
        newList.addAll(list)
        differ.submitList(newList)
    }

    class NotificationViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: NotificationModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_notification.text = item.notification_desc

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: NotificationModel)
    }
}