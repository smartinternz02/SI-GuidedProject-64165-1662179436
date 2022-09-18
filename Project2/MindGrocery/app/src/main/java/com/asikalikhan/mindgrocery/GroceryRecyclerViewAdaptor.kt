package com.asikalikhan.mindgrocery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class GroceryRecyclerViewAdaptor(var list : List<GroceryItems>, private val groceryItemClickInterface: GroceryItemClickInterface )
    : RecyclerView.Adapter<GroceryRecyclerViewAdaptor.GroceryItemViewHolder>() {

    interface GroceryItemClickInterface{
        fun onItemDeleteClick(groceryItems: GroceryItems, position: Int)
        fun onItemCheck(groceryItems: GroceryItems,position: Int,check :Boolean)
        fun onItemClick(groceryItems: GroceryItems, position: Int)
    }

   inner  class GroceryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.constraintLayoutItem)
       val textViewQuantity: TextView = itemView.findViewById(R.id.textViewQuantity)
       val textViewRate: TextView = itemView.findViewById(R.id.textViewRate)
       val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
       val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
       val checkbox : CheckBox = itemView.findViewById(R.id.checkBox)


   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_item,parent,false)
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
      holder.imageViewDelete.setOnClickListener {
          groceryItemClickInterface.onItemDeleteClick(grocery,position)
      }
      holder.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
          if(compoundButton.isPressed){
              holder.checkbox.isChecked = isChecked
              groceryItemClickInterface.onItemCheck(grocery,position,isChecked)
          }

      }
      holder.constraintLayout.setOnClickListener {
          groceryItemClickInterface.onItemClick(grocery,position)
      }

    }

    override fun getItemCount(): Int {
    return list.size   }
}