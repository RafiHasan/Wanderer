package com.example.wonderer.wonderer.loginregister;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


   private CallbackManager mCallbackManager;


    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email,password;
    Button loginButton;
    EditText emailfield,passwordfield;

    public  static int indicator=0;
    public static DatabaseReference makeprofile;
    public static DatabaseReference maketour;
    public static DatabaseReference plantour;
    public static DatabaseReference home;
    public static DatabaseReference root;

    public static String userid;

    public static Profile userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mCallbackManager = CallbackManager.Factory.create();

        LoginButton log=(LoginButton)findViewById(R.id.login_button);
        log.setReadPermissions("email", "public_profile" ,"user_friends" );
        log.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(loginResult.getAccessToken(),
                        //AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    JSONArray friendslist;
                                    ArrayList<String> friends = new ArrayList<String>();


                                    Toast.makeText(getApplicationContext(),rawName.getJSONObject(0).getString("id").toString(),Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error", "facebook:onError", error);

            }
        });


        emailfield = (EditText)findViewById(R.id.input_email);
        passwordfield=(EditText)findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailfield.getText().toString();
                password = passwordfield.getText().toString();

                signIn(email,password);
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("nihal997", "onAuthStateChanged:signed_in:" + user.getUid());
                    userid=user.getUid();
                    makeprofile= FirebaseDatabase.getInstance().getReference("Newuser");
                    maketour= FirebaseDatabase.getInstance().getReference("Newtrip");
                    plantour=FirebaseDatabase.getInstance().getReference("Plantrip");
                    home= FirebaseDatabase.getInstance().getReference("Newhome");
                    root=FirebaseDatabase.getInstance().getReference();
                    makeprofile.child(userid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                                userprofile=dataSnapshot.getValue(Profile.class);
                             }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(indicator>0)
                    { indicator=0;
                        finish();}
                    else
                    {Intent i=new Intent(getApplicationContext(),TravelPlaceList.class);
                    startActivity(i);
                        finish();
                    }

                } else {

                }

            }
        };




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("nihal997", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("nihal997", "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login.this, "login failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id="";
                            final FirebaseUser user = mAuth.getCurrentUser();

                            GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(token,
                                    //AccessToken.getCurrentAccessToken(),
                                    "/me",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            try {
                                                JSONObject rawName = response.getJSONObject();

                                                String s="";
                                                if(!user.getEmail().equals(null))
                                                    s=user.getEmail();
                                                Profile myuser=new Profile(user.getDisplayName().toString(),user.getPhotoUrl().toString(),s, "nothing",rawName.getString("id").toString());
                                                makeprofile.child(user.getUid()).setValue(myuser);

                                                Toast.makeText(getApplicationContext(),rawName.getString("id").toString(),Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                            ).executeAsync();


                            Toast.makeText(getApplicationContext(),user.getDisplayName(),Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


}
