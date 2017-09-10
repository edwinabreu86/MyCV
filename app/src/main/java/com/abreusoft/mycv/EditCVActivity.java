package com.abreusoft.mycv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

public class EditCVActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int RESULT_LOAD_IMG = 1;
    private ImageView photo;
    private EditText fullNameT, emailT, phoneT, addressT, cityT, bachelorT, graduateT, postGraduateT;
    private Spinner provinceS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcv);

        photo = (ImageView) findViewById(R.id.photo);
        fullNameT = (EditText) findViewById(R.id.full_name);
        emailT = (EditText) findViewById(R.id.email);
        phoneT = (EditText) findViewById(R.id.phone);
        addressT = (EditText) findViewById(R.id.address);
        cityT = (EditText) findViewById(R.id.city);
        provinceS = (Spinner) findViewById(R.id.province);
        bachelorT = (EditText) findViewById(R.id.bachelor);
        graduateT = (EditText) findViewById(R.id.graduate);
        postGraduateT = (EditText) findViewById(R.id.post_graduate);
        photo.setOnClickListener(this);
        photo.setOnLongClickListener(this);

    }

    public void saveProfile(View view) {

        String fullname = fullNameT.getText().toString();
        String email = emailT.getText().toString();
        String phone = phoneT.getText().toString();
        String address = addressT.getText().toString();
        String city = cityT.getText().toString();
        String province = provinceS.getSelectedItem().toString();
        String bachelor = bachelorT.getText().toString();
        String graduate = graduateT.getText().toString();
        String postGraduate = postGraduateT.getText().toString();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Deje presionado para cambiar la imagen.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        // This Intent will call an app to view my images and to pick one image.
        Intent photoI = new Intent(Intent.ACTION_PICK);
        photoI.setType("image/*");
        startActivityForResult(photoI, RESULT_LOAD_IMG);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                //The Uri is a reference used to identify the image that I selected.
                final Uri imageUri = data.getData();
                //The Uri will be read to convert the referenced image to a Bitmap.
                final Bitmap photoB = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                photo.setImageBitmap(photoB);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
