package com.example.firebaseauth.Model

class User {
    public lateinit var name:String
    public lateinit var email:String
    public lateinit var phone:String
    public lateinit var role:String

    constructor(name: String, email: String, phone: String,role:String) {
        this.name = name
        this.email = email
        this.phone = phone
        this.role = role;
    }

    constructor(name: String, email: String, phone: String) {
        this.name = name
        this.email = email
        this.phone = phone
    }


    constructor()

}