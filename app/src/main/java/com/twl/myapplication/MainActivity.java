package com.twl.myapplication;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.exam.bean.module.login.entity.UserBean;
import com.exam.lib.tlog.TLog;
import com.exam.http.callback.AbsRequestCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MainActivity";
    private Map<Integer, String> mIntegerStringMap = new HashMap<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    public short mShort;
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Reflection.exemptAll();
        if (savedInstanceState != null) Log.d(TAG, "onCreate: ");

        TextView textView = findViewById(R.id.tv_main);
        textView.setText(getAbi());
        new Thread();
        Log.d(TAG, "onCreate: ");
        List<Class> list = new ArrayList<>();
        getSubClass(UserBean.class, list);
        for (Class aClass : list) {
            Log.d(TAG, "onCreate: " + aClass.getName() + ":" + getSUID(aClass));
        }
        new Message();
        new ArrayList<Integer>();

        new Message();
        new Thread();
        TLog.debug(TAG, "f11" + 1);

        Log.d(TAG, "onCreate: ");

        TLog.error("11111111111111111111111111test11111111111111111", "hah");

        TLog.debug(TAG, "%d", "1");

        TLog.debug(TAG, "%d", "");



        TLog.info(TAG, "fuck");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("postDelayed");
            }
        }, 1000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("postDelayed");
            }
        }, 2000);
        if (mHandler.hasMessages(1)) Log.d(TAG, "onCreate: ");
        AbsRequestCallback absRequestCallback3 = new AbsRequestCallback<String>() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
                File file;
            }
        };



        new AbsRequestCallback<String>() {
            @Override
            public void onSuccess() {
                Dialog dialog;
            }
        };

        new Thread();
        if (false){
            this.getApplication().startActivity(null);
            this.getApplicationContext().startActivity(null);
        }


        //ScopedStorageForSdCardDetector
        new File("/sdcard");
        String path = Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 生成executionData
     */
    public void generateCoverageFile() {
        OutputStream out = null;
        try {
            out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/code_coverage.ec", false); //在SDcard根目录下生产检测报告，文件名自定义
            Object agent = Class.forName("org.jacoco.agent.rt.RT").getMethod("getAgent").invoke(null);
            // 这里之下就统计不到了
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class).invoke(agent, false));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getAbi() {
        StringBuilder info = new StringBuilder();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String[] abis = Build.SUPPORTED_ABIS;
            for (String abi : abis) {
                info.append(abi).append(";\r\n");
            }
        } else {
            info.append(Build.CPU_ABI).append(";\r\n").append(Build.CPU_ABI2);
        }
        return info.toString();
    }

    public static void getSubClass(Class cl, List<Class> list) {
        if (cl != null) {
            String canonical = cl.getCanonicalName();
            if (canonical.startsWith("java.")
                    || canonical.startsWith("android.")) {
                return;
            }
            if (list.contains(cl)) {
                return;
            }
            list.add(cl);
            Field[] fields = cl.getDeclaredFields();

            for (Field field : fields) {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) {
                    continue;
                }
                Class type = field.getType();
                if (type == String.class
                        || type == boolean.class || type == Boolean.class
                        || type == double.class || type == Double.class
                        || type == float.class || type == Float.class
                        || type == long.class || type == Long.class
                        || type == int.class || type == Integer.class
                        || type == short.class || type == Short.class
                        || type == byte.class || type == Byte.class
                        || type == byte[].class || type == Byte[].class
                        || type == char.class || type == Character.class) {
                    continue;
                }

                if (field.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) field.getGenericType();
                    Type[] actualTypeArguments = pt.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        if (actualTypeArgument instanceof Class)
                            getSubClass((Class) actualTypeArgument, list);
                    }
                    continue;
                }

                getSubClass(type, list);
            }
        }
    }

    public static Long getSUID(Class<?> cl) {
        try {
            Method method = ObjectStreamClass.class.getDeclaredMethod("computeDefaultSUID", Class.class);
            method.setAccessible(true);
            long suid = (long) method.invoke(null, cl);
            return suid;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
