package com.example.diyarna.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.diyarna.base.BaseActivity
import com.example.diyarna.databinding.ActivityLoginBinding
import com.example.diyarna.presentation.main.MainActivity
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
import org.json.JSONException
import java.util.*


@AndroidEntryPoint
class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {
    lateinit var binding:ActivityLoginBinding
    lateinit var callbackManager: CallbackManager
    lateinit var loginManager: LoginManager
    lateinit var googleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callbackManager = CallbackManager.Factory.create()
        facebookLogin()
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
        disconnectFromFacebook()

    }

    fun facebookLogin() {
        loginManager = LoginManager.getInstance()
        callbackManager = CallbackManager.Factory.create()
        loginManager
            .registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(
                            loginResult.accessToken
                        ) { `object`, response ->
                            if (`object` != null) {
                                try {
                                    val name = `object`.getString("name")
                                    val email = `object`.getString("email")
                                    val fbUserID = `object`.getString("id")
                                    gotoProfile(name,email)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                } catch (e: NullPointerException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        val parameters = Bundle()
                        parameters.putString(
                            "fields",
                            "id, name, email, gender, birthday"
                        )
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        Log.v("LoginScreen", "---onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.v(
                            "LoginScreen", "----onError: "
                                    + error.message
                        )
                    }
                })
    }

    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return
        }
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE
        ) { LoginManager.getInstance().logOut() }
            .executeAsync()
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
            data?.let { handleSignInResult(it) }
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

    private fun handleSignInResult(result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnCompleteListener {
                Log.d("TAG", "handleSignInResult: "+ "isSuccessful ${it.isSuccessful}")
                if (it.isSuccessful){
                    // user successfully logged-in
                    gotoProfile(it.result.displayName!!,it.result.email!!)

                } else {
                    // authentication failed
                    Log.d("TAG", "handleSignInResult: "+"exception ${it.exception}")
                    Toast.makeText(this@LoginActivity, "Sign in cancel", Toast.LENGTH_LONG).show()
                }
            }
        }


    private fun gotoProfile(name:String?, email: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name",name)
        intent.putExtra("email",email)
        startActivity(intent)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
}