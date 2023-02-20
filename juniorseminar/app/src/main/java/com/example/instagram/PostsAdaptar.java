package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostsAdaptar extends RecyclerView.Adapter<PostsAdaptar.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private ImageView ivprofilepic;
    private ImageView ivlike;
    private TextView tvCreatedAt;
    List<String> likeBy;


    public PostsAdaptar(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivprofilepic= itemView.findViewById(R.id.ivprofilepic);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {

            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            ivlike= itemView.findViewById(R.id.ig_heart);
            tvCreatedAt.setText(timeAgo);


            ivlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> likeBy = post.getlikedBy();
                    if(!likeBy.contains(ParseUser.getCurrentUser().getObjectId()))
                    {
                        likeBy.add(ParseUser.getCurrentUser().getObjectId());
                        post.setlikedBy(likeBy);
                        Drawable newImage = context.getDrawable(R.drawable.ig_heart);
                        ivlike.setImageDrawable(newImage);
                        Log.e("PostsAdapter", "like");

                    }
                    else{
                        likeBy.remove(ParseUser.getCurrentUser().getObjectId());
                        post.setlikedBy(likeBy);
                        Drawable newImage = context.getDrawable(R.drawable.ig_heart_black);
                        ivlike.setImageDrawable(newImage);
                        Log.e("PostsAdapter", "unlike");
                    }
                    post.saveInBackground();


                }
            });

            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile ivProfilepic = post.getUser().getParseFile("profilepic");
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            if (ivProfilepic != null) {
                Glide.with(context).load(ivProfilepic.getUrl()).into(ivprofilepic);
            }



        }
        public void clear() {
            posts.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<Post> list) {
            posts.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                // show the activity
                context.startActivity(intent);


            }


        }
    }
}