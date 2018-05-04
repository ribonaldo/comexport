/**
 * 
 */
package br.com.comexport.domain;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de Testes para {@link ContaContabilDomain}
 * 
 * @author Ricardo Bonaldo
 */
public class ContaContabilDomainTest {

	@Test
	public void testData() throws Exception {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		
		calendar.set(Calendar.YEAR, 2018);
		calendar.set(Calendar.MONTH, Calendar.MAY);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		ContaContabilDomain domain = new ContaContabilDomain();
		domain.setData("20180531");
		
		Assert.assertEquals(calendar.getTime(), domain.getDataN());
	}

	@Test
	public void testDataNotOk() throws Exception {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		
		calendar.set(Calendar.YEAR, 2018);
		calendar.set(Calendar.MONTH, Calendar.MAY);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		ContaContabilDomain domain = new ContaContabilDomain();
		domain.setData("20180530");
		
		Assert.assertNotEquals(calendar.getTime(), domain.getDataN());
	}
	
	@Test(expected = Exception.class)
	public void testDataInvalida() throws Exception {
		ContaContabilDomain domain = new ContaContabilDomain();
		domain.setData("201801332");
	}
	
}
