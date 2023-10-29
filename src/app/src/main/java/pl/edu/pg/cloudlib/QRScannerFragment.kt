package pl.edu.pg.cloudlib

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentQrscannerBinding.inflate(layoutInflater)

        getPermission(Manifest.permission.CAMERA)
        codeScanner = CodeScanner(requireActivity(), binding.scannerView).apply {
            // parameters
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            // callbacks
            decodeCallback = DecodeCallback {
                activity?.runOnUiThread{
                    sendMessage(it.text)
                }
            }
            errorCallback = ErrorCallback {
                activity?.runOnUiThread{
                    Log.e(TAG,"Camera initialization error: ${it.message}")
                }
            }
        }

        binding.scannerView.setOnClickListener{
            codeScanner.startPreview()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qrscanner, container, false)
    }


    fun getPermission(permission: String){
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
        val intent = Intent(activity, LibElementFragment::class.java)
        intent.putExtra("message", message)
        startActivity(intent)
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
    }


}