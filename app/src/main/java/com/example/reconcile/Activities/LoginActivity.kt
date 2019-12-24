package com.example.reconcile.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.reconcile.R
import com.example.reconcile.RegisterActivity
import com.example.reconcile.Util.ToastUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.password
import kotlinx.android.synthetic.main.activity_register.progressbar

class LoginActivity : AppCompatActivity() , View.OnClickListener{

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener(this)
        directToRegister.setOnClickListener(this)
    }

    private fun login(){
        val userEmail = email.text
        val password = password.text
        progressbar.setProgress(50,true)
        progressbar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(userEmail.toString(), password.toString()).addOnCompleteListener{
                task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Log.d(RegisterActivity.TAG, "login user with email ${userEmail} is successful")
                //login OK
                ToastUtil.also { it.showToast(this, it.LOGIN_SUCCESSFUL) }
                startActivity(Intent(this, UserHomeActivity::class.java ))
            } else {
                //Registration error
                ToastUtil.also { it.showToast(this, it.CANNOT_LOGIN_CURRENT_USER) }
            }
            progressbar.also { if (it.visibility == View.VISIBLE) it.visibility = View.INVISIBLE }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login -> login().also { Log.d(TAG, "Register button clicked") }
            R.id.directToRegister -> startActivity(Intent(this, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

    }
    companion object{
        val TAG = LoginActivity::class.java.simpleName
    }

}