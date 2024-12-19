package com.example.carpoolingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.models.RidePost;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private List<RidePost> rideList;
    private OnRideClickListener onRideClickListener;

    public RideAdapter(List<RidePost> rideList, OnRideClickListener onRideClickListener) {
        this.rideList = rideList;
        this.onRideClickListener = onRideClickListener;
    }

    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ride, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        RidePost ridePost = rideList.get(position);
        holder.tvStartLocation.setText(ridePost.getStartLocation());
        holder.tvDestination.setText(ridePost.getDestination());
        holder.tvDepartureTime.setText(ridePost.getDepartureTime());
        holder.tvCarModel.setText(ridePost.getCarModel());
        holder.tvCost.setText("$" + ridePost.getCost());
        holder.itemView.setOnClickListener(v -> onRideClickListener.onRideClick(ridePost));
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public class RideViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartLocation, tvDestination, tvDepartureTime, tvCarModel, tvCost;

        public RideViewHolder(View itemView) {
            super(itemView);
            tvStartLocation = itemView.findViewById(R.id.tvStartLocation);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvCarModel = itemView.findViewById(R.id.tvCarModel);
            tvCost = itemView.findViewById(R.id.tvCost);
        }
    }

    public interface OnRideClickListener {
        void onRideClick(RidePost ridePost);
    }
}
