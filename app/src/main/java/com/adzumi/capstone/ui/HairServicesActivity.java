package com.adzumi.capstone.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adzumi.capstone.Constants;
import com.adzumi.capstone.R;
import com.adzumi.capstone.adapters.FirebaseBookViewHolder;
import com.adzumi.capstone.adapters.StylistsAdapter;
import com.adzumi.capstone.models.Employees;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HairServicesActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mSavedBookReference;
    private StylistsAdapter mstylistAdapter;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    public ArrayList<Employees> employees = new ArrayList<>();

    @BindView(R.id.my_toolbar) Toolbar myToolbar;
    @BindView(R.id.editDate) EditText editDate;
    @BindView(R.id.toolbar_text) TextView toolbarText;
    @BindView(R.id.nextButton) Button mNextButton;
    @BindView(R.id.yesRadioButton) RadioButton mYesRadioButton;
    @BindView(R.id.noRadioButton) RadioButton mNoRadioButton;
    @BindView(R.id.thirdQRadioButton) RadioGroup mThirdQRadioButton;
    @BindView(R.id.stylistAvailableTextView) TextView mStylistAvailableTextView;
    @BindView(R.id.stylistsRecyclerView) RecyclerView mstylistsRecyclerView;
    @BindView(R.id.onPremiseRelativeLayout) RelativeLayout mOnPremiseRelativeLayout;
    @BindView(R.id.onSalonRelativeLayout) RelativeLayout mOnSalonRelativeLayout;
    Context context = this;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.FRENCH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_services);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mSavedBookReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_EMPLOYEES);

        setUpFirebaseAdapter();


        mOnSalonRelativeLayout.setVisibility(View.GONE);
        mOnPremiseRelativeLayout.setVisibility(View.GONE);

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText("dd.MM.yyyy");

        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        mThirdQRadioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (mYesRadioButton.isChecked() == true) {
                    mOnPremiseRelativeLayout.setVisibility(View.VISIBLE);
                    mOnSalonRelativeLayout.setVisibility(View.GONE);
                } else if (mNoRadioButton.isChecked() == true){
                    mOnSalonRelativeLayout.setVisibility(View.VISIBLE);
                    mOnPremiseRelativeLayout.setVisibility(View.GONE);
                }
                else{
                    mOnPremiseRelativeLayout.setVisibility(View.GONE);
                    mOnSalonRelativeLayout.setVisibility(View.GONE);
                }

            }
        });

        editDate.setHint("dd.MM.yyyy");
        mNextButton.setOnClickListener(this);
        editDate.setOnClickListener(this);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
    public void onClick(View view){
        if (view == editDate){
            new DatePickerDialog(context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if (view == mNextButton){
            Intent intent =  new Intent(HairServicesActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
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
        Intent intent = new Intent(HairServicesActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
