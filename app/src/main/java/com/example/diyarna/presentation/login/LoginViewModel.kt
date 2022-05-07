package com.example.diyarna.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.data.repository.DataRepoImpl
import com.example.diyarna.domain.repositry.DataRepo
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONException
import kotlin.coroutines.resumeWithException

class LoginViewModel  @ViewModelInject constructor(private val dataRepo: DataRepoImpl): ViewModel() {

    @ExperimentalCoroutinesApi
    suspend fun getFacebookToken(callbackManager: CallbackManager): LoginResult =
        suspendCancellableCoroutine { continuation ->
            LoginManager.getInstance()
                .registerCallback(callbackManager, object :
                    FacebookCallback<LoginResult> {

                    override fun onSuccess(loginResult: LoginResult) {
                        continuation.resume(loginResult){
                            val request = GraphRequest.newMeRequest(
                                loginResult.accessToken
                            ) { `object`, response ->
                                if (`object` != null) {
                                    try {
                                        val name = `object`.getString("name")
                                        val email = `object`.getString("email")
                                        val fbUserID = `object`.getString("id")

                                        Log.d("TAG", "onSuccess: "+name)
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
                        // handling cancelled flow (probably don't need anything here)
                        continuation.cancel()
                        Log.v("LoginScreen", "---onCancel")

                    }

                    override fun onError(exception: FacebookException) {
                        // Facebook authorization error
                        continuation.resumeWithException(exception)
                        Log.v(
                            "LoginScreen", "----onError: "
                                    + exception.message
                        )
                    }
                })
        }





}