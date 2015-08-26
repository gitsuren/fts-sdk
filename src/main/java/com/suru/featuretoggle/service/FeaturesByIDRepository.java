package com.suru.featuretoggle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;

public class FeaturesByIDRepository {

	private static final Logger LOG = LoggerFactory
			.getLogger(FeaturesByIDRepository.class);

	protected String url;
	protected String systemName;
	protected RestOperations restTemplate;

	public FeaturesByIDRepository(final String url, final String systemName,
			final RestOperations restTemplate) {
		super();
		this.url = url;
		this.systemName = systemName;
		this.restTemplate = restTemplate;
	}

	public FeaturesForIdResponse getFeaturesById(final String id) {

		try {
			return restTemplate.getForObject(url, FeaturesForIdResponse.class,
					new Object[] { systemName, id });
		} catch (Exception e) {
			LOG.error("Error getting response for " + systemName + " : " + id,
					e);
		}
		return null;
	}
}
