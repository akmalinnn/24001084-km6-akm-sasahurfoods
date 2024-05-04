package com.akmalin.sasahurfoods.data.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String,
    val username: String,
    val email: String,
)

fun FirebaseUser?.toUser() =
    this?.let {
        User(
            id = this.uid,
            username = this.displayName.orEmpty(),
            email = this.email.orEmpty(),
        )
    }
