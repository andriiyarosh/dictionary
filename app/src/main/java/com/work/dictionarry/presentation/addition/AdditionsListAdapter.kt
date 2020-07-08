package com.work.dictionarry.presentation.addition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.work.dictionarry.R
import kotlinx.android.synthetic.main.item_addition.view.*

class AdditionsListAdapter(private val onAdditionClicked: (word: String) -> Unit) : RecyclerView.Adapter<AdditionsListAdapter.ViewHolder>() {

    var additions: List<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_addition, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return additions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            addition.text = additions[position]
            setOnClickListener { onAdditionClicked(additions[position]) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}