package com.example.lesson14

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lesson14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.users.observe(this, Observer { users ->
            if (users.isNotEmpty()) {
                val user = users[0]
                binding.nameTextView.text = "${user.name.title} ${user.name.first} ${user.name.last}"
                binding.genderTextView.text = "Gender: ${user.gender}"
                binding.emailTextView.text = "Email: ${user.email}"
                binding.phoneTextView.text = "Phone: ${user.phone}"
                binding.cellTextView.text = "Cell: ${user.cell}"
                binding.locationTextView.text = "Location: ${user.location.street.number} ${user.location.street.name}, ${user.location.city}, ${user.location.state}, ${user.location.country}"

                Glide.with(this)
                    .load(user.picture.large)
                    .into(binding.profileImageView)
            }
        })

        binding.reloadButton.setOnClickListener {
            viewModel.getUsers()
        }
    }
}