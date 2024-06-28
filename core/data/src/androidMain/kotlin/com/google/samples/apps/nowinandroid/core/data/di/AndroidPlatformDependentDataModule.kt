/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.data.di

import android.app.Application
import com.google.samples.apps.nowinandroid.core.data.util.ConnectivityManagerNetworkMonitor
import com.google.samples.apps.nowinandroid.core.data.util.NetworkMonitor
import com.google.samples.apps.nowinandroid.core.data.util.TimeZoneBroadcastMonitor
import com.google.samples.apps.nowinandroid.core.data.util.TimeZoneMonitor
import com.google.samples.apps.nowinandroid.core.di.AndroidApplicationComponent
import com.google.samples.apps.nowinandroid.core.di.CoroutineScopeComponent
import com.google.samples.apps.nowinandroid.core.di.DispatchersComponent
import com.google.samples.apps.nowinandroid.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AndroidPlatformDependentDataModule(
    @Component val applicationComponent: AndroidApplicationComponent,
    @Component val dispatchersComponent: DispatchersComponent,
    @Component val coroutineScopeComponent: CoroutineScopeComponent,
) : PlatformDependentDataModule() {
    abstract val application: Application

    @IoDispatcher abstract val ioDispatcher: CoroutineDispatcher
    abstract val coroutineScope: CoroutineScope

    @Provides
    override fun bindsNetworkMonitor(): NetworkMonitor {
        return ConnectivityManagerNetworkMonitor(
            application,
            ioDispatcher,
        )
    }

    @Provides
    override fun bindsTimeZoneMonitor(): TimeZoneMonitor {
        return TimeZoneBroadcastMonitor(application, coroutineScope, ioDispatcher)
    }
}
