package com.loftschool.loftcoin.domain;

import dagger.Binds;
import dagger.Module;

@Module
public interface FormattersModule {

	@Binds
	PriceFormatter priceFormatter(PriceFormatterImpl impl);

	@Binds
	ImageUrlFormatter imageUrlFormatter(ImageUrlFormatterImpl impl);

	@Binds
	ChangeFormatter changeFormatter(ChangeFormatterImpl impl);
}
