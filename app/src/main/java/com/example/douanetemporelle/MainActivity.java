
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

    // 1. N3arfou les composants mta3 l'interface
    private EditText etNom;
    private EditText etNaissance;
    private EditText etDestination;
    private Spinner spMotif;
    private Button btnInspecter;

    // 2. N7ottou les 4 derniers chiffres mta3 carte etudiant
    // 1234 exemple bark, lazm yetbaddel bel matricule s7i7
    private static final String MATRICULE = "1234";

    // 3. N7adhrou les motifs w nzidou choix initial bech yodhher kima placeholder
    private static final String[] MOTIFS = {
            "Sélectionner un motif",
            "Tourisme",
            "Modification du passé",
            "Fuite"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 4. Naffichiw l'interface principale
        setContentView(R.layout.activity_main);

        // 5. Nrabtou les composants bel XML
        initialiserVues();

        // 6. Nconfiguriw el Spinner
        configurerSpinner();

        // 7. Nrabtou el bouton bel traitement
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    // 8. N3aynou les composants men XML
    private void initialiserVues() {

        etNom = findViewById(R.id.et_nom_hs);
        etNaissance = findViewById(R.id.et_naissance_hs);
        etDestination = findViewById(R.id.et_destination_hs);
        spMotif = findViewById(R.id.sp_motif_hs);
        btnInspecter = findViewById(R.id.btn_inspecter_hs);
    }

    // 9. Nconfiguriw liste mta3 les motifs
    private void configurerSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                MOTIFS
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spMotif.setAdapter(adapter);

        // 10. N5alliou el choix initial howa placeholder
        spMotif.setSelection(0);
    }

    // 11. Nverifiw les données w naba3thouhom lel activité thenya
    private void inspecterProfil() {

        // 12. Na9raw les valeurs elli dkhalhom el voyageur
        String nom = etNom.getText().toString().trim();
        String naissanceTexte = etNaissance.getText().toString().trim();
        String destinationTexte = etDestination.getText().toString().trim();

        // 13. Nverifiw esm el voyageur
        if (nom.isEmpty()) {
            etNom.setError("Veuillez saisir votre nom");
            etNom.requestFocus();
            return;
        }

        // 14. Nverifiw année de naissance
        if (naissanceTexte.isEmpty()) {
            etNaissance.setError("Veuillez saisir votre année de naissance");
            etNaissance.requestFocus();
            return;
        }

        // 15. Nverifiw année de destination
        if (destinationTexte.isEmpty()) {
            etDestination.setError("Veuillez saisir votre année de destination");
            etDestination.requestFocus();
            return;
        }

        int anneeNaissance;
        int anneeDestination;

        // 16. N7awlou les années l'entiers
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

        // 17. Nverifiw elli les années positifs
        if (anneeNaissance <= 0) {
            etNaissance.setError("L'année doit être positive");
            etNaissance.requestFocus();
            return;
        }

        if (anneeDestination <= 0) {
            etDestination.setError("L'année doit être positive");
            etDestination.requestFocus();
            return;
        }

        // 18. Nverifiw elli destination ma tkounch 9bal naissance
        if (anneeDestination < anneeNaissance) {
            etDestination.setError(
                    "L'année de destination doit être supérieure ou égale à l'année de naissance"
            );
            etDestination.requestFocus();
            return;
        }

        // 19. Na5dh el motif elli ekhtarah el voyageur
        Object motifSelectionne = spMotif.getSelectedItem();

        // 20. Nverifiw elli el voyageur ekhtar motif w moch placeholder
        if (motifSelectionne == null || spMotif.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Veuillez sélectionner un motif de voyage",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String motif = motifSelectionne.toString();

        // 21. Nverifiw elli el motif mel choix elli talabnehom
        boolean motifValide = false;

        for (int i = 1; i < MOTIFS.length; i++) {
            if (MOTIFS[i].equals(motif)) {
                motifValide = true;
                break;
            }
        }

        if (!motifValide) {
            Toast.makeText(
                    this,
                    "Veuillez sélectionner un motif valide",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // 22. N7adhrou Intent explicite lel VerificationActivity
        Intent intent = new Intent(
                MainActivity.this,
                VerificationActivity.class
        );

        // 23. Naba3thou esm el voyageur bel cle dynamique
        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "NOM_VOYAGEUR"),
                nom
        );

        // 24. Naba3thou année de naissance
        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_NAISSANCE"),
                anneeNaissance
        );

        // 25. Naba3thou année de destination
        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "ANNEE_DESTINATION"),
                anneeDestination
        );

        // 26. Naba3thou motif de voyage
        intent.putExtra(
                IntentKeys.getKey(MATRICULE, "MOTIF"),
                motif
        );

        // 27. N7ellou activité de verification
        startActivity(intent);
    }
}
