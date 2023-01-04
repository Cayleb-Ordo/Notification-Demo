package de.fentzl.notification_demo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

public class NotificationRationaleDialog extends DialogFragment {
    private static final int PERMISSION_REQUEST_NOT = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name).setMessage(R.string.AskPerm)
                .setPositiveButton(R.string.Aktivate, (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ActivityCompat.requestPermissions((Activity) requireContext(),
                                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                PERMISSION_REQUEST_NOT);
                    }
                });
        return builder.create();
    }
}
