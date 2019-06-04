package com.example.sanzu.eclass.Notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.example.sanzu.eclass.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_note_contents.*
import kotlinx.android.synthetic.main.notes_loader.view.*

class noticeContents : AppCompatActivity() {
    val ref=FirebaseDatabase.getInstance().getReference()
    val storageref=FirebaseStorage.getInstance().getReference("/Notes/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subjectselected = intent.getStringExtra("subject")
        setContentView(R.layout.activity_note_contents)
        val noticemgr= androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerview_notes.layoutManager=noticemgr
        fetchnotes(subjectselected)
    }
fun fetchnotes(subjects:String){
        val selectnotes = ref.child("/Notes/$subjects")
        selectnotes.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                    val notes = p0.getValue(notesdetail::class.java)
                        val title=notes?.nameofnote.toString()
                val url=notes?.url.toString()
                        Log.d("note", "the subject is:${title}")
                        adapter.add(noteitem(title))

                adapter.setOnItemClickListener { item, view ->
                 downloadfile(url)
                }
       recyclerview_notes.adapter=adapter
            }

        })
    }
private fun downloadfile(Url:String){

}
}
class noteitem(val notes:String):Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.notes_title.text=notes
        Log.d("note","the title is:$notes")
    }

    override fun getLayout(): Int {
        return R.layout.notes_loader
    }
}
class notesdetail(val nameofnote:String,val url:String){
    constructor():this("","")
}