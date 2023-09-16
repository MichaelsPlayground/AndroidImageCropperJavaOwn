package de.androidcrypto.androidimagecropperjavaown;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import com.canhub.cropper.CropImageView.CropResult;
import com.canhub.cropper.CropImageView.OnCropImageCompleteListener;
import com.canhub.cropper.CropImageView.OnSetImageUriCompleteListener;
import de.androidcrypto.androidimagecropperjavaown.optionsdialog.SampleOptionsBottomSheet;
import de.androidcrypto.androidimagecropperjavaown.R;
import de.androidcrypto.androidimagecropperjavaown.databinding.FragmentCropImageViewBinding;
import timber.log.Timber;

public class SampleUsingImageViewFragment extends Fragment
        implements SampleOptionsBottomSheet.Listener, OnSetImageUriCompleteListener,
        OnCropImageCompleteListener {

    private FragmentCropImageViewBinding binding;
    private CropImageOptions options;

    private final ActivityResultContracts.GetContent openPicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                binding.cropImageView.setImageUriAsync(uri);
            });

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        binding = FragmentCropImageViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.cropImageView.setOnSetImageUriCompleteListener(null);
        binding.cropImageView.setOnCropImageCompleteListener(null);
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOptions();

        binding.cropImageView.setOnSetImageUriCompleteListener(this);
        binding.cropImageView.setOnCropImageCompleteListener(this);

        if (savedInstanceState == null) {
            binding.cropImageView.setImageResource(R.drawable.cat);
        }

        binding.settings.setOnClickListener(v -> {
            SampleOptionsBottomSheet.show(getChildFragmentManager(), options, this);
        });

        binding.searchImage.setOnClickListener(v -> {
            openPicker.launch("image/*");
        });

        binding.reset.setOnClickListener(v -> {
            binding.cropImageView.resetCropRect();
            binding.cropImageView.setImageResource(R.drawable.cat);
            onOptionsApplySelected(new CropImageOptions());
        });
    }

    @Override
    public void onOptionsApplySelected(CropImageOptions options) {
        this.options = options;
        binding.cropImageView.setImageCropOptions(options);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_crop:
                binding.cropImageView.croppedImageAsync();
                return true;
            case R.id.main_action_rotate:
                binding.cropImageView.rotateImage(90);
                return true;
            case R.id.main_action_flip_horizontally:
                binding.cropImageView.flipImageHorizontally();
                return true;
            case R.id.main_action_flip_vertically:
                binding.cropImageView.flipImageVertically();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error != null) {
            Timber.tag("AIC-Sample").e(error, "Failed to load image by URI");
            Toast.makeText(getActivity(), "Image load failed: " + error.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropResult result) {
        if (result.getError() == null) {
            CropImageOptions.CropShape cropShape = binding.cropImageView.getCropShape();
            if (cropShape == CropImageOptions.CropShape.OVAL) {
                result.getBitmap();
            } else {
                result.getBitmap();
            }
            Timber.tag("AIC-Sample").i("Original bitmap: " + result.getOriginalBitmap());
            Timber.tag("AIC-Sample").i("Original uri: " + result.getOriginalUri());
            Timber.tag("AIC-Sample").i("Output bitmap: " + result.getBitmap());
            Timber.tag("AIC-Sample").i("Output uri: " + result.getUriFilePath(view.getContext()));
            SampleResultScreen.start(
                    this,
                    result.getBitmap(),
                    result.getUriContent(),
                    result.getSampleSize()
            );
        } else {
            Timber.tag("AIC-Sample").e(result.getError(), "Failed to crop image");
            Toast.makeText(
                    getActivity(),
                    "Crop failed: " + result.getError().getMessage(),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void setOptions() {
        binding.cropImageView.setCropRect(new Rect(100, 300, 500, 1200));
        onOptionsApplySelected(new CropImageOptions());
    }
}

