package com.asikalikhan.mindgrocery.customadaptor

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asikalikhan.mindgrocery.data.GroceryItems
import com.asikalikhan.mindgrocery.R
import com.asikalikhan.mindgrocery.data.SettingSavedPrefs

class GroceryRecyclerViewAdaptor(var list : List<GroceryItems>, private val groceryItemClickInterface: GroceryItemClickInterface)
    : RecyclerView.Adapter<GroceryRecyclerViewAdaptor.GroceryItemViewHolder>() {
    private var  safeMode : Boolean ?= false

    fun setSafeMode(safe : Boolean){
        safeMode = safe
    }

    interface GroceryItemClickInterface{
        fun onItemDeleteClick(groceryItems: GroceryItems, position: Int)
        fun onItemCheck(groceryItems: GroceryItems, position: Int, check :Boolean)
        fun onItemClick(groceryItems: GroceryItems, position: Int)
    }

   inner  class GroceryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       val textViewQuantity: TextView = itemView.findViewById(R.id.textViewQuantity)
       val textViewRate: TextView = itemView.findViewById(R.id.textViewRate)
       val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
       val imageViewEdit: ImageView = itemView.findViewById(R.id.imageViewEdit)
       val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
       val checkbox : CheckBox = itemView.findViewById(R.id.checkBox)



   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_item2,parent,false)
       return GroceryItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
      val grocery = list[position]
        holder.checkbox.text =  grocery.itemName
        holder.checkbox.isChecked = grocery.isItemChecked
      holder.textViewQuantity.text = grocery.itemQuantity.toString()
      holder.textViewRate.text = "₹ ${grocery.itemPrice}"
      val itemTotal :Int = (grocery.itemPrice)*(grocery.itemQuantity)
      holder.textViewAmount.text = "₹ $itemTotal"

      if (safeMode==true){
          holder.imageViewDelete.visibility = GONE
          holder.imageViewEdit.visibility = GONE
      } else{
          holder.imageViewDelete.visibility = VISIBLE
          holder.imageViewEdit.visibility = VISIBLE
      }

      holder.imageViewDelete?.setOnClickListener {
          groceryItemClickInterface.onItemDeleteClick(grocery,position)
      }

      holder.imageViewEdit?.setOnClickListener {
          groceryItemClickInterface.onItemClick(grocery,position)
      }
      holder.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(compoundButton.isPressed){
                holder.checkbox.isChecked = isChecked
                groceryItemClickInterface.onItemCheck(grocery,position,isChecked)
            }

        }

    }

    override fun getItemCount(): Int {
    return list.size   }
}