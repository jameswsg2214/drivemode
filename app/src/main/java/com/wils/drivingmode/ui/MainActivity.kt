package com.wils.drivingmode.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wils.drivingmode.R
import com.wils.drivingmode.roomdb.ContactEntity
import com.wils.drivingmode.roomdb.DeviceRoomDb
import com.wils.drivingmode.ui.contactList.ui.ContactListViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLitinors()


    }

    private fun initLitinors() {

        nextPage?.setOnClickListener {

            startActivity(Intent(this,ContactListViewActivity::class.java))

        }
/*
        val applicationScope = CoroutineScope(SupervisorJob())
        val wordDao = DeviceRoomDb.getDataBase(this,applicationScope).contactDao()

        var word = ContactEntity( contactNo = "902909209209", contactName = "wils")
        applicationScope.launch(Dispatchers.IO) {
            wordDao.insert(word)

        }*/
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

}