package com.example.adlforum.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adlforum.R;
import com.example.adlforum.ui.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    // Create a ViewHolder class for the individual items
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        // Get the comment for the current position
        Comment currentComment = comments.get(position);

        // Bind the data to the views
        holder.author.setText(currentComment.getAuthor());
        holder.comment.setText(currentComment.getComments());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
