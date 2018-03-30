package com.stickerbox.tabbarcontroller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stickerbox.tabbarcontroller.R
import com.stickerbox.tabbarcontroller.navigationController
import kotlinx.android.synthetic.main.fragment_home_detail.*

class HomeDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_detail, container, false)
    }

    override fun onStart() {
        super.onStart()

        detail_button.setOnClickListener {
            navigationController?.pop()
        }
    }
}