package com.ankuran.ui.adaptar.listener;

import android.view.View;

public interface OnRecyclerItemClickListener {
    void onItemClick(View view, int position);
    void onItemClick(View view, int position,Object object);
}


