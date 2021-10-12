package com.example.amplifytest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageDownloadFileOptions
import com.amplifyframework.storage.options.StorageUploadFileOptions
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var btn_upload : Button
    lateinit var btn_download : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_upload = findViewById(R.id.btn_upload)
        btn_download = findViewById(R.id.btn_download)

        btn_upload.setOnClickListener({
            uploadFile()
        })

        btn_download.setOnClickListener({
            downloadFile()
        })

        val thisDir = this.getFilesDir().toString()
        Log.d("MyAmplifyApp", thisDir)
    }

    private fun uploadFile() {
        val exampleFile = File(applicationContext.filesDir, "ExampleKey")
        exampleFile.writeText("Example file contents")

        val options = StorageUploadFileOptions.defaultInstance()
        Amplify.Storage.uploadFile("ExampleKey", exampleFile, options,
            { Log.i("MyAmplifyApp", "Fraction completed: ${it.fractionCompleted}") },
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
    }

    private fun downloadFile() {
        val file = File("${applicationContext.filesDir}/download")
        val options = StorageDownloadFileOptions.defaultInstance()
        Amplify.Storage.downloadFile("ExampleKey", file, options,
            { Log.i("MyAmplifyApp", "Fraction completed: ${it.fractionCompleted}") },
            { Log.i("MyAmplifyApp", "Successfully downloaded: ${it.file.name}") },
            { Log.e("MyAmplifyApp", "Download Failure", it) }
        )
    }

}