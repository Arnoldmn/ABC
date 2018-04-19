package com.arnold.mna.abcinsurance.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class ViewHolder extends RecyclerView {

    public View mView;

    public ViewHolder(Context context) {
        super(context);
    }

    public ViewHolder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewHolder(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
