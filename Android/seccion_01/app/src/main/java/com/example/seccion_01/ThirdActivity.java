package com.example.seccion_01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamera;

    private final int PHONE_CALL_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Avtivar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imgBtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);

        //Boton para la llamada
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    //comprobar version actual de android
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //comprobar si ha aceptado, no ha aceptado o nunca se le pregunto
                        if(checkPermission(Manifest.permission.CALL_PHONE)){
                            // Ha aceptado
                            Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                            //revisar esto
                            if(ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)return;
                                startActivity(in);
                            } else {
                                //ha rechazado o es la primera vez que se le pregunta
                                if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                    // No se le ha preguntado aun
                                    requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                                } else {
                                    Toast.makeText(ThirdActivity.this, "Please, activa el permiso", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    i.addCategory(Intent.CATEGORY_DEFAULT);
                                    i.setData(Uri.parse("package:"+getPackageName()));
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(i);
                                }
                            }
                        //requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    } else {
                        olderVersions(phoneNumber);
                    }
                }
                else {
                    Toast.makeText(ThirdActivity.this, "Ingrese un numero de tel", Toast.LENGTH_LONG);
                }
            }

            private void olderVersions(String phoneNumber) {

                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "No tienes los permisos", Toast.LENGTH_LONG);
                }

            }
        });
        // boton para la direccion web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                String email = "victorrivarola20@gmail.com";
                if(url != null && !url.isEmpty()) {
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
                    /*otra forma
                    intentWeb.setAction(intentWeb.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://"+url));
                    */

                    Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));

                    //email rapido
                    Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
                    // email completo
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                    //intentMail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail"); //intent explicito
                    //intentMail.setType("plain/text");
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT,"asunto del correo");
                    intentMail.putExtra(Intent.EXTRA_TEXT,"texto body del correo");
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[] {"victorrivarola20@gmail.com", "aaa@gmail.com"});
                    //chosser siempre forza a preguntar con que apliacion abrir
                    startActivity(Intent.createChooser(intentMail, "Elige el cliente de correo"));
                    //otra forma de hacer llamadas sin pedir permisos
                    Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:78678765678"));

                    //startActivity(intentMail);

                }
            }
        });

        imgBtnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intentCamara);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // llega un requestCode porque no sabemos quien pidio el permiso
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    // Comprobar si ha sido aceptado o denegado la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //Concedio el permiso
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                            startActivity(intentCall);
                        } else {
                            //No concedio el permiso
                            Toast.makeText(ThirdActivity.this, "No concediste el permiso", Toast.LENGTH_LONG);
                        }
                    }
                    break;

                    default:
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                        break;
                }

        }

    private boolean checkPermission(String permisssion) {
        int result = this.checkCallingOrSelfPermission(permisssion);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
