package com.suru.featuretoggle.service.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.FeaturesByIDRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCacheStrategy implements FtsCacheStrategy {

	private final static Logger LOGGER = LoggerFactory.getLogger(GuavaCacheStrategy.class);

	protected LoadingCache<String, FeaturesForIdResponse> cache = null;

	protected FeaturesByIDRepository repository;

	protected GuavaCacheStrategy() {

		buildCache(1);
	}

	public GuavaCacheStrategy(final FeaturesByIDRepository repository) {

		this.repository = repository;
		buildCache(1);
	}
	
	public GuavaCacheStrategy(final FeaturesByIDRepository repository, final int hoursToExpire) {

		this.repository = repository;
		buildCache(hoursToExpire);
	}
		
	@Override
	public FeaturesForIdResponse getResponse(final String id) {

		try {
			return cache.get(id);
		} catch (Exception e) {
			LOGGER.debug("Error selecting item from cache", e);
		}
		return null;
	}

	@Override
	public void remove(FeaturesForIdResponse response) {

		cache.invalidate(response);
	}

	@Override
	public void add(FeaturesForIdResponse response) {
		throw new UnsupportedOperationException();
		
	}

	protected void setRepository(FeaturesByIDRepository repository) {
		this.repository = repository;
	}	

	private FeaturesForIdResponse acquireById(String id) {
		FeaturesForIdResponse response = repository.getFeaturesById(id);
		return response;
	}
	
	private void buildCache(final int hours) {
		cache = CacheBuilder.newBuilder().expireAfterWrite(hours, TimeUnit.HOURS)
				.build(new CacheLoader<String, FeaturesForIdResponse>() {
					public FeaturesForIdResponse load(final String id) throws Exception {
						return acquireById(id);
					}
				});
	}
}
