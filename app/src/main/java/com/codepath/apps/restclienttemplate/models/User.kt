package com.codepath.apps.restclienttemplate.models

import org.json.JSONObject

class User {

    var name: String = ""
    var screenName: String = ""
    var publicImageUrl: String = ""

    companion object {
        // We want a method that takes in a json object gotten from the callback, and convert it into data
        fun fromJson(jsonObject: JSONObject): User {
            val user = User()
            user.name = jsonObject.getString("name")
            user.screenName = jsonObject.getString("screen_name")
            user.publicImageUrl = jsonObject.getString("profile_image_url_https")

            return user
        }
    }
}