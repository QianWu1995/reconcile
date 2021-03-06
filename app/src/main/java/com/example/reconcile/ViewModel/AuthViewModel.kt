package com.example.reconcile.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reconcile.DI.Component.DaggerUserAuthComponent
import com.example.reconcile.Util.requestStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthViewModel : ViewModel(){
    @Inject
    internal lateinit var auth: FirebaseAuth
    internal lateinit var user: FirebaseUser
    internal val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }
    internal val displayName: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.displayName)
    }

    init {
        Log.d(TAG, "AuthViewModel is created")
        DaggerUserAuthComponent.create().inject(this)
        user =  auth.currentUser!!
        email.value = auth.currentUser?.email
    }

    internal fun updateProfile(newDisplayName: String?, photoURI:String): requestStatus{
        val request = UserProfileChangeRequest.Builder().setDisplayName(newDisplayName).setPhotoUri(Uri.parse(photoURI)).build()
        var status = requestStatus.FAIL
        user?.updateProfile(request)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
                displayName.value = newDisplayName
                status = requestStatus.SUCESS
            }
           else if(task.isCanceled){
                Log.d(TAG, "User profile update is canceled.")
                status = requestStatus.FAIL
            }
            else{
                status = requestStatus.FAIL
            }
        }
        return status
    }

    companion object{
        val TAG = AuthViewModel::class.java.simpleName
    }


}