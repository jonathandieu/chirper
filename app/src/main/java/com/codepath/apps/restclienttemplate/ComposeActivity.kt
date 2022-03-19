package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ComposeActivity : AppCompatActivity() {
    // Variable to edit text
    lateinit var etCompose: EditText
    lateinit var btnTweet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        // Set onclick listener that handles the user's click on the tweet button
        btnTweet.setOnClickListener {

            // Grab the content of the edittext (etCompose)
            val tweetContent = etCompose.text.toString()

            // Make sure the tweet isn't empty
            if (tweetContent.isEmpty()) {
                Toast.makeText(this, "Empty tweets are n o t allowed!", Toast.LENGTH_SHORT).show()
                    // Look into using a snackbar instead of a toast
            } else

            // Make sure the tweet is under the character count
            if (tweetContent.length > 180) {
                Toast.makeText(this,  "This tweet is too long! The limit is 140 characters.", Toast.LENGTH_SHORT)
                    .show()
            } else {
            Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
            // Make an api call to twitter that publishes the tweet
            }


        }

    }
}