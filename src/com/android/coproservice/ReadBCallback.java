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

import android.copro.ICoproSerialPort;

import android.hardware.copro.V1_0.ICoproSerialPortHal;
import android.hardware.copro.V1_0.RequestStatus;

class ReadBCallback implements ICoproSerialPortHal.readBCallback {
    private byte[] reply;

    @Override
    public void onValues(RequestStatus debugErrno, ArrayList<Byte> result) {
        if(debugErrno.ret) {
            reply = new byte[result.size()];
            for(int i = 0; i < result.size(); i++) {
                reply[i] = result.get(i).byteValue();
            }
        }
    }

    public byte[] getResult() {
        return reply;
    }

}
