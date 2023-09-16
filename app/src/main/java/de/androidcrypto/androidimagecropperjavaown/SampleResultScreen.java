package de.androidcrypto.androidimagecropperjavaown;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

//import com.canhub.cropper.parcelable;
import de.androidcrypto.androidimagecropperjavaown.databinding.ActivityCropResultBinding;

public class SampleResultScreen extends Activity {

    private ActivityCropResultBinding binding;
    private static Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityCropResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.resultImageView.setBackgroundResource(R.drawable.backdrop);
        binding.resultImageView.setOnClickListener(v -> {
            releaseBitmap();
            finish();
        });

        if (image != null) {
            binding.resultImageView.setImageBitmap(image);
            int sampleSize = getIntent().getIntExtra(SAMPLE_SIZE, 1);
            double ratio = (10 * image.getWidth() / (double) image.getHeight()) / 10.0;
            int byteCount = image.getByteCount() / 1024;
            String desc = "(" + image.getWidth() + ", " + image.getHeight() + "), Sample: " + sampleSize + ", Ratio: " + ratio + ", Bytes: " + byteCount + " K";
            binding.resultImageText.setText(desc);
        } else {
            Uri imageUri = getIntent().getParcelableExtra(URI);
            if (imageUri != null) {
                binding.resultImageView.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseBitmap();
    }

    private void releaseBitmap() {
        if (image != null) {
            image.recycle();
            image = null;
        }
    }

    public static void start(Fragment fragment, Bitmap imageBitmap, Uri uri, Integer sampleSize) {
        image = imageBitmap;
        Intent intent = new Intent(fragment.requireContext(), SampleResultScreen.class);
        if (sampleSize != null) {
            intent.putExtra(SAMPLE_SIZE, sampleSize);
        }
        if (uri != null) {
            intent.putExtra(URI, uri);
        }
        fragment.startActivity(intent);
    }

    private static final String SAMPLE_SIZE = "SAMPLE_SIZE";
    private static final String URI = "URI";
}
