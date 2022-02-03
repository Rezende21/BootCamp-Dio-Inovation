package com.example.bootcampdio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(val constactList: ArrayList<Contact>): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(contact: Contact) {
            val textName = itemView.findViewById<TextView>(R.id.txtcontatos_name)
            val textPhoneColumns = itemView.findViewById<TextView>(R.id.txtcontatos_tel)

            textName.text = contact.name
            textPhoneColumns.text = contact.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_contatos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(constactList[position])
    }

    override fun getItemCount(): Int {
        return constactList.size
    }
}