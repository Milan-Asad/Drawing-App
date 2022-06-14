package com.example.drawingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    // VARS
    View view;
    Button button;
    ConstraintLayout constraintLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchButton, smoothBtn, performanceBtn, ultradarkmodebtn, errorreportbtn;
    Button SaveChangesBtn;
    TextView performancetxt, antialiasingtxt, ultradarkmodetxt, darkmodetxt, errorreporttxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // THE VAR'S
        ultradarkmodebtn = (Switch) findViewById(R.id.ultradarkmodebtn);
        constraintLayout = findViewById(R.id.c_layout);
        performancetxt = (TextView) findViewById(R.id.performancetxt);
        antialiasingtxt = (TextView) findViewById(R.id.antialiasingtxt);
        darkmodetxt = (TextView) findViewById(R.id.darkmodetxt);
        ultradarkmodetxt = (TextView) findViewById(R.id.ultradarkmodetxt);
        errorreporttxt = (TextView) findViewById(R.id.errorreporttxt);


        switchButton = (Switch) findViewById(R.id.darkmodeBtn);
        constraintLayout = findViewById(R.id.c_layout);


        // DARK MODE BUTTON
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  // On Checked Changed = seeing if its ticked
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) { // Boolean's name is "isChecked"

                if(isChecked == true) { // Double == means true
                    constraintLayout.setBackgroundColor(Color.GRAY);
                    Toast.makeText(Settings.this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
                    performancetxt.setTextColor(Color.parseColor("black"));
                    antialiasingtxt.setTextColor(Color.parseColor("black"));
                    darkmodetxt.setTextColor(Color.parseColor("black"));
                    ultradarkmodetxt.setTextColor(Color.parseColor("black"));
                    errorreporttxt.setTextColor(Color.parseColor("black"));
                } else {
                    constraintLayout.setBackgroundColor(Color.WHITE);
                    Toast.makeText(Settings.this, "Dark mode disabled", Toast.LENGTH_SHORT).show();
                    performancetxt.setTextColor(Color.parseColor("grey"));
                    antialiasingtxt.setTextColor(Color.parseColor("grey"));
                    darkmodetxt.setTextColor(Color.parseColor("grey"));
                    ultradarkmodetxt.setTextColor(Color.parseColor("grey"));
                    errorreporttxt.setTextColor(Color.parseColor("grey"));
                }
            }
        });


        // SMOOTH BUTTON FEATURE
        smoothBtn = (Switch) findViewById(R.id.smoothBtn);

        smoothBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true) {
                    Toast.makeText(Settings.this, "Anti-Aliasing enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Settings.this, "Anti-Aliasing disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // PERFORMANCE BUTTON FEATURE
        performanceBtn = (Switch) findViewById(R.id.performanceBtn);

        performanceBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true) {
                    Toast.makeText(Settings.this, "Performance Mode enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Settings.this, "Performance Mode disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ERROR BUTTON REPORT TOAST
        errorreportbtn = (Switch) findViewById(R.id.errorreportbtn);
        errorreportbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true) {
                    Toast.makeText(Settings.this, "Error and bugs will be reported", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Settings.this, "Error and bugs will not be reported", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // ULTRA DARK MODE CODE
        ultradarkmodebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true) {
                    Toast.makeText(Settings.this, "Ultra dark mode enabled", Toast.LENGTH_SHORT).show();
                    performancetxt.setTextColor(Color.parseColor("white"));
                    antialiasingtxt.setTextColor(Color.parseColor("white"));
                    darkmodetxt.setTextColor(Color.parseColor("white"));
                    ultradarkmodetxt.setTextColor(Color.parseColor("white"));
                    errorreporttxt.setTextColor(Color.parseColor("white"));
                    constraintLayout.setBackgroundColor(Color.BLACK);

                } else {
                    Toast.makeText(Settings.this, "Ultra dark mode disabled", Toast.LENGTH_SHORT).show();
                    constraintLayout.setBackgroundColor(Color.WHITE);
                    performancetxt.setTextColor(Color.parseColor("grey"));
                    antialiasingtxt.setTextColor(Color.parseColor("grey"));
                    darkmodetxt.setTextColor(Color.parseColor("grey"));
                    ultradarkmodetxt.setTextColor(Color.parseColor("grey"));
                    errorreporttxt.setTextColor(Color.parseColor("grey"));
                }
            }
        });


        // SAVE BUTTON TOAST MESSAGE
        SaveChangesBtn = (Button) findViewById(R.id.save_changes_btn);
        SaveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Settings.this, "Changes have been saved", Toast.LENGTH_SHORT).show();
            }
        });



        // ********** SAVE THE STATE PART **********
        // SAVES THE STATE OF BUTTON WHEN YOU LEAVE THE APP


        // BUTTON VARIABLES
        // DARK MODE BUTTON SAVE STUFF (SAVES THE STATE WHEN YOU LEAVE THE APP)
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);  // This starts the save feature

        switchButton.setChecked(sharedPreferences.getBoolean("value", true));
        performanceBtn.setChecked(sharedPreferences.getBoolean("value", true));
        ultradarkmodebtn.setChecked(sharedPreferences.getBoolean("value", true));
        smoothBtn.setChecked(sharedPreferences.getBoolean("value", true));
        errorreportbtn.setChecked(sharedPreferences.getBoolean("value", true));

        // DARK MODE SAVE STATE STUFF
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchButton.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);                      // Boolean are true or false only
                    editor.apply();                                             // You apply the changes
                    switchButton.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);                    // Boolean are true or false only
                    editor.apply();                                            // You apply the changes
                    switchButton.setChecked(false);
                }
            }
        });

        // PERFORMANCE MODE BUTTON SAVE STUFF (SAVES THE STATE WHEN YOU LEAVE THE APP)
        performanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (performanceBtn.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);                      // Boolean are true or false only
                    editor.apply();                                             // You apply the changes
                    performanceBtn.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);                    // Boolean are true or false only
                    editor.apply();                                            // You apply the changes
                    performanceBtn.setChecked(false);
                }
            }
        });

        // ANTI ALIASING MODE BUTTON SAVE STUFF (SAVES THE STATE WHEN YOU LEAVE THE APP)
        smoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (smoothBtn.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);                      // Boolean are true or false only
                    editor.apply();                                             // You apply the changes
                    smoothBtn.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);                    // Boolean are true or false only
                    editor.apply();                                            // You apply the changes
                    smoothBtn.setChecked(false);
                }
            }
        });

        // ULTRA DARK MODE BUTTON SAVE STUFF (SAVES THE STATE WHEN YOU LEAVE THE APP)
        ultradarkmodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultradarkmodebtn.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);                      // Boolean are true or false only
                    editor.apply();                                             // You apply the changes
                    ultradarkmodebtn.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);                    // Boolean are true or false only
                    editor.apply();                                            // You apply the changes
                    ultradarkmodebtn.setChecked(false);
                }
            }
        });


        // ERROR REPORT BUTTON SAVE STUFF (SAVES THE STATE WHEN YOU LEAVE THE APP)
        errorreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errorreportbtn.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);                      // Boolean are true or false only
                    editor.apply();                                             // You apply the changes
                    errorreportbtn.setChecked(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);                    // Boolean are true or false only
                    editor.apply();                                            // You apply the changes
                    errorreportbtn.setChecked(false);
                }
            }
        });
    }
}