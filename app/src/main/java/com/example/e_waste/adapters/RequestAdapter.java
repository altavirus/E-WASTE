/*
package com.example.e_waste.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_waste.R;
import com.example.e_waste.RequestViewHolder;
import com.example.e_waste.models.request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    private List<request> requestList;

    public RequestAdapter(List<request> requestList) {
        this.requestList = requestList;
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        request currentRequest = requestList.get(position);
        holder.usernameTextView.setText(currentRequest.getUsername());
        holder.locationTextView.setText(currentRequest.getLocation());
        // Set other properties as needed
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
*/
