package com.suru.featuretoggle.service;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.cache.FtsCacheStrategy;

public class FeatureService {
	
	protected FtsCacheStrategy cacheStrategy;

	public FeatureService(final FtsCacheStrategy strategy) {
		super();
		this.cacheStrategy = strategy;
	}

	public final boolean hasAccess(final String id, final String feature) {

		FeaturesForIdResponse response = getResponse(id);
		if (response != null) {
			return checkForFeature(response, feature);
		} else {
			return false;
		}
	}

	private boolean checkForFeature(final FeaturesForIdResponse response,
			final String feature) {
		return response.getFeatures().contains(feature);
	}

	private FeaturesForIdResponse getResponse(final String id) {

		FeaturesForIdResponse response = cacheStrategy.getResponse(id);
		return response;
	}
}
