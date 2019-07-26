let permissions = require('nativescript-permissions')
const license = require('!raw-loader!!./license.txt')
const application = require("tns-core-modules/application")
const platformModule = require("tns-core-modules/platform")
import IVMF from "../scandit/bubble/IndicatorViewModelFactory"
let context 
let picker
export default {
    inti() {

    },
    data() {
        return {
          Total: 0
        };
      },
    MatrixScanOverlayListener(contextt, pickerr) {
        context = contextt;
        picker = pickerr;

        let size = (0,0);
        let screenWidth = platformModule.screen.mainScreen.widthDIPs;
        let bubbleFactory = IVMF.IndicatorViewModelFactory(context);
    },
    setViewBasedMatrixScanOverlay(viewBasedMatrixScanOverlay) {
        this.viewBasedMatrixScanOverlay = viewBasedMatrixScanOverlay;
    },
    convertQuadrilateral(quadrilateral) {
        return new Quadrilateral(picker.convertPointToPickerCoordinates(quadrilateral.top_left),
            picker.convertPointToPickerCoordinates(quadrilateral.top_right),
            picker.convertPointToPickerCoordinates(quadrilateral.bottom_left),
            picker.convertPointToPickerCoordinates(quadrilateral.bottom_right));
    },
    calculateIndicatorState(Map, TrackedBarcode) {
        list = new ArrayList();
        for (TrackedBarcode; recognizedCodes.values();) {
            convertedLocation = convertQuadrilateral(code.getLocation());
            list.add(convertedLocation.top_right.x - convertedLocation.top_left.x);
        }
        Collections.sort(list);

        let indicatorSizeRatio = list.get(recognizedCodes.size() / 2) / screenWidth;

        if (recognizedCodes.size() > MAX_INDICATOR_NUMBER) {
            return HIGHLIGHT_ONLY;
        } else if (indicatorSizeRatio > MAXIMISED_RATIO) {
            return MAXIMISED;
        } else if (indicatorSizeRatio > MINIMISED_RATIO) {
            return MINIMISED;
        } else {
            return HIGHLIGHT_ONLY;
        }
    },
    convertQuadrilateral(quadrilateral) {
        return new Quadrilateral(picker.convertPointToPickerCoordinates(quadrilateral.top_left),
            picker.convertPointToPickerCoordinates(quadrilateral.top_right),
            picker.convertPointToPickerCoordinates(quadrilateral.bottom_left),
            picker.convertPointToPickerCoordinates(quadrilateral.bottom_right));
    },
    getColorForCode(barcode, trackingId) {
        let indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return -1;
        }

        return indicator.highlightColor;
    },
    getViewForCode(barcode,trackingId) {
        let indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return null;
        }

        let inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return indicator.getView(context, inflater);
    },
    getOffsetForCode(barcode,trackingId) {
        let indicator = bubbles.get(trackingId);
        if (indicator == null) {
            return null;
        }

        return new Point(-UiUtils.pxFromDp(context, indicator.getWidth()) / 2,
                -UiUtils.pxFromDp(context, indicator.getHeight()));
    },
    onCodeTouched(barcode,trackingId) {
        bubbleClickCallback.run(barcode);
    }/*,
    matrixScan(matrixScan,didUpdate) {
        if (didUpdate.getTrackedCodes().isEmpty()) {
            return;
        }

        let indicatorState = calculateIndicatorState(didUpdate.getTrackedCodes());
        if (lastIndicatorState != indicatorState) {
            bubbles.clear();
            if (indicatorState != HIGHLIGHT_ONLY) {
                for (Map.Entry<Long, TrackedBarcode> it ; didUpdate.getTrackedCodes().entrySet()) {
                    bubbles.put(it.getKey(), bubbleFactory.createBubble(indicatorState, it.getValue()));

                    viewBasedMatrixScanOverlay
                            .setOffsetForCode(getOffsetForCode(it.getValue(), it.getKey()), it.getKey());
                    viewBasedMatrixScanOverlay.post(new Runnable() {
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

        for (let id ; didUpdate.getAddedIdentifiers()) {
            if (didUpdate.getTrackedCodes().containsKey(id)) {
                bubbles.put(id, bubbleFactory.createBubble(indicatorState, didUpdate.getTrackedCodes().get(id)));
            }
        }

        for (let id ; didUpdate.getRemovedIdentifiers()) {
            bubbles.remove(id);
        }
    }*/
}