package com.hwq.ruminate.ipc_service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    public int bookId;
    public String bookName;
    public long readTime;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", readTime=" + readTime +
                '}';
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        readTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeLong(readTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
