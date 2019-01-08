package com.whyble.farm.seed.view.fragment.myinfo;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.gson.Gson;
import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.SharedPrefManager;
import com.whyble.farm.seed.databinding.FragmentMyInfoBinding;
import com.whyble.farm.seed.domain.User;
import com.whyble.farm.seed.domain.seeds.shipment.Shipment;
import com.whyble.farm.seed.user.signup.update.UpdateActivity;
import com.whyble.farm.seed.util.TextManager.TextManager;
import com.whyble.farm.seed.view.board.BoardActivity;
import com.whyble.farm.seed.view.notice.NoticeActivity;
import com.whyble.farm.seed.view.sub.shipment.ShipmentActivity;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements MyInfoIn.View{



    public SharedPrefManager sharedPrefManager;




    FragmentMyInfoBinding binding;

    MyInfoIn.Presenter presenter;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    private Uri photoUri;

    private String imageFilePath;

    BottomDialog builder;

    BottomSheetDialog dialog;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false);
        binding.setActivity(this);
        presenter = new MyInfoPresenter(this);
        presenter.loadData(getActivity());
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        if(!sharedPrefManager.getStringExtra(TextManager.VALID_USER_IMAGE_PATH).matches("")){
            presenter.setPathToImage(getActivity(), sharedPrefManager.getStringExtra(TextManager.VALID_USER_IMAGE_PATH));
        }
        presenter.order();
        return binding.getRoot();
    }

    public void onClickEditUserInfo(View view){
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        startActivity(intent);
    }

    public void onClickNotice(View view){
        Intent intent = new Intent(getActivity(), NoticeActivity.class);
        startActivity(intent);
    }

    public void onClickShipment(View view) {
        Intent intent = new Intent(getActivity(), ShipmentActivity.class);
        startActivity(intent);
    }

    public void onClickBoard(View view){
        Intent intent = new Intent(getActivity(), BoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendResult(String s) {
        Gson gson = new Gson();
        User user = gson.fromJson(s, User.class);
        binding.setDomain(user);
    }

    @Override
    public void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if ( cameraIntent.resolveActivity(getActivity().getPackageManager()) != null ) {
            File photo = presenter.setSaveImageFile(getActivity());
            if ( photo != null ) {
                imageFilePath = photo.getAbsolutePath();

                photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), photo);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        }
    }

    @Override
    public void setSaveImageFile(Bitmap bitmap) {
        binding.profile.setImageBitmap(bitmap);
    }

    public void onClickCameraIcon(View view){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.image_select_bottom_custom_view);
        dialog.show();

        Button camera = (Button) dialog.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    Toast.makeText(getActivity(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                    presenter.openCamera();
                }
            }
        });

        Button gallery = (Button) dialog.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionStorage == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                } else {
                    openGallery();
                    dialog.dismiss();
                }
            }
        });
    }

    public void openGallery () {
       Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
        Toast.makeText(getActivity(), "gallery", Toast.LENGTH_LONG).show();
        //startActivityForResult( Intent.createChooser( new Intent(Intent.ACTION_GET_CONTENT) .setType("image/*"), "Choose an image"), GALLERY_REQUEST);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            presenter.openCamera(); // start WIFIScan
                        } else {
                            Toast.makeText(getActivity(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case STORAGE_PERMISSION_CODE :
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            openGallery();
                        } else {
//                            Toast.makeText(getApplicationContext(), "CAMERA permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.rotationImage(imageFilePath);
            Log.e("HJLEE", "pic imagePath : " + imageFilePath);
            sharedPrefManager.putStringExtra(TextManager.VALID_USER_IMAGE_PATH, imageFilePath);
        } else if ( requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK ) {
            String imagePath = getRealPathFromURI(getActivity(), data.getData());
            Log.e("HJLEE", "gel imagePath : " + imagePath);
            sharedPrefManager.putStringExtra(TextManager.VALID_USER_IMAGE_PATH, imagePath);
            presenter.setGalleryImage(getActivity(), data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
}
