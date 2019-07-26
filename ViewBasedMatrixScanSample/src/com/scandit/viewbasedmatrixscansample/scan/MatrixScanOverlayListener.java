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

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.matrixscan.Frame;
import com.scandit.matrixscan.MatrixScan;
import com.scandit.matrixscan.MatrixScanListener;
import com.scandit.matrixscan.SimpleMatrixScanOverlay;
import com.scandit.matrixscan.ViewBasedMatrixScanOverlay;
import com.scandit.recognition.Quadrilateral;
import com.scandit.recognition.TrackedBarcode;
import com.scandit.viewbasedmatrixscansample.scan.bubbles.BaseBubble;
import com.scandit.viewbasedmatrixscansample.scan.bubbles.IndicatorState;
import com.scandit.viewbasedmatrixscansample.scan.bubbles.IndicatorViewModelFactory;
import com.scandit.viewbasedmatrixscansample.utility.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.scandit.viewbasedmatrixscansample.scan.bubbles.IndicatorState.HIGHLIGHT_ONLY;
import static com.scandit.viewbasedmatrixscansample.scan.bubbles.IndicatorState.MAXIMISED;
import static com.scandit.viewbasedmatrixscansample.scan.bubbles.IndicatorState.MINIMISED;

/**
 * A class implementing 3 interfaces related to matrix scan:
 * - MatrixScanListener - an interface used by the {@link MatrixScan} to control the processing of
 * tracked barcodes,
 * - SimpleMatrixScanOverlay.SimpleMatrixScanOverlayListener - an interface required by the
 * {@link SimpleMatrixScanOverlay} providing user basic control over the overlay,
 * - ViewBasedMatrixScanOverlay.ViewBasedMatrixScanOverlayListener - an interface required by the
 * {@link ViewBasedMatrixScanOverlay} providing user basic control over the overlay.
 */
public class MatrixScanOverlayListener implements
        SimpleMatrixScanOverlay.SimpleMatrixScanOverlayListener,
        ViewBasedMatrixScanOverlay.ViewBasedMatrixScanOverlayListener, MatrixScanListener {

    private static final float MINIMISED_RATIO = 0.1f;
    private static final float MAXIMISED_RATIO = 0.25f;
    private static final int MAX_INDICATOR_NUMBER = 10;

    private Context context;
    private ClickCallback bubbleClickCallback;
    private BarcodePicker picker;

    private ViewBasedMatrixScanOverlay viewBasedMatrixScanOverlay;

    private IndicatorState lastIndicatorState = IndicatorState.MINIMISED;
    private IndicatorViewModelFactory bubbleFactory;
    private Map<Long, BaseBubble> bubbles = new ConcurrentSkipListMap<>();
    private float screenWidth;

    public MatrixScanOverlayListener(Context context, ClickCallback bubbleClickCallback, BarcodePicker picker) {
        this.context = context;
        this.bubbleClickCallback = bubbleClickCallback;
        this.picker = picker;

        Point size = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        bubbleFactory = new IndicatorViewModelFactory(context);
    }

    public void setViewBasedMatrixScanOverlay(ViewBasedMatrixScanOverlay viewBasedMatrixScanOverlay) {
        this.viewBasedMatrixScanOverlay = viewBasedMatrixScanOverlay;
    }

    private IndicatorState calculateIndicatorState(Map<Long, TrackedBarcode> recognizedCodes) {
        List<Integer> list = new ArrayList<>();
        for (TrackedBarcode code : recognizedCodes.values()) {
            Quadrilateral convertedLocation = convertQuadrilateral(code.getLocation());
            list.add(convertedLocation.top_right.x - convertedLocation.top_left.x);
        }
        Collections.sort(list);

        float indicatorSizeRatio = list.get(recognizedCodes.size() / 2) / screenWidth;

        if (recognizedCodes.size() > MAX_INDICATOR_NUMBER) {
            return HIGHLIGHT_ONLY;
        } else if (indicatorSizeRatio > MAXIMISED_RATIO) {
            return MAXIMISED;
        } else if (indicatorSizeRatio > MINIMISED_RATIO) {
            return MINIMISED;
        } else {
            return HIGHLIGHT_ONLY;
        }
    }

    private Quadrilateral convertQuadrilateral(Quadrilateral quadrilateral) {
        return new Quadrilateral(picker.convertPointToPickerCoordinates(quadrilateral.top_left),
                picker.convertPointToPickerCoordinates(quadrilateral.top_right),
                picker.convertPointToPickerCoordinates(quadrilateral.bottom_left),
                picker.convertPointToPickerCoordinates(quadrilateral.bottom_right));
    }

    @Override
    public int getColorForCode(TrackedBarcode barcode, long trackingId) {
        BaseBubble indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return -1;
        }

        return indicator.highlightColor;
    }

    @Override
    public View getViewForCode(TrackedBarcode barcode, long trackingId) {
        BaseBubble indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return null;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return indicator.getView(context, inflater);
    }

    @Override
    public Point getOffsetForCode(TrackedBarcode barcode, long trackingId) {
        BaseBubble indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return null;
        }

        return new Point(-UiUtils.pxFromDp(context, indicator.getWidth()) / 2,
                -UiUtils.pxFromDp(context, indicator.getHeight()));
    }

    @Override
    public void onCodeTouched(TrackedBarcode barcode, long trackingId) {
        bubbleClickCallback.run(barcode);
    }

    @Override
    public void matrixScan(MatrixScan matrixScan, Frame didUpdate) {
        if (didUpdate.getTrackedCodes().isEmpty()) {
            return;
        }

        IndicatorState indicatorState = calculateIndicatorState(didUpdate.getTrackedCodes());
        if (lastIndicatorState != indicatorState) {
            bubbles.clear();
            if (indicatorState != HIGHLIGHT_ONLY) {
                for (final Map.Entry<Long, TrackedBarcode> it : didUpdate.getTrackedCodes().entrySet()) {
                    bubbles.put(it.getKey(), bubbleFactory.createBubble(indicatorState, it.getValue()));

                    viewBasedMatrixScanOverlay
                            .setOffsetForCode(getOffsetForCode(it.getValue(), it.getKey()), it.getKey());
                    viewBasedMatrixScanOverlay.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bubbles.containsKey(it.getKey())) {
                                viewBasedMatrixScanOverlay
                                        .setViewForCode(getViewForCode(it.getValue(), it.getKey()), it.getKey());
                            }
                        }
                    });
                }
            }
        }
        lastIndicatorState = indicatorState;

        for (long id : didUpdate.getAddedIdentifiers()) {
            if (didUpdate.getTrackedCodes().containsKey(id)) {
                bubbles.put(id, bubbleFactory.createBubble(indicatorState, didUpdate.getTrackedCodes().get(id)));
            }
        }

        for (long id : didUpdate.getRemovedIdentifiers()) {
            bubbles.remove(id);
        }
    }

    @Override
    public boolean shouldRejectCode(MatrixScan matrixScan, TrackedBarcode trackedBarcode) {
        return false;
    }
}
