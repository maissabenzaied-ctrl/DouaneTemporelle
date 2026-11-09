
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // 1. N3arfu les composants eli bech nesta3mlouhom
    private TextView tvNom;
    private TextView tvAge;
    private TextView tvMotif;
    private Button btnApprouver;
    private Button btnRefuser;

    // 2. Nesta9blou les données eli ba3aththom MainActivity
    private static final String MATRICULE = "1234";

    private String nomVoyageur;
    private int ageTemporel;
    private String motifVoyage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 3. Nrabtou les composants Java m3a XML
        tvNom = findViewById(R.id.tv_nom_verification_hs);
        tvAge = findViewById(R.id.tv_age_verification_hs);
        tvMotif = findViewById(R.id.tv_motif_verification_hs);

        btnApprouver = findViewById(R.id.btn_approuver_hs);
        btnRefuser = findViewById(R.id.btn_refuser_hs);

        // 4. Nesta9blou les informations men Intent
        Intent intent = getIntent();

        nomVoyageur = intent.getStringExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR")
        );

        int anneeNaissance = intent.getIntExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_NAISSANCE"),
                0
        );

        int anneeDestination = intent.getIntExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_DESTINATION"),
                0
        );

        motifVoyage = intent.getStringExtra(
                IntentKeys.getKey(MATRICULE, "MOTIF")
        );

        // 5. N7asbou l'age temporel
        ageTemporel = anneeDestination - anneeNaissance;

        // 6. Naffichiw les informations fil écran
        tvNom.setText("Nom : " + nomVoyageur);
        tvAge.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotif.setText("Motif : " + motifVoyage);

        // 7. Nverifiw condition mta3 nombre premier
        boolean modificationPasse =
                "Modification du passé".equals(motifVoyage);

        boolean differencePremiere = estPremier(ageTemporel);

        // 8. Ken les deux conditions sont vraies, n3atلو bouton Approuver
        if (modificationPasse && differencePremiere) {
            btnApprouver.setEnabled(false);
        }

        // 9. Ki nenzel 3al Approuver, naba3thou verdict true
        btnApprouver.setOnClickListener(v -> envoyerVerdict(true));

        // 10. Ki nenzel 3al Refuser, naba3thou verdict false
        btnRefuser.setOnClickListener(v -> envoyerVerdict(false));
    }

    // 11. Méthode bech nverifiw ken nombre premier
    private boolean estPremier(int nombre) {

        if (nombre <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    // 12. Méthode bech naba3thou le verdict lel ResultActivity
    private void envoyerVerdict(boolean verdict) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "VERDICT"),
                verdict
        );

        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR"),
                nomVoyageur
        );

        startActivity(intent);
    }
}
