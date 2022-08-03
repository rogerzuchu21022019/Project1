package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentUpdateToptopIdBinding


class UpdateTopTopID : Fragment() {
   lateinit var binding:FragmentUpdateToptopIdBinding
   lateinit var dbUser :DatabaseReference
   lateinit var dbVideo :DatabaseReference
   val navArgs : UpdateFullnameArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateToptopIdBinding.inflate(layoutInflater)
        clickButton()
        loadTopTopID()
        return binding.root
    }

    fun getUser():User{
        return navArgs.user!!
    }
    private fun loadTopTopID(){
        binding.edtReTopTopID.setText(getUser().topTopID)
    }

    private fun clickButton() {
        binding.btnUpdateTopTopID.apply {
            setOnClickListener {
                renameTopTopID()
                val action = UpdateTopTopIDDirections.actionUpdateTopTopIDToEditProfileFM(getUser())
                findNavController().navigate(action)
            }
        }
    }

    private fun renameTopTopID() {
        val topTopID = binding.edtReTopTopID.text.toString().trim()
        dbUser = Firebase.database.getReference("users")
        dbUser.child(getUser().uuid!!).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                updateTopTopID(topTopID,dbUser.child(getUser().uuid!!))
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        dbVideo = Firebase.database.getReference("videos")
        dbVideo.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)!!
                    user.topTopID = topTopID
                    Log.d("haha","${user.topTopID}")
                    val uidVideo = it.child("uidVideo").getValue(String::class.java)!!


                    updateTopTopID(topTopID,dbVideo.child(uidVideo).child("user"))
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }
    private fun updateTopTopID(topTopID:String, dbUser: DatabaseReference) {

        var hashMap: MutableMap<String, String> = HashMap()
        hashMap.put("topTopID", topTopID)
        dbUser.updateChildren(hashMap as Map<String, Int>)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}