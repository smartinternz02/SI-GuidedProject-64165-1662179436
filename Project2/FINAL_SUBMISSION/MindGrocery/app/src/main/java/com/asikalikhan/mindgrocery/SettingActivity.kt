package com.asikalikhan.mindgrocery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.asikalikhan.mindgrocery.data.SettingSavedPrefs
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private lateinit var switchReadOnly: SwitchMaterial
    private lateinit var switchBudget: SwitchMaterial


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val actionBar = supportActionBar
        actionBar?.let {
            it.title = "Setting"
        }
        var sharedPref = SettingSavedPrefs(this)

        switchBudget = findViewById(R.id.switchBudget)
        switchReadOnly = findViewById(R.id.switchReadOnly)

        switchReadOnly.isChecked = sharedPref.getEdit()
        switchBudget.isChecked = sharedPref.getBudget()

        switchBudget.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                sharedPref.setBudget(isChecked)
                if (isChecked){
                    Toast.makeText(this,"Combined grocery items will be shown in each list",Toast.LENGTH_LONG).show() }
                restartApp()

            }
        }
        switchReadOnly.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed){
                sharedPref.setEdit(isChecked)
                if (isChecked){
                    Toast.makeText(this,"DELETE & EDIT OPERATIONS ARE DISABLED NOW",Toast.LENGTH_LONG).show() }
                restartApp()
            }

        }

    }

    private fun restartApp() {
      val  intent = Intent(applicationContext,ListNameActivity::class.java)
        startActivity(intent)
      val  intentJump = Intent(applicationContext,SettingActivity::class.java)
        startActivity(intentJump)
    }

}