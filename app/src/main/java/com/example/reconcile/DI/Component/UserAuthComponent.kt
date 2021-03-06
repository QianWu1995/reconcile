package com.example.reconcile.DI.Component

import com.example.reconcile.Activities.FriendListActivity
import com.example.reconcile.Activities.LoginActivity
import com.example.reconcile.DI.Module.UserAuthModule
import com.example.reconcile.RegisterActivity
import com.example.reconcile.ViewModel.AuthViewModel
import dagger.Component

@Component(modules = [UserAuthModule::class])
interface UserAuthComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(friendListActivity: FriendListActivity)
    fun inject(authViewModel: AuthViewModel)
}