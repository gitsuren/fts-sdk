package com.suru.featuretoggle.service.cache;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.FeaturesByIDRepository;

public class NoCacheStrategy implements FtsCacheStrategy {

	private FeaturesByIDRepository repository;
	
	public NoCacheStrategy(FeaturesByIDRepository repository) {
		super();
		if (repository == null) {
			throw new IllegalArgumentException("Repository must not be null");
		} else {
			this.repository = repository;
		}
	}

	@Override
	public FeaturesForIdResponse getResponse(final String id) {
		return repository.getFeaturesById(id);
	}

	@Override
	public void remove(final FeaturesForIdResponse response) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(final FeaturesForIdResponse response) {
		throw new UnsupportedOperationException();
	}

}
