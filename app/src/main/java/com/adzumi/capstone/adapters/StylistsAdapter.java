package com.adzumi.capstone.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.adzumi.capstone.R;
import com.adzumi.capstone.models.Employees;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StylistsAdapter
        extends RecyclerView.Adapter<StylistsAdapter.StylistViewHolder> {
    private List<Employees> mEmployees;
    private Context mContext;

    public StylistsAdapter(Context context, List<Employees> stylists) {
        mContext = context;
        mEmployees = stylists;
    }

    @Override
    public void onBindViewHolder(StylistViewHolder holder, int position) {
        holder.bindBooks(mEmployees.get(position));
    }

    @Override
    public StylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stylist_layout, parent, false);
        StylistViewHolder viewHolder = new StylistViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public class StylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.employeeImageView) ImageView mEmployeeImageView;
        @BindView(R.id.employeeNameTextView) TextView mEmployeeNameTextView;
        @BindView(R.id.deptTextView) TextView mDeptTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;
        @BindView(R.id.ratingsTextView) TextView mRatingsTextView;
        @BindView(R.id.ratingBar2) RatingBar mEmployeeRatingBar;

        private Context mContext;

        public StylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
//            Intent intent = new Intent(mContext, BookDetailsActivity.class);
//            intent.putExtra("position", itemPosition);
//            intent.putExtra("work", Parcels.wrap(mWork));
//            mContext.startActivity(intent);
        }


        public void bindBooks(Employees employees) {
            mEmployeeNameTextView.setText(employees.getName());
            mDeptTextView.setText(employees.getDept());
            mRatingTextView.setText(employees.getRating() + "/5");
            mRatingsTextView.setText(employees.getRatings() + " Ratings");
            mEmployeeRatingBar.setRating(Float.valueOf(employees.getRating()));
        }
    }
}
