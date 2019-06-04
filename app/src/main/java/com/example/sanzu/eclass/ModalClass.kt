package com.example.sanzu.eclass

import android.os.Parcelable
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
class discussionmodal(val discussiontitle:String,val discussioncontent:String,val postername:String ,val posttime:String,val discussionid:String):Parcelable{
    constructor():this("","","","","")
}

class commentdetails(val commentername:String,val commentbody:String,val commenttime:String){
    constructor():this("","","")
}

class noticeclass(val title:String,val body: String,val timeofnotice:String,val urlimage:String,val postername:String){
    constructor():this("","","","","")
}
class assignmentModal(val title:String,val body:String,val dateofsubmission:String){
    constructor():this("","","")
}
class Users(val uid:String,val names:String,val roll:String,val adminautho:Boolean,val section:String){
    constructor():this("","","",false,"")
}
class  routinesModelClass (

    val name:String,
    val time1s:String,
    val time1e:String,
    val time2s:String,
    val time2e:String,
    val time3s:String,
    val time3e:String,
    val time4s:String,
    val time4e:String,
    val time5s:String,
    val time5e:String,
    val time6s:String,
    val time6e:String,
    val time7s:String,
    val time7e:String,
    val  p1:String,
    val  p2:String,
    val  p3:String,
    val  p4:String,
    val  p5:String,
    val  p6:String,
    val  p7:String
){constructor():this("","","","","","","","",
    "","","","","","","","","","",
    "","","","")}


object User_details {
    val bct_a_roll:Collection<String> =Arrays.asList("01-BCT-A","02-BCT-A","03-BCT-A","04-BCT-A","06-BCT-A","07-BCT-A","08-BCT-A","09-BCT-A")

    val bct_b_roll:Collection<String> = Arrays.asList("45-BCT-B","80-BCT-B","47-BCT-B","78-BCT-B","76-BCT-B",
        "46-BCT-B","48-BCT-B","49-BCT-B","50-BCT-B","51-BCT-B",
        "52-BCT-B","53-BCT-B","54-BCT-B","55-BCT-B","56-BCT-B",
        "57-BCT-B","58-BCT-B","59-BCT-B","60-BCT-B","61-BCT-B",
        "62-BCT-B","63-BCT-B","64-BCT-B","65-BCT-B","66-BCT-B",
        "67-BCT-B","68-BCT-B","69-BCT-B","70-BCT-B","71-BCT-B",
        "72-BCT-B","73-BCT-B","74-BCT-B","75-BCT-B","77-BCT-B",
        "79-BCT-B","81-BCT-B" ,"82-BCT-B","83-BCT-B","84-BCT-B",
        "85-BCT-B","86-BCT-B","87-BCT-B","88-BCT-B")
    val bct_c_roll:Collection<String> =Arrays.asList("89-BCT-C","90-BCT-C","91-BCT-C","90-BCT-C","91-BCT-C","92-BCT-C")
    val admin_eclass:Collection<String> = Arrays.asList("ADMIN-ECLASS","80-BCT-B","47-BCT-B","78-BCT-B","76-BCT-B")
}