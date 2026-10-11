package com.soulai.player

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.util.Locale

// Starter prototype: local playback + phone's installed Hindi TTS sample.
// It does not yet transcribe or translate video audio automatically.
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var player: ExoPlayer? = null
    private var tts: TextToSpeech? = null
    private var hindiReady = false
    private lateinit var videoView: PlayerView
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 18, 24, 18)
            setBackgroundColor(0xFF090B14.toInt())
        }
        fun label(textValue: String, size: Float, color: Int) = TextView(this).apply {
            text = textValue; textSize = size; setTextColor(color)
        }
        root.addView(label("Soul AI", 30f, 0xFFFFFFFF.toInt()))
        root.addView(label("Smart Video Player • Free Hindi voice sample", 14f, 0xFFB8B8D0.toInt()))
        videoView = PlayerView(this).apply {
            useController = true
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
        }
        root.addView(videoView)
        message = label("Open a video from your phone to begin.", 14f, 0xFFE5E7EB.toInt())
        root.addView(message)
        root.addView(Button(this).apply {
            text = "Open video from Gallery"
            setOnClickListener { chooseVideo() }
        })
        root.addView(Button(this).apply {
            text = "Test Hindi voice"
            setOnClickListener {
                if (hindiReady) {
                    tts?.language = Locale("hi", "IN")
                    tts?.speak("नमस्ते! यह सोल एआई की हिंदी आवाज़ का नमूना है।", TextToSpeech.QUEUE_FLUSH, null, "sample")
                    message.text = "Hindi sample playing. Voice quality depends on your phone's installed TTS voice."
                } else Toast.makeText(this@MainActivity, "Hindi voice unavailable or still loading.", Toast.LENGTH_LONG).show()
            }
        })
        root.addView(label("Not implemented yet: automatic English-to-Hindi dubbing and YouTube dubbing.", 12f, 0xFFFFD18A.toInt()))
        setContentView(root)
    }

    private fun chooseVideo() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE); type = "video/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivityForResult(intent, 1201)
    }

    @Deprecated("Compact starter example")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 1201 || resultCode != Activity.RESULT_OK) return
        val uri: Uri = data?.data ?: return
        if (player == null) player = ExoPlayer.Builder(this).build().also { videoView.player = it }
        player?.setMediaItem(MediaItem.fromUri(uri)); player?.prepare(); player?.play()
        message.text = "Video playing. Hindi button is only a voice sample, not video dubbing yet."
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val r = tts?.setLanguage(Locale("hi", "IN"))
            hindiReady = r != TextToSpeech.LANG_MISSING_DATA && r != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    override fun onDestroy() {
        videoView.player = null; player?.release(); tts?.stop(); tts?.shutdown()
        super.onDestroy()
    }
}
