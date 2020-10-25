package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.general.groups.GroupsData


class GroupsSpinnerAdapter(context: Context, governs: List<GroupsData>) :
    ArrayAdapter<GroupsData>(context, 0, governs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return initView(position, convertView, parent)
    }


    fun initView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_spinner_layout, parent, false)
        }
        val text = view?.findViewById<TextView>(R.id.text_name)
        val sizeItem = getItem(position)
        sizeItem?.let {
            text?.text = it.group_name
        }

        return view
    }
}