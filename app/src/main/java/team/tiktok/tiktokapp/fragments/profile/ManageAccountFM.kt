package team.tiktok.tiktokapp.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.databinding.FragmentManageAccountBinding


class ManageAccountFM : Fragment() {
    lateinit var binding: FragmentManageAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageAccountBinding.inflate(layoutInflater)
        clickButton()
        loadData()
        return binding.root
    }

    fun updateUI(user: User) {
        binding.user = user
    }

    fun loadData() {
        val auth = Firebase.auth
        val userRef =
            Firebase.database.getReference("users").child(auth.currentUser!!.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)!!
                updateUI(user)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun clickButton() {
        binding.ivBack.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_manageAccountFM_to_settingAndPrivacyFM)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}