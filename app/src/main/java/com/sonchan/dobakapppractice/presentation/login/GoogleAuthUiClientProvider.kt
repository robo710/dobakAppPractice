package com.sonchan.dobakapppractice.presentation.login

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity

object GoogleAuthUiClientProvider {
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    fun initialize(context: Context) {
        googleAuthUiClient = GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
}
