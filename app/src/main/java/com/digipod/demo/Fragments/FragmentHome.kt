package com.digipod.demo.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide.init
import com.digipod.demo.ImageAdapter
import com.digipod.demo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instantConsultation.setOnClickListener {
//            startActivity(Intent(activity, InstantConsultation::class.java))
            // val dialIntent = Intent(Intent.ACTION_DIAL)
            // dialIntent.data = Uri.parse("tel:" + et_whatsapp.text.toString())
            // startActivity(dialIntent)
            val mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"

            val resolver: ContentResolver = requireActivity().contentResolver
            val cursor: Cursor? = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )

            while (cursor!!.moveToNext()) {
                var Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
                val _id = cursor.getLong(Col_Index)

                Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var number = cursor.getString(Col_Index)

                Col_Index =
                    cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val displayName = cursor.getString(Col_Index)

                Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.MIMETYPE)
                val mimeType = cursor.getString(Col_Index)
                // println("Data: " + _id.toString() + " ---" + displayName + "---" + number + "---" + mimeType )

                var my_number = "+917518139985"
                my_number = my_number.replace(" ", "")
                my_number = my_number.replace("+", "")


                if (!number.isNullOrBlank()) {
                    // println("Number : " + number )
                    number = number.replace(" ", "")
                    number = number.replace("+", "")

                    // my_number.substring(1)
                    // println(">>" + my_number)
                    if (number.endsWith(my_number.substring(1) + "@s.whatsapp.net")) {
                        if (mimeType.equals(mimeString)) {
                            val data = "content://com.android.contacts/data/$_id"
                            val sendIntent = Intent()
                            sendIntent.action = Intent.ACTION_VIEW
                            sendIntent.setDataAndType(Uri.parse(data), mimeString)
                            sendIntent.setPackage("com.whatsapp")
                            if(checkAndRequestPermissions()){
                                startActivity(sendIntent)
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    fun checkAndRequestPermissions(): Boolean {
        val call =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        val listPermissionsNeeded = ArrayList<String>()
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
