package com.softwarica.a4thassignment.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements View.OnClickListener {
    private EditText username, password;
    private Button btnlogin;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initiate(view);
        return view;
    }

    //this is a method that will initiate in oncreateview
    public void initiate(View view) {
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btnlogin = view.findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(this);
    }
//this method will validate Login Page
    public boolean checkLogin(){
        if(username.getText().toString().isEmpty()){
            username.setError("Please Typer your Username ");
            username.requestFocus();
            return false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Please Typer your Username ");
            password.requestFocus();
            return false;
        }
        return true;
    }
//this method is called when Login button is clicked
    public void UserLogin() {
        Url url = new Url();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        Call<String> userCall = url.createInstanceofRetrofit().checkUser(user, pass);
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),response.body()
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(response.body().equals("Successfully")){
                        Intent intentdash=new Intent(getActivity(),Dashboard.class);
                        startActivity(intentdash);
                        getActivity().finish();
                    }
                    else{
                        Toast.makeText(getActivity(),"Either username or password is incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(),"Cannot Login",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
           if(checkLogin()==true){
               UserLogin();

           }
        }
    }
}
