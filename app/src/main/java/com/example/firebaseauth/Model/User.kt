package com.example.firebaseauth.Model

class User {
    public lateinit var name:String
    public lateinit var email:String
    public lateinit var phone:String
    public lateinit var role:String
    public lateinit var uid:String
    public lateinit var active:String
    public lateinit var height:String
    public lateinit var weight:String

    constructor(name: String, email: String, phone: String,role:String,uid:String) {
        this.name = name
        this.email = email
        this.phone = phone
        this.role = role;
        this.uid = uid;
    }

    constructor(name: String, email: String, phone: String) {
        this.name = name
        this.email = email
        this.phone = phone
    }


    constructor()
    constructor(
        name: String,
        email: String,
        phone: String,
        role: String,
        uid: String,
        active: String
    ) {
        this.name = name
        this.email = email
        this.phone = phone
        this.role = role
        this.uid = uid
        this.active = active
    }

    constructor(
        name: String,
        email: String,
        phone: String,
        role: String,
        uid: String,
        active: String,
        height: String,
        weight: String
    ) {
        this.name = name
        this.email = email
        this.phone = phone
        this.role = role
        this.uid = uid
        this.active = active
        this.height = height
        this.weight = weight
    }


}