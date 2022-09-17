package com.asikalikhan.mindgrocery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRecyclerViewAdaptor(var list : List<GroceryItems>, val groceryItemClickInterface: GroceryItemClickInterface )
    : RecyclerView.Adapter<GroceryRecyclerViewAdaptor.GroceryItemViewHolder>() {

    interface GroceryItemClickInterface{
        fun onItemClick(groceryItems: GroceryItems,position: Int)
    }

   inner  class GroceryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)
       val textViewQuantity: TextView = itemView.findViewById(R.id.textViewQuantity)
       val textViewRate: TextView = itemView.findViewById(R.id.textViewRate)
       val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
       val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_item_recyclerview_element,parent,false)
       return GroceryItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
      val grocery = list.get(position)
      holder.textViewItemName.text = grocery.itemName
      holder.textViewQuantity.text = grocery.itemQuantity.toString()
      holder.textViewRate.text = "Rs. ${grocery.itemPrice}"
      val itemTotal :Int = (grocery.itemPrice)*(grocery.itemQuantity)
      holder.textViewAmount.text = "Rs. $itemTotal"
      holder.imageViewDelete.setOnClickListener {
          groceryItemClickInterface.onItemClick(grocery,position)
      }
    }

    override fun getItemCount(): Int {
    return list.size   }
}