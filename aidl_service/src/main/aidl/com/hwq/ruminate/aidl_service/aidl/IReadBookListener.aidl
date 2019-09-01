package com.hwq.ruminate.aidl_service.aidl;
import com.hwq.ruminate.aidl_service.aidl.Book;

interface IReadBookListener {
    void onReadBookOver(in Book book);
}
