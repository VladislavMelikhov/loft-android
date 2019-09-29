package com.loftschool.loftcoin.domain;

import dagger.Binds;
import dagger.Module;

@Module
public interface LoadersModule {

	@Binds
	ImageLoader imageLoader(ImageLoaderImpl impl);
}
