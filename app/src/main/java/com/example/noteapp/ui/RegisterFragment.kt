package com.example.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.noteapp.viewModel.AuthViewModels
import com.example.noteapp.R
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.Utlils.TokenManager
import com.example.noteapp.databinding.FragmentRegisterBinding
import com.example.noteapp.models.UserRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {

          private var _binding : FragmentRegisterBinding? = null
          private val binding get() = _binding!!
          private val authViewModels by viewModels<AuthViewModels>()
        @Inject
        lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        if (tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
          //  authViewModels.signup(UserRequest("shams@gmail","sha123","xyz"))
        }
        binding.btnSignUp.setOnClickListener {
         //  authViewModels.signup(UserRequest("shams@gmail","sha123","xyz"))
           val validateResult = validateUserInput()
            if (validateResult.first){
                authViewModels.signup(getUserRequest())
            }else{
                binding.txtError.text=validateResult.second
            }


                   }

        bindObserver()

    }

    fun getUserRequest() : UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val username = binding.txtUsername.text.toString()

        return UserRequest(emailAddress,password,username)
    }

   private fun validateUserInput(): Pair<Boolean, String> {
       val userRequest = getUserRequest()
       return authViewModels.validatesCredentials(userRequest.userName,userRequest.email,userRequest.password,false)

   }

    private fun bindObserver() {
        authViewModels.userResponseLiveData.observe(viewLifecycleOwner,
            Observer {
                //     binding.progressbar.isVisible = false
                when(it){
                    is NetworkResult.Success->{
                        tokenManager.saveToken(it.data!!.token)
                        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                    }
                    is NetworkResult.Error->{
                        binding.txtError.text=it.message

                    }
                    is NetworkResult.Loading->{
                        //    binding.progressbar.isVisible = true

                    }

                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}