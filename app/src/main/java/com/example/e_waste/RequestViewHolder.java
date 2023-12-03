package com.example.e_waste;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RequestViewHolder extends RecyclerView.ViewHolder {
    public TextView usernameTextView, locationTextView;

    public RequestViewHolder(View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.textView7);
        locationTextView = itemView.findViewById(R.id.textView11);
    }
}
