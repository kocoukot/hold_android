package com.hold.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class GalleryActivityResultContract(private val allowMultiple: Boolean) :
    ActivityResultContract<Unit, List<Uri?>>() {


    override fun createIntent(context: Context, input: Unit): Intent =
        Intent().apply {
            type = INTENT_TYPE_IMAGES
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri?> {
        val uriList = mutableListOf<Uri>()
        val clipData = intent?.clipData
        val data = intent?.data
        if ((clipData != null && clipData.itemCount <= MAX_IMAGES_NUMBER) || data != null) {
            if (clipData != null) {
                for (item in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(item).uri
                    uriList.add(uri)
                }
            } else {
                if (data != null) {
                    uriList.add(data)
                }
            }
        }
        return if (allowMultiple) uriList else listOf(intent?.data)
    }

    companion object {
        private const val INTENT_TYPE_IMAGES = "image/jpeg"
        private const val MAX_IMAGES_NUMBER = 10
    }


}