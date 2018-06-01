package com.cambricon.myapplication;

import com.huawei.hiai.vision.visionkit.image.detector.Label;
/**
 * Created by xiaoxiao on 18-6-1.
 */

public interface MMListener {
    void onTaskCompleted(Label label);
}
