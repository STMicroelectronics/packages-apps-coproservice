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

import java.util.ArrayList;
import android.os.RemoteException;

import android.copro.ICoproService;
import android.copro.ICoproSerialPort;
import android.copro.FirmwareInfo;

import android.hardware.copro.V1_0.ICopro;
import android.hardware.copro.V1_0.HalFwInfo;
import android.hardware.copro.V1_0.RequestStatus;

class ICoproServiceImpl extends ICoproService.Stub {
    private static final String TAG = "ICoproServiceImpl";
    private ICopro mCoproHal = null;

    public ICoproServiceImpl() {
        Slog.d(TAG, "Build service");
        try {
            mCoproHal = ICopro.getService();
        } catch (Exception e)
        {
            mCoproHal = null;
        }
    }

    /**
     * Implementation of the methods described in AIDL interface
     */
    @Override
    public FirmwareInfo[] getFirmwareList() throws RemoteException {
        ArrayList<FirmwareInfo> temp = new ArrayList<FirmwareInfo>();
        Slog.d(TAG, "COPRO : enter getFirmwareList");

        if (mCoproHal != null) {
            mCoproHal.getFwList(new ICopro.getFwListCallback() {

                @Override
                public void onValues(RequestStatus debugErrno, ArrayList<HalFwInfo> fwInfoList) {
                    Slog.d(TAG, "COPRO : enter onValue");
                    for (HalFwInfo fw : fwInfoList) {
                        temp.add(new FirmwareInfo(fw.id, fw.name, fw.state));
                    }
                }
            });
        }

        Slog.d(TAG, "COPRO : end getFirmwareList");

        return temp.toArray(new FirmwareInfo[temp.size()]);
    }

    @Override
    public FirmwareInfo getFirmwareByName(String name) throws RemoteException {
        GetFwByNameCallBack cb = new GetFwByNameCallBack();

        if (mCoproHal != null) {
            mCoproHal.getFwByName(name, cb);
        }

        return cb.getResult();
    }

    @Override
    public boolean isFirmwareRunning(int id) throws RemoteException {
        IsFwRunningCallback cb = new IsFwRunningCallback();

        if (mCoproHal != null) {
            mCoproHal.isFwRunning(id, cb);
        }

        return cb.getResult();
    }

    @Override
    public void startFirmware(int id) throws RemoteException {
        if (mCoproHal != null) {
            mCoproHal.startFw(id);
        }
    }

    @Override
    public void stopFirmware() throws RemoteException {
        if (mCoproHal != null) {
            mCoproHal.stopFw(0);
        }
    }

    @Override
    public ICoproSerialPort getSerialPort() {
        return new ICoproSerialPortImpl();

    }
}
