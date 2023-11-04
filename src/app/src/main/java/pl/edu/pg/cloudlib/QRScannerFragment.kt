package pl.edu.pg.cloudlib

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import pl.edu.pg.cloudlib.databinding.FragmentQrscannerBinding


class QRScannerFragment : Fragment() {

    private lateinit var binding: FragmentQrscannerBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrscannerBinding.inflate(inflater,container,false)

        activity?.title = getString(R.string.qr_scanner_page_title)

        getPermission(Manifest.permission.CAMERA)

        val act = requireActivity()
        codeScanner = CodeScanner(act.baseContext, binding.scannerView).apply {
            // parameters
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            // callbacks
            decodeCallback = DecodeCallback {
                act.runOnUiThread{
                    sendMessage(it.text)
                }
            }
            errorCallback = ErrorCallback {
                act.runOnUiThread{
                    Log.e(TAG,"Camera initialization error: ${it.message}")
                }
            }
        }

        val sv = binding.scannerView
        sv.setOnClickListener{
            codeScanner.startPreview()
            Toast.makeText(act.baseContext,"Starting preview", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


    private fun getPermission(permission: String){
        if (ContextCompat.checkSelfPermission(requireContext(), permission)
            != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    // success
                } else {
                    Toast.makeText(context,
                        "Permission request denied",
                        Toast.LENGTH_SHORT).show()
                }
            }
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun sendMessage(message: String){
        setFragmentResult(BUNDLE_KEY, bundleOf(BUNDLE_KEY to message))
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    companion object {
        private const val TAG = "QRScanner"
        const val BUNDLE_KEY = "QRScannerResult"
    }


}