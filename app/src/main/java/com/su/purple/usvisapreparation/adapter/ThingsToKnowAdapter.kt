package com.su.purple.usvisapreparation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.su.purple.usvisapreparation.R
import com.su.purple.usvisapreparation.listener.OnItemClick
import com.su.purple.usvisapreparation.model.ThingsToKnow

class ThingsToKnowAdapter(
    private val list: List<ThingsToKnow>,
    private val listener: OnItemClick): RecyclerView.Adapter<ThingsToKnowAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.list_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_things_to_know, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title

        holder.itemView.setOnClickListener { listener.onClick(item.id) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}