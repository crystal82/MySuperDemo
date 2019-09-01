package com.hwq.ruminate.ipc_service.aidl;
import com.hwq.ruminate.ipc_service.aidl.Book;

interface IReadBookListener {
    void onReadBookOver(in Book book);
}
