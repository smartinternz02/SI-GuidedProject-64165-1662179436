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
import com.asikalikhan.mindgrocery.customadaptor.GroceryRecyclerViewAdaptor
import com.asikalikhan.mindgrocery.data.GroceryDatabase
import com.asikalikhan.mindgrocery.data.GroceryItems
import com.asikalikhan.mindgrocery.data.SettingSavedPrefs
import com.asikalikhan.mindgrocery.model.GroceryRepository
import com.asikalikhan.mindgrocery.model.GroceryViewModel
import com.asikalikhan.mindgrocery.model.GroceryViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GroceryItemActivity : AppCompatActivity(), GroceryRecyclerViewAdaptor.GroceryItemClickInterface {
    private lateinit var groceryItemRecyclerView : RecyclerView
    private lateinit var addGroceryFloatingButton : FloatingActionButton
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRecyclerViewAdaptor: GroceryRecyclerViewAdaptor
    private lateinit var groceryViewModel: GroceryViewModel

    private lateinit var textViewSumCheckedTotalAmount:TextView
    private lateinit var textViewSumTotalAmount:TextView
    private  var intentTable : Int = -1
    private lateinit var code :ListModel


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val   actionBar = supportActionBar
        val sharedPref = SettingSavedPrefs(this)


        val intentValue = intent.getStringExtra("LIST_NAME")
        actionBar?.let {
            if (sharedPref.getBudget()){ it.title = "Combined Grocery List" }
          else  { it.title = "$intentValue Grocery List" }
        }

        intentTable = intent.getIntExtra("LIST_NAME_TABLE",-1)

        //viewModel uses
        code = ListModel()
        code.setRelatedString(intentTable.toString())


        textViewSumTotalAmount = findViewById(R.id.textViewCartCost)
        textViewSumCheckedTotalAmount = findViewById(R.id.textViewCheckedCost)

        groceryItemRecyclerView = findViewById(R.id.recyclerViewGroceryItems)
        addGroceryFloatingButton = findViewById(R.id.floatingActionButton)

        list = ArrayList()
        groceryRecyclerViewAdaptor = GroceryRecyclerViewAdaptor(list,this)
        groceryRecyclerViewAdaptor.setSafeMode(sharedPref.getEdit())
        groceryItemRecyclerView.adapter = groceryRecyclerViewAdaptor
        groceryItemRecyclerView.layoutManager = LinearLayoutManager(this)


        val repo = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(repo)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]


        if (sharedPref.getBudget()){

            //gets all items from different lists
            groceryViewModel.allGroceryItems().observe(this) {
                groceryRecyclerViewAdaptor.list = it
                groceryRecyclerViewAdaptor.notifyDataSetChanged()
            }

            //budgeting mode - gets the total and checked cost considering all list
            groceryViewModel.allItemsTotalCost().observe(this){

                textViewSumTotalAmount.text = "₹ $it"
            }
            groceryViewModel.allCheckedItemsTotalCost(true).observe(this){

                textViewSumCheckedTotalAmount.text = "₹ $it "
            }
            //budgeting mode
        }
        else {
            //GETS ITEMS OF PARTICULAR LIST

            groceryViewModel.allGroceryItemsRelatedTo(code.getRelatedString()).observe(this){
                groceryRecyclerViewAdaptor.list = it
                groceryRecyclerViewAdaptor.notifyDataSetChanged()
            }

            //normal mode
            groceryViewModel.allItemsTotalCost(code.getRelatedString()).observe(this){

                textViewSumTotalAmount.text = "₹ $it"
            }
            groceryViewModel.allCheckedItemsTotalCost(true,code.getRelatedString()).observe(this){

                textViewSumCheckedTotalAmount.text = "₹ $it "
            }
            //normal mode
        }


        addGroceryFloatingButton.setOnClickListener {

            openAddEditDialog(false, null, null)
        }

    }

//    override fun onBackPressed() {
//        finish()
//        super.onBackPressed()
//    }

    override fun onItemDeleteClick(groceryItems: GroceryItems, position : Int) {
        groceryViewModel.delete(groceryItems)
        groceryRecyclerViewAdaptor.notifyItemRemoved(position)

    }


    override fun onItemCheck(groceryItems: GroceryItems, position: Int, check: Boolean) {
        groceryItems.isItemChecked = check
        groceryViewModel.update(groceryItems)
        groceryRecyclerViewAdaptor.notifyItemChanged(position)
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClick(groceryItems: GroceryItems, position: Int) {

        openAddEditDialog(true, groceryItems, position)
    }

    @SuppressLint("SetTextI18n")
    private fun openAddEditDialog(
        edit: Boolean,
        groceryItems: GroceryItems?,
        position: Int?,
    ) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_addedit_dialog)

        val cancelButton = dialog.findViewById<Button>(R.id.buttonCancel)
        val editButton = dialog.findViewById<Button>(R.id.buttonAdd)

        val itemName = dialog.findViewById<EditText>(R.id.editTextItemName)
        val itemQuantity = dialog.findViewById<EditText>(R.id.editTextItemQuantity)
        val itemRate = dialog.findViewById<EditText>(R.id.editTextItemRate)

        if (edit) {
            //changing button and title texts of dialog box
            val dialogBoxName = dialog.findViewById<TextView>(R.id.textViewDialogTitle)
            dialogBoxName.text = "Edit Item from Cart"
            editButton.text = "EDIT"
            // converting strings to editable
            groceryItems?.let {
                itemName.text = it.itemName.toEditable()
                itemQuantity.text = it.itemQuantity.toString().toEditable()
                itemRate.text = it.itemPrice.toString().toEditable()
            }

        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        editButton.setOnClickListener {
            if (itemName.text.isNotEmpty() && itemRate.text.isNotEmpty() && itemQuantity.text.isNotEmpty()) {

                val grocery = GroceryItems(itemName.text.toString(),
                    itemQuantity.text.toString().toInt(),
                    itemRate.text.toString().toInt(),
                    false,code.getRelatedString())
                if (edit) {
                    grocery.id = groceryItems?.id
                    groceryViewModel.update(grocery)
                    if (position != null) {
                        groceryRecyclerViewAdaptor.notifyItemChanged(position)
                    }
                } else {
                    groceryViewModel.insert(grocery)
                    groceryRecyclerViewAdaptor.notifyItemInserted(groceryRecyclerViewAdaptor.itemCount + 1)
                    //  groceryRecyclerViewAdaptor.notifyDataSetChanged()
                    groceryItemRecyclerView.scrollToPosition(groceryRecyclerViewAdaptor.itemCount - 1)

                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Enter all data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun String.toEditable() :Editable = Editable.Factory.getInstance().newEditable(this)


}

