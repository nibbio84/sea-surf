package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.SeaSurfConfig;

import org.junit.Assert;
import org.junit.Test;

public class ConfigLoaderTest {

	@Test
	public void testLoader1() {
		
		ConfigLoader loader = new ConfigLoader();
		loader.setConfigFileName("META-INF/sea-surf-test-config-1.xml");
		
		SeaSurfConfig config = loader.loadConfig();
		Assert.assertNotNull(config);
		
	}
	
}
