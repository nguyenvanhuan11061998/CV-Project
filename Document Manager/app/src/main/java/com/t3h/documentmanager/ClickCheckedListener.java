package com.t3h.documentmanager;

import com.t3h.documentmanager.model.ItemFile;

public interface ClickCheckedListener<T extends ItemFile> extends FileAdapter.ItemClickListener {
    void clickCheck(T t);
    void onClickItem(T t);
    boolean onLongClickItem(T t);
}
