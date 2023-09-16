package de.androidcrypto.androidimagecropperjavaown.optionsdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import de.androidcrypto.androidimagecropperjavaown.databinding.FragmentOptionsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/*
This Java code mirrors the functionality of the provided Kotlin code while adhering to Java syntax
and conventions. Please note that I kept the comments as placeholders for where the option updates
would be, as the specific option update code was not provided in the original Kotlin code.
You will need to implement the logic for updating the options object within the updateOptions
method as needed for your application.
 */
public class SampleOptionsBottomSheet extends BottomSheetDialogFragment {
    public interface Listener {
        void onOptionsApplySelected(CropImageOptions options);
    }

    private FragmentOptionsBinding binding;
    private CropImageOptions options;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        options = getArguments().getParcelable(OPTIONS_KEY);
        if (options == null) {
            options = new CropImageOptions();
        }
        updateOptions(options);

        bindingActions();
    }

    private void updateOptions(CropImageOptions options) {
        // Update options here as in the original Kotlin code
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (listener != null) {
            listener.onOptionsApplySelected(options);
        }
        super.onDismiss(dialog);
    }

    private void bindingActions() {
        // Define your binding actions as in the original Kotlin code
    }

    public static void show(
            FragmentManager fragmentManager,
            CropImageOptions options,
            Listener listener
    ) {
        SampleOptionsBottomSheet.listener = listener;
        SampleOptionsBottomSheet bottomSheet = new SampleOptionsBottomSheet();
        Bundle args = new Bundle();
        args.putParcelable(OPTIONS_KEY, options);
        bottomSheet.setArguments(args);
        bottomSheet.show(fragmentManager, null);
    }

    private static Listener listener;
    private static final String OPTIONS_KEY = "OPTIONS_KEY";
}

