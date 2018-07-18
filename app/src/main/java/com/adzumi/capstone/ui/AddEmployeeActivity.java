package com.adzumi.capstone.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.adzumi.capstone.Constants;
import com.adzumi.capstone.R;
import com.adzumi.capstone.models.Employees;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_toolbar) Toolbar myToolbar;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.firstQRadioButton) RadioGroup mFirstQRadioButton;
    @BindView(R.id.phoneEditText) EditText mPhoneEditText;
    @BindView(R.id.addEmployeeButton) Button mAddEmployeeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAddEmployeeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mAddEmployeeButton){
            String name = mNameEditText.getText().toString();
            int selectedId = mFirstQRadioButton.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String dept = radioButton.getText().toString();
            String phone = mPhoneEditText.getText().toString();
            Employees new_employee = new Employees(name, dept, phone);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference mSavedBookReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constants.FIREBASE_CHILD_EMPLOYEES);
            DatabaseReference pushRef = mSavedBookReference.push();
            pushRef.setValue(new_employee);

            Toast.makeText(AddEmployeeActivity.this, "Employee Has Been Added.", Toast.LENGTH_SHORT).show();
        }
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
        Intent intent = new Intent(AddEmployeeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
