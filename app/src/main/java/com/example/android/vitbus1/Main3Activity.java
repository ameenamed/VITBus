package com.example.android.vitbus1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.Application;

import com.firebase.client.Firebase;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Firebase.setAndroidContext(this);
    }
    public void getback(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void map(View view)
    {
        int f=0;

        EditText reg = (EditText) findViewById(R.id.reg);
        EditText email =(EditText) findViewById(R.id.email);

        EditText pass = (EditText) findViewById(R.id.pass);
        EditText name = (EditText) findViewById(R.id.name);
        EditText bno = (EditText) findViewById(R.id.bno);
        RadioGroup rb = (RadioGroup) findViewById(R.id.rg);
        if(TextUtils.isEmpty(name.getText()))
        {
            f=1;
            name.setError("Name required");
            Toast.makeText(getApplicationContext(),"Name required",Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(reg.getText()))
        {
            f=1;
            reg.setError("Register No. required");
            Toast.makeText(getApplicationContext(),"Reg No. required",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pass.getText()))
        {
            f=1;
            pass.setError("Password required");
            Toast.makeText(getApplicationContext(),"Password required",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(bno.getText()))
        {
            f=1;
            bno.setError("Bus No. required");
            Toast.makeText(getApplicationContext(),"Bus No. required",Toast.LENGTH_SHORT).show();
        }
        if(rb.getCheckedRadioButtonId()==-1)
        {
            f=1;
            Toast.makeText(getApplicationContext(),"Please select Faculty/Student",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(email.getText()))
        {
            f=1;
            Toast.makeText(getApplicationContext(),"Email required",Toast.LENGTH_SHORT).show();
        }

        if(f==0)
        {

            Intent intent=new Intent(this,GoogleMapActivity.class);





        startActivity(intent);

        }
    }
}
