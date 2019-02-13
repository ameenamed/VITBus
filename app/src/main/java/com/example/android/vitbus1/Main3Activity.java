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
import android.app.Activity;
import com.example.android.vitbus1.GoogleMapActivity;
import com.example.android.vitbus1.MainActivity;
import com.example.android.vitbus1.MyDBHandler;
import com.example.android.vitbus1.R;
import com.example.android.vitbus1.Temp;
import com.example.android.vitbus1.User;
import com.firebase.client.Firebase;

public class Main3Activity extends AppCompatActivity {
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Firebase.setAndroidContext(this);
        myDBHandler=new MyDBHandler(getApplicationContext(),"user",null,1);
        Temp.setMyDBHandler(myDBHandler);
        myDBHandler=Temp.getMyDBHandler();
    }
    public void getback(View view)
    {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void map1(View view)
    {
        int f=0;

        EditText reg = (EditText) findViewById(R.id.reg);
        EditText email =(EditText) findViewById(R.id.email);

        EditText pass = (EditText) findViewById(R.id.pass);
        EditText name = (EditText) findViewById(R.id.name);
        EditText bno = (EditText) findViewById(R.id.bno);
        RadioGroup rb = (RadioGroup) findViewById(R.id.rg);

        String user_name=name.getText().toString();
        String user_id=reg.getText().toString();
        String user_pass=pass.getText().toString();
        String user_bus=bno.getText().toString();
        String user_email=email.getText().toString();


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
            User user=new User();
            user.setName1(user_name);
            user.setRegNo(user_id);
            user.setBusNo(user_bus);
            user.setEmail1(user_email);
            user.setPassword(user_pass);
            int i=myDBHandler.insertUser(user);
            if(i==0)
            {
                Toast.makeText(this,"Error Occurred",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,GoogleMapActivity.class);
                startActivity(intent);

            }
        }
    }
}