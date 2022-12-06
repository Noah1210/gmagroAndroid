package com.npardon.gmagroandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.npardon.gmagroandroid.R;
import com.npardon.gmagroandroid.beans.Intervenant;
import com.npardon.gmagroandroid.daos.DaoIntervenant;
import com.npardon.gmagroandroid.daos.DelegateAsyncTask;

public class Connexion extends Activity {
    private TextView inputLogin, inputPassword;
    public static Intervenant intervenantConnecte = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        Button btnConnexion = findViewById(R.id.bConnexion);
        inputLogin = findViewById(R.id.txLogin);
        inputPassword = findViewById(R.id.txPassword);
        inputLogin.setText("Steve.Joe");
        inputPassword.setText("123");

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickConnexion();
            }
        });
    }

    private void OnClickConnexion() {
        String login = inputLogin.getText().toString();
        String mdp = inputPassword.getText().toString();
        if (login.isEmpty()) {
            showError(inputLogin, "Please enter a login");
        } else if (mdp.isEmpty()) {
            showError(inputPassword, "Please enter a password");
        } else {
            DaoIntervenant.getInstance().getConnexion(login, mdp, new DelegateAsyncTask() {
                @Override
                public void whenWSIsTerminated(Object result) {
                    Intervenant in = (Intervenant) result;
                    if (in != null) {
                        Toast.makeText(getApplicationContext(), "Bonjour " + in.getPrenom(), Toast.LENGTH_SHORT).show();
                        intervenantConnecte = in;
                        Intent intent = new Intent(getApplicationContext(), LesInterventions.class);
                        intent.putExtra("in", in);
                        finish();
                        Connexion.this.startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Vous n'avez pas pu vous connecter", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(TextView input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}