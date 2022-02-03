package com.example.bootcampdio

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.style.BulletSpan
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {

    var image_uri : Uri? = null

    companion object {
        private val PERMISSION_CODE_IMAGE_PICK = 1000
        private val IMG_PICK_CODE = 1001

        private val PERMISSION_CODE_CAMERA = 2000
        private val OPEM_CAMERA_CODE = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        btget_picture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE_IMAGE_PICK)
                }else {
                    pickImageFromGalery()
                }
            } else {
                pickImageFromGalery()
            }
        }

        bttake_picture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {

                            val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            requestPermissions(permission, PERMISSION_CODE_CAMERA)

                } else {
                    opemCamera()
                }
            } else {
                opemCamera()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE_IMAGE_PICK -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalery()
                } else {
                    Toast.makeText(this, "Permissão negada pelo usuario", Toast.LENGTH_LONG).show()
                }
            }

            PERMISSION_CODE_CAMERA -> {
                if (grantResults.size > 1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    opemCamera()
                } else {
                    Toast.makeText(this, "Permissão negada pelo usuario", Toast.LENGTH_LONG).show()
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun opemCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "nova foto")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem capturada pela camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        startActivityForResult(cameraIntent, OPEM_CAMERA_CODE)
    }

    private fun pickImageFromGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMG_PICK_CODE) {
            imageView.setImageURI(data?.data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == OPEM_CAMERA_CODE)
            imageView.setImageURI(image_uri)
    }
}