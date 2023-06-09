package pl.edu.pg.cloudlib

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import pl.edu.pg.cloudlib.databinding.ActivityQrscannerBinding


class QRScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrscannerBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPermission(Manifest.permission.CAMERA)
        codeScanner = CodeScanner(this, binding.scannerView).apply {
            // parameters
            camera = CodeScanner.CAMERA_BACK
            formats = listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            // callbacks
            decodeCallback = DecodeCallback {
                runOnUiThread{
                    sendMessage(it.text)
                }
            }
            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e(TAG,"Camera initialization error: ${it.message}")
                }
            }
        }

        binding.scannerView.setOnClickListener{
            codeScanner.startPreview()
        }


    }

    private fun getPermission(permission: String){
        if (ContextCompat.checkSelfPermission(baseContext, permission)
            != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher = registerForActivityResult(
                RequestPermission()) { isGranted ->
                if (isGranted) {
                    // success
                } else {
                    Toast.makeText(baseContext,
                        "Permission request denied",
                        Toast.LENGTH_SHORT).show()
                }
            }
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun sendMessage(message: String){
        val intent = Intent(this, LibElementActivity::class.java)
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