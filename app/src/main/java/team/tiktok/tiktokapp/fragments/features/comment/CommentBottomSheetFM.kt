package team.tiktok.tiktokapp.fragments.features.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentCommentBottomSheetBinding
import team.tiktok.tiktokapp.databinding.FragmentProfileBottomSheetBinding

class CommentBottomSheetFM : BottomSheetDialogFragment(), View.OnClickListener  {
    lateinit var binding: FragmentCommentBottomSheetBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBottomSheetBinding.inflate(layoutInflater)
//        clickButton()
        initClick()
        return binding!!.root
    }

    private fun initClick() {
        binding.ivClose.setOnClickListener(this)
    }

    //
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id){
            R.id.ivClose -> {
                this.dismiss()
            }
        }
    }


}