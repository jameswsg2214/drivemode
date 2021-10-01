package com.wils.drivingmode.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val uuid:Long=0,
    val contactName:String,
    val contactNo: String,
    val isStatus:Boolean= true
)