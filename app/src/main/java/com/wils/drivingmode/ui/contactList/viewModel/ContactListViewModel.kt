package com.wils.drivingmode.ui.contactList.viewModel

import androidx.lifecycle.*
import com.wils.drivingmode.roomdb.ContactEntity
import kotlinx.coroutines.launch

class ContactListViewModel(private val contactListRepository: ContactListRepository):ViewModel() {

    val getAllData:LiveData<List<ContactEntity>> = contactListRepository.allContactList.asLiveData()

    init {

    }

    fun insertData(listData:List<ContactEntity>) = viewModelScope.launch {
        contactListRepository.insert(listData)
    }


}

class ContactListViewModelFactory(private val contactListRepository: ContactListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ContactListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ContactListViewModel(contactListRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}