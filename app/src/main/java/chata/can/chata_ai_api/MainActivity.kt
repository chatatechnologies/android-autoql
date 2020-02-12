package chata.can.chata_ai_api

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val urlRequest = "https://backend.chata.ai/api/v1/autocomplete?q=Hellö Wörld@Java&projectid=1"
        Log.e("URL", encodeURL(urlRequest))
        setContentView(R.layout.activity_main)
    }

    @Suppress("DEPRECATION")
    private fun encodeURL(value: String): String
    {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            URLEncoder.encode(value, "UTF-8")
        }
        else
        {
            URLEncoder.encode(value)
        }
    }
}
