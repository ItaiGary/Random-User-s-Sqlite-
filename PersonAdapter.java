package com.example.mysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysql.models.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    ArrayList<Person> people;
    private Context context;
    private int layout;

    public PersonAdapter(Context context, int layout, List<Person> people) {
        super(context, layout, people);
        this.context = context;
        this.layout = layout;
        this.people = (ArrayList<Person>) people;
    }

    @Override
    public View getView(int i, View _view, ViewGroup parent) {

        View view = _view;

        if (view == null){
            view = LayoutInflater.from(context).inflate(layout,parent,false);
        }

        TextView name = view.findViewById(R.id.main_TV_name);
        TextView gender = view.findViewById(R.id.main_Tv_gender);
        TextView street = view.findViewById(R.id.main_TV_street);
        TextView state = view.findViewById(R.id.main_TV_state);
        TextView postCode = view.findViewById(R.id.main_TV_postcode);

        Person person = people.get(i);

        name.setText(person.getUser_name());
        gender.setText(person.getUser_gender());
        street.setText(person.getUser_street());
        state.setText(person.getUser_state());
        postCode.setText(person.getUser_postcode());


        return view;


    }
}
