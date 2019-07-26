package com.scandit.viewbasedmatrixscansample.scan.bubbles;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scandit.viewbasedmatrixscansample.R;
import com.scandit.viewbasedmatrixscansample.utility.UiUtils;

public class MaximisedBubble extends BaseBubble {

    private static final int WIDTH = 200;
    private static final int FULL_INDICATOR_HEIGHT = 104;
    private static final int INDICATOR_WITHOUT_DELIVERY_HEIGHT = 82;

    private int deliveryVisibility;
    private Drawable backgroundDrawable;

    private BubbleData bubbleData;

    public MaximisedBubble(Context context, BubbleData bubbleData) {
        super(context, bubbleData);
        this.bubbleData = bubbleData;

        if (!this.bubbleData.getDeliveryDate().isEmpty()) {
            deliveryVisibility = View.VISIBLE;
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.bg_transparent_black);
        } else {
            deliveryVisibility = View.GONE;
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.bg_rounded_bottom_black);
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        if (!bubbleData.getDeliveryDate().isEmpty()) {
            return FULL_INDICATOR_HEIGHT;
        } else {
            return INDICATOR_WITHOUT_DELIVERY_HEIGHT;
        }
    }

    @Override
    public View getView(Context context, LayoutInflater inflater) {
        int targetHeight = getHeight();
        View view = inflater.inflate(R.layout.bubble_maximised, null);
        view.findViewById(R.id.top).setBackground(backgroundDrawable);

        ((TextView) view.findViewById(R.id.stock_header)).setTextColor(highlightColor);
        ((TextView) view.findViewById(R.id.stock_value)).setText(String.valueOf(bubbleData.getStock()));

        ((TextView) view.findViewById(R.id.online_header)).setTextColor(highlightColor);
        ((TextView) view.findViewById(R.id.online_value)).setText(String.valueOf(bubbleData.getOnline()));

        view.findViewById(R.id.bottom).setVisibility(deliveryVisibility);

        ((TextView) view.findViewById(R.id.delivery)).setText(bubbleData.getDeliveryDate());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.findViewById(R.id.header).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
            view.findViewById(R.id.bottom).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
            view.findViewById(R.id.triangle).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
        }

        view.setLayoutParams(new FrameLayout.LayoutParams(
                UiUtils.pxFromDp(context, WIDTH), UiUtils.pxFromDp(context, targetHeight)));

        return view;
    }
}
