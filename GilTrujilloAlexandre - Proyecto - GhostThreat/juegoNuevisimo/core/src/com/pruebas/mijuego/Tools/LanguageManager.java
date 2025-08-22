package com.pruebas.mijuego.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.pruebas.mijuego.MiJuego;

import java.util.Locale;

public class LanguageManager {
    /**
     * instancia del LanguageManager
     */
    private static LanguageManager instance;
    /**
     * bundle de los lenguajes
     */
    private I18NBundle bundle;

    /**
     * Constructor del manager
     */
    private LanguageManager() {
        updateBundle();
    }

    /**
     * devuelve una instancia de language manager
     * @return
     */
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    /**
     * actualiza el bundle
     */
    private void updateBundle() {
        String lang = MiJuego.prefs.getString("lang", "es");
        Locale locale = new Locale(lang);
        bundle = I18NBundle.createBundle(Gdx.files.internal("languages/strings"), locale);
    }

    /**
     * Devuelve el texto en su idioma correspondiente a partir de una clave
     * @param key clave necesaria
     * @return valor de la clave
     */
    public String getText(String key) {
        return bundle.get(key);
    }

    /**
     * Cambia el idioma
     * @param language idioma a cambiar
     */
    public void changeLanguage(String language) {
        MiJuego.prefs.putString("lang", language);
        MiJuego.prefs.flush();
        updateBundle();
    }
}
