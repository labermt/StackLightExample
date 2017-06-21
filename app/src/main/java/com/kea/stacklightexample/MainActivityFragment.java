package com.kea.stacklightexample;

import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.kea.industry.stacklight.Segment;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    static final @IdRes int INVALID_ID = -1;
    static final String CURRENT_SEGMENT_ID = "currentSegmentId";

    private RadioButton radioButtonBlink_ = null;
    private RadioButton radioButtonOn_ = null;
    private RadioButton radioButtonOff_ = null;
    private Segment stackLightSegmentCurrent_ = null;
    private LinearLayout current_segment_ = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FragmentActivity fragmentActivity = getActivity();

        current_segment_ = (LinearLayout) fragmentActivity.findViewById(R.id.current_segment);
        current_segment_.setVisibility(View.INVISIBLE);

        radioButtonBlink_ = (RadioButton) fragmentActivity.findViewById(R.id.radioButtonBlink);
        radioButtonOn_ = (RadioButton) fragmentActivity.findViewById(R.id.radioButtonOn);
        radioButtonOff_ = (RadioButton) fragmentActivity.findViewById(R.id.radioButtonOff);

        radioButtonBlink_.setEnabled(false);
        radioButtonOn_.setEnabled(false);
        radioButtonOff_.setEnabled(false);

        radioButtonBlink_.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (stackLightSegmentCurrent_ != null) {
                    stackLightSegmentCurrent_.setBlink(true);
                }
            }
        });

        radioButtonOn_.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (stackLightSegmentCurrent_ != null) {
                    stackLightSegmentCurrent_.setOnOff(true);
                }
            }
        });

        radioButtonOff_.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (stackLightSegmentCurrent_ != null) {
                    stackLightSegmentCurrent_.setOnOff(false);
                }
            }
        });

        final View.OnClickListener stackLightSegmentListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_segment_.setVisibility(View.VISIBLE);

                radioButtonBlink_.setEnabled(true);
                radioButtonOn_.setEnabled(true);
                radioButtonOff_.setEnabled(true);

                stackLightSegmentCurrent_ = (Segment) v;
                final boolean off = stackLightSegmentCurrent_.getOff();
                final boolean on = stackLightSegmentCurrent_.getOn();
                final boolean blink = stackLightSegmentCurrent_.getBlink();
                if (blink) {
                    radioButtonBlink_.setChecked(true);
                } else if (on) {
                    radioButtonOn_.setChecked(true);
                } else {
                    radioButtonOff_.setChecked(true);
                }
                final @ColorInt int color = stackLightSegmentCurrent_.getColor();
                current_segment_.setBackgroundColor(color);
            }
        };

        final Segment stackLightSegmentRed = (Segment) fragmentActivity.findViewById(R.id.stack_light_segment_red);
        final Segment stackLightSegmentAmber = (Segment) fragmentActivity.findViewById(R.id.stack_light_segment_amber);
        final Segment stackLightSegmentGreen = (Segment) fragmentActivity.findViewById(R.id.stack_light_segment_green);
        final Segment stackLightSegmentBlue = (Segment) fragmentActivity.findViewById(R.id.stack_light_segment_blue);
        final Segment stackLightSegmentWhite = (Segment) fragmentActivity.findViewById(R.id.stack_light_segment_white);

        stackLightSegmentRed.setOnClickListener(stackLightSegmentListener);
        stackLightSegmentAmber.setOnClickListener(stackLightSegmentListener);
        stackLightSegmentGreen.setOnClickListener(stackLightSegmentListener);
        stackLightSegmentBlue.setOnClickListener(stackLightSegmentListener);
        stackLightSegmentWhite.setOnClickListener(stackLightSegmentListener);

        if (savedInstanceState != null) {
            final @IdRes int currentSegmentId = savedInstanceState.getInt(CURRENT_SEGMENT_ID);
            if (currentSegmentId != INVALID_ID) {
                final Segment stackLightSegment = (Segment) fragmentActivity.findViewById(currentSegmentId);
                stackLightSegment.performClick();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        @IdRes int currentSegmentId = INVALID_ID;
        if (stackLightSegmentCurrent_ != null) {
            currentSegmentId = stackLightSegmentCurrent_.getId();
        }
        savedInstanceState.putInt(CURRENT_SEGMENT_ID, currentSegmentId);

        super.onSaveInstanceState(savedInstanceState);
    }
}
