package com.tfreeman.foodrecipes

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    private val mNetworkIO =
        Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

    companion object {
        var instance: AppExecutors? = null
            get() {
                if (field == null) {
                    field = AppExecutors()
                }
                return field
            }
            private set
    }
}