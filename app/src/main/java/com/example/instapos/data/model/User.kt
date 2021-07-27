package com.example.instapos.data.model

data class User(

    val uid: String="",
    val email : String = "",
    var name: String = "",
    val biography: String = "",
    var image : String = "" ,
    var postCount: Int = 0,
    var followersCount: Int = 0,
    var followingCount: Int = 0
)
