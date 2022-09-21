package com.asikalikhan.mindgrocery.customadaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asikalikhan.mindgrocery.R
import com.asikalikhan.mindgrocery.data.ListNameItems

class ListNameRecyclerViewAdaptor(var list : List<ListNameItems>, private val listNameItemClickInterface: ListNameItemClickInterface)
    : RecyclerView.Adapter<ListNameRecyclerViewAdaptor.ListNameItemViewHolder>() {

    private var  safeMode : Boolean ?= false

    fun setSafeMode(safe : Boolean){
        safeMode = safe
    }
    interface ListNameItemClickInterface{
        fun onItemDeleteClick(listItem: ListNameItems, position: Int)
        fun onItemEditClick(listItem: ListNameItems, position: Int)
        fun onItemCheck(listItem: ListNameItems, position: Int, check :Boolean)
        fun onItemClick(listItem: ListNameItems, position: Int)
    }



   inner  class ListNameItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.constraintLayoutList)
       val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDeleteName)
       val checkbox : CheckBox = itemView.findViewById(R.id.checkBoxListName)
       val imageViewEdit: ImageView = itemView.findViewById(R.id.imageViewEditName)

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameItemViewHolder {

       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_name_item,parent,false)
       return ListNameItemViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListNameRecyclerViewAdaptor.ListNameItemViewHolder, position: Int) {

      val listName = list[position]
        holder.checkbox.text =  listName.listName
        holder.checkbox.isChecked = listName.isListNameChecked
        if (safeMode==true){
            holder.imageViewDelete.visibility = View.GONE
            holder.imageViewEdit.visibility = View.GONE
        } else{
            holder.imageViewDelete.visibility = View.VISIBLE
            holder.imageViewEdit.visibility = View.VISIBLE
        }

      holder.imageViewDelete?.setOnClickListener {
          listNameItemClickInterface.onItemDeleteClick(listName,position)
      }

      holder.imageViewEdit?.setOnClickListener {

            listNameItemClickInterface.onItemEditClick(listName,position)
        }

      holder.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
          if(compoundButton.isPressed){
              holder.checkbox.isChecked = isChecked
              listNameItemClickInterface.onItemCheck(listName,position,isChecked)
          }
      }

      holder.constraintLayout.setOnClickListener {

          listNameItemClickInterface.onItemClick(listName,position)
      }



    }
    override fun getItemCount(): Int {
    return list.size   }

}