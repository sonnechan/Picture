package com.egco428.picture

import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private val Request_Code = 1
    private var bitmap: Bitmap? = null //var because we need to update variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            //image file has many types
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, Request_Code)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var stream: InputStream? = null
        if (requestCode == Request_Code && resultCode == Activity.RESULT_OK) {
            try {
                if (bitmap != null) {
                    bitmap!!.recycle()
                }
                stream = contentResolver.openInputStream(data!!.data)
                bitmap = BitmapFactory.decodeStream(stream)

                imageView.setImageBitmap(bitmap)
            } catch (e:FileNotFoundException){
                e.printStackTrace()
            } finally {
                if (stream != null) {
                    try {
                        stream!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }
}
