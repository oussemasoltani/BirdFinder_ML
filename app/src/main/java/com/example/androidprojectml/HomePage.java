package com.example.androidprojectml;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidprojectml.ml.LiteModelAiyVisionClassifierBirdsV13;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

public class HomePage extends AppCompatActivity {
    Button btnLoad;
    TextView tvResult;
    ImageView imageView;
    Bitmap bitmap;
    Bitmap imageBitmap;
    ActivityResultLauncher<String> mGetContent;
    int SELECT_IMAGE_CODE=1;

/*    //method permission
    void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HomePage.this,new String[]{Manifest.permission.CAMERA},11);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //permission
       // getPermission();

        imageView = (ImageView) findViewById(R.id.pickedImage);
        tvResult = findViewById(R.id.tv_Result);
        btnLoad = findViewById(R.id.btnLoad);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });


      /*  mGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    Bitmap imageBitmap = null;
                    imageBitmap = UriToBitmap(result);

                } catch (IOException e){
                    e.printStackTrace();
                }
                //ivAddImage.setImageURI(result)
                addImage.setImageBitmap(imageBitmap);
                outputGenerator(imageBitmap);

                Log.d("TAG_URI",""+ result);

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("images/*");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }
        });
        */
        /*

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + tvResult.getText().toString()));
                startActivity(intent);
            }
        });




       addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,10 );
            }
        });


*/

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + tvResult.getText().toString()));
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            Bitmap imageBitmap = null;
            try {
                imageBitmap = UriToBitmap(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(imageBitmap);
            outputGenerator(imageBitmap);



        }


    }

    private  Bitmap UriToBitmap (Uri result) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(),result);
    }


    private void outputGenerator(Bitmap imageBitmap){
        try {
            LiteModelAiyVisionClassifierBirdsV13 model = LiteModelAiyVisionClassifierBirdsV13.newInstance(HomePage.this);

            // Creates inputs for reference.
            TensorImage image = TensorImage.fromBitmap(imageBitmap);

            // Runs model inference and gets result.
            LiteModelAiyVisionClassifierBirdsV13.Outputs outputs = model.process(image);
            List<Category> probability = outputs.getProbabilityAsCategoryList();

            //probability of 0 index
            int index =0;
            float max=probability.get(0).getScore();

            for (int i=0; i<probability.size();i++ ){
                if (max<probability.get(i).getScore()){
                    max=probability.get(i).getScore();
                    index=i;
                }
            }

            Category output= probability.get(index);
            tvResult.setText(output.getLabel());


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }
  /*  //gg  @Override
    protected void onActivityResult(Uri result) {
     try {
         Bitmap imageBitmap = null;
         imageBitmap = UriToBitmap(result);

     } catch (IOException e){
         e.printStackTrace();
     }
       //ivAddImage.setImageURI(result)
        addImage.setImageBitmap(imageBitmap);
        outputGenerator(imageBitmap);

        Log.d("TAG_URI",""+ result);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    addImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
*/

}




