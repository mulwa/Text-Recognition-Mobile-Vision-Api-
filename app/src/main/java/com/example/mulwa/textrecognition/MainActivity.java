package com.example.mulwa.textrecognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {
    private ImageView m_image_view;
    private Button m_btn_process;
    private TextView m_resuilt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_image_view = (ImageView) findViewById(R.id.img_view);
        m_btn_process = (Button) findViewById(R.id.btnProcess);
        m_resuilt = (TextView) findViewById(R.id.tv_txt_found);

        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.creditcard);
        m_image_view.setImageBitmap(bitmap);

        m_btn_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if(!textRecognizer.isOperational()){
                    showToast("Text detector dependencies are not ready");

                }else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i =0; i<items.size(); i++){
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    m_resuilt.setText(stringBuilder.toString());

                }
            }
        });


    }
    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
