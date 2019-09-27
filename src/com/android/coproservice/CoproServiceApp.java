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
import android.os.IBinder;

import android.app.Application;
import android.os.ServiceManager;

import android.copro.ICoproService;

public class CoproServiceApp extends Application {
    private static final String REMOTE_SERVICE_NAME = ICoproService.class.getName();
    private static final String TAG = "CoproServiceApp";
    private IBinder mCoproService = null;

    public void onCreate() {
        super.onCreate();
        Slog.d(TAG, "Build service");
        mCoproService = new ICoproServiceImpl();
        ServiceManager.addService(REMOTE_SERVICE_NAME, mCoproService);
    }

    public void onTerminate() {
        super.onTerminate();
    }

}
