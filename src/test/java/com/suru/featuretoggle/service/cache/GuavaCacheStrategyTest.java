package com.suru.featuretoggle.service.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.suru.featuretoggle.dto.FeaturesForIdResponse;
import com.suru.featuretoggle.service.FeaturesByIDRepository;

@RunWith(MockitoJUnitRunner.class)
public class GuavaCacheStrategyTest {

	private static final String ANY_ID = "anyId";

	@InjectMocks
	private GuavaCacheStrategy classToTest;

	@Mock
	private FeaturesByIDRepository mockRepository;
	
	@Before
	public void setup() {
		classToTest.repository = mockRepository;
	}
	
	@Test
	public void testOptionalConstructor1() {
		
		GuavaCacheStrategy strat = new GuavaCacheStrategy(mockRepository);
		assertNotNull(strat.cache);
	}
	
	@Test
	public void testOptionalConstructor2() {
		
		GuavaCacheStrategy strat = new GuavaCacheStrategy(mockRepository, 1);
		assertNotNull(strat.cache);
	}
	
	@Test
	public void testGetResponseWhenResponseIsNotFound() throws ExecutionException {

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
		assertEquals(ANY_ID, response.getId());
		response = classToTest.getResponse(ANY_ID);
		assertEquals(ANY_ID, response.getId());
		verify(mockRepository, times(1)).getFeaturesById(ANY_ID);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveWithNullValue() {

		classToTest.remove(null);
	}

	@Test
	public void testRemoveWithUncachedValue() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setId(ANY_ID);
		classToTest.remove(response);
		FeaturesForIdResponse expectedResponse = new FeaturesForIdResponse();
		expectedResponse.setId(ANY_ID);
		when(mockRepository.getFeaturesById(ANY_ID)).thenReturn(expectedResponse);
		response = classToTest.getResponse(ANY_ID);
		assertEquals(expectedResponse, response);
		verify(mockRepository).getFeaturesById(ANY_ID);
	}
	
	@Test
	public void testRemoveWithCachedValue() {

		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setId(ANY_ID);
		classToTest.remove(response);
		FeaturesForIdResponse expectedResponse = new FeaturesForIdResponse();
		expectedResponse.setId(ANY_ID);
		when(mockRepository.getFeaturesById(ANY_ID)).thenReturn(expectedResponse);
		response = classToTest.getResponse(ANY_ID);
		assertEquals(expectedResponse, response);
		verify(mockRepository).getFeaturesById(ANY_ID);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAddShouldThrowUnsupportedOperationException() {
		
		classToTest.add(new FeaturesForIdResponse());
	}
	
	@Test
	public void testProtectedSetRepository() {
		
		GuavaCacheStrategy testObject = new GuavaCacheStrategy();
		testObject.setRepository(mockRepository);
		assertEquals(mockRepository, testObject.repository);
	}
}
