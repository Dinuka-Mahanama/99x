package com.scandit.viewbasedmatrixscansample;
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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.viewbasedmatrixscansample.scan.ShelfManagementFragment;

/**
 * This example shows how to use the view-based Matrix Scan of the Scandit BarcodeScanner SDK, which
 * is a high-level abstraction of the base Scandit Matrix Scan.
 * <p>
 * The sample demonstrates how the view-based Matrix Scan can be used to track multiple barcodes
 * simultaneously and how to apply multiple overlays over the detected barcodes.
 */
public class MainActivity extends AppCompatActivity {

    // Enter your Scandit SDK License key here.
    // Your Scandit SDK License key is available via your Scandit SDK web account.
    private static final String sScanditSdkAppKey = "-- ENTER YOUR SCANDIT LICENSE KEY HERE --";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScanditLicense.setAppKey(sScanditSdkAppKey);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, new ShelfManagementFragment(), null)
                .commit();
    }
}
