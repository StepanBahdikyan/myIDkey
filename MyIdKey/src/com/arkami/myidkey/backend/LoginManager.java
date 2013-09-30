/**
 *
 */
package com.arkami.myidkey.backend;

import java.util.Date;

import android.content.Context;

import com.arkami.myidkey.database.datasources.PassPhraseDataSource;
import com.arkami.myidkey.database.tables.PassPhrase;

/**
 * @author sbahdikyan
 */
public class LoginManager {
    /**
     * @param passPhrase
     * @return true if the user is authenticated
     */
    public static boolean login(String passPhrase, Context context) {
        if ((passPhrase != null)
                && (passPhrase.length() > 0)) {
            PassPhraseDataSource passPhraseDataSource = new PassPhraseDataSource(context);
            if (passPhraseDataSource.hasPassPhrase()) {
                String pass = passPhraseDataSource.getPassPhrase();
                if (pass != null) {
                    return (String.valueOf(passPhrase).equals(String.valueOf(pass)));
                }
            }
        }
        return false;
    }

    /**
     * @return true if the user already had stored a pass phrase.
     */

    public static boolean hasPassPhrase(Context context) {
        PassPhraseDataSource passPhraseDataSource = new PassPhraseDataSource(context);
        return passPhraseDataSource.hasPassPhrase();
    }

    /**
     * Saves the pass phrase into the database.
     *
     * @param passPhrase
     * @param repeatPassPhrase
     * @return true if the pass phrase is stored in the database
     */
    public static boolean savePassphrase(String passPhrase, String repeatPassPhrase, Context context) {
        //saving pass phrase in the data base
        if (String.valueOf(passPhrase).equals(String.valueOf(repeatPassPhrase))) {
            //save it
            PassPhraseDataSource passPhraseDataSource = new PassPhraseDataSource(context);
            PassPhrase pass = new PassPhrase();
            pass.setPassPhrase(passPhrase);
            pass.setCreateDate(new Date().getTime());
            pass.setModifyDate(pass.getCreateDate());
            passPhraseDataSource.insert(pass);
            return true;
        }
        return false;
    }
}
