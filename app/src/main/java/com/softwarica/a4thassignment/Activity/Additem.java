package com.softwarica.a4thassignment.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.softwarica.a4thassignment.Model.API;
import com.softwarica.a4thassignment.Model.ImageFile;
import com.softwarica.a4thassignment.Model.Item;
import com.softwarica.a4thassignment.Model.Url;
import com.softwarica.a4thassignment.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Additem extends AppCompatActivity implements View.OnClickListener {
    EditText Itemname, ItemPrice, ItemDesc, Itemimg;
    Button btnAdd, btnBrowse, btnUpload;
    ImageView imgview;
    Uri imguri;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 1;
    ActionBar action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        initiate();
        action=getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        action.setTitle("Add Items");
    }
//for back button in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
//this function will be called when user presses browse
    public void opengallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(getIntent().createChooser(gallery, "Choose image"), PICK_IMAGE);

    }
//this will set Image name into Editext when Upload is clicked
    public void addImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            File file = new File(this.getCacheDir(), "image.jpg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), rb);
            Url url = new Url();
            Call<ImageFile> imgCall = url.createInstanceofRetrofit().uploadImage(body);
            imgCall.enqueue(new Callback<ImageFile>() {

                @Override
                public void onResponse(Call<ImageFile> call, Response<ImageFile> response) {
                    Itemimg.setText(response.body().getFilename());
                }

                @Override
                public void onFailure(Call<ImageFile> call, Throwable t) {
                    Toast.makeText(Additem.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(Additem.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
//initiating all the widgets and on Click listener
    public void initiate() {
        Itemname = findViewById(R.id.etitemname);
        ItemPrice = findViewById(R.id.etitemprice);
        ItemDesc = findViewById(R.id.etitemdesc);
        Itemimg = findViewById(R.id.etitemimg);
        imgview = findViewById(R.id.imgview);

        btnAdd = findViewById(R.id.btnAddItem);
        btnAdd.setOnClickListener(this);
        btnBrowse = findViewById(R.id.btnbrowse);
        btnBrowse.setOnClickListener(this);

        btnUpload = findViewById(R.id.btnupload);
        btnUpload.setOnClickListener(this);
    }
//this will add all the data of form to API
    public void addItem() {
            Url url = new Url();
            Item item = new Item(Itemname.getText().toString(), ItemPrice.getText().toString(), Itemimg.getText().toString(), ItemDesc.getText().toString());
            Call<Void> additem = url.createInstanceofRetrofit().addNewItem(item);
            additem.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(Additem.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Additem.this,Dashboard.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(Additem.this, "Error:"+t, Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            //this will set Bitmap to ImageView
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
                imguri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                    imgview.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//this will validate the add item form
        public boolean validation(){
        String itemname=Itemname.getText().toString();
        String price=ItemPrice.getText().toString();
        String des=ItemDesc.getText().toString();
        String image=Itemimg.getText().toString();
        if(itemname.isEmpty()){
            Itemname.setError("Please enter the item name");
            Itemname.requestFocus();
            return false;
        }
            if(price.isEmpty()){
                ItemPrice.setError("Please enter the item name");
                ItemPrice.requestFocus();
                return false;
            }
            if(des.isEmpty()){
                ItemDesc.setError("Please enter the item name");
                ItemDesc.requestFocus();
                return false;
            }
            if(image.isEmpty()){
                Itemimg.setError("Please Select the image first");
                return false;
            }


            return true;
        }

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.btnAddItem:
                    if(validation()==true){
                        addItem();
                    }else{
                        Toast.makeText(Additem.this,"Cannot Add Item",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.btnbrowse:
                    opengallery();
                    break;
                case R.id.btnupload:
                    addImage(bitmap);
                    break;
            }
        }
    }
