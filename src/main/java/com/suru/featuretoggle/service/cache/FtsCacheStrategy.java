package com.suru.featuretoggle.service.cache;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;

public interface FtsCacheStrategy {

	FeaturesForIdResponse getResponse(String id);
	void remove(FeaturesForIdResponse response);
	void add(FeaturesForIdResponse response);
}
