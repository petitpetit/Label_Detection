package com.cambricon.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.huawei.hiai.vision.image.detector.LabelDetector;
import com.huawei.hiai.vision.visionkit.common.Frame;
import com.huawei.hiai.vision.visionkit.image.detector.Label;

import org.json.JSONObject;
/**
 * Created by xiaoxiao on 18-6-1.
 */

public class LabelDetectTask extends AsyncTask {
    private static final String LOG_TAG = "label_detect";
    private MMListener listener;
    private long startTime;
    private long endTime;

    LabelDetector labelDetector;

    public LabelDetectTask(MMListener listener) {
        this.listener = listener;
    }


    private Label getLabel(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(LOG_TAG, "bitmap is null");
            return null;
        }
        Frame frame = new Frame();
        frame.setBitmap(bitmap);
        Log.d(LOG_TAG, "runVisionService " + "start get label");
        JSONObject jsonObject = labelDetector.detect(frame, null);
        Label label = labelDetector.convertResult(jsonObject);
        if (null == label) {
            Log.e(LOG_TAG, "label is null ");
            return null;
        }
        return label;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        Log.i(LOG_TAG, "init LabelDetector");
        labelDetector = new LabelDetector((Context)listener);

        Log.i(LOG_TAG, "start to get label");
        startTime = System.currentTimeMillis();
        Label result_label = getLabel([0]);
        endTime = System.currentTimeMillis();
        Log.i(LOG_TAG, String.format("labeldetect whole time: %d ms", endTime - startTime));
        //release engine after detect finished
        labelDetector.release();
        return result_label;
    }
}






