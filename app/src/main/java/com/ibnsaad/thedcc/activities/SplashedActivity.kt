package com.ibnsaad.thedcc.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.ibnsaad.thedcc.R

/**
 * Created by nixan on 12.01.2018.
 */

private const val ACTIVITY_AUTH = 1000

abstract class SplashedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (true) {
            startActivityForResult(Intent(this, LoginActivity::class.java), ACTIVITY_AUTH)
        }
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    private fun onAuthenticatedCallback(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_CANCELED -> finish()
            Activity.RESULT_OK -> recreate()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ACTIVITY_AUTH -> onAuthenticatedCallback(resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}