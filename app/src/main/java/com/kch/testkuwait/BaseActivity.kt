package com.kch.testkuwait

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    var progressbar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        progressbar = findViewById(R.id.idLoadingPB)

    }

    companion object {
        var baseActivity: BaseActivity? = null
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("NewApi")
    fun checkForActivityIsDestroyed(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed || isDestroyed
        } else {
            isDestroyed
        }
    }

    fun openActivity(classType: Class<*>) {
        val intent = Intent(this, classType)
        startActivity(intent)
    }


    fun openActivity(classType: Class<*>, extra: Bundle) {
        val intent = Intent(this, classType)
        intent.putExtras(extra)
        startActivity(intent)
    }

    fun openSettingScreen(){
        val intent: Intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        startActivity(intent)
    }

    fun showLoading(loading: Boolean) {
        progressbar = findViewById(R.id.idLoadingPB)
        when (loading) {
            true -> progressbar!!.visibility = View.VISIBLE
            false -> progressbar!!.visibility = View.GONE
        }
    }
}