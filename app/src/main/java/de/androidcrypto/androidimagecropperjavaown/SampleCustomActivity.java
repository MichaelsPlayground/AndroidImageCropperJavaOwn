package de.androidcrypto.androidimagecropperjavaown;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import androidx.core.app.ActivityCompat;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import com.canhub.cropper.CropImageView;
import de.androidcrypto.androidimagecropperjavaown.databinding.ExtendedActivityBinding;
import timber.log.Timber;

public class SampleCustomActivity extends CropImageActivity {

    private ExtendedActivityBinding binding;

    public static void start(Activity activity) {
        ActivityCompat.startActivity(
                activity,
                new Intent(activity, SampleCustomActivity.class),
                null
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        binding = ExtendedActivityBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        binding.saveBtn.setOnClickListener(v -> cropImage());
        // todo binding.backBtn.setOnClickListener(v -> onBackPressedDispatcher.onBackPressed());
        binding.rotateText.setOnClickListener(v -> onRotateClick());

        binding.cropImageView.setOnCropWindowChangedListener(() -> updateExpectedImageSize());

        setCropImageView(binding.cropImageView);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        super.onSetImageUriComplete(view, uri, error);

        updateRotationCounter();
        updateExpectedImageSize();
    }

    private void updateExpectedImageSize() {
        binding.expectedImageSize.setText(String.valueOf(binding.cropImageView.expectedImageSize()));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(binding.getRoot());
    }

    private void updateRotationCounter() {
        binding.rotateText.setText(getString(R.string.rotation_value, String.valueOf(binding.cropImageView.rotatedDegrees)));
    }

    @Override
    public void onPickImageResult(Uri resultUri) {
        super.onPickImageResult(resultUri);

        if (resultUri != null) {
            binding.cropImageView.setImageUriAsync(resultUri);
        }
    }

    @Override
    public Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        Intent result = super.getResultIntent(uri, error, sampleSize);
        // Adding some more information.
        return result.putExtra("EXTRA_KEY", "Extra data");
    }

    @Override
    public void setResult(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result = new CropImage.ActivityResult(
                binding.cropImageView.getImageUri(),
                uri,
                error,
                binding.cropImageView.cropPoints,
                binding.cropImageView.cropRect,
                binding.cropImageView.rotatedDegrees,
                binding.cropImageView.wholeImageRect,
                sampleSize
        );
        // todo Timber
        /*
        Timber.tag("AIC-Sample").i("Original bitmap: " + result.originalBitmap);
        Timber.tag("AIC-Sample").i("Original uri: " + result.originalUri);
        Timber.tag("AIC-Sample").i("Output bitmap: " + result.bitmap);
        Timber.tag("AIC-Sample").i("Output uri: " + result.getUriFilePath(this));
         */
        binding.cropImageView.setImageUriAsync(result.uriContent);
    }

    /*
    @Override
    public void setResult(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result = new CropImage.ActivityResult(
                binding.cropImageView.imageUri,
                uri,
                error,
                binding.cropImageView.cropPoints,
                binding.cropImageView.cropRect,
                binding.cropImageView.rotatedDegrees,
                binding.cropImageView.wholeImageRect,
                sampleSize
        );

        Timber.tag("AIC-Sample").i("Original bitmap: " + result.originalBitmap);
        Timber.tag("AIC-Sample").i("Original uri: " + result.originalUri);
        Timber.tag("AIC-Sample").i("Output bitmap: " + result.bitmap);
        Timber.tag("AIC-Sample").i("Output uri: " + result.getUriFilePath(this));
        binding.cropImageView.setImageUriAsync(result.uriContent);
    }
     */

    @Override
    public void setResultCancel() {
        Timber.tag("AIC-Sample").i("User this override to change behavior when cancel");
        super.setResultCancel();
    }

    @Override
    public void updateMenuItemIconColor(Menu menu, int itemId, int color) {
        Timber.tag("AIC-Sample").i("If not using your layout, this can be one option to change colors");
        super.updateMenuItemIconColor(menu, itemId, color);
    }

    private void onRotateClick() {
        binding.cropImageView.rotateImage(90);
        updateRotationCounter();
    }
}
