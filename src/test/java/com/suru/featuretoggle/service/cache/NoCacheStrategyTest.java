package com.suru.featuretoggle.service.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.FeaturesByIDRepository;

@RunWith(MockitoJUnitRunner.class)
public class NoCacheStrategyTest {

	private static final String ANY_ID = "anyId";

	@InjectMocks
	private NoCacheStrategy classToTest;

	@Mock
	private FeaturesByIDRepository mockRepository;
	
	@Test
	public void testGetResponseWhenResponseIsNotFound() {

		when(mockRepository.getFeaturesById(ANY_ID)).thenReturn(null);
		FeaturesForIdResponse response = classToTest.getResponse(ANY_ID);
		assertNull(response);
		verify(mockRepository).getFeaturesById(ANY_ID);
	}
	
	@Test
	public void testGetResponseWhenResponseIsFound() {
		
		FeaturesForIdResponse expectedResponse = new FeaturesForIdResponse();
		expectedResponse.setId(ANY_ID);
		when(mockRepository.getFeaturesById(ANY_ID)).thenReturn(expectedResponse);
		FeaturesForIdResponse response = classToTest.getResponse(ANY_ID);
		verify(mockRepository).getFeaturesById(ANY_ID);
		assertEquals(ANY_ID, response.getId());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		classToTest.remove(response);
		verifyZeroInteractions(mockRepository);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testAdd() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		classToTest.add(response);
		verifyZeroInteractions(mockRepository);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testContructorWithNullRepository() {
		
		NoCacheStrategy result = new NoCacheStrategy(null);
	}
}
