package com.adzumi.capstone.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.adzumi.capstone.Constants;
import com.adzumi.capstone.R;
import com.adzumi.capstone.models.Employees;
import com.adzumi.capstone.ui.HairServicesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirebaseBookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.employeeImageView) ImageView mEmployeeImageView;
    @BindView(R.id.employeeNameTextView) TextView mEmployeeNameTextView;
    @BindView(R.id.deptTextView) TextView mDeptTextView;
    @BindView(R.id.ratingTextView) TextView mRatingTextView;
    @BindView(R.id.ratingsTextView) TextView mRatingsTextView;
    @BindView(R.id.ratingBar2) RatingBar mEmployeeRatingBar;

    View mView;
    Context mContext;

    public FirebaseBookViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindBooks(Employees employees) {
        mEmployeeNameTextView.setText(employees.getName());
        mDeptTextView.setText(employees.getDept());

//        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
//        double bookRatings = Double.parseDouble(employees.getRatingsCount().getText());
//        mBookNameTextView.setText(employees.getBestBook().getTitle());
//        mAuthorTextView.setText("By " + employees.getBestBook().getAuthor().getName());
//        mRatingTextView.setText(employees.getAverageRating() + "/5");
//        mRatingsTextView.setText(formatter.format(bookRatings) + " Ratings");
//        mBookRatingBar.setRating(Float.valueOf(employees.getAverageRating()));


//        if (!(employees.getBestBook().getSmallImageUrl().isEmpty())) {
//            Picasso.with(mContext).load(employees.getBestBook().getSmallImageUrl())
//                    .fit()
//                    .centerCrop()
//                    .into(mBookImageView);
//        }
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Employees> books = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_EMPLOYEES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    books.add(snapshot.getValue(Employees.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, HairServicesActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("books", Parcels.wrap(books));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

