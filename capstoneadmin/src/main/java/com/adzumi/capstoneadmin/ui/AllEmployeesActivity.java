package com.adzumi.capstoneadmin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adzumi.capstone.models.Employees;
import com.adzumi.capstoneadmin.Constants;
import com.adzumi.capstoneadmin.R;
import com.adzumi.capstoneadmin.adapters.FirebaseBookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEmployeesActivity extends AppCompatActivity {

    @BindView(R.id.stylistsRecyclerView) RecyclerView mstylistsRecyclerView;
    @BindView(R.id.my_toolbar) Toolbar myToolbar;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private DatabaseReference mSavedBookReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_employees);
        ButterKnife.bind(this);

        mSavedBookReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_EMPLOYEES);

        setUpFirebaseAdapter();

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));
            }
        });
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Employees, FirebaseBookViewHolder>
                (Employees.class, R.layout.stylist_layout, FirebaseBookViewHolder.class,
                        mSavedBookReference) {

            @Override
            protected void populateViewHolder(FirebaseBookViewHolder viewHolder,
                                              Employees model, int position) {
                viewHolder.bindBooks(model);
            }
        };
        mstylistsRecyclerView.setHasFixedSize(true);
        mstylistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mstylistsRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AllEmployeesActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
