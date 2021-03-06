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

import android.copro.FirmwareInfo;

import android.hardware.copro.V1_0.ICopro;
import android.hardware.copro.V1_0.RequestStatus;
import android.hardware.copro.V1_0.HalFwInfo;

import android.os.RemoteException;

class GetFwByNameCallBack implements ICopro.getFwByNameCallback {
    private FirmwareInfo fw = null;

    @Override
    public void onValues(RequestStatus debugErrno, HalFwInfo fwInfo) {
        if(debugErrno.ret) {
            fw = new FirmwareInfo(fwInfo.id, fwInfo.name, fwInfo.state);
        }
    }

    public FirmwareInfo getResult() {
        return fw;
    }
}
