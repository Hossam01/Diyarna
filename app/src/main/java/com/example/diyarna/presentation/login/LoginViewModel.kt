package com.example.diyarna.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.diyarna.data.repository.DataRepoImpl
import com.example.diyarna.domain.repositry.DataRepo
import com.example.diyarna.util.Resource
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONException
import kotlin.coroutines.resumeWithException
import kotlin.math.log

class LoginViewModel  @ViewModelInject constructor(private val dataRepo: DataRepoImpl): ViewModel() {

    var mutableStateFlow= MutableStateFlow<Resource<Boolean>>(Resource.loading(false))

    @ExperimentalCoroutinesApi
    suspend fun getFacebookToken(callbackManager: CallbackManager,loginManager:LoginManager): LoginResult =

        suspendCancellableCoroutine { continuation ->
            loginManager
                .registerCallback(callbackManager, object :
                    FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        continuation.resume(loginResult) {
                            val request = GraphRequest.newMeRequest(
                                loginResult.accessToken
                            ) { `object`, response ->
                                if (`object` != null) {
                                    try {
                                        val name = `object`.getString("name")
                                        val email = `object`.getString("email")
                                        val fbUserID = `object`.getString("id")

                                        Log.d("TAG", "onSuccess: "+name)
                                        dataRepo.save("name",name)
                                        dataRepo.save("email",email)
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

                    }

                    override fun onCancel() {
                        continuation.cancel()
                        Log.v("LoginScreen", "---onCancel")

                    }

                    override fun onError(exception: FacebookException) {
                        continuation.resumeWithException(exception)
                        Log.v(
                            "LoginScreen", "----onError: "
                                    + exception.message
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


    fun handleSignInResult(result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnCompleteListener {
                Log.d("TAG", "handleSignInResult: "+ "isSuccessful ${it.isSuccessful}")
                if (it.isSuccessful){
                    viewModelScope.launch {
                        dataRepo.save("name",it.result.displayName.toString())
                        dataRepo.save("email",it.result.email.toString())
                        mutableStateFlow.emit(Resource.success(true))
                    }
                } else {
                    Log.d("TAG", "handleSignInResult: "+"exception ${it.exception}")
                    viewModelScope.launch {
                        mutableStateFlow.emit(Resource.error(it.exception.toString(),false))
                    }
                }
            }
    }



}