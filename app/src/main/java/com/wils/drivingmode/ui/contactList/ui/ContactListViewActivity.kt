package com.wils.drivingmode.ui.contactList.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wils.drivingmode.R
import androidx.activity.viewModels
import com.wils.drivingmode.application.ProApplication
import com.wils.drivingmode.roomdb.ContactEntity
import com.wils.drivingmode.ui.contactList.viewModel.ContactListViewModel
import com.wils.drivingmode.ui.contactList.viewModel.ContactListViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi

import android.provider.ContactsContract

import android.content.Intent
import android.database.Cursor
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_contact_list_view.*
import androidx.core.app.ActivityCompat
import com.wils.drivingmode.ui.contactList.MobileContactList


class ContactListViewActivity:AppCompatActivity() {

    private val viewModel:ContactListViewModel by viewModels {
        ContactListViewModelFactory((application as ProApplication).contactListRepository)
    }

    val REQUEST_READ_CONTACTS:Int = 79

    var mobileArray:ArrayList<MobileContactList> = ArrayList()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contact_list_view)
        initView()
        initClickListener()


    }

    private fun initClickListener() {
        contactName?.setOnClickListener {

            showContacts()
        }
    }

    private fun getContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, 1)
    }


    @InternalCoroutinesApi
    private fun initView() {


        var listD :ArrayList<ContactEntity> = ArrayList()


        listD.add(
            ContactEntity(
                contactName = "wills",
                contactNo = "9087189381"
            )

        )
        val result = viewModel.insertData(listD as List<ContactEntity>)

    }


    //In Kotlin
    private fun showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                1001
            )
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContactList()
        }
    }

    @SuppressLint("Range")
    private fun getContactList() {
        val cr: ContentResolver = this.contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cur?.count ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id: String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    pCur?.let {
                        while (it.moveToNext()) {
                            val phoneNo: String = it.getString(
                                it.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            Log.i("Contact","Name: $name")
                            Log.i("Contact","Phone Number: $phoneNo")
                        }
                        pCur.close()
                    }

                }
            }
        }
        cur?.close()
    }
/*

    val contactResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){

            val intent = it.data


            val c: Cursor? = contentResolver.query(intent, null, null, null, null)
            if (c.moveToFirst()) {
                var phoneNumber = ""
                var emailAddress = ""
                val name: String =
                    c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contactId: String = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID))
                //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer
                var hasPhone: String =
                    c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                hasPhone = if (hasPhone.equals("1", ignoreCase = true)) "true" else "false"
                if (Boolean.parseBoolean(hasPhone)) {
                    val phones: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null
                    )
                    while (phones.moveToNext()) {
                        phoneNumber =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    phones.close()
                }

                // Find Email Addresses
                val emails: Cursor? = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null,
                    null
                )
                while (emails.moveToNext()) {
                    emailAddress =
                        emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                }
                emails.close()

                //mainActivity.onBackPressed();
                // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();
                tvname.setText("Name: $name")
                tvphone.setText("Phone: $phoneNumber")
                tvmail.setText("Email: $emailAddress")
                Log.d("curs", "$name num$phoneNumber mail$emailAddress")
            }
            c.close()



        }
        else{


        }
    }
*/


    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_CONTACTS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mobileArray = getAllContacts()
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

    private fun getAllContacts(): ArrayList<MobileContactList> {

        var listData:ArrayList<MobileContactList>  = ArrayList()



        return listData
    }




}