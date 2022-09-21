package com.asikalikhan.mindgrocery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListNameItems::class], version = 1)
abstract class ListNameDatabase :RoomDatabase() {

    abstract fun getListNameDao() : ListNameDao

    companion object {
        @Volatile
        private var instance: ListNameDatabase? = null
        private val LOCKED = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCKED) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ListNameDatabase::class.java, "ListNameDatabase.db").build()
    }

}