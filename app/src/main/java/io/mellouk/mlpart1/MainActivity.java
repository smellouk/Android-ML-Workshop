package io.mellouk.mlpart1;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import io.mellouk.mlpart1.classifier.MobileNetClassifier;
import io.mellouk.mlpart1.classifier.Recognition;

public class MainActivity extends BaseCameraPermissionActivity {
    private MobileNetClassifier classifier;
    private TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraView = findViewById(R.id.camera);
        tvResults = findViewById(R.id.tv_results);

        mCameraView.setImageStreamingListener(bitmap -> {
            try {
                if(classifier == null){
                    classifier = new MobileNetClassifier(this, 3);
                }
                final List<Recognition> recognitionList = classifier.recognizeImage(bitmap);

                runOnUiThread(() -> tvResults.setText(listToString(recognitionList)));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private String listToString(final List<Recognition> recognitionList){
        StringBuilder result = new StringBuilder();
        for (final Recognition recognition : recognitionList) {
            result.append(String.format("%s (%6.2f%%)%n", recognition.getTitle(), recognition.getConfidence()*100));
        }
        return result.toString();
    }

    @Override
    protected void onDestroy() {
        classifier.close();
        super.onDestroy();
    }
}
