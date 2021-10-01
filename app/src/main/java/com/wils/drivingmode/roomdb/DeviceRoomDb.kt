package com.wils.drivingmode.roomdb

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wils.drivingmode.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ContactEntity::class],version = 1)
abstract class DeviceRoomDb:RoomDatabase() {

    abstract fun contactDao():ContactDao

    companion object{
        @Volatile
        private var INSTANCE: DeviceRoomDb? = null

        fun getDataBase(context: Context,
                        scope: CoroutineScope
        ):DeviceRoomDb{

            return  INSTANCE?: synchronized(this) {

                val instance = Room.databaseBuilder(context.applicationContext,DeviceRoomDb::class.java,"build").
                    fallbackToDestructiveMigration().addCallback(DatabaseCallback(scope)).build()


                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.contactDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(wordDao: ContactDao) {

        }

    }


}