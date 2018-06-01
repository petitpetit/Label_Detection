package com.cambricon.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.pm.PackageManager;

import com.huawei.hiai.vision.common.ConnectionCallback;
import com.huawei.hiai.vision.common.VisionBase;
import com.huawei.hiai.vision.visionkit.image.detector.Label;
import com.huawei.hiai.vision.visionkit.image.detector.LabelContent;

import java.io.File;
import java.util.HashMap;




import com.huawei.hiai.vision.visionkit.image.detector.Label;

public class MainActivity extends AppCompatActivity implements MMListener{
    private static final String LOG_TAG = "label_detect";
    private Button btnTake;
    private Button btnSelect;
    private ImageView ivImage;
    private TextView tvLabel;

    private static final int REQUEST_IMAGE_TAKE = 100;
    private static final int REQUEST_IMAGE_SELECT = 200;

    private static final String[] LABEL_CATEGORYS = {
            "People",
            "Food",
            "Landscapes",
            "Documents",
            "Festival",
            "Activities",
            "Animal",
            "Sports",
            "Vehicle",
            "Household products",
            "Appliance",
            "Art",
            "Tools",
            "Apparel",
            "Accessories",
            "Toy"
    };

    private static final HashMap LABEL_CONTENTS = new HashMap<>();
    static {
        LABEL_CONTENTS.put(0,"people");
        LABEL_CONTENTS.put(1,"food");
        LABEL_CONTENTS.put(2,"landscapes");
        LABEL_CONTENTS.put(3,"document");
        LABEL_CONTENTS.put(4,"id card");
        LABEL_CONTENTS.put(5,"passport");
        LABEL_CONTENTS.put(6,"debit card");
        LABEL_CONTENTS.put(7,"bicycle");
        LABEL_CONTENTS.put(8,"bus");
        LABEL_CONTENTS.put(9,"ship");
        LABEL_CONTENTS.put(10,"train");
        LABEL_CONTENTS.put(11,"airplane");
        LABEL_CONTENTS.put(12,"automobile");
        LABEL_CONTENTS.put(13,"bird");
        LABEL_CONTENTS.put(14,"cat");
        LABEL_CONTENTS.put(15,"dog");
        LABEL_CONTENTS.put(16,"fish");
        LABEL_CONTENTS.put(18,"wardrobe");
        LABEL_CONTENTS.put(19,"smartphone");
        LABEL_CONTENTS.put(20,"laptop");
        LABEL_CONTENTS.put(24,"bridal veil");
        LABEL_CONTENTS.put(25,"flower");
        LABEL_CONTENTS.put(26,"toy block");
        LABEL_CONTENTS.put(27,"sushi");
        LABEL_CONTENTS.put(28,"barbecue");
        LABEL_CONTENTS.put(29,"banana");
        LABEL_CONTENTS.put(31,"watermelon");
        LABEL_CONTENTS.put(32,"noodle");
        LABEL_CONTENTS.put(34,"piano");
        LABEL_CONTENTS.put(35,"wedding");
        LABEL_CONTENTS.put(36,"playing chess");
        LABEL_CONTENTS.put(37,"basketball");
        LABEL_CONTENTS.put(38,"badminton");
        LABEL_CONTENTS.put(39,"football");
        LABEL_CONTENTS.put(40,"city overlook");
        LABEL_CONTENTS.put(41,"sunrise sunset");
        LABEL_CONTENTS.put(42,"ocean & beach");
        LABEL_CONTENTS.put(43,"bridge");
        LABEL_CONTENTS.put(44,"sky");
        LABEL_CONTENTS.put(45,"grassland");
        LABEL_CONTENTS.put(46,"street");
        LABEL_CONTENTS.put(47,"night");
        LABEL_CONTENTS.put(49,"grove");
        LABEL_CONTENTS.put(50,"lake");
        LABEL_CONTENTS.put(51,"snow");
        LABEL_CONTENTS.put(52,"mountain");
        LABEL_CONTENTS.put(53,"building");
        LABEL_CONTENTS.put(54,"cloud");
        LABEL_CONTENTS.put(55,"waterfall");
        LABEL_CONTENTS.put(56,"fog & haze");
        LABEL_CONTENTS.put(57,"porcelain");
        LABEL_CONTENTS.put(58,"model runway");
        LABEL_CONTENTS.put(59,"rainbow");
        LABEL_CONTENTS.put(60,"candle");
        LABEL_CONTENTS.put(62,"statue of liberty");
        LABEL_CONTENTS.put(63,"ppt");
        LABEL_CONTENTS.put(66,"baby carriage");
        LABEL_CONTENTS.put(67,"group photo");
        LABEL_CONTENTS.put(68,"dine together");
        LABEL_CONTENTS.put(69,"eiffel tower");
        LABEL_CONTENTS.put(70,"dolphin");
        LABEL_CONTENTS.put(71,"giraffe");
        LABEL_CONTENTS.put(72,"penguin");
        LABEL_CONTENTS.put(73,"tiger");
        LABEL_CONTENTS.put(74,"zebra");
        LABEL_CONTENTS.put(76,"lion");
        LABEL_CONTENTS.put(77,"elephant");
        LABEL_CONTENTS.put(78,"leopard");
        LABEL_CONTENTS.put(79,"peafowl");
        LABEL_CONTENTS.put(80,"blackboard");
        LABEL_CONTENTS.put(81,"balloon");
        LABEL_CONTENTS.put(83,"air conditioner");
        LABEL_CONTENTS.put(84,"washing machine");
        LABEL_CONTENTS.put(85,"refrigerator");
        LABEL_CONTENTS.put(86,"camera");
        LABEL_CONTENTS.put(88,"gun");
        LABEL_CONTENTS.put(89,"dress & skirt");
        LABEL_CONTENTS.put(91,"uav");
        LABEL_CONTENTS.put(92,"apple");
        LABEL_CONTENTS.put(93,"dumpling");
        LABEL_CONTENTS.put(94,"coffee");
        LABEL_CONTENTS.put(95,"grape");
        LABEL_CONTENTS.put(96,"hot pot");
        LABEL_CONTENTS.put(97,"diploma");
        LABEL_CONTENTS.put(102,"watch");
        LABEL_CONTENTS.put(103,"glasses");
        LABEL_CONTENTS.put(104,"ferris wheel");
        LABEL_CONTENTS.put(105,"fountain");
        LABEL_CONTENTS.put(106,"pavilion");
        LABEL_CONTENTS.put(107,"fireworks");
        LABEL_CONTENTS.put(108,"business card");
        LABEL_CONTENTS.put(109,"riding");
        LABEL_CONTENTS.put(110,"music show");
        LABEL_CONTENTS.put(111,"sailboat");
        LABEL_CONTENTS.put(112,"giant panda");
        LABEL_CONTENTS.put(113,"birthday cake");
        LABEL_CONTENTS.put(114,"birthday");
        LABEL_CONTENTS.put(115,"christmas");
        LABEL_CONTENTS.put(116,"the great wall");
        LABEL_CONTENTS.put(117,"oriental pearl tower");
        LABEL_CONTENTS.put(118,"guangzhou tower");
        LABEL_CONTENTS.put(120,"tower");
        LABEL_CONTENTS.put(121,"rabbit");
        LABEL_CONTENTS.put(123,"trolley case");
        LABEL_CONTENTS.put(124,"nail");
        LABEL_CONTENTS.put(125,"guitar");
    }




    @Override
    public void onTaskCompleted(Label label) {
        ivImage.setImageBitmap(bmp);

        if (label == null){
            tvLabel.setText("not get label");
        }else {
            String strLabel = "category:";
            int categoryID = label.getCategory();
            if (categoryID<0||categoryID>=LABEL_CATEGORYS.length){
                strLabel += "Others";
            }else {
                strLabel += LABEL_CATEGORYS[categoryID];
            }
            strLabel += ", probability: " + String.valueOf(label.getCategoryProbability()) + "\n";

            ListlabelContents = label.getLabelContent();
            for (LabelContent labelContent : labelContents) {
                strLabel += "labelContent: ";
                int labelContentID = labelContent.getLabelId();
                String name = LABEL_CONTENTS.get(labelContentID);
                if (name == null) {
                    strLabel += "other";
                } else {
                    strLabel += name;
                }
                strLabel += ", probability: " + String.valueOf(labelContent.getProbability()) + "\n";
            }
            tvLabel.setText(strLabel);
        }

        btnTake.setEnabled(true);
        btnSelect.setEnabled(true);

        if (dialog != null) {
            dialog.dismiss();
        }
    }
        }

    }

    private Uri fileUri;
    private Bitmap bmp;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = (ImageView) findViewById(R.id.image);
        tvLabel = (TextView) findViewById(R.id.label);


        //configure buttons
        btnTake = (Button) findViewById(R.id.btn_take);
        btnSelect = (Button)findViewById(R.id.btn_select);

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetect();
                checkCameraPermission();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetect();
                checkStoragePermission();
            }
        });
        //To connect HiAi Engine service using VisionBase
        VisionBase.init(MainActivity.this, new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
                //This callback method is called when the connection to the service is successful.
                //Here you can initialize the detector class, mark the service connection status, and more.
                Log.i(LOG_TAG, "onServiceConnect ");
            }

            @Override
            public void onServiceDisconnect() {
                //This callback method is called when disconnected from the service.
                //You can choose to reconnect here or to handle exceptions.
                Log.i(LOG_TAG, "onServiceDisconnect");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_IMAGE_TAKE || requestCode == REQUEST_IMAGE_SELECT) && resultCode == RESULT_OK) {
            String imgPath;

            if (requestCode == REQUEST_IMAGE_TAKE) {
                imgPath = Environment.getExternalStorageDirectory()+ fileUri.getPath();
            } else {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = MainActivity.this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
            }
            Log.d(LOG_TAG, "imgPath = " + imgPath);
            bmp = BitmapFactory.decodeFile(imgPath);
            dialog = ProgressDialog.show(MainActivity.this,
                    "Predicting...", "Wait for one sec...", true);

            LabelDetectTask cnnTask = new LabelDetectTask(MainActivity.this);
            cnnTask.execute(bmp);
        } else {
            btnTake.setEnabled(true);
            btnSelect.setEnabled(true);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    REQUEST_IMAGE_TAKE);
        }else {
            chooseImageAndClassify();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    REQUEST_IMAGE_SELECT);
        } else {
            takeImageAndClassify();
        }
    }

    private void takeImageAndClassify(){
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(),"Images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder,"image.jpg");
        Uri fileUri = Uri.fromFile(image);
        takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        if (takeImageIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takeImageIntent,REQUEST_IMAGE_TAKE);
        }
    }

    private void chooseImageAndClassify(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
    }

    private void initDetect(){
        btnTake.setEnabled(false);
        btnSelect.setEnabled(false);
    }
}
