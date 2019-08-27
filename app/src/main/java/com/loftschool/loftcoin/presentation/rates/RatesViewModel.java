package com.loftschool.loftcoin.presentation.rates;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.loftschool.loftcoin.data.ApiFactory;
import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.data.dto.Quote;
import com.loftschool.loftcoin.domain.ChangeFormatter;
import com.loftschool.loftcoin.domain.CoinRate;
import com.loftschool.loftcoin.domain.ImageUrlFormatter;
import com.loftschool.loftcoin.domain.PriceFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RatesViewModel extends ViewModel {

	static final class Factory implements ViewModelProvider.Factory {

		private final Context context;

		public Factory(@NonNull final Context context) {
			this.context = Objects.requireNonNull(context);
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
			return (T) new RatesViewModel(
				new CoinsRepository(new ApiFactory().createCoinMarketCapApi()),
				new PriceFormatter(context),
				new ChangeFormatter(context),
				new ImageUrlFormatter()
			);
		}
	}

	private final CoinsRepository coinsRepository;
	private final PriceFormatter priceFormatter;
	private final ChangeFormatter changeFormatter;
	private final ImageUrlFormatter imageUrlFormatter;

	private final MutableLiveData<List<CoinRate>> coinRates = new MutableLiveData<>();
	private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
	private final MutableLiveData<Throwable> errorState = new MutableLiveData<>();

	public RatesViewModel(@NonNull final CoinsRepository coinsRepository,
	                      @NonNull final PriceFormatter priceFormatter,
	                      @NonNull final ChangeFormatter changeFormatter,
	                      @NonNull final ImageUrlFormatter imageUrlFormatter) {
		this.coinsRepository = Objects.requireNonNull(coinsRepository);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);
		this.changeFormatter = Objects.requireNonNull(changeFormatter);
		this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);
		refresh();
	}

	@NonNull
	public LiveData<List<CoinRate>> getCoinRates() {
		return coinRates;
	}

	@NonNull
	public LiveData<Boolean> getLoadingState() {
		return loadingState;
	}

	@NonNull
	public LiveData<Throwable> getErrorState() {
		return errorState;
	}

	void refresh() {
		loadingState.postValue(true);
		coinsRepository.listings("USD",
			coins -> {
				coinRates.postValue(convertToCoinRates(coins, "USD"));
				loadingState.postValue(false);
			},
			error -> {
				errorState.postValue(error);
				loadingState.postValue(false);
			});
	}

	@NonNull
	private List<CoinRate> convertToCoinRates(@NonNull final List<Coin> coins,
	                                          @NonNull final String convert) {
		final List<CoinRate> rates = new ArrayList<>(coins.size());

		for (final Coin coin : coins) {
			final Quote quote = Objects.requireNonNull(
				coin.getQuotes().get(convert)
			);

			rates.add(
				CoinRate.builder()
					.id(coin.getId())
					.symbol(coin.getSymbol())
					.imageUrl(imageUrlFormatter.format(coin.getId()))
					.price(priceFormatter.format(quote.getPrice()))
					.change24(changeFormatter.format(quote.getChange24h()))
					.isChange24Negative(quote.getChange24h() < 0)
					.build()
			);
		}

		return rates;
	}
}
