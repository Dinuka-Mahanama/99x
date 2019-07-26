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
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.scandit.viewbasedmatrixscansample.R;

public abstract class BaseBubble {

    private Context context;
    private int greenThreshold;
    private int yellowThreshold;

    public int highlightColor;

    public BaseBubble(Context context, BubbleData bubbleData) {
        this(context, bubbleData, 15, 2);
    }

    public BaseBubble(Context context, BubbleData bubbleData, int greenThreshold, int yellowThreshold) {
        this.context = context;
        this.greenThreshold = greenThreshold;
        this.yellowThreshold = yellowThreshold;

        highlightColor = getHighlightColor(bubbleData.getStock());
    }

    private int getHighlightColor(int inStock) {
        if (inStock > greenThreshold) {
            return ContextCompat.getColor(context, R.color.transparentGreen);
        } else if (inStock > yellowThreshold) {
            return ContextCompat.getColor(context, R.color.transparentYellow);
        } else {
            return ContextCompat.getColor(context, R.color.transparentRed);
        }
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract View getView(Context context, LayoutInflater inflater);
}
