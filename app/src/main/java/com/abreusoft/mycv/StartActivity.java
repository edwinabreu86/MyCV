package com.abreusoft.mycv;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class StartActivity extends AppCompatActivity
        implements DialogInterface.OnClickListener, DialogInterface.OnKeyListener {

    Runnable r;
    Handler h = new Handler();
    AlertDialog.Builder adBuilder;
    AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        adBuilder = new AlertDialog.Builder(this);

        r = new Runnable() {
            @Override
            public void run() {
                if (!checkedConnectionOK()) {
                    createConnectionAlert();
                } else {
                    createLoginDialog();
                }
            }
        };
        h.postDelayed(r, 1500);
    }

    private boolean checkedConnectionOK() {
        showProgressDialog();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return (nInfo!= null && nInfo.isConnectedOrConnecting());
    }

    private void createConnectionAlert() {

        r = new Runnable() {
            @Override
            public void run() {
                (new ProgressDialog(getApplicationContext())).dismiss();
                adBuilder.setTitle("Sin conexión")
                        .setMessage("Asegúrate de tener conexión a internet")
                        .setIcon(R.mipmap.ic_warning);
                adBuilder.setPositiveButton("Cerrar \n aplicación", StartActivity.this)
                        .setNegativeButton("Intentar \n de nuevo", StartActivity.this)
                        .setOnKeyListener(StartActivity.this);
                //.setOnDismissListener(this);
                ad = adBuilder.create();
                ad.show();
            }
        };
        h.postDelayed(r, 1500);
    }

    private void createLoginDialog() {
        showProgressDialog();
        r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        };
        h.postDelayed(r, 1500);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            finish();
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            if(!checkedConnectionOK()){
                ad.dismiss();
                createConnectionAlert();
            } else {
                createLoginDialog();
            }
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return false;
    }

    public void showProgressDialog() {
        ProgressDialog pd = new ProgressDialog(StartActivity.this);
        pd.setMessage("Buscando conexion");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }
}
