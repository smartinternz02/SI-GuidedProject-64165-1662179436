package com.asikalikhan.mindgrocery.data

import android.content.Context
import android.content.SharedPreferences

class SettingSavedPrefs(context: Context) {
    private val sharedPrefs : SharedPreferences = context.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)

    companion object{
        const val BUDGET_MODE_KEY = "budget_mode_com_AsikAliKhan_mindgrocery"
        const val EDIT_MODE_KEY = "edit_mode_com_AsikAliKhan_mindgrocery"
    }

    fun setBudget( mode : Boolean){
      val editor : SharedPreferences.Editor = sharedPrefs.edit()
      editor.putBoolean(BUDGET_MODE_KEY,mode)
      editor.apply()
    }
    fun getBudget(): Boolean = sharedPrefs.getBoolean(BUDGET_MODE_KEY,false)

    fun setEdit(mode : Boolean){
        val editor : SharedPreferences.Editor = sharedPrefs.edit()
        editor.putBoolean(EDIT_MODE_KEY,mode)
        editor.apply()
    }
    fun getEdit() : Boolean = sharedPrefs.getBoolean(EDIT_MODE_KEY,false)


}