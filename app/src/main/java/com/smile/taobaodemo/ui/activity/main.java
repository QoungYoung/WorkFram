//package com.mycompany.myapp2;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import android.view.Window;
//import android.support.v7.app.ActionBarActivity;
//import android.widget.Button;
//import android.view.View.OnClickListener;
//import android.view.View;
//
//import com.smile.taobaodemo.R;
//
//public class MainActivity extends ActionBarActivity
//{
//    int a=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        //取消标题
//      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.main);
//     /*   TextView q1=(TextView) findViewById(R.id.mainTextView1);
//        TextView q2=(TextView) findViewById(R.id.mainTextView2);
//
//        StringBuffer buffer = new StringBuffer();
//        try {
//            FileInputStream fis = new FileInputStream("/storage/emulated/0/mythroad/com.skymobi.mopowydtkd0l/dataCache/Login_ List_ Accountlnfo_ History");
//            InputStreamReader isr = new InputStreamReader(fis,"GB2312");//文件编码Unicode,UTF-8,ASCII,GB2312,Big5
//            Reader in = new BufferedReader(isr);
//            int ch;
//            while ((ch = in.read()) > -1) {
//                buffer.append((char)ch);
//            }
//            in.close();
//            q1.setText(buffer.toString()); //buffer.toString())就是读出的内容字符
//        } catch (IOException e) {
//            q1.setText("文件不存在!");
//            }
//
//        StringBuffer buffer2 = new StringBuffer();
//        try {
//            FileInputStream fis2 = new FileInputStream("/storage/emulated/0/mythroad/com.skymobi.mopowydtkd0l/dataCache/sns_ properties_ key");
//            InputStreamReader isr2 = new InputStreamReader(fis2,"GB2312");//文件编码Unicode,UTF-8,ASCII,GB2312,Big5
//            Reader in2 = new BufferedReader(isr2);
//            int ch2;
//            while ((ch2 = in2.read()) > -1) {
//                buffer.append((char)ch2);
//            }
//            in2.close();
//            q2.setText(buffer2.toString()); //buffer.toString())就是读出的内容字符
//        } catch (IOException e) {
//            q2.setText("文件不存在!");
//        }*/
//        final TextView tv=(TextView) findViewById(R.id.mainTextView1);
//        Button b1=(Button) findViewById(R.id.mainButton1);
//        Button b2=(Button) findViewById(R.id.mainButton2);
//
//        b1.setOnClickListener(new OnClickListener(){
//
//                @Override
//                public void onClick(View p1)
//                {
//                    //先
//                 int   factorOneInt1 = Integer.parseInt(tv.getText().toString());
//                    //计算两个值的积
//                  int  result = factorOneInt1 + 1;
//                 //   int b=a+1;
//                  //  a++;
//                    tv.setText(result+"");
//                    // TODO: Implement this method
//                }
//            });
//
//        b2.setOnClickListener(new OnClickListener(){
//
//                @Override
//                public void onClick(View p1)
//                {
//                    a=0;
//                    a++;
//                    tv.setText(a+"");
//                    // TODO: Implement this method
//                }
//            });
//
//        }
//}
