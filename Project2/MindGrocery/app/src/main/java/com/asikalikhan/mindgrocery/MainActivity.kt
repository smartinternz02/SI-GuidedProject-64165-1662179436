package com.asikalikhan.mindgrocery

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

    private lateinit var textViewSumCheckedTotalAmount:TextView
    private lateinit var textViewSumTotalAmount:TextView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val   actionBar = supportActionBar
        actionBar?.let {
            it.title = "Add your Grocery List"
        }
        textViewSumTotalAmount = findViewById(R.id.textViewCartCost)
        textViewSumCheckedTotalAmount = findViewById(R.id.textViewCheckedCost)

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
        groceryViewModel.allItemsTotalCost().observe(this){

            textViewSumTotalAmount.text = "₹ $it"
        }
        groceryViewModel.allCheckedItemsTotalCost(true).observe(this){

            textViewSumCheckedTotalAmount.text = "₹ $it "
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
                val grocery = GroceryItems(itemName.text.toString(),itemQuantity.text.toString().toInt(),itemRate.text.toString().toInt(),false)
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

    override fun onItemDeleteClick(groceryItems: GroceryItems, position : Int) {
        groceryViewModel.delete(groceryItems)
        groceryRecyclerViewAdaptor.notifyItemRemoved(position)

    }


    override fun onItemCheck(groceryItems: GroceryItems, position: Int, check: Boolean) {
        val groceryUpdated = groceryItems
        groceryUpdated.isItemChecked = check
        groceryViewModel.update(groceryUpdated)
        groceryRecyclerViewAdaptor.notifyItemChanged(position)
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClick(groceryItems: GroceryItems, position: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val dialogBoxName = dialog.findViewById<TextView>(R.id.textViewDialogTitle)
        dialogBoxName.text = "Edit Item from Cart"

        val cancelButton = dialog.findViewById<Button>(R.id.buttonCancel)
        val editButton = dialog.findViewById<Button>(R.id.buttonAdd)
        editButton.text = "EDIT"
        val itemName = dialog.findViewById<EditText>(R.id.editTextItemName)
        val itemQuantity = dialog.findViewById<EditText>(R.id.editTextItemQuantity)
        val itemRate = dialog.findViewById<EditText>(R.id.editTextItemRate)

       // val groceryItem = groceryItems
        itemName.text = groceryItems.itemName.toEditable()
        itemQuantity.text = groceryItems.itemQuantity.toString().toEditable()
        itemRate.text = groceryItems.itemPrice.toString().toEditable()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        editButton.setOnClickListener {
            if (itemName.text.isNotEmpty()  && itemRate.text.isNotEmpty() && itemQuantity.text.isNotEmpty()){

                val grocery = GroceryItems(itemName.text.toString(),itemQuantity.text.toString().toInt(),itemRate.text.toString().toInt(),false)
                grocery.id =  groceryItems.id
                groceryViewModel.update(grocery)
                groceryRecyclerViewAdaptor.notifyItemChanged(position)
                dialog.dismiss()
            }
            else {
                Toast.makeText(this,"Please Enter all data",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    private fun String.toEditable() :Editable = Editable.Factory.getInstance().newEditable(this)


}

