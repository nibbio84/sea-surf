package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TokenManagerTest {

	@Test
	public void hexTest1() {
		
		byte[] bytes = {0, 1};
		String hex = TokenManager.toHex(bytes);
		Logger.getLogger(getClass()).debug("Hex: " + hex);
		Assert.assertEquals("0001", hex);
	}
	
	@Test
	public void hexTest2() {
		
		byte[] bytes = {0, 10, 15, -1};
		String hex = TokenManager.toHex(bytes);
		Logger.getLogger(getClass()).debug("Hex: " + hex);
		Assert.assertEquals("000a0fff", hex);
	}
	
	
	@Test
	public void tokenManager1() {
		
		Preferences prefs = new Preferences();
		prefs.setTokenLength(34);
		String token = TokenManager.newRandomToken(prefs);
		
		Logger.getLogger(getClass()).debug("Token: " + token);
		Assert.assertEquals(34, token.length());
	}
	
}
