
package com.example.douanetemporelle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // 1. N3arfou les composants mta3 l'interface
    private TextView tvNom;
    private TextView tvAge;
    private TextView tvMotif;
    private TextView tvAjouterPhoto;
    private ImageView imgPhoto;
    private Button btnApprouver;
    private Button btnRefuser;

    // 2. Nesta3mlou nafs el matricule fil 3 Activities
    private static final String MATRICULE = "1234";

    private String nomVoyageur;
    private int ageTemporel;
    private String motifVoyage;

    // 3. N7adhrou el outil eli y5alli el utilisateur ye5tar photo men galerie
    private ActivityResultLauncher<String> selecteurPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 4. Nrabtou les composants Java m3a XML
        tvNom = findViewById(R.id.tv_nom_verification_hs);
        tvAge = findViewById(R.id.tv_age_verification_hs);
        tvMotif = findViewById(R.id.tv_motif_verification_hs);

        imgPhoto = findViewById(R.id.img_photo_hs);
        tvAjouterPhoto = findViewById(R.id.tv_ajouter_photo_hs);

        btnApprouver = findViewById(R.id.btn_approuver_hs);
        btnRefuser = findViewById(R.id.btn_refuser_hs);

        // 5. Nconfiguriw choix photo comme fonctionnalite supplementaire optionnelle
        selecteurPhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imgPhoto.setImageURI(uri);
                        tvAjouterPhoto.setText("Photo ajoutée");
                    }
                }
        );

        // 6. Ki yenzel el utilisateur 3al zone photo, n7ellou galerie
        imgPhoto.setOnClickListener(v -> selecteurPhoto.launch("image/*"));
        tvAjouterPhoto.setOnClickListener(v -> selecteurPhoto.launch("image/*"));

        // 7. Nesta9blou les informations men MainActivity
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

        // 8. N7asbou l'age temporel
        ageTemporel = anneeDestination - anneeNaissance;

        // 9. Naffichiw les informations fil écran
        tvNom.setText("Nom : " + nomVoyageur);
        tvAge.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotif.setText("Motif : " + motifVoyage);

        // 10. Nverifiw condition mta3 nombre premier
        boolean modificationPasse =
                "Modification du passé".equals(motifVoyage);

        boolean differencePremiere = estPremier(ageTemporel);

        // 11. Ken les deux conditions s7a7, n3atلو bouton Approuver
        if (modificationPasse && differencePremiere) {
            btnApprouver.setEnabled(false);
        }

        // 12. Ki nenzel 3al Approuver, naba3thou verdict true
        btnApprouver.setOnClickListener(v -> envoyerVerdict(true));

        // 13. Ki nenzel 3al Refuser, naba3thou verdict false
        btnRefuser.setOnClickListener(v -> envoyerVerdict(false));
    }

    // 14. Méthode bech nverifiw ken nombre premier
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

    // 15. Méthode bech naba3thou le verdict lel ResultActivity
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
