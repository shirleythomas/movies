package com.project.shirley.popularmovies.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by Shirley Thomas on 3/7/2016.
 * To wrap synopsis around poster.
 * Referred:http://stackoverflow.com/questions/11494158/how-to-align-textview-around-an-imageview
 */
public class LeadingMarginHelper implements LeadingMarginSpan.LeadingMarginSpan2{
    private int margin;
    private int lines;

    public LeadingMarginHelper(int lines, int margin) {
        this.margin = margin;
        this.lines = lines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (first) {
            return margin;
        } else {
            return 0;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom, CharSequence text,
                                  int start, int end, boolean first, Layout layout) {}


    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }
}
