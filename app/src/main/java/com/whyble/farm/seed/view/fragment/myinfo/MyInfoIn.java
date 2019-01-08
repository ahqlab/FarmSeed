package com.whyble.farm.seed.view.fragment.myinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class MyInfoIn {

    interface View{

        void sendResult(String s);

        void openCamera();

        void setSaveImageFile(Bitmap bitmap);
    }
    interface Presenter{

        void loadData(Context context);

        void order();

        void openCamera();

        File setSaveImageFile(Context context);

        void rotationImage ( String imageFilePath );

        void setGalleryImage (Context context, Uri img);

        void setPathToImage(Context context,String imagePath);
    }
}
