package com.mehdisekoba.imdb.utils.di

import androidx.fragment.app.Fragment
import com.mehdisekoba.imdb.ui.video.adapter.AdapterExplore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    fun provideAdapterExplore(fragment: Fragment): AdapterExplore {
        return AdapterExplore(fragment.lifecycle)
    }
}
