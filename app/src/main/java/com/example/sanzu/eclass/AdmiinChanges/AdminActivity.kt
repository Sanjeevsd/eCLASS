package com.example.sanzu.eclass.AdmiinChanges


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast

import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.assignmentModal

import com.example.sanzu.eclass.noticeclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_admin.*

import java.text.SimpleDateFormat
import java.util.*


open class AdminActivity : AppCompatActivity() {
    var checkbutn = arrayListOf<String>("")
    var pname: String = ""
    var imageurl: String = ""
    val adminref = FirebaseDatabase.getInstance().getReference()
    val aid = FirebaseAuth.getInstance().uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        upload_notice_image.setOnClickListener {
            selectPicture()
        }
        upload_notice_picture.setOnClickListener {
            selectPicture()
        }
        post_notice_button.setOnClickListener {
            postUploadDatabase()
        }
        post_assignment_button.setOnClickListener {
            uploadAssignment()
        }

    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox_assign_a -> {
                    if (checked) {
                        checkbutn.add("A")
                    }
                    else{
                        checkbutn.remove("A")
                    }
                }
                R.id.checkBox_assign_b -> {
                    if (checked) {
                        checkbutn.add("B")
                    }
                    else{
                        checkbutn.remove("B")
                    }
                }
                R.id.checkBox_assign_c -> {
                    if (checked) {
                        checkbutn.add("C")
                    }
                    else{
                        checkbutn.remove("C")
                    }
                }
                R.id.checkBox_a -> {
                    if (checked) {
                        checkbutn.add("A")
                    }
                    else{
                        checkbutn.remove("A")
                    }
                }
                R.id.checkBox_b -> {
                    if (checked) {
                        checkbutn.add("B")
                    }
                    else{
                        checkbutn.remove("B")
                    }
                }
                R.id.checkBox_c -> {
                    if (checked) {
                        checkbutn.add("C")
                    }
                    else{
                        checkbutn.remove("C")
                    }
                }
                else -> {
                return
                }
            }
        }
    }
    private fun uploadAssignment() {
            val Asubject: String = assignment_subject_tv.text.toString()
            val Adate: String = assignment_date_tv.text.toString()
            val Adescription = assignment_details_tv.text.toString()
            val assigntitle = "New Assignment Of $Asubject"
            val assignbody = "$Adescription Which is to Be submitted on $Adate "
            Log.d("admin", "the subject is $Asubject and date is $Adate")
            if (Asubject.isEmpty() || Adate.isEmpty() || Adescription.isEmpty()) {
                Toast.makeText(this, "Empty Details", Toast.LENGTH_SHORT).show()
                return
            }
        checkbutn.forEach {
            Log.d("admin", "section is $it")
            if(it=="A" || it=="B" || it=="C"){
            val assignmentref = adminref.child("/Assignments/$it")
            val assignments = assignmentModal(assigntitle, assignbody, Adate)
            assignmentref.push().setValue(assignments)
                .addOnCompleteListener {
                    Log.d("admin", "Suessfully Posted Assignments")
                    Toast.makeText(this, "Posted Assignment Details", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed To post", Toast.LENGTH_SHORT).show()
                }      }  }
            assignment_date_tv.text.clear()
            assignment_details_tv.text.clear()
            assignment_subject_tv.text.clear()

    }

    private fun selectPicture() {
        val selectPic = Intent(Intent.ACTION_PICK)
        selectPic.type = "image/*"
        startActivityForResult(selectPic, 0)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("admin", "photowas selected")
            var selectedPhotouri = data.data
            if (selectedPhotouri != null) {
                val fileid = UUID.randomUUID().toString()
                val imageref = FirebaseStorage.getInstance().getReference("/images/$fileid")
                imageref.putFile(selectedPhotouri)
                    .addOnSuccessListener {
                        Log.d("admin", "Uploaded image")
                        imageref.downloadUrl.addOnSuccessListener {

                            Log.d("admin", "urlis${it.toString()}")
                            imageurl = it.toString()
                        }
                    }
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun postUploadDatabase() {

        val noticeTitle = title_notice.text.toString()
        val bodyNotice = body_notice.text.toString()
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val timeOfNotice: String = formatter.format(Date()).toString()
        Log.d("admin", "the time is::${timeOfNotice}")
        Log.d("admin", "notice-title:$noticeTitle")
        Log.d("admin", "notice-body:$bodyNotice")

        if (noticeTitle.isEmpty() || bodyNotice.isEmpty()) {
            Toast.makeText(this, "Empty Title or Body", Toast.LENGTH_SHORT).show()
            return
        }
        checkbutn.forEach {
            if (it=="A" || it=="B" || it=="C"){
        val adminnotice = adminref.child("/Notices/$it/")
        Log.d("admin", "url is:${imageurl}")

         adminref.child("/user/$aid").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                pname = p0.child("names").getValue().toString()
                Log.d("admin", "name is $pname")
                val noticepublish = noticeclass(noticeTitle, bodyNotice, timeOfNotice, imageurl, pname)
                Log.d("admin", "output name:$pname")
                adminnotice.push().setValue(noticepublish)
                    .addOnCompleteListener {
                        Log.d("admin", "Successfully published Notice")
                    }
            }
        })
        Toast.makeText(this, "Notice Posted", Toast.LENGTH_SHORT).show()
        title_notice.getText().clear()
        body_notice.getText().clear()
    }}
}

 }