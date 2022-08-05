package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
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
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentUpdateFullnameBinding


class UpdateFullname : Fragment(),View.OnClickListener {
   lateinit var binding: FragmentUpdateFullnameBinding
   lateinit var dbUser :DatabaseReference
   val navArgs : UpdateFullnameArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateFullnameBinding.inflate(layoutInflater)
        clickButton()
        loadFullName()
        return binding.root
    }
    fun test(){

    }

    fun getUser():User{
        return navArgs.user!!
    }
    fun loadFullName(){
        binding.edtRename.setText(getUser().fullName)
    }

    private fun clickButton() {
        binding.btnUpdate.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    private fun updateFullname() {
        val fullName = binding.edtRename.text.toString().trim()
        dbUser = Firebase.database.getReference("users")
        dbUser.child(getUser().uuid!!).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                updateFullName(fullName,dbUser.child(getUser().uuid!!))
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
    private fun updateFullName(fullName:String, dbUser: DatabaseReference) {

        var hashMap: MutableMap<String, String> = HashMap()
        hashMap.put("fullName", fullName)
        dbUser.updateChildren(hashMap as Map<String, Int>)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id){
            R.id.btnUpdate ->{
                updateFullname()
                val action = UpdateFullnameDirections.actionUpdateFullnameToEditProfileFM(getUser())
                findNavController().navigate(action)
            }
            R.id.ivBack ->{
                updateFullname()
                val action = UpdateFullnameDirections.actionUpdateFullnameToEditProfileFM(getUser())
                findNavController().navigate(action)
            }
        }
    }
}