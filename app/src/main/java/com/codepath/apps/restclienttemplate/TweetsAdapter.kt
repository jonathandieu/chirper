package com.codepath.apps.restclienttemplate

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet
import org.w3c.dom.Text

class TweetsAdapter(val tweets: List<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {
    // Inflates each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        // Provide Context
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate our item layout
        val view = inflater.inflate(R.layout.item_tweet, parent, false)
        return ViewHolder(view)
    }

    // In charge of actually populating the data into the item
    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        // Get the specific tweet data based on position
        val tweet: Tweet = tweets.get(position)

        // Set item views based on views and data model

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body


        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage) // Safe check with ?
    }

    // Tells our adapter how many views will be in the recyclerView, or the length of tweets list
    override fun getItemCount(): Int {
        return tweets.size
    }

    // Needs to reference each item in the item_tweet layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)

    }

}