## TODOS, TODOS, TODOS
### 1- first things first, create Android Project solution :P
### 2- Add Jitpack maven repo
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
### 3- Add dependency to tensorflowlite
```
    implementation 'org.tensorflow:tensorflow-lite:1.13.1'
```

### 4- Add dependency to CameraView
```
    implementation 'com.github.smellouk:Camera.Android:stream'
```
Note: Please use the version `stream` because it contains custom code that will helps us to run the workshop smoothly.
For more information about the CameraView library please check this repo : [Camera View Repo](https://github.com/smellouk/Camera.Android)
Try to get familiar with the library before you start implementing step 6

### 5- Download MobileNet Model
* Download Quantized MobileNet model from here [MobileNet](http://download.tensorflow.org/models/mobilenet_v1_2018_08_02/mobilenet_v1_1.0_224_quant.tgz)
* Unzip the downloaded file to your asset folder of your app

For more detail about MobileNet model please check [this](https://www.tensorflow.org/lite/models/image_classification/overview)

### 6- Download/Import those classes to your project
* [MobileNetClassifier](https://github.com/smellouk/Android-ML-Workshop/blob/Part1-Solution/app/src/main/java/io/mellouk/mlpart1/classifier/MobileNetClassifier.java)
* [Recognition](https://github.com/smellouk/Android-ML-Workshop/blob/Part1-Solution/app/src/main/java/io/mellouk/mlpart1/classifier/Recognition.java)

### 7- Create an Activity which will contain your classification page
### 8- Add Camera View to your activity
Note: Don't forget to handle on resume and on stop camera view lifecycle

Note2: Don't forget to handle Camera permissions :P

### 9- Set Image Streaming Listener
```
        mCameraView.setImageStreamingListener(bitmap -> {
        ....
        });
```
Note: The returned bitmap is running on different thread than Main thread

### 10- Create instance of MobileNetClassifier
```
final MobileNetClassifier classifier = new MobileNetClassifier(this, 3); //3 represent number of threads
```
Note: MobileNetClassifier instance creation is heavy which mean will block your MainThread you have two option to create your instance :
1- Creating a thread which will handle your instance creation
2- Using the Streaming thread to create your instance
Something similar to
```
        mCameraView.setImageStreamingListener(bitmap -> {
                if(classifier == null){
                    classifier = new MobileNetClassifier(this, 3);
                }
        });
```
### 11- Start classifying or recognizing !!!
To recognize your object you just need to call `run` method of your classifier
```
        mCameraView.setImageStreamingListener(bitmap -> {
                if(classifier == null){
                    classifier = new MobileNetClassifier(this, 3);
                }

                final List<Recognition> recognitionList = classifier.recognizeImage(bitmap)
        });
```

### 12- Render the recognition result list !
Kind of preview you will get after finishing all steps

<img src="media1.png" width="400"/>

## Congratulation now you are a Machine Learning Expert guy, next Part is to train your laptop to make you coffee