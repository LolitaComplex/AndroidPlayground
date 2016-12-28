// AidlManager.aidl
package com.doing.androidipc.aidl;

// Declare any non-default types here with import statements

import com.doing.androidipc.aidl.Callback;
import com.doing.androidipc.aidl.User;

interface AidlManager {

    boolean addUser(in String text,in String name,out List<String> arg,in User user);
    void registCallback(Callback callback);
    oneway void unRegistCallback(Callback callback);

}
