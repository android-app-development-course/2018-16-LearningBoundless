package com.scnu.learningboundless.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
//import jp.wasabeef.glide.transformations.BlurTransformation;
//
//import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
//import jp.wasabeef.glide.transformations.BlurTransformation;
//
//import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MyActivity extends BaseActivity {

    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CROP_PHOTO = 2;//裁剪
    public static final int SELECT_PIC = 0;//从相册选择
    @BindView(R.id.lv_personal_information)
    protected ListView mLvPersonalInformation;

    @BindView(R.id.iv_big_head)
    protected ImageView myHeadPhoto;

//    @BindView(R.id.iv_headimage)
//    protected ImageView iv_headimage;

    @BindView(R.id.iv_head_bg)
    protected ImageView iv_head_bg;

    private CharSequence[] its = {"拍照", "从相册选择"};
    private Uri imageUri; //图片路径
    private String filename; //图片名称

    /**
     * 图像转字节
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 图像转String
     *
     * @param bitmap
     * @return
     */
    public static String Bitmap2String(Bitmap bitmap) {
        return Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap String2Bitmap(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把bitmap转成圆形
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        //取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        //构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initTransparentStatusBar();
        initMyList();
//        myHeadPhoto.setImageResource(R.drawable.add);
    }

    /**
     * 初始化透明状态栏
     */
    private void initTransparentStatusBar() {
        // 实现透明状态栏效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_back})
    public void handleAllClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_back:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     *
     */
    private void initMyList() {
        int[] imageIdList = new int[]{R.drawable.collect, R.drawable.history, R.drawable.score, R.drawable.settings};

        String[] titleList = new String[]{"我的收藏", "浏览历史", "我的积分", "设置"};

        List<Map<String, Object>> messageItemsList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < imageIdList.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageIdList[i]);
            map.put("title", titleList[i]);

            messageItemsList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(MyActivity.this, messageItemsList, R.layout.
                personal_information_item, new String[]{"image", "title"}, new int[]{R.id.image, R.id.title});

        mLvPersonalInformation.setAdapter(adapter);
    }

    @OnClick({R.id.iv_big_head})
    public void selectHeadPhoto() {
        new AlertDialog.Builder(MyActivity.this).setTitle("更换头像").setItems(its, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                switch (which) {
                    case 0://拍照
                        //先检查获取运行时权限
                        if (ActivityCompat.checkSelfPermission(MyActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //权限发生了改变 true  //  false,没有权限时
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MyActivity.this, Manifest.permission.CAMERA)) {
                                new AlertDialog.Builder(MyActivity.this).setTitle("title").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 请求授权
                                        ActivityCompat.requestPermissions(MyActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                                    }
                                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //不请求权限的操作
                                    }
                                }).create().show();
                            } else {
                                ActivityCompat.requestPermissions(MyActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                            }
                        } else {
                            if (ContextCompat.checkSelfPermission(MyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                //权限发生了改变 true  //  false,没有权限时
                                if (ActivityCompat.shouldShowRequestPermissionRationale(MyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    new AlertDialog.Builder(MyActivity.this).setTitle("title").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 请求授权
                                            ActivityCompat.requestPermissions(MyActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                                        }
                                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //不请求权限的操作
                                        }
                                    }).create().show();
                                } else {
                                    ActivityCompat.requestPermissions(MyActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                                }

//                                申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
//                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                            } else {
                                //图片名称 时间命名
                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                                Date date = new Date(System.currentTimeMillis());
                                filename = format.format(date);  //图片名称 时间命名
                                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM); //保存在外部存储的路径
                                File outputImage = new File(path, filename + ".jpg");
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //将File对象转换为Uri并启动照相程序
//                            imageUri = Uri.fromFile(outputImage);     //下面那句和这句的注释是用来避免运行时出现 曝光 错误
                                imageUri = FileProvider.getUriForFile(MyActivity.this, MyActivity.this.getApplicationContext().getPackageName() + ".provider", outputImage);
                                Intent tTntent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
                                tTntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
                                startActivityForResult(tTntent, TAKE_PHOTO); //启动照相
                            }
                        }

                        break;
                    case 1://从相册选择
                        intent.setAction(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");

                        intent.putExtra("return-data", true);
                        intent.putExtra("crop", "true");
                        //设置宽高比例

                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        //设置裁剪图片宽高、
                        intent.putExtra("outputX", 450);
                        intent.putExtra("outputY", 450);
                        startActivityForResult(intent, SELECT_PIC);
                        break;
                }
            }
        }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = null;
        Uri extras;
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case SELECT_PIC://相册
                extras = data.getData();
                try {
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(extras));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (photo != null) {
                    myHeadPhoto.setImageBitmap(photo);//设置/裁剪图片
//                    iv_headimage.setImageBitmap(photo); //设置侧滑菜单的头像
                    Glide.with(MyActivity.this).load(photo)     //设置头像后的背景为模糊图片
                            .into(iv_head_bg);
                }
                break;
            case TAKE_PHOTO://相机            /***/
                try {
                    Intent intent = new Intent("com.android.camera.action.CROP"); //剪裁
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    //设置宽高比例
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    //设置裁剪图片宽高
                    intent.putExtra("outputX", 450);
                    intent.putExtra("outputY", 450);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    Toast.makeText(MyActivity.this, "剪裁图片", Toast.LENGTH_SHORT).show();
                    //广播刷新相册
                    Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intentBc.setData(imageUri);
                    this.sendBroadcast(intentBc);
                    Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    myHeadPhoto.setImageBitmap(bitmap1);

                    Glide.with(MyActivity.this).load(bitmap1) //设置头像后的背景为模糊图片
                            .into(iv_head_bg);

//                    iv_headimage.setImageBitmap(bitmap1);   //设置侧滑菜单的头像
//                    startActivityForResult(intent, CROP_PHOTO); //设置裁剪参数显示图片至ImageView
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CROP_PHOTO:
                try {
                    //图片解析成Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

//                    myHeadPhoto.setImageBitmap(bitmap);
                    myHeadPhoto.setImageBitmap(toRoundBitmap(bitmap));
//                    UserController.updateHeadImage(userId, headImage, handler);       //上传到服务器
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}

