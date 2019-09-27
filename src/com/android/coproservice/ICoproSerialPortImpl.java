/*
 * Copyright (C) 2019 STMicroelectronics
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.coproservice;

import android.util.Log;
import android.util.Slog;

import android.os.RemoteException;

import android.copro.ICoproSerialPort;

import android.hardware.copro.V1_0.ICoproSerialPortHal;
import android.hardware.copro.V1_0.RequestStatus;

class ICoproSerialPortImpl extends ICoproSerialPort.Stub {
    private static final String TAG = "ICoproSerialPortImpl";
    private ICoproSerialPortHal mCoproSerialPortHal = null;

    public ICoproSerialPortImpl() {
        Slog.d(TAG, "Build service");
        try {
            mCoproSerialPortHal = ICoproSerialPortHal.getService();
        } catch (Exception e)
        {
            mCoproSerialPortHal = null;
        }
    }
    /**
     * Implementation of the methods described in AIDL interface
     */
    @Override
    public boolean open(int mode) throws RemoteException {
        if(mCoproSerialPortHal != null) {
            RequestStatus ret = mCoproSerialPortHal.open(mode);
            return ret.ret;
        }
        return false;
    }

    @Override
    public void close() throws RemoteException {
        if(mCoproSerialPortHal != null) {
            mCoproSerialPortHal.close();
        }
    }

    @Override
    public String read() throws RemoteException {
        ReadCallback cb = new ReadCallback();
        if(mCoproSerialPortHal != null) {
            mCoproSerialPortHal.read(cb);
        }
        return cb.getResult();
    }

    @Override
    public void write(String command) throws RemoteException {
        if(mCoproSerialPortHal != null) {
            mCoproSerialPortHal.write(command);
        }
    }
}
