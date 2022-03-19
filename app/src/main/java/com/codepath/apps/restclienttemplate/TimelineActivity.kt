package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {

    lateinit var client: TwitterClient // Declare client

    lateinit var rvTweets: RecyclerView

    lateinit var adapter: TweetsAdapter

    val tweets = ArrayList<Tweet>()

    // Variable to contain the SwipeRefreshLayout
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client = TwitterApplication.getRestClient(this)

        // Initialize the swiperefreshlayout
        swipeContainer = findViewById(R.id.swipeContainer)

        // Set refresh listener
        swipeContainer.setOnRefreshListener {
            Log.i(TAG, "Refreshing the timeline!")
            populateHomeTimeline()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        rvTweets = findViewById(R.id.rvTweets)
        adapter = TweetsAdapter(tweets)

        rvTweets.layoutManager = LinearLayoutManager(this)
        rvTweets.adapter = adapter

        populateHomeTimeline()
    }

    // Inflates by saying that inside this file, inflate a specific resource file.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle having been clicked on the icon, clicking on menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.compose) {
            Toast.makeText(this, "Ready to compose tweet!", Toast.LENGTH_SHORT)
                .show()

            // Navigate to compose screen using an INTENT
            val intent = Intent(this, ComposeActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE) // Deprecated, but lets us know where we came from
        }
        return super.onOptionsItemSelected(item)
    }

    // Called when we come back from compose activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // REQUEST_CODE defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Get data from our intent (Our tweet)
            val tweet = data?.getParcelableExtra("tweet") as Tweet

            // Update the timeline
            // Modifying the data source of tweets
            tweets.add(0, tweet)

            // Update adapter
            adapter.notifyItemInserted(0)

            // Scroll back up to see the new tweet
            rvTweets.smoothScrollToPosition(0)
        }



        super.onActivityResult(requestCode, resultCode, data)
    }

    fun populateHomeTimeline() {
        client.getHomeTimeline(object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) { // No need for null check in onSuccess
                val jsonArray = json.jsonArray

                try {
                    adapter.clear() // We need to clear out currently fetched tweets on refresh, to avoid having duplicates
                    val listOfNewTweetsRetrieved = Tweet.fromJsonArray(jsonArray)
                    tweets.addAll(listOfNewTweetsRetrieved)
                    adapter.notifyDataSetChanged()
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false) // Without this line, we'll continue seeing the refreshing icon
                } catch (e: JSONException) {
                    Log.e(TAG, "JSON Exception $e")
                }

                Log.i(TAG, "onSuccess! (Home Timeline Populated)")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "onFailure :( $statusCode")
            }


        })
    }

    companion object {
        val TAG = "TimelineActivity"
        val REQUEST_CODE = 10
    }
}