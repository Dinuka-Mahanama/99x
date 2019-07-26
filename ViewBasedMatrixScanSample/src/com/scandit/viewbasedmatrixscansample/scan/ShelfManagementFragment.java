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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.matrixscan.MatrixScan;
import com.scandit.matrixscan.SimpleMatrixScanOverlay;
import com.scandit.matrixscan.ViewBasedMatrixScanOverlay;
import com.scandit.recognition.TrackedBarcode;
import com.scandit.viewbasedmatrixscansample.R;

/**
 * Main Fragment of the ViewBasedMatrixScanSample. It prepares and manages the view of the app,
 * as well as controls the matrix scan events/callbacks via the MatrixScanOverlayListener.
 */
public class ShelfManagementFragment extends ScanFragment {

    private MatrixScanOverlayListener matrixScanListener;

    private boolean frozen = false;
    private FrameLayout pickerContainer;
    private Button freezeButton;
    private TextView detail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        initializePicker();

        matrixScanListener = new MatrixScanOverlayListener(getContext(), new ClickCallback() {
            @Override
            public void run(TrackedBarcode barcode) {
                bubbleClick(barcode);
            }
        }, picker);
        MatrixScan matrixScan = new MatrixScan(picker, matrixScanListener);
        ViewBasedMatrixScanOverlay viewBasedOverlay =
                new ViewBasedMatrixScanOverlay(getContext(), matrixScan, matrixScanListener);
        matrixScan.addOverlay(new SimpleMatrixScanOverlay(getContext(), matrixScan, matrixScanListener));
        matrixScan.addOverlay(viewBasedOverlay);

        // You can enable beeping via:
        matrixScan.setBeepOnNewCode(true);

        matrixScanListener.setViewBasedMatrixScanOverlay(viewBasedOverlay);

        View view = inflater.inflate(R.layout.shelf_management_fragment, null);

        pickerContainer = view.findViewById(R.id.picker);
        pickerContainer.addView(picker);
        freezeButton = view.findViewById(R.id.freeze_button);
        freezeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeze();
            }
        });
        detail = view.findViewById(R.id.detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDetail();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar bar = ((AppCompatActivity) getContext()).getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pickerContainer.removeAllViews();
    }

    private void bubbleClick(TrackedBarcode barcode) {
        detail.setText(barcode.getData());
        detail.setVisibility(View.VISIBLE);
    }

    private void closeDetail() {
        detail.setVisibility(View.INVISIBLE);
    }

    private void freeze() {
        if (frozen) {
            frozen = false;
            setScanState(ScanState.SCANNING);
            freezeButton.setText(getString(R.string.sm_freeze));
        } else {
            frozen = true;
            setScanState(ScanState.STOPPED);
            freezeButton.setText(getString(R.string.sm_done));
        }
    }
}
