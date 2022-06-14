package com.example.drawingapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    // MAKING VARIABLES TO CALL FOR LATER
    int defaultColor;
    SignatureView signatureView;
    ImageButton imgEraser, imgColor, imgSave;
    SeekBar seekBar;
    TextView txtPenSize;


    private static String fileName;
    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPaintings" );

    //  File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPaintings" );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TOOLBAR STUFF
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DISABLES DARK MODE FOR APP (NO BLACK BOXES)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // ASSIGNING VARIABLES TO THE COMPONENTS ON XML LAYOUT
        signatureView = findViewById(R.id.signature_view);
        seekBar = findViewById(R.id.penSize);
        txtPenSize = findViewById(R.id.txtPenSize);
        imgColor = findViewById(R.id.btnColor);
        imgEraser = findViewById(R.id.btnEraser);
        imgSave = findViewById(R.id.btnSave);

        // ASKS PERMISSION FOR STORAGE (AT THE START)
        askPermission();


        // *************** FILE AND FORMAT STUFF ***************

        // DATE AND TIME FOR WHEN IMAGE IS SAVED
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());
        fileName = path + "/" + date + ".png";

        // IF THE PATH EXISTS:
        if (!path.exists())
        {
            path.mkdir();               // If it doesn't exist (path), it'll create a file
        }                               // It relates to the "File path" section where we named our folder "myPaintings"

        // SETS THE DEFAULT COLOR
        defaultColor = ContextCompat.getColor(MainActivity.this, R.color.black);

        // IMAGE ON CLICK LISTENER (IF STATEMENT CONVERTED)
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IF IMAGE IS EMPTY, IMAGE WONT SAVE
                if (!signatureView.isBitmapEmpty()) {
                    try {
                        saveImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Couldn't save Drawing!", Toast.LENGTH_SHORT).show();
                    }
                } 
            }
        });

        // ON CLICKING IMG COLOR BUTTON, IT OPENS THE COLOR PICKER
        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColourPicker();
            }
        });

        // CLEARS CANVAS ON ERASER CLICK
        imgEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // OnClick as you're pressing a button
                signatureView.clearCanvas();        // Clears canvas when you press image button
            }
        });

        // PEN SIZE ON SEEKBAR
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { // On progress as Seekbar isn't a button
                txtPenSize.setText(i + "dp");                                  // So the pen is 1dp by default
                signatureView.setPenSize(i);                                   // Making a variable for setTxt
                seekBar.setMax(150);                                           // Maximum pen size on seekbar
            }

            // SCALING OF SEEKBAR (IT TRACKS THE SIZE INCREASE AND DECREASE)
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {     // Starts to track the increase

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {      // Stops tracking the increase

            }
        });

    } // END OF ONCREATE STUFF


    // **************************************************** MENU STUFF ****************************************************

    // MENU DROPDOWN INFLATION
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   // So the menu opens when its called
        MenuInflater inflater = getMenuInflater();    // Basically shows the menu drop down on clicked
        getMenuInflater().inflate(R.menu.menu,menu);  // Gets the Menu.XML and makes it into a dropdown menu
        return true;                                  // Makes it true
    }


    // On Option Item Selected section
    // OIS is when you select an item....self explanatory in the name
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //Toast.makeText(this, "TESTING", Toast.LENGTH_SHORT).show();
                openSettingsPage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // OPEN SETTING PAGE INTENT
    private void openSettingsPage() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    // SAVE IMAGE CODE
    private void saveImage() throws IOException {
        File file = new File(fileName);                     // Makes a new file name

        Bitmap bitmap = signatureView.getSignatureBitmap(); // As we're using signature view + it tells us to get it as bitmap

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(file);  // Used to write data to our destination (for when we save drawing)
        fos.write(bitmapData);                              // Writes the image (in PNG) format
        fos.flush();                                        // flush() writes the content
        fos.close();                                        // Close() closes the writing of the file content
        Toast.makeText(this, "Drawing Saved!", Toast.LENGTH_SHORT).show();
    }

    // COLOR PICKER (AmbilWarnaDialog) retrives the color picker
    private void openColourPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() { //Vars
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // Nothing as onCancel is when the user cancels the colour picker
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // OnOK is when you click "OK" on the colour picker aka it saves the colour you pick
                defaultColor = color;
                signatureView.setPenColor(color);
            }
        });
        ambilWarnaDialog.show();
    }


    // DEXTER LIBRARY: it simplifies the permission and asks you for permission before using the app
    // DEXTER LIBRARY LINK: https://github.com/Karumi/Dexter
    private void askPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,         // This is when we gave permission on manifest
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {  // The MultiplePermissionsReport contains all the details of the permission
                        Toast.makeText(MainActivity.this, "Granted!", Toast.LENGTH_SHORT).show(); // When permission is granted

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}



