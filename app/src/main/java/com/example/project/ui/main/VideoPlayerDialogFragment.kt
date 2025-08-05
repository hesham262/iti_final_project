package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.project.R

class VideoPlayerDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_VIDEO_URL = "video_url"

        fun newInstance(videoUrl: String): VideoPlayerDialogFragment {
            val fragment = VideoPlayerDialogFragment()
            val args = Bundle()
            args.putString(ARG_VIDEO_URL, videoUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_video_player, container, false)
        val webView = view.findViewById<WebView>(R.id.webView)
        val closeBtn = view.findViewById<TextView>(R.id.btnClose)

        val url = arguments?.getString(ARG_VIDEO_URL)
        val embedUrl = "https://www.youtube.com/embed/${extractVideoId(url)}"

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(embedUrl)

        closeBtn.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun extractVideoId(youtubeUrl: String?): String {
        return youtubeUrl?.substringAfter("v=")?.take(11) ?: ""
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
