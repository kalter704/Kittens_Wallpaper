package com.aleksandr.nikitin.kittens_wallpaper;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageFragmentWithPremiumWallpaper extends Fragment {
    static final String ARGUMENT_IMAGE_ID = "arg_image_id";
    static final String ARGUMENT_IMAGE_NUMBER = "arg_image_number";
    static final String ARGUMENT_SET_BUTTON = "arg_set_button";
    int imageId;
    int numberOfImg;

    LinearLayout linLayoutWithTextAndBtn;

    LinearLayout linImg;
    ImageView imgRotate;
    Animation animRotate;
    Animation animAlphaVilible;
    Animation animAlphaInvilible;

    onShowVideoAdListener showVideoAdListener;

    static Fragment newInstance(int image, int number) {
        PageFragmentWithPremiumWallpaper pageFragment = new PageFragmentWithPremiumWallpaper();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_IMAGE_ID, image);
        arguments.putInt(ARGUMENT_IMAGE_NUMBER, number);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageId = getArguments().getInt(ARGUMENT_IMAGE_ID);
        numberOfImg = getArguments().getInt(ARGUMENT_IMAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_with_premium_wallpaper, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);
        imageView.setImageResource(imageId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            imageView.setAlpha((float) 0.3);
        }

        linImg = (LinearLayout) view.findViewById(R.id.linImg);
        imgRotate = (ImageView) view.findViewById(R.id.imageRotate);
        animRotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotation_proccess);
        animAlphaVilible = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_vilible);
        animAlphaInvilible = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_invilible);

        animAlphaVilible.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imgRotate.startAnimation(animRotate);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animAlphaInvilible.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linImg.setVisibility(View.INVISIBLE);
                imgRotate.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        linLayoutWithTextAndBtn = (LinearLayout) view.findViewById(R.id.linLayoutWithTextAndBtn);

        Button btnWatchVideo = (Button) view.findViewById(R.id.btnWatchVideo);
        btnWatchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linLayoutWithTextAndBtn.setVisibility(View.INVISIBLE);
                linImg.startAnimation(animAlphaVilible);
                showVideoAdListener.onShowVideoAd(numberOfImg);
            }
        });


        return view;

    }

    public interface onShowVideoAdListener {
        public void onShowVideoAd(int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            showVideoAdListener = (onShowVideoAdListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

}
