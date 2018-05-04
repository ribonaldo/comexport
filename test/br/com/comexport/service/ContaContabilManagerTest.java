/**
 * 
 */
package br.com.comexport.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.comexport.domain.ContaContabilDomain;
import br.com.comexport.domain.ContaContabilHashDomain;
import br.com.comexport.domain.EstatisticaContaContabilDomain;
import junit.framework.TestCase;

/**
 * Classe de Testes para {@link ContaContabilManager}
 * 
 * @author Ricardo Bonaldo
 *
 */
public class ContaContabilManagerTest extends TestCase {

	private static ContaContabilManager manager = ContaContabilManager.getManager();
	
	private final Long numeroConta1 = 123456L;
	
	private final Long numeroConta2 = 223456L;
	
	private final Long numeroConta3 = 323456L;
	
	private final Long numeroConta4 = 423456L;
	
	private final String data = "20180514";
	
	private final BigDecimal valor1 = new BigDecimal("153.14");
	
	private final BigDecimal valor2 = new BigDecimal("49");
	
	private final BigDecimal valor3 = new BigDecimal("27.8");
	
	private final BigDecimal valor4 = new BigDecimal("32.01");
	
	private final BigDecimal valor5 = new BigDecimal("44.2");
	
	private final BigDecimal valor6 = new BigDecimal("51");
	
	private final BigDecimal valor7 = new BigDecimal("19.33");
	
	private final BigDecimal valor8 = new BigDecimal("7.99");
	
	private final BigDecimal maxNoFilter = new BigDecimal("153.14");
	
	private final BigDecimal minNoFilter = new BigDecimal("7.99");
	
	private final BigDecimal sumNoFilter = new BigDecimal("384.47");
	
	private final BigDecimal averageNoFilter = new BigDecimal("48.06");
	
	private final BigDecimal countNoFilter = new BigDecimal("8");
	
	private final BigDecimal maxFilter1 = new BigDecimal("153.14");
	
	private final BigDecimal minFilter1 = new BigDecimal("7.99");
	
	private final BigDecimal sumFilter1 = new BigDecimal("242.14");
	
	private final BigDecimal averageFilter1 = new BigDecimal("60.54");
	
	private final BigDecimal countFilter1 = new BigDecimal("4");
	
	private final BigDecimal maxFilter2 = new BigDecimal("27.8");
	
	private final BigDecimal minFilter2 = new BigDecimal("19.33");
	
	private final BigDecimal sumFilter2 = new BigDecimal("47.13");
	
	private final BigDecimal averageFilter2 = new BigDecimal("23.57");
	
	private final BigDecimal countFilter2 = new BigDecimal("2");
	
	@Test
	public void testAddData() throws Exception {
		ContaContabilDomain conta = new ContaContabilDomain();
		conta.setContaContabil(this.numeroConta1);
		conta.setData(this.data);
		conta.setValor(this.valor1);
		ContaContabilHashDomain hash = manager.addData(conta);
		Assert.assertNotNull(hash);
		Assert.assertNotNull(hash.getId());
	}
	
	@Test
	public void testAddDataMany() throws Exception {
		ContaContabilDomain conta = new ContaContabilDomain();
		conta.setContaContabil(this.numeroConta1);
		conta.setData(this.data);
		conta.setValor(this.valor1);
		
		ContaContabilHashDomain hash = manager.addData(conta);

		ContaContabilDomain conta2 = new ContaContabilDomain();
		conta2.setContaContabil(this.numeroConta1);
		conta2.setData(this.data);
		conta2.setValor(this.valor1);
		
		ContaContabilHashDomain hash2 = manager.addData(conta2);
		
		Assert.assertNotNull(hash);
		Assert.assertNotNull(hash.getId());
		
		Assert.assertNotNull(hash2);
		Assert.assertNotNull(hash2.getId());
		
		Assert.assertNotSame(hash2.getId(), hash.getId());
	}
	
	@Test
	public void testAddDataFailConta() throws Exception {
		ContaContabilDomain conta = new ContaContabilDomain();
		conta.setData(this.data);
		conta.setValor(this.valor1);
		try {
			manager.addData(conta);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("conta"));
		}
	}
	
	@Test
	public void testAddDataFailData() throws Exception {
		ContaContabilDomain conta = new ContaContabilDomain();
		conta.setContaContabil(this.numeroConta1);
		conta.setValor(this.valor1);
		try {
			manager.addData(conta);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("data"));
		}
	}
	
	@Test()
	public void testAddDataFailValor() throws Exception {
		ContaContabilDomain conta = new ContaContabilDomain();
		conta.setContaContabil(this.numeroConta1);
		conta.setData(this.data);
		try {
			manager.addData(conta);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("valor"));
		}
	}
	
	@Test
	public void testGetByHash() throws Exception {
		ContaContabilHashDomain someHash = this.makeMany(); 
		ContaContabilHashDomain sameHash = manager.get(someHash.getId());
		
		Assert.assertSame(someHash, sameHash);
	}
	
	@Test
	public void testGetByConta() throws Exception {
		this.makeMany();
		List<ContaContabilHashDomain> list = manager.get(this.numeroConta1);
		
		Assert.assertEquals(list.size(), 4);
		
		list = manager.get(this.numeroConta2);
		
		Assert.assertEquals(list.size(), 2);
		
		list = manager.get(this.numeroConta3);
		
		Assert.assertEquals(list.size(), 1);
	}
	
	@Test
	public void testStats() throws Exception {
		this.makeMany();
		
		EstatisticaContaContabilDomain stats = manager.getStats(null);
		
		Assert.assertEquals(stats.getMax(), this.maxNoFilter);
		Assert.assertEquals(stats.getMin(), this.minNoFilter);
		Assert.assertEquals(stats.getSoma(), this.sumNoFilter);
		Assert.assertEquals(stats.getMedia(), this.averageNoFilter);
		Assert.assertEquals(stats.getQtde(), this.countNoFilter);
	}
	
	@Test
	public void testStatsConta() throws Exception {
		this.makeMany();
		
		EstatisticaContaContabilDomain stats = manager.getStats(this.numeroConta1);
		
		Assert.assertEquals(stats.getMax(), this.maxFilter1);
		Assert.assertEquals(stats.getMin(), this.minFilter1);
		Assert.assertEquals(stats.getSoma(), this.sumFilter1);
		Assert.assertEquals(stats.getMedia(), this.averageFilter1);
		Assert.assertEquals(stats.getQtde(), this.countFilter1);
		
		stats = manager.getStats(this.numeroConta2);
		
		Assert.assertEquals(stats.getMax(), this.maxFilter2);
		Assert.assertEquals(stats.getMin(), this.minFilter2);
		Assert.assertEquals(stats.getSoma(), this.sumFilter2);
		Assert.assertEquals(stats.getMedia(), this.averageFilter2);
		Assert.assertEquals(stats.getQtde(), this.countFilter2);
	}
	
	/**
	 * Gera vários lançamentos na conta contábil
	 * @return {@link ContaContabilHashDomain}
	 * @throws Exception
	 */
	private ContaContabilHashDomain makeMany() throws Exception {
		ContaContabilDomain domain1 = new ContaContabilDomain();
		ContaContabilDomain domain2 = new ContaContabilDomain();
		ContaContabilDomain domain3 = new ContaContabilDomain();
		ContaContabilDomain domain4 = new ContaContabilDomain();
		ContaContabilDomain domain5 = new ContaContabilDomain();
		ContaContabilDomain domain6 = new ContaContabilDomain();
		ContaContabilDomain domain7 = new ContaContabilDomain();
		ContaContabilDomain domain8 = new ContaContabilDomain();
		
		domain1.setContaContabil(this.numeroConta1);
		domain2.setContaContabil(this.numeroConta1);
		domain3.setContaContabil(this.numeroConta2);
		domain4.setContaContabil(this.numeroConta1);
		domain5.setContaContabil(this.numeroConta3);
		domain6.setContaContabil(this.numeroConta4);
		domain7.setContaContabil(this.numeroConta2);
		domain8.setContaContabil(this.numeroConta1);
		
		domain1.setData(this.data);
		domain2.setData(this.data);
		domain3.setData(this.data);
		domain4.setData(this.data);
		domain5.setData(this.data);
		domain6.setData(this.data);
		domain7.setData(this.data);
		domain8.setData(this.data);
		
		domain1.setValor(this.valor1);
		domain2.setValor(this.valor2);
		domain3.setValor(this.valor3);
		domain4.setValor(this.valor4);
		domain5.setValor(this.valor5);
		domain6.setValor(this.valor6);
		domain7.setValor(this.valor7);
		domain8.setValor(this.valor8);
		
		manager.clear();
		manager.addData(domain1);
		manager.addData(domain2);
		manager.addData(domain3);
		ContaContabilHashDomain someHash = manager.addData(domain4);
		manager.addData(domain5);
		manager.addData(domain6);
		manager.addData(domain7);
		manager.addData(domain8);
		
		return someHash;
	}
}
