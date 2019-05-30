package com.softwarica.a4thassignment.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.Model.User;
import com.softwarica.a4thassignment.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment implements View.OnClickListener {
    private EditText fname, lname, username, password;
    private Button btnregister;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initiate(view);

        return view;
    }
//initiating edittext and buttons
    public void initiate(View view) {
        fname = view.findViewById(R.id.firstname);
        lname = view.findViewById(R.id.lastname);
        username = view.findViewById(R.id.txtusername);
        password = view.findViewById(R.id.pwpassword);
        btnregister = view.findViewById(R.id.btnregister);

        btnregister.setOnClickListener(this);

    }
//this method will validate user while registering
    public boolean validateuser(){
        if(fname.getText().toString().isEmpty()){
            fname.setError("Enter your first name");
            fname.requestFocus();
            return false;
        }
        if(lname.getText().toString().isEmpty()){
            lname.setError("Enter your  last name");
            lname.requestFocus();
            return false;
        }
        if(username.getText().toString().isEmpty()){
            username.setError("Enter your  last name");
            username.requestFocus();
            return false;
        }
        if(password.getText().toString().isEmpty()||password.getText().toString().length()<6){
            password.setError("Enter your password with digit more than 6");
            password.requestFocus();
            return false;
        }
        return true;
    }
//this method will register users when clicked at register
    public void Register() {
        Url url = new Url();
        User user = new User(fname.getText().toString(), lname.getText().toString(), username.getText().toString(), password.getText().toString());
        Call<Void> registerUser = url.createInstanceofRetrofit().addNewUser(user);
        registerUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "User Registered successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregister:
                if(validateuser()){
                    Register();
                }else{
                    Toast.makeText(getActivity(),"Cannot register user",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
