
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. N3arfu les composants eli 3anna fil interface
    private EditText etNom;
    private EditText etNaissance;
    private EditText etDestination;
    private Spinner spMotif;
    private Button btnInspecter;

    // 2. N7ottou matricule provisoire
    private static final String MATRICULE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3. Nrabtou les composants Java m3a les IDs mta3 XML
        etNom = findViewById(R.id.et_nom_hs);
        etNaissance = findViewById(R.id.et_naissance_hs);
        etDestination = findViewById(R.id.et_destination_hs);
        spMotif = findViewById(R.id.sp_motif_hs);
        btnInspecter = findViewById(R.id.btn_inspecter_hs);

        // 4. N7adhrou les choix mta3 motif de voyage
        String[] motifs = {
                "Tourisme",
                "Modification du passé",
                "Fuite"
        };

        // 5. N3amlou Adapter bech n'affichiw les choix fil Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                motifs
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spMotif.setAdapter(adapter);

        // 6. Ki l'utilisateur yenzel 3al bouton, n3aytou lel methode
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    private void inspecterProfil() {

        // 7. Na9raw les informations eli d5alhom l'utilisateur
        String nom = etNom.getText().toString().trim();
        String naissanceTexte = etNaissance.getText().toString().trim();
        String destinationTexte = etDestination.getText().toString().trim();
        String motif = spMotif.getSelectedItem().toString();

        // 8. Nverifiw ken fama champ feragh
        if (nom.isEmpty() || naissanceTexte.isEmpty()
                || destinationTexte.isEmpty()) {

            Toast.makeText(
                    this,
                    "Veuillez remplir tous les champs",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int anneeNaissance;
        int anneeDestination;

        // 9. N7awlou les années men String l'int
        try {
            anneeNaissance = Integer.parseInt(naissanceTexte);
            anneeDestination = Integer.parseInt(destinationTexte);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Veuillez saisir des années valides",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // 10. Nverifiw eli les années sont positives
        if (anneeNaissance <= 0 || anneeDestination <= 0) {

            Toast.makeText(
                    this,
                    "Les années doivent être positives",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // 11. N7adhrou Intent explicite bech nemchiw lel Activity thenya
        Intent intent = new Intent(
                MainActivity.this,
                VerificationActivity.class
        );

        // 12. Naba3thou les données lel VerificationActivity
        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR"),
                nom
        );

        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_NAISSANCE"),
                anneeNaissance
        );

        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_DESTINATION"),
                anneeDestination
        );

        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "MOTIF"),
                motif
        );

        // 13. N7ellou l'écran theni
        startActivity(intent);
    }
}
