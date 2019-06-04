package com.example.sanzu.eclass.Notes

import android.app.Activity
import android.content.Intent
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.sanzu.eclass.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_notes.*
import java.util.*

class NotesActivity : AppCompatActivity() {
   val storeref=FirebaseStorage.getInstance().getReference("/Notes/")
    val dataref=FirebaseDatabase.getInstance().getReference()
    var fileurl:String=""
    var Nsubject:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
       val uid=FirebaseAuth.getInstance().uid
        val student=dataref.child("/user/$uid/adminautho")
     student.addListenerForSingleValueEvent(object:ValueEventListener{
         override fun onCancelled(p0: DatabaseError) {
         }

         override fun onDataChange(p0: DataSnapshot) {
             val adminauth=p0.value.toString()
             if(adminauth=="false"){
                 spinner_notes.visibility=View.INVISIBLE
                 select_pdf.visibility=View.INVISIBLE
                 pdf_notes_view.visibility=View.INVISIBLE
                 input_file_name.visibility=View.INVISIBLE
                 upload_note.visibility=View.INVISIBLE
             }
         }

     })
        val options= arrayOf("Select Subject","Comunication English","Software engineering","Computer Graphics","Instrumentation II","Computer architecture","Data Communication","Statistics")

        spinner_notes.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)
        spinner_notes.onItemSelectedListener= object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
             Nsubject=""
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               Nsubject=options.get(position)
                Log.d("note","$Nsubject is selected")
            }
        }
        Log.d("note","$Nsubject is selected")
        subject_1.setOnClickListener {
         val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Communication English")
            startActivity(noticeintent)
        }
        subject_2.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Software engineering")
            startActivity(noticeintent)
        }
        subject_3.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Computer Graphics")
            startActivity(noticeintent)
        }
        subject_4.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Instrumentation II")
            startActivity(noticeintent)
        }
        subject_5.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Computer architecture")
            startActivity(noticeintent)
        }
        subject_6.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Data Communication")
        }
        subject_7.setOnClickListener {
            val noticeintent=Intent(this,noticeContents::class.java)
            noticeintent.putExtra("subject","Statistics")
            startActivity(noticeintent)
        }
        select_pdf.setOnClickListener {
            selectPdf()
        }
        upload_note.setOnClickListener {
            uploadfile()
        }
    }
    private fun selectPdf() {
        val selectPdf = Intent(Intent.ACTION_GET_CONTENT)
        selectPdf.type = "application/pdf"
        startActivityForResult(selectPdf, 0)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("note", "File was selected")
            var selectedFileuri = data.data
            if (selectedFileuri != null) {
                val fileid = UUID.randomUUID().toString()
             storeref.child("/$fileid").putFile(selectedFileuri)
                 .addOnSuccessListener {
                     storeref.downloadUrl.addOnSuccessListener {

                         Log.d("admin", "urlis${it.toString()}")
                         fileurl = it.toString()
                         Log.d("note","the url is:$fileurl")
                     }
                 }
            }
        }
    }

    private fun uploadfile(){
    val filename=input_file_name.text.toString()
        Log.d("note","the url is::::$fileurl")
        val notessend=notesdetail(filename,fileurl)
        Log.d("note","$Nsubject is selected")
        dataref.child("/Notes/$Nsubject").setValue(notessend)
            .addOnCompleteListener {
                Log.d("note","Sucessfully uploaded")
                Log.d("note","$Nsubject is selected")
            }


        }

}
