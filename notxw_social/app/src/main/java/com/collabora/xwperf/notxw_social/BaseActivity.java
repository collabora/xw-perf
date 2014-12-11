package com.collabora.xwperf.notxw_social;

/*
 * Copyright 2014 Intel Corporation. All rights reserved.
 * License: BSD-3-clause-Intel, see LICENSE.txt
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.collabora.xwperf.fps_measure_module.FpsGraphView;
import com.collabora.xwperf.fps_measure_module.FpsMeterView;
import com.collabora.xwperf.fps_measure_module.IFpsListener;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends ActionBarActivity implements View.OnClickListener {

    private FpsMeterView fpsMeter;
    private FpsGraphView fpsGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpsMeter = (FpsMeterView) findViewById(R.id.fps_meter);
        fpsGraph = (FpsGraphView) findViewById(R.id.fps_graph_view);
        fpsGraph.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_widget);
        setSupportActionBar(toolbar);
        pushFragment(savedInstanceState);
    }

    public abstract void pushFragment(Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();
        fpsMeter.setFpsListener(new IFpsListener() {
            @Override
            public void onFpsCount(long fps) {
                fpsGraph.addValue(fps);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        fpsMeter.removeFpsListener();
    }

    @Override
    public void onClick(View v) {
        if (v == fpsGraph) {
            v.postInvalidate();
        }
    }
}
