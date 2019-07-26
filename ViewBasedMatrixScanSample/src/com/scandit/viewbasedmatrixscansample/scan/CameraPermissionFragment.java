package com.scandit.viewbasedmatrixscansample.scan;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Abstract class inheriting from Fragment, that provides the camera permission handling.
 */
public abstract class CameraPermissionFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST = 0;

    private boolean deniedCameraAccess = false;
    private boolean paused = true;

    @Override
    public void onPause() {
        super.onPause();

        paused = true;
    }

    @Override
    public void onResume() {
        super.onResume();

        paused = false;
    }

    protected boolean hasCameraPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || getContext().checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestCameraPermission() {
        // Handle permissions for Marshmallow and onwards...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (!deniedCameraAccess) {
                // It's pretty clear for why the camera is required. We don't need to give a
                // detailed reason.
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST);
            }
        } else {
            // We already have the permission or don't need it.
            onCameraPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                deniedCameraAccess = false;
                if (!paused) {
                    onCameraPermissionGranted();
                }
            } else {
                deniedCameraAccess = true;
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public abstract void onCameraPermissionGranted();
}
