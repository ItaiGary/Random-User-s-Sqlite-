package com.example.mysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysql.models.Person;
import com.example.mysql.models.WebResults;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //
    public static final String BASE_URL = "https://randomuser.me/api/";
    //
    public PersonAdapter personAdapter;
    public ArrayList<Person> personList;
    private Person p1;
    public ListView lv;
    public MySQLiteHelper mySQL;

    String correct_postcode;

    String correct_name;

    String correct_gender;

    String correct_state;

    String correct_street;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(this ,R.layout.one_person_layout,personList);
        lv = findViewById(R.id.list_View);
        lv.setAdapter(personAdapter);

        mySQL = new MySQLiteHelper(this);
        setTitle(MySQLiteHelper.TABLE_NAME);

        Button clear = findViewById(R.id.main_btn_clear);

        Button btn = findViewById(R.id.main_btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPerson();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
                b.setTitle("Delete '" + personList.get(position).getUser_name() + "'");
                b.setMessage("Do you want to delete this user from the list?");
                b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String name = p1.getUser_name();

                        mySQL.DeleteUser(name);

                        personList.remove(position);
                        personAdapter.notifyDataSetChanged();
                        Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "You are in the path of rightness", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alert = b.create();
                alert.show();

                return true;
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());
                b.setTitle("Delete All'");
                b.setMessage("Do you want to delete everyone from the list?");
                b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Collection<Person> collection = personList;

                        personList.removeAll(collection);
                        mySQL.clearAll();
                        personAdapter.notifyDataSetChanged();

                        Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_LONG).show();

                        dialogInterface.dismiss();
                    }
                });
                b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "You are in the path of rightness", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alert = b.create();
                alert.show();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = personAdapter.people.get(position);
                Intent intent = new Intent(getApplication(),InfoActivity.class);

                intent.putExtra("name",person.getUser_name());
                intent.putExtra("gender",person.getUser_gender());
                intent.putExtra("street",person.getUser_street());
                intent.putExtra("state",person.getUser_state());
                intent.putExtra("postcode",person.getUser_postcode());


                String _name = intent.getStringExtra("name2");
                String _gender = intent.getStringExtra("gender2");
                String _street = intent.getStringExtra("street2");
                String _state = intent.getStringExtra("state2");
                String _postcode = intent.getStringExtra("postcode2");

                person.setUser_name(_name);
                person.setUser_gender(_gender);
                person.setUser_street(_street);
                person.setUser_state(_state);
                person.setUser_postcode(_postcode);

                personAdapter.notifyDataSetChanged();
                startActivity(intent);
                intent.getExtras();
            }
        });
    }


        private void getPerson(){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()/*.get() ? */.url(BASE_URL).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.i(TAG, "CALL FAIL : " + e);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String json = response.body().string();

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        WebResults webResults = gson.fromJson(json, WebResults.class);

                        correct_name = webResults.results[0].name.first;
                        correct_gender = webResults.results[0].gender;
                        correct_street = webResults.results[0].location.street.name;
                        correct_state =  webResults.results[0].location.state;
                        correct_postcode = webResults.results[0].location.postcode;

                        p1 = new Person("name: " + correct_name,"is a " + correct_gender,
                                "St." + correct_street, "state of " + correct_state,
                                correct_postcode);
                        personList.add(p1);

                        Log.e(TAG, "onResponse: " + p1 );

                        Log.i(TAG, "JASON MADE IT");
                        /*Type personArrayType = new TypeToken<ArrayList<Person>>() {
                        }.getType();
                        personList = gson.fromJson(json, personArrayType);*/


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                lv.setAdapter(personAdapter);

                                if (mySQL.insertData(p1)) {
                                    Toast.makeText(getBaseContext(), "A new PERSON!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "Usten... We have a problem", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                }
            });
        }
    }









