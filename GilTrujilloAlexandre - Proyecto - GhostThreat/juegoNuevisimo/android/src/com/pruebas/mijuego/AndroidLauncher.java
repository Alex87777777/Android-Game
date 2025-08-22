package com.pruebas.mijuego;

import android.os.Bundle;
import android.os.Vibrator;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

//		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

//		MiJuego miJuego = new MiJuego();

		initialize(new MiJuego(), config);

//		miJuego.setVibrationHandler(this);
	}
//	@Override
//	public void vibrate(long milliseconds) {
//		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//		if (vibrator != null) {
//			vibrator.vibrate(milliseconds);
//		}
//	}
}
