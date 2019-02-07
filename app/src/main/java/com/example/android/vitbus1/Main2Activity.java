package com.example.android.vitbus1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Application;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Main2Activity extends AppCompatActivity {

    SignInButton signInButton;
    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Firebase.setAndroidContext(this);

        signInButton = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener((view)->{signIn();});
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);
            Intent intent=new Intent(this,GoogleMapActivity.class);
            startActivity(intent);
        } catch (ApiException e)
        {
            Log.w("Google Sign In in Error","signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(Main2Activity.this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }




    public void map(View view)
    {
        EditText reg = (EditText) findViewById(R.id.reg);
        EditText pass = (EditText) findViewById(R.id.pass);
        if(TextUtils.isEmpty(reg.getText()) || TextUtils.isEmpty(pass.getText()))
        {
            //reg.setError("Register No. required /  Password required" );
            Toast.makeText(getApplicationContext(),"Reg No./ Password required",Toast.LENGTH_SHORT).show();
        }
        else
        {Intent intent=new Intent(this,GoogleMapActivity.class);
        startActivity(intent);}
    }
    public void getback(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
