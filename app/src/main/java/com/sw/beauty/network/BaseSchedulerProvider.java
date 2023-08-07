/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package com.sw.beauty.network;

import androidx.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
