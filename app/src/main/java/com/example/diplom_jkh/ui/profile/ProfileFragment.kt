package com.example.diplom_jkh.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.user.UserAddress
import com.example.diplom_jkh.data.user.UserAuth
import com.example.diplom_jkh.data.user.UserData
import com.example.diplom_jkh.data.user.UserFullName

class ProfileFragment : Fragment() {
    private val userAuth: UserAuth = UserAuth("qwerty", "qwerty")
    private val fullNamePerson: UserFullName = UserFullName(
        "Иванов", "Валерий",
        "Петрович"
    )
    private val fullAddressPerson: UserAddress = UserAddress(
        "Москва", "Автозаводской",
        "Мира", 56, 145
    )
    private val person: UserData = UserData(
        userAuth,
        "qwerty@mail.ru", "+79123456789",
        fullAddressPerson.getFullAddress(), fullNamePerson
    )

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        val surname: TextView = rootView.findViewById(R.id.surname)
        val name: TextView = rootView.findViewById(R.id.name)
        val middleName: TextView = rootView.findViewById(R.id.middle_name)
        val phoneNumber: TextView = rootView.findViewById(R.id.phone_number)
        val email: TextView = rootView.findViewById(R.id.email)
        val fullAddress: TextView = rootView.findViewById(R.id.full_address)

        surname.text = person.userFullName.surname
        name.text = person.userFullName.name
        middleName.text = person.userFullName.middleName
        phoneNumber.text = person.phoneNumber
        email.text = person.email
        fullAddress.text = person.address



        return rootView
    }

    fun getPerson(): UserData {
        return this.person
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}