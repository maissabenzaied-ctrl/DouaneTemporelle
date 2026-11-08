
package com.example.douanetemporelle;

public final class IntentKeys {

    private IntentKeys() {
        // Utility class
    }

    // 1. Na3mlou methode statique bech nbniw les cles
    public static String getKey(String matricule, String nomCle) {

        // 2. Nna7iw les espaces zeydin
        String safeMatricule = matricule == null ? "" : matricule.trim();
        String safeNomCle = nomCle == null ? "" : nomCle.trim();

        // 3. Nethabtou eli les deux valeurs mahoumech ferghin
        if (safeMatricule.isEmpty() || safeNomCle.isEmpty()) {
            throw new IllegalArgumentException(
                    "Le matricule et le nom de la cle sont obligatoires"
            );
        }

        // 4. Nrakkbou matricule m3a esm el cle
        return safeMatricule + "_" + safeNomCle;
    }
}
