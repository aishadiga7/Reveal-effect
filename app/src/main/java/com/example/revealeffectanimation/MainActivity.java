package com.example.revealeffectanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private RelativeLayout mActionBarCustom;
    private ImageView mAttachmentImageView;
    private LinearLayout mRevealView;
    private boolean hidden = true;
    private ImageView mGalleryIconImagView,mCameraIconImageView,mVideoIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialzeUI();
    }

    private void initialzeUI() {
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.GONE);
        mGalleryIconImagView = (ImageView) mRevealView.findViewById(R.id.gallery_icon);
        mGalleryIconImagView.setOnClickListener(this);
        mCameraIconImageView = (ImageView) mRevealView.findViewById(R.id.camera_icon);
        mCameraIconImageView.setOnClickListener(this);
        mVideoIconImageView = (ImageView) mRevealView.findViewById(R.id.video_icon);
        mVideoIconImageView.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mActionBarCustom = (RelativeLayout) inflater.inflate(R.layout.toolbar_layout, null);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

         mAttachmentImageView= (ImageView) mActionBarCustom.findViewById(R.id.attachment);
         mAttachmentImageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // call revel effect animation and show option to choose mulitmedia
                 showRevealEffectAnimation();
             }
         });

        getSupportActionBar().setCustomView(mActionBarCustom);
    }


    // This function call when click on attachment button of actionbar and display reveal animation layout
    private void showRevealEffectAnimation() {
        int cx = (mRevealView.getLeft() + mRevealView.getRight());
        int cy = mRevealView.getTop();
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

        //Below Android LOLIPOP Version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator =
                    ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(700);
            SupportAnimator animatorReverse = animator.reverse();

            if (hidden) {
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
            } else {
                animatorReverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animatorReverse.start();
            }
        }
        // Android LOLIPOP And ABOVE Version
        else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;
            } else {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                anim.start();
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == mGalleryIconImagView.getId()) {
            // Invisible reveal animation layout
            mRevealView.setVisibility(View.INVISIBLE);
            hidden = true;

            Toast.makeText(this,"Clicked Gallery",Toast.LENGTH_SHORT).show();

        } else if (v.getId() == mCameraIconImageView.getId()) {
            // Invisible reveal animation layout
            mRevealView.setVisibility(View.INVISIBLE);
            hidden = true;

            Toast.makeText(this,"Clicked Camera",Toast.LENGTH_SHORT).show();

        } else if (v.getId() == mVideoIconImageView.getId()) {
            mRevealView.setVisibility(View.INVISIBLE);
            hidden = true;

            Toast.makeText(this,"Clicked Video",Toast.LENGTH_SHORT).show();
        }
    }
}
