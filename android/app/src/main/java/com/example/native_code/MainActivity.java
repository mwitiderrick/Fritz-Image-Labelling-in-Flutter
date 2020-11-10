package com.example.native_code;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodChannel;
import android.view.View;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.os.Bundle;
import ai.fritz.core.Fritz;
import ai.fritz.vision.FritzVision;
import ai.fritz.vision.FritzVisionImage;
import ai.fritz.vision.FritzVisionModels;
import ai.fritz.vision.imagelabeling.FritzVisionLabelPredictor;
import ai.fritz.vision.imagelabeling.FritzVisionLabelResult;
import ai.fritz.vision.imagelabeling.LabelingOnDeviceModel;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.graphics.BitmapFactory;
import android.util.Log;


public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "heartbeat.fritz.ai/native";

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    Fritz.configure(this, "YOUR_API_KEY");
    GeneratedPluginRegistrant.registerWith(flutterEngine);

    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
        .setMethodCallHandler((call, result) -> {
          if (call.method.equals("labelImage")) {
               
            String obtainedLabel = labelImage();
            result.success(obtainedLabel);   
          }

        });
  }
    private String labelImage(){
      
        Bitmap loadedImage = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
          
        FritzVisionImage visionImage = FritzVisionImage.fromBitmap(loadedImage);
        LabelingOnDeviceModel imageLabelOnDeviceModel = FritzVisionModels.getImageLabelingOnDeviceModel();
        FritzVisionLabelPredictor predictor = FritzVision.ImageLabeling.getPredictor(
                imageLabelOnDeviceModel
        );
        FritzVisionLabelResult labelResult = predictor.predict(visionImage);
       String label = labelResult.getResultString();
        Log.i( "Info", "Label 1 is "  + label); 
        
        return label;  
  }
  
}