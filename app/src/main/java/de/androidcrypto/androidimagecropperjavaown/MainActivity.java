package de.androidcrypto.androidimagecropperjavaown;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import de.androidcrypto.androidimagecropperjavaown.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sampleCropImageView.setOnClickListener(view -> showFragment(new SampleUsingImageViewFragment()));
        binding.sampleCustomActivity.setOnClickListener(view -> SampleCustomActivity.start(this));
        binding.sampleCropImage.setOnClickListener(view -> showFragment(new SampleCropFragment()));

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
@Override
public void handleOnBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(binding.container.getId());
        setEnabled(fragment != null);

        if (fragment != null) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        } else {
        // todo onBackPressedDispatcher.onBackPressed();
        }

        setEnabled(true);
        }
        };

        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
        }

private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
        .beginTransaction()
        .replace(binding.container.getId(), fragment)
        .commit();
        }
        }