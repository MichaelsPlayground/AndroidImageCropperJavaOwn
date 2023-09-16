package de.androidcrypto.androidimagecropperjavaown;

import android.app.Application;
import android.os.StrictMode;
import timber.log.Timber;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .penaltyLog()
                .build());
    }
}
