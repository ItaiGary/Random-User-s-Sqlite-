package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mysql.models.Person;

public class InfoActivity extends AppCompatActivity {

    public MySQLiteHelper mySQLiteHelper;
    Context _context;
    PersonAdapter personAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        this.mySQLiteHelper = new MySQLiteHelper(this);

        final Intent intent = getIntent();

        final String _name = intent.getStringExtra("name");
        String _gender = intent.getStringExtra("gender");
        String _street = intent.getStringExtra("street");
        String _state = intent.getStringExtra("state");
        String _postcode = intent.getStringExtra("postcode");

        final TextView name = findViewById(R.id.info_label_name);
        final TextView gender = findViewById(R.id.info_label_gender);
        final TextView street = findViewById(R.id.info_label_street);
        final TextView state = findViewById(R.id.info_label_state);
        final TextView postcode = findViewById(R.id.info_label_postcode);

        final EditText ed_Name = findViewById(R.id.info_name);
        final EditText ed_Gender = findViewById(R.id.info_gender);
        final EditText ed_Street = findViewById(R.id.info_street);
        final EditText ed_State = findViewById(R.id.info_state);
        final EditText ed_PostCode = findViewById(R.id.info_postcode);

        name.setText(_name);
        gender.setText(_gender);
        street.setText(_street);
        state.setText(_state);
        postcode.setText(_postcode);

        Button update = findViewById(R.id.info_btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(ed_Name.getText());
                gender.setText(ed_Gender.getText());
                street.setText(ed_Street.getText());
                state.setText(ed_State.getText());
                postcode.setText(ed_PostCode.getText());

                intent.getExtras();
                Person person = new Person(name.toString(),gender.toString(),street.toString(),state.toString(),postcode.toString());




                intent.putExtra("name2",_name);
                intent.putExtra("gender2",person.getUser_gender());
                intent.putExtra("street2",person.getUser_street());
                intent.putExtra("state2",person.getUser_state());
                intent.putExtra("postcode2",person.getUser_postcode());

                mySQLiteHelper.updateUser(person);
            }
        });
    }
}
