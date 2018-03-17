package com.valentishealth.app.valentishealth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://34.227.113.66:9000/account/api-token-auth/";
    public static final String KEY_USERNAME ="userN";
    public static final String KEY_PASSWORD ="password";
    ApiUtils apiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText username,pass;
        Button loginbtn;
        Button Registerbtn;
        final ProgressBar mprogressbar;
        username = findViewById(R.id.edtEmail);
        pass = findViewById(R.id.edtPass);
        loginbtn = findViewById(R.id.btnLogin);
        Registerbtn = findViewById(R.id.btnReg);
        mprogressbar = findViewById(R.id.progressBar);


        final String TAG = "CallBack";


        mprogressbar.setVisibility(View.GONE);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Authenticating if user exists

                String userN = username.getText().toString().trim();
                final String password = pass.getText().toString().trim();

             if(TextUtils.isEmpty(userN)) {

                 Toast.makeText(MainActivity.this, "Enter a Valid Username", Toast.LENGTH_SHORT).show();
             }
             if (TextUtils.isEmpty(password)) {

                 Toast.makeText(MainActivity.this, "Please Enter password", Toast.LENGTH_SHORT).show();
             }

             //Volley Request


                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Success")) {
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            //Now navigate to pin authentication
                            Intent intent = new Intent(MainActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                        else  {
                            Toast.makeText(MainActivity.this, "Login failed, please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "error ", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username",username.getText().toString());
                        params.put("password",pass.getText().toString());
                        Log.d(TAG,"Error response"+super.getParams());
                        return super.getParams();

                    }
                };



            }
        });

    }


    public void btnRecover(View view) {
        Intent intent = new Intent(MainActivity.this, RecoveryActivity.class);
        startActivity(intent);
    }
}
