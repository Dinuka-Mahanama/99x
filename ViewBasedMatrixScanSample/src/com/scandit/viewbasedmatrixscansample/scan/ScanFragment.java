package com.scandit.viewbasedmatrixscansample.scan;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *BarcodePicker(context, getScanSettings())
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.barcodepicker.ScanOverlay;
import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.recognition.Barcode;

/**
 * A Fragment responsible for barcode scanning, preparing and setting the ScanSettings,
 * controlling scanning flow (stopping and resuming scanning when necessary), etc.
 */
public abstract class ScanFragment extends CameraPermissionFragment {

    enum ScanState {
        STOPPED, SCANNING
    }

    private ScanState scanState = ScanState.STOPPED;
    protected BarcodePicker picker;

    protected void initializePicker() {
        if (picker == null) {
            picker = new BarcodePicker(getContext(), getScanSettings());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePicker();

        picker.applyScanSettings(getScanSettings());
        View view = getView();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    updateScanUi(picker);
                }
            });
        }

        setScanState(ScanState.SCANNING);
    }

    @Override
    public void onStop() {
        super.onStop();
        setScanState(ScanState.STOPPED);
    }

    @Override
    public void onResume() {
        super.onResume();
        setScanState(ScanState.SCANNING);
    }

    @Override
    public void onPause() {
        super.onPause();
        setScanState(ScanState.STOPPED);
    }

    protected ScanSettings getScanSettings() {
        ScanSettings settings = ScanSettings.create();
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCE, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN8, true);
        settings.setMatrixScanEnabled(true);
        settings.setMaxNumberOfCodesPerFrame(12);
        settings.setHighDensityModeEnabled(true);
        settings.setCodeCachingDuration(0);
        settings.setCodeDuplicateFilter(0);

        return settings;
    }

    protected void updateScanUi(BarcodePicker picker) {
        initializePicker();

        picker.getOverlayView().setGuiStyle(ScanOverlay.GUI_STYLE_NONE);
        picker.getOverlayView().setTorchEnabled(false);
        picker.getOverlayView().setViewfinderDimension(0.9f, 0.75f, 0.95f, 0.9f);
    }

    protected void setScanState(ScanState state) {
        setScanState(state, null);
    }

    protected void setScanState(ScanState state, ScanSession scanSession) {
        scanState = state;

        switch (state) {
            case STOPPED:
                if (scanSession != null) {
                    scanSession.stopScanning();
                } else {
                    picker.stopScanning();
                }
                break;
            case SCANNING:
                if (hasCameraPermission()) {
                    picker.startScanning();
                } else {
                    requestCameraPermission();
                }
                break;
        }
    }

    @Override
    public void onCameraPermissionGranted() {
        setScanState(scanState);
    }
}
