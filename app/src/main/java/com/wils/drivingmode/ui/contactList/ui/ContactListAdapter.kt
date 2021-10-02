package com.wils.drivingmode.ui.contactList.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wils.drivingmode.R
import com.wils.drivingmode.ui.contactList.MobileContactList
import kotlinx.android.synthetic.main.adapter_list_conatact.view.*

class ContactListAdapter(val listData: ArrayList<MobileContactList>):RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {


    class ContactViewHolder(view:View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        return ContactViewHolder(view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_conatact,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.itemView.contactName.text = listData[position].name
        holder.itemView.contactNo.text = listData[position].number

    }

    override fun getItemCount(): Int {
        return listData.size
    }
}