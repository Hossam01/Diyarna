package com.example.diyarna.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.base.BaseActivity
import com.example.diyarna.data.repository.DataRepoImpl
import com.example.diyarna.databinding.ActivityLoginBinding
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.util.Status
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import okhttp3.internal.wait
import org.json.JSONException
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {
    lateinit var binding:ActivityLoginBinding
    lateinit var callbackManager: CallbackManager
    lateinit var loginManager: LoginManager

    lateinit var googleApiClient: GoogleApiClient
    val loginViewModel:LoginViewModel by viewModels()
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()

        lifecycleScope.launchWhenCreated {
            var loginResult= loginViewModel.getFacebookToken(callbackManager,loginManager)
            if (!loginResult.recentlyGrantedPermissions.isEmpty())
            {
                Log.d("TAG", "onCreatetest: "+loginResult.recentlyGrantedPermissions.toString())
                gotoProfile("","")
            }
        }

        binding.buttonFacebook.setOnClickListener {
            loginManager.logInWithReadPermissions(this, Arrays.asList(
                "email",
                "public_profile",
                "user_birthday"))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this@LoginActivity)
            .enableAutoManage(this@LoginActivity,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        binding.signInButton.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, RC_SIGN_IN)
        }

        binding.guest.setOnClickListener {
            gotoProfile(null,null)
        }
    }

    override fun onPause() {
        super.onPause()
        disconnectFromGoogle()
        loginViewModel.disconnectFromFacebook()
    }


    fun disconnectFromGoogle() {
        googleApiClient.stopAutoManage(this@LoginActivity)
        googleApiClient.disconnect()
    }




    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == RC_SIGN_IN) {
            data?.let { loginViewModel.handleSignInResult(it) }
        }

        lifecycleScope.launchWhenCreated {
            loginViewModel.mutableStateFlow.collect {
                when(it.status)
                {
                    Status.OK->{
                        gotoProfile("","")
                    }
                    Status.ERROR->{
                        Toast.makeText(this@LoginActivity,it.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        callbackManager.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }




    private fun gotoProfile(name:String?, email: String?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("TAG", "onConnectionFailed: "+p0.errorMessage)
    }
}