package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.data.dto.Quote;
import com.loftschool.loftcoin.domain.ChangeFormatter;
import com.loftschool.loftcoin.domain.CoinRate;
import com.loftschool.loftcoin.domain.ImageUrlFormatter;
import com.loftschool.loftcoin.domain.PriceFormatter;
import com.loftschool.loftcoin.util.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public final class RatesMapper implements Function<List<Coin>, List<CoinRate>> {

	private final ImageUrlFormatter imageUrlFormatter;
	private final PriceFormatter priceFormatter;
	private final ChangeFormatter changeFormatter;

	@Inject
	public RatesMapper(@NonNull final ImageUrlFormatter imageUrlFormatter,
	                   @NonNull final PriceFormatter priceFormatter,
	                   @NonNull final ChangeFormatter changeFormatter) {
		this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);
		this.changeFormatter = Objects.requireNonNull(changeFormatter);
	}

	@Override
	public List<CoinRate> apply(List<Coin> value) {
		final List<CoinRate> rates = new ArrayList<>(value.size());

		for (final Coin coin : value) {
			final Quote quote = Objects.requireNonNull(
				coin.getQuotes().get("USD")
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
