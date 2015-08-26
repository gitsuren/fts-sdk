package com.suru.featuretoggle.service.program;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTestProgram {
	static {
        System.setProperty("IafConfigSuffix","Test");
        System.setProperty("IafMOD","Test");
	}
	
	protected ApplicationContext applicationContext;


	protected String[] getContextConfigLocations() {
		return new String[] { "classpath*:/spring-configuration/test-applicationContext.xml" };
	}


	protected void setup() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(getContextConfigLocations());

		// Autowire any dependencies on this bean
		applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
	}


	public void run() throws Exception {
		setup();
		execute();
	}


	protected abstract void execute() throws Exception;
}