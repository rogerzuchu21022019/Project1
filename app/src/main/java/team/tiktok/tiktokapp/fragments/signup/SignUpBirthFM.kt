package team.tiktok.tiktokapp.fragments.signup

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.databinding.FragmentSignupBirthBinding
import java.util.*


class SignUpBirthFM : Fragment() {
   lateinit var binding: FragmentSignupBirthBinding
    var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBirthBinding.inflate(layoutInflater)

        initDatePicker()
        clickButton()
        return binding.root
    }

    private fun clickButton() {
        binding.btnNext.apply {
            setOnClickListener {

            }
        }
    }


    private fun initDatePicker() {
        val style = DatePickerDialog.THEME_HOLO_LIGHT
        val pickerDialog = DatePickerDialog(
            requireActivity()!!, style,
            OnDateSetListener { view, year, month, dayOfMonth ->
                binding.edtBirth.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year)
                if (TextUtils.isEmpty(binding.edtBirth.text)) {
                    Toast.makeText(
                        requireContext(),
                        "Please pick date and click ok to finish",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnDateSetListener
                }
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
        pickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}