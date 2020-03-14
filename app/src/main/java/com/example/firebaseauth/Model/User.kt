package com.example.firebaseauth.Model

class User {
    public lateinit var name:String
    public lateinit var email:String
    public lateinit var phone:String

    constructor(name: String, email: String, phone: String) {
        this.name = name
        this.email = email
        this.phone = phone
    }
}