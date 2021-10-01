package com.wils.drivingmode.ui.contactList.viewModel

import com.wils.drivingmode.roomdb.ContactDao
import com.wils.drivingmode.roomdb.ContactEntity
import kotlinx.coroutines.flow.Flow


class ContactListRepository(private val contactDetails:ContactDao) {

    val allContactList: Flow<List<ContactEntity>> = contactDetails.getContactEntity()

    suspend fun insert(listData:List<ContactEntity>) = contactDetails.insertSelected(listData)


}