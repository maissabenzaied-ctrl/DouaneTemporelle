package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // 1. N3arfu les composants mta3 l'interface
    private LinearLayout layoutResultat;
    private TextView tvMessage;
    private Button btnNouveauVoyageur;

    // 2. Nesta3mlou nafs matricule provisoire
    private static final String MATRICULE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 3. Nrabtou les composants Java m3a XML
        layoutResultat = findViewById(R.id.layout_resultat_hs);
        tvMessage = findViewById(R.id.tv_message_resultat_hs);
        btnNouveauVoyageur = findViewById(R.id.btn_nouveau_voyageur_hs);

        // 4. Nesta9blou le nom w le verdict men VerificationActivity
        Intent intent = getIntent();

        String nom = intent.getStringExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR")
        );

        boolean verdict = intent.getBooleanExtra(
                IntentKeys.getKey(MATRICULE, "VERDICT"),
                false
        );

        // 5. Nverifiw el nom bech ma yodhhorch null
        if (nom == null || nom.trim().isEmpty()) {
            nom = "Voyageur";
        }

        // 6. Naffichiw message w nbaddlou couleur selon verdict
        if (verdict) {

            // El voyage accepté : Deep Forest
            layoutResultat.setBackgroundColor(Color.parseColor("#263B35"));
            tvMessage.setText("Bon voyage " + nom);

        } else {

            // El voyage refusé : Burgundy
            layoutResultat.setBackgroundColor(Color.parseColor("#713F43"));
            tvMessage.setText(
                    nom + " est en état d'arrestation temporelle"
            );
        }

        // 7. Ki nenzel 3al bouton, narj3ou lel MainActivity
        btnNouveauVoyageur.setOnClickListener(v -> {

            Intent retour = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            // 8. Nsakkrou les Activities eli 9bal
            retour.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(retour);
            finish();
        });
    }
}
// TEST SYNCHRONISATION
