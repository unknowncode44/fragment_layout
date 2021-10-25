package com.example.landscapecontacts

import android.net.Uri
import android.os.Bundle
import android.service.carrier.CarrierIdentifier
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.contacts_details.*
import java.net.URI

class ContactsDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var name: TextView
    private lateinit var number: TextView
    private lateinit var email: TextView
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var imageUrl: String
    private lateinit var divider: RelativeLayout
    private lateinit var imageCardView: CardView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.contacts_details, container, false)

        image = vista.findViewById(R.id.detail_image)
        name = vista.findViewById(R.id.detail_name)
        number = vista.findViewById(R.id.detail_number)
        email = vista.findViewById(R.id.detail_email)
        title = vista.findViewById(R.id.detail_title)
        imageUrl = "${arguments?.getString("imageUrl")}"

        imageCardView = vista.findViewById(R.id.card_view_image)

        name.text = "${arguments?.getString("name")}"
        number.text = "${arguments?.getString("number")}"
        email.text = "${arguments?.getString("email")}"
        title.text = "${arguments?.getString("title")}"
        Glide.with(this).load(imageUrl).into(image)

        return vista



    }



}