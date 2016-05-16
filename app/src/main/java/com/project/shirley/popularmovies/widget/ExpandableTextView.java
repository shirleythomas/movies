package com.project.shirley.popularmovies.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.project.shirley.popularmovies.R;

/**
 * User: Bazlur Rahman Rokon
 * Date: 9/7/13 - 3:33 AM
 * Source: http://stackoverflow.com/questions/15627530/android-expandable-textview-with-animation
 */
public class ExpandableTextView extends TextView {
    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String ELLIPSIS = "... ";
    private static final String MORE = "More";
    private static final String LESS = "Less";
    private static boolean viewMore = false;


    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;
    private int textSize;
    private SpannableStringBuilder spannableStringBuilder = null;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();
        this.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {

        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;

        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (text != null && text.length() > trimLength) {
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text, 0, trimLength + 1);
            spannableStringBuilder.append(ELLIPSIS);
            spannableStringBuilder.append(MORE);
            addClickableSpanMore(spannableStringBuilder);
            return spannableStringBuilder;
        } else {
            return text;
        }
    }

    private void addClickableSpanLess(SpannableStringBuilder spannableStringBuilder) {

        String str = spannableStringBuilder.toString();

        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                SpannableStringBuilder builder = new SpannableStringBuilder(originalText);
                builder.append(MORE);
                addClickableSpanMore(builder);
                ExpandableTextView.super.setText(trimmedText, bufferType);

            }
        }, str.indexOf(LESS), str.indexOf(LESS) + 4, 0);
    }


    private void addClickableSpanMore(SpannableStringBuilder spannableStringBuilder) {

        String str = spannableStringBuilder.toString();

        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                SpannableStringBuilder builder = new SpannableStringBuilder(originalText);
                builder.append(LESS);
                addClickableSpanLess(builder);
                //setText(builder);
                trim = !trim;
                ExpandableTextView.super.setText(builder, bufferType);

            }
        }, str.indexOf(MORE), str.indexOf(MORE) + 4, 0);
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }
}