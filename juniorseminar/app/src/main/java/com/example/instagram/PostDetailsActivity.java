package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

   TextView tvUsername;
   TextView tvDate;
   TextView tvDescription;
   ImageView ivImage;
   RecyclerView rvComments;
   ImageView igComment;
   CommentAdapter adapter;
   Button ivback;


    Post post;

    @Override
    protected void onRestart() {

        super.onRestart();

        reFreshComment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivback = findViewById(R.id.btnback);
        igComment = findViewById(R.id.ig_comment);
        rvComments = findViewById(R.id.rvComments);
        adapter = new CommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);


        tvUsername = (TextView) findViewById(R.id.tvUsername);
        //tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getUser()));
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getDescription()));
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getImage()));



        //tvUsername.setText(post.getUser());
        //tvDescription.setText(post.getDescription());

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFeedActivity();
            }
        });

        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(PostDetailsActivity.this).load(image.getUrl()).into(ivImage);
        }

        igComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this, ComposeComment.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });

        reFreshComment();
    }

    private void goFeedActivity() {
        Intent i = new Intent(this, FeedActivity.class);
        startActivity(i);
        finish();

    }

    private void reFreshComment() {

        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.include(Comment.KEY_AUTHOR);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if(e!= null){
                    Log.e("error", e.getMessage());
                    return;
                }else{
                    adapter.clear();
                    adapter.addAll(objects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
