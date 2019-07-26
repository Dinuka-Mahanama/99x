let permissions = require('nativescript-permissions')
const license = require('!raw-loader!!./license.txt')
const application = require("tns-core-modules/application")
import Listerner from "../scandit/MatrixScanListener"
import { listenerCount } from "cluster";

function scanItem(time, code, value) {
  let scan = { time, code, value }
  console.log('Updating scans with', scan.value)
}

var MS = new Listerner();
export default {
  init() {

    if (application.android) {
      let context = application.android.context

      com.scandit.barcodepicker.ScanditLicense.setAppKey(license)
      let settings = com.scandit.barcodepicker.ScanSettings.create()
      //Set Barcode Type here
      settings.setSymbologyEnabled(com.scandit.recognition.Barcode.SYMBOLOGY_CODE93, true)
      settings.setSymbologyEnabled(com.scandit.recognition.Barcode.SYMBOLOGY_CODE128, true)
      settings.setMatrixScanEnabled(true)
      settings.setMaxNumberOfCodesPerFrame(12);
      settings.setCodeRejectionEnabled(true);
      settings.setHighDensityModeEnabled(true);
      settings.setCodeCachingDuration(0);
      settings.setCodeDuplicateFilter(0);

      let picker = new com.scandit.barcodepicker.BarcodePicker(context, settings)
      picker.getOverlayView().setGuiStyle(com.scandit.barcodepicker.ScanOverlay.GUI_STYLE_MATRIX_SCAN);
      picker.getOverlayView().setVibrateEnabled(true);

      picker.getOverlayView().setViewfinderDecodedColor(171, 71, 188)

      picker.getOverlayView().setViewfinderColor(0, 0.8, 1)
      picker.getOverlayView().drawViewfinder(true)
      picker.getOverlayView().setViewfinderLandscapeDimension(25, 30)
      picker.setOnScanListener(new com.scandit.barcodepicker.OnScanListener(
        {
          didScan: function (scanSession) {
            let codes = scanSession.getNewlyRecognizedCodes().toArray()
            for (let i = 0; i < codes.length; i++) {
              let code = codes[i]
              let type = code.getSymbologyName()
              let data = code.getData()
              scanItem(Date.now(), type, data)
            }
          }

        }))

      permissions.requestPermissions(
        [
          android.Manifest.permission.CAMERA,
          android.Manifest.permission.INTERNET,
          android.Manifest.permission.ACCESS_NETWORK_STATE
        ], "Permissions needed for barcode scanning and license verification")
        .then(() => {
          application.android.foregroundActivity.setContentView(picker)
          console.log("index line 46");
          picker.startScanning()
        }).catch((err) => {
          console.error("Some permission error", err)
        })
    } else if (application.ios) {
      //Magnus do your shit!
    }
  },
  mscan() {
    if (application.android) {
      let context = application.android.context

      com.scandit.barcodepicker.ScanditLicense.setAppKey(license)
      let settings = com.scandit.barcodepicker.ScanSettings.create()
      //Set Barcode Type here
      settings.setSymbologyEnabled(com.scandit.recognition.Barcode.SYMBOLOGY_CODE93, true)
      settings.setSymbologyEnabled(com.scandit.recognition.Barcode.SYMBOLOGY_CODE128, true)
      settings.setMatrixScanEnabled(true)
      settings.setMaxNumberOfCodesPerFrame(10);
      settings.setCodeRejectionEnabled(true);

      let picker = new com.scandit.barcodepicker.BarcodePicker(context, settings)
      picker.getOverlayView().setGuiStyle(com.scandit.barcodepicker.ScanOverlay.GUI_STYLE_MATRIX_SCAN);
      picker.getOverlayView().setVibrateEnabled(true);

      picker.getOverlayView().setViewfinderDecodedColor(171, 71, 188)

      picker.getOverlayView().setViewfinderColor(0, 0.8, 1)
      picker.getOverlayView().drawViewfinder(true)
      picker.getOverlayView().setViewfinderLandscapeDimension(25, 30)
      picker.setOnScanListener(new com.scandit.barcodepicker.OnScanListener(
        {
          didScan: function (scanSession) {
            let codes = scanSession.getNewlyRecognizedCodes().toArray()
            for (let i = 0; i < codes.length; i++) {
              let code = codes[i]
              let type = code.getSymbologyName()
              let data = code.getData()
              scanItem(Date.now(), type, data)
            }
          }

        }))

      try {
         let matrixScanListener =MS.MatrixScanOverlayListener(context, picker)
         let matrixScan = new com.scandit.matrixscan.MatrixScan(picker, matrixScanListener)
      } catch (err) {
        console.log('error', err.message)
      }

      permissions.requestPermissions(
        [
          android.Manifest.permission.CAMERA,
          android.Manifest.permission.INTERNET,
          android.Manifest.permission.ACCESS_NETWORK_STATE
        ], "Permissions needed for barcode scanning and license verification")
        .then(() => {
          application.android.foregroundActivity.setContentView(picker)
          console.log("index line 46");
          picker.startScanning()
        }).catch((err) => {
          console.error("Some permission error", err)
        })
    } else if (application.ios) {
      //Magnus do your shit!
    }
  },
  methods: {

  }
}
