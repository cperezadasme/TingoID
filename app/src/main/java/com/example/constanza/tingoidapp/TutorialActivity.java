package com.example.constanza.tingoidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vPager;
    private int[] layout = {R.layout.first_layout,R.layout.second_layout,R.layout.third_layout,R.layout.fourth_layout,R.layout.fifth_layout};
    private VpagerAdapter vpagerAdapter;

    private LinearLayout Dots_layout;
    private ImageView[] dots;

    private Button Next, Skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_tutorial);

        vPager = (ViewPager)findViewById(R.id.viewpager);
        vpagerAdapter = new VpagerAdapter(layout,this);
        vPager.setAdapter(vpagerAdapter);

        Dots_layout = (LinearLayout) findViewById(R.id.dots);
        Next = (Button)findViewById(R.id.next);
        Skip = (Button)findViewById(R.id.skip);
        Next.setOnClickListener(this);
        Skip.setOnClickListener(this);
        create_Dots(0);

        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                create_Dots(position);
                if(position==layout.length-1){
                    Next.setText("¡Vamos!");
                    Skip.setVisibility(View.INVISIBLE);
                }
                else {
                    Next.setText("siguiente");
                    Skip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void create_Dots(int currPos){
        if(Dots_layout!=null)
            Dots_layout.removeAllViews();

        dots = new ImageView[layout.length];

        for (int i = 0; i < layout.length; i++){
            dots[i] = new ImageView(this);
            if (i == currPos){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_layout.addView(dots[i],params);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                loadNextSlide();
                break;
            case R.id.skip:
                loadHome();
                break;
        }
    }

    private void loadHome(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    private void loadNextSlide(){
        int next_slide = vPager.getCurrentItem()+1;

        if(next_slide<layout.length){
            vPager.setCurrentItem(next_slide);
        }
        else {
            loadHome();
        }
    }
}
