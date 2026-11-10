
package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // 1. N3arfou les composants mta3 l'interface
    private LinearLayout layoutResultat;
    private TextView tvMessage;
    private Button btnNouveauVoyageur;

    // 2. Nesta3mlou nafs el matricule fil 3 Activities
    private static final String MATRICULE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 3. Naffichiw layout mta3 resultat
        setContentView(R.layout.activity_result);

        // 4. Nrabtou les composants bel XML
        initialiserVues();

        // 5. Nesta9blou les données men VerificationActivity
        afficherResultat();

        // 6. Nربطou bouton nouveau voyageur
        btnNouveauVoyageur.setOnClickListener(v -> retournerAccueil());
    }

    // 7. N3aynou les composants men XML
    private void initialiserVues() {

        layoutResultat = findViewById(R.id.layout_resultat_hs);
        tvMessage = findViewById(R.id.tv_message_resultat_hs);
        btnNouveauVoyageur = findViewById(R.id.btn_nouveau_voyageur_hs);
    }

    // 8. N9raw verdict w esm el voyageur
    private void afficherResultat() {

        Intent intent = getIntent();

        String nom = intent.getStringExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR")
        );

        boolean verdict = intent.getBooleanExtra(
                IntentKeys.getKey(MATRICULE, "VERDICT"),
                false
        );

        // 9. N7ottou esm par défaut ken ma fama ch esm
        if (nom == null || nom.trim().isEmpty()) {
            nom = "Voyageur";
        }

        // 10. Naffichiw resultat 7asb el verdict
        if (verdict) {

            // 10.1 El voyage accepté: fond vert
            layoutResultat.setBackgroundColor(
                    Color.parseColor("#263B35")
            );

            tvMessage.setText("Bon voyage " + nom);

        } else {

            // 10.2 El voyage refusé: fond rouge
            layoutResultat.setBackgroundColor(
                    Color.parseColor("#713F43")
            );

            tvMessage.setText(
                    nom + " est en état d'arrestation temporelle"
            );
        }
    }

    // 11. Nرج3ou lel MainActivity w nsakrou les anciennes Activities
    private void retournerAccueil() {

        Intent retour = new Intent(
                ResultActivity.this,
                MainActivity.class
        );

        retour.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        startActivity(retour);
        finish();
    }
}
