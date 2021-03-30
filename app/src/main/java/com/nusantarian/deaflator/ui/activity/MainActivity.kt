package com.nusantarian.deaflator.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nusantarian.deaflator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            checkPermission()
    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(
                this,
                listOf(Manifest.permission.RECORD_AUDIO).toTypedArray(),
                RecordAudioRequestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty())
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RecordAudioRequestCode = 1
    }
}