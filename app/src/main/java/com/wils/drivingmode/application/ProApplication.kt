package com.wils.drivingmode.application

import android.app.Application
import com.wils.drivingmode.roomdb.DeviceRoomDb
import com.wils.drivingmode.ui.contactList.viewModel.ContactListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProApplication:Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val roomDb by lazy {  DeviceRoomDb.getDataBase(this, applicationScope) }

    val contactListRepository by lazy { ContactListRepository(roomDb.contactDao()) }


}