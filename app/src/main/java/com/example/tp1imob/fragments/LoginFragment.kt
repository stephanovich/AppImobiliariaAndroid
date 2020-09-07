package com.example.tp1imob.fragments

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tp1imob.MainActivity
import com.example.tp1imob.R
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*


@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {
    lateinit var imobiliariaViewModel : ImobiliariaViewModel
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FacebookSdk.sdkInitialize(activity?.applicationContext)
        activity?.let {

            imobiliariaViewModel = ViewModelProviders.of(it).get(ImobiliariaViewModel::class.java)

        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()

        fbLoginBt.fragment = this
        fbLoginBt.setReadPermissions("email", "public_profile")
        fbLoginBt.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }
                override fun onCancel() {
                }
                override fun onError(error: FacebookException) {
                   Toast.makeText(
                        this@LoginFragment.requireContext(),
                        error.message, Toast.LENGTH_LONG
                    ).show()
                }
            })

        cadasBt.setOnClickListener{
            findNavController().navigate(R.id.cadastroFragment)
        }

        loginBt.setOnClickListener{
            val email : String = userLogin.text.toString()
            val senha : String = passLogin.text.toString()
            if(email.isNotEmpty() && senha.isNotEmpty()){
                signIn(email,senha)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun signIn(email: String, pass: String){
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            when{
                it.isSuccessful -> {
                    dbFirestore.collection("imobiliarias").whereEqualTo("email", mAuth.currentUser?.email).get().addOnCompleteListener{ data ->
                        if(data.isSuccessful){
                            val imobiliaria = data.result?.toObjects(Imobiliaria::class.java)?.get(0)
                            var intent = Intent(activity,MainActivity::class.java)
                            intent.putExtra("imob", imobiliaria)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        val task = mAuth.signInWithCredential(credential)
        task.addOnFailureListener{e ->
            Toast.makeText(
                this@LoginFragment.requireContext(),
                e.message, Toast.LENGTH_LONG
            ).show()
        }
        task.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("TA AQUI")
                val email = task.result?.user?.email
                dbFirestore.collection("imobiliarias").whereEqualTo("email", email).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result?.size()!! > 0) {
                            val imobiliaria = it.result?.toObjects(Imobiliaria::class.java)?.get(0)
                            var intent = Intent(activity, MainActivity::class.java)
                            intent.putExtra("imob", imobiliaria)
                            startActivity(intent)
                        } else {
                            imobiliariaViewModel.imobiliaria?.email = email
                            findNavController().navigate(R.id.posLoginFbFragment)
                        }
                    } else {
                        mAuth.signOut()
                    }
                }
            }
        }
    }
}