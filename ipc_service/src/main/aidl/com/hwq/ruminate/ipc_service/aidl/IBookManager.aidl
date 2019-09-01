// IBookManager.aidl
package com.hwq.ruminate.ipc_service.aidl;
import com.hwq.ruminate.ipc_service.aidl.Book;
import com.hwq.ruminate.ipc_service.aidl.IReadBookListener;

interface IBookManager {
    long readBook(int id);
    void registerReadBookListener(IReadBookListener listener);
    void unRegisterReadBookListener(IReadBookListener listener);

    int getBookId(String name);
    List<Book> getBookList();
}
