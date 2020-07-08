package com.work.dictionarry.presentation.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.work.dictionarry.R
import com.work.dictionarry.model.networking.models.Meaning
import kotlinx.android.synthetic.main.item_definition.view.*

class DefinitionsListAdapter : RecyclerView.Adapter<DefinitionsListAdapter.ViewHolder>() {

    private val definitions = mutableListOf<String>()

    fun setDefinitions(list: List<String>) {
        definitions.clear()
        definitions.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_definition, parent, false))
    }

    override fun getItemCount() = definitions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            definitionNumber.text = (position+1).toString()
            definitionWord.text = definitions[position]
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}