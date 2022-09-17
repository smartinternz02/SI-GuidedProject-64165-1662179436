package com.asikalikhan.mindgrocery

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRecyclerViewAdaptor.GroceryItemClickInterface {
    private lateinit var groceryItemRecyclerView : RecyclerView
    private lateinit var addGroceryFloatingButton : FloatingActionButton
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRecyclerViewAdaptor: GroceryRecyclerViewAdaptor
    private lateinit var groceryViewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val   actionBar = supportActionBar
        actionBar?.let {
            it.title = "Add your Grocery List"
        }


        groceryItemRecyclerView = findViewById(R.id.recyclerViewGroceryItems)
        addGroceryFloatingButton = findViewById(R.id.floatingActionButton)

        list = ArrayList<GroceryItems>()
        groceryRecyclerViewAdaptor = GroceryRecyclerViewAdaptor(list,this)
        groceryItemRecyclerView.adapter = groceryRecyclerViewAdaptor
        groceryItemRecyclerView.layoutManager = LinearLayoutManager(this)
        val repo = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(repo)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryViewModel.allGroceryItems().observe(this) {
            groceryRecyclerViewAdaptor.list = it
            groceryRecyclerViewAdaptor.notifyDataSetChanged()
        }
        addGroceryFloatingButton.setOnClickListener {
            openDialog()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDialog() {
       val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelButton = dialog.findViewById<Button>(R.id.buttonCancel)
        val addButton = dialog.findViewById<Button>(R.id.buttonAdd)

        val itemName = dialog.findViewById<EditText>(R.id.editTextItemName)
        val itemQuantity = dialog.findViewById<EditText>(R.id.editTextItemQuantity)
        val itemRate = dialog.findViewById<EditText>(R.id.editTextItemRate)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        addButton.setOnClickListener {
            if (itemName.text.isNotEmpty()  && itemRate.text.isNotEmpty() && itemQuantity.text.isNotEmpty()){
                val grocery = GroceryItems(itemName.text.toString(),itemQuantity.text.toString().toInt(),itemRate.text.toString().toInt())
                groceryViewModel.insert(grocery)
                groceryRecyclerViewAdaptor.notifyDataSetChanged()
                groceryItemRecyclerView.scrollToPosition(groceryRecyclerViewAdaptor.itemCount -1)
                dialog.dismiss()
            }
            else {
                Toast.makeText(this,"Please Enter all data",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()

    }

    override fun onItemClick(groceryItems: GroceryItems,position : Int) {
        groceryViewModel.delete(groceryItems)
        groceryRecyclerViewAdaptor.notifyItemRemoved(position)

    }
}