package com.asikalikhan.mindgrocery

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asikalikhan.mindgrocery.customadaptor.ListNameRecyclerViewAdaptor
import com.asikalikhan.mindgrocery.data.ListNameDatabase
import com.asikalikhan.mindgrocery.data.ListNameItems
import com.asikalikhan.mindgrocery.data.SettingSavedPrefs
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNameActivity : AppCompatActivity(),ListNameRecyclerViewAdaptor.ListNameItemClickInterface {
    private lateinit var listNameItemsRecyclerView : RecyclerView
    private lateinit var addListNameFloatingButton : FloatingActionButton
    private lateinit var list: List<ListNameItems>
    private lateinit var listNameRecyclerViewAdaptor: ListNameRecyclerViewAdaptor
    private lateinit var listNameViewModel: ListNameViewModel



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_name)
        val actionBar = supportActionBar
        actionBar?.let {
               it.title = "Create List"
        }
        val sharedPrefs = SettingSavedPrefs(this)

        addListNameFloatingButton = findViewById(R.id.floatingActionButton2)
        listNameItemsRecyclerView = findViewById(R.id.listRecyclerView)

        list = ArrayList()
        listNameRecyclerViewAdaptor = ListNameRecyclerViewAdaptor(list,this)
        listNameItemsRecyclerView.adapter = listNameRecyclerViewAdaptor
        listNameRecyclerViewAdaptor.setSafeMode(sharedPrefs.getEdit())
        listNameItemsRecyclerView.layoutManager = LinearLayoutManager(this)

        val repo = ListNameRepository(ListNameDatabase(this))
        val factory = ListNameViewModelFactory(repo)
        listNameViewModel = ViewModelProvider(this,factory)[ListNameViewModel::class.java]

        listNameViewModel.allListNameItems().observe(this){
            listNameRecyclerViewAdaptor.list = it
            listNameRecyclerViewAdaptor.notifyDataSetChanged()
        }

        addListNameFloatingButton.setOnClickListener{
            openAddEditDialog(false, null, null)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_three_dots,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.about ->{
                openAboutDialog()
            }
            R.id.settings -> {
                val handler =  Handler()
                handler.postDelayed({  Toast.makeText(this,"Designed & Developed By \uD83D\uDC96 Asik Ali Khan",Toast.LENGTH_SHORT).show() }, 500)
                startActivity(Intent(this,SettingActivity::class.java))

            }
            R.id.exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAboutDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.about_dialog)

        val okButton = dialog.findViewById<Button>(R.id.buttonOk)

        okButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    @SuppressLint("SetTextI18n")
    private fun openAddEditDialog(
        edit: Boolean,
        listNameItems: ListNameItems?,
        position: Int?,
    ) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.list_name_addedit_dialog)

        val cancelButton = dialog.findViewById<Button>(R.id.buttonCancel)
        val editButton = dialog.findViewById<Button>(R.id.buttonAdd)

        val itemName = dialog.findViewById<EditText>(R.id.editTextItemName)


        if (edit) {
            //changing button and title texts of dialog box
            val dialogBoxName = dialog.findViewById<TextView>(R.id.textViewDialogTitle)
            dialogBoxName.text = "Edit List Name"
            editButton.text = "EDIT"
            // converting strings to editable
            listNameItems?.let {
                itemName.text = it.listName.toEditable()

            }

        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        editButton.setOnClickListener {
            if (itemName.text.isNotEmpty()) {

                val listName = ListNameItems(itemName.text.toString(), false)
                if (edit) {
                    listName.id = listNameItems?.id
                    listNameViewModel.update(listName)
                    if (position != null) {
                        listNameRecyclerViewAdaptor.notifyItemChanged(position)
                    }
                } else {
                    listNameViewModel.insert(listName)
                    listNameRecyclerViewAdaptor.notifyItemInserted(listNameRecyclerViewAdaptor.itemCount + 1)
                    listNameItemsRecyclerView.scrollToPosition(listNameRecyclerViewAdaptor.itemCount - 1)

                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter a List Name", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    private fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onItemDeleteClick(listItem: ListNameItems, position: Int) {
        listNameViewModel.delete(listItem)
        listNameRecyclerViewAdaptor.notifyItemRemoved(position)
    }

    override fun onItemEditClick(listItem: ListNameItems, position: Int) {
        openAddEditDialog(true, listItem, position)
    }

    override fun onItemCheck(listItem: ListNameItems, position: Int, check: Boolean) {
        listItem.isListNameChecked = check
        listNameViewModel.update(listItem)
        listNameRecyclerViewAdaptor.notifyItemChanged(position)
    }

    override fun onItemClick(listItem: ListNameItems, position: Int) {
       //todo go to grocery list activity
        val intent = Intent(this,GroceryItemActivity::class.java)
        intent.putExtra("LIST_NAME_TABLE",listItem.id)
        intent.putExtra("LIST_NAME",listItem.listName)
        startActivity(intent)
    }


}

