package com.wils.drivingmode.ui.contactList.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wils.drivingmode.R
import androidx.activity.viewModels
import com.wils.drivingmode.application.ProApplication
import com.wils.drivingmode.ui.contactList.viewModel.ContactListViewModel
import com.wils.drivingmode.ui.contactList.viewModel.ContactListViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi

import android.provider.ContactsContract

import android.database.Cursor
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import kotlinx.android.synthetic.main.activity_contact_list_view.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.wils.drivingmode.utils.Utils
import com.wils.drivingmode.ui.contactList.MobileContactList
import com.wils.drivingmode.utils.ViewExtension


class ContactListViewActivity : AppCompatActivity() {

    private val viewModel: ContactListViewModel by viewModels {

        ContactListViewModelFactory((application as ProApplication).contactListRepository)

    }

    var contactAdapter: ContactListAdapter? = null

    var contactListData: ArrayList<MobileContactList> = ArrayList()
    val REQUEST_READ_CONTACTS: Int = 79

    var mobileArray: ArrayList<MobileContactList> = ArrayList()

    var progressDialog: Dialog? = null

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contact_list_view)
        initView()
        initObserver()
        initRecyclerView()
        initClickListener()


    }

    private fun initObserver() {
        viewModel.progress.observe(this, {
            if (it == View.VISIBLE)
                progressDialog?.show()
            else if (it == View.GONE)
                progressDialog?.dismiss()
        }

        )

    }

    private fun initRecyclerView() {

        contactListRecycler.layoutManager = LinearLayoutManager(this)

        contactAdapter = ContactListAdapter(contactListData)

        contactListRecycler.adapter = contactAdapter

//        showContacts()
    }

    private fun initClickListener() {
        contactName?.setOnClickListener {

            showContacts()

        }
    }


    @InternalCoroutinesApi
    private fun initView() {
        progressDialog = ViewExtension.progressDialog(this)

/*
        var listD :ArrayList<ContactEntity> = ArrayList()

        listD.add(
            ContactEntity(
                contactName = "wills",
                contactNo = "9087189381"
            )

        )
        val result = viewModel.insertData(listD as List<ContactEntity>)
*/

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
            progress(true)
            getContactList()

        }
    }

    fun progress(status: Boolean) {
        if (status)
            progressDialog?.show()
        else
            progressDialog?.dismiss()

    }

    @SuppressLint("Range")
    private fun getContactList() {
        val cr: ContentResolver = this.contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        contactListData.clear()

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
                /*            Log.i("Contact", "Name: $name")
                            Log.i("Contact", "Phone Number: $phoneNo")
*/
                            contactListData.add(
                                MobileContactList(
                                    name = name,
                                    number = phoneNo
                                )
                            )
                        }
                        pCur.close()
                    }

                }
            }

            contactListRecycler?.adapter?.notifyDataSetChanged()
        }
        cur?.close()

//        viewModel.progress.value=8
        progress(false)
    }

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

        var listData: ArrayList<MobileContactList> = ArrayList()



        return listData
    }


}