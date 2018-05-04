/**
 * 
 */
package br.com.comexport.webservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import br.com.comexport.domain.ContaContabilDomain;
import br.com.comexport.domain.ContaContabilHashDomain;
import br.com.comexport.domain.EstatisticaContaContabilDomain;
import junit.framework.TestCase;

/**
 * Classe de Testes para {@link ContaContabil}. Para estes testes é mandatório que a aplicação esteja "no ar".
 * 
 * @author Ricardo Bonaldo
 */
public class ContaContabilTest extends TestCase {
	
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
	
	private String hash;
	
	@Test
	public void testPost() throws Exception {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setContaContabil(this.numeroConta1);
		contaContabilDomain.setData(data);
		contaContabilDomain.setValor(this.valor1);
		
		this.postCall(contaContabilDomain, null);
	}
	
	@Test
	public void testPostFail() throws Exception {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setData(data);
		contaContabilDomain.setValor(this.valor1);
		
		this.postCall(contaContabilDomain, Arrays.asList("conta"));
	}
	
	@Test
	public void testPostFail2() {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setContaContabil(this.numeroConta1);
		contaContabilDomain.setValor(this.valor1);
		
		this.postCall(contaContabilDomain, Arrays.asList("data"));
	}
	
	@Test
	public void testPostFail3() throws Exception {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setContaContabil(this.numeroConta1);
		contaContabilDomain.setData(data);
		
		this.postCall(contaContabilDomain, Arrays.asList("valor"));
	}
	
	@Test
	public void testPostFail4() throws Exception {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setData(data);
		
		this.postCall(contaContabilDomain, Arrays.asList("conta", "valor"));
	}
	
	@Test
	public void testPostFail5() {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		
		this.postCall(contaContabilDomain, Arrays.asList("conta", "data", "valor"));
	}
	
	@Test
	public void testGetHash() throws Exception {
		ContaContabilDomain contaContabilDomain = this.defaultPostOk();
		
		ContaContabilDomain conta = this.getCall(ContaContabilDomain.class, this.hash, Status.OK);
		
		Assert.assertEquals(contaContabilDomain.getContaContabil(), conta.getContaContabil());
		Assert.assertEquals(contaContabilDomain.getDataN(), conta.getDataN());
		Assert.assertEquals(contaContabilDomain.getValor(), conta.getValor());
	}
	
	@Test
	public void testGetHashFail() throws Exception {
		this.defaultPostOk();
		
		this.hash += "x";
		
		this.getCall(String.class, this.hash, Status.NO_CONTENT);
	}
	
	@Test
	public void testGetConta() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		List<ContaContabilDomain> contas = this.getCallForList(ContaContabilDomain.class, "?contaContabil=" + this.numeroConta1, Status.OK);
		
		Assert.assertEquals(contas.size(), 4);
		
		contas = this.getCallForList(ContaContabilDomain.class, "?contaContabil=" + this.numeroConta2, Status.OK);
		
		Assert.assertEquals(contas.size(), 2);
		
		contas = this.getCallForList(ContaContabilDomain.class, "?contaContabil=" + this.numeroConta3, Status.OK);
		
		Assert.assertEquals(contas.size(), 1);
		
		contas = this.getCallForList(ContaContabilDomain.class, "?contaContabil=" + this.numeroConta4, Status.OK);
		
		Assert.assertEquals(contas.size(), 1);
		
		contas = this.getCallForList(ContaContabilDomain.class, "?contaContabil=" + (this.numeroConta4 + 1), Status.OK);
		
		Assert.assertEquals(contas.size(), 0);
	}
	
	@Test
	public void testStats() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		EstatisticaContaContabilDomain stats = this.getCall(EstatisticaContaContabilDomain.class, "_stats", Status.OK);
		
		Assert.assertEquals(stats.getMax(), this.maxNoFilter);
		Assert.assertEquals(stats.getMin(), this.minNoFilter);
		Assert.assertEquals(stats.getSoma(), this.sumNoFilter);
		Assert.assertEquals(stats.getMedia(), this.averageNoFilter);
		Assert.assertEquals(stats.getQtde(), this.countNoFilter);
	}
	
	@Test
	public void testStatsConta() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		EstatisticaContaContabilDomain stats = this.getCall(EstatisticaContaContabilDomain.class, "_stats/?contaContabil=" + this.numeroConta1, Status.OK);
		
		Assert.assertEquals(stats.getMax(), this.maxFilter1);
		Assert.assertEquals(stats.getMin(), this.minFilter1);
		Assert.assertEquals(stats.getSoma(), this.sumFilter1);
		Assert.assertEquals(stats.getMedia(), this.averageFilter1);
		Assert.assertEquals(stats.getQtde(), this.countFilter1);
	}
	
	@Test
	public void testStatsConta2() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		EstatisticaContaContabilDomain stats = this.getCall(EstatisticaContaContabilDomain.class, "_stats/?contaContabil=" + this.numeroConta2, Status.OK);
		
		Assert.assertEquals(stats.getMax(), this.maxFilter2);
		Assert.assertEquals(stats.getMin(), this.minFilter2);
		Assert.assertEquals(stats.getSoma(), this.sumFilter2);
		Assert.assertEquals(stats.getMedia(), this.averageFilter2);
		Assert.assertEquals(stats.getQtde(), this.countFilter2);
	}
	
	@Test
	public void testStatsConta3() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		EstatisticaContaContabilDomain stats = this.getCall(EstatisticaContaContabilDomain.class, "_stats/?contaContabil=" + this.numeroConta3, Status.OK);
		
		Assert.assertEquals(stats.getMax(), this.valor5);
		Assert.assertEquals(stats.getMin(), this.valor5);
		Assert.assertEquals(stats.getSoma(), this.valor5);
		Assert.assertEquals(stats.getMedia(), this.valor5.setScale(2, RoundingMode.HALF_UP));
		Assert.assertEquals(stats.getQtde(), BigDecimal.ONE);
	}
	
	@Test
	public void testStatsContaInexistente() throws Exception {
		List<ContaContabilDomain> list = this.getList();
		
		for (ContaContabilDomain conta : list) {
			this.postCall(conta, null);
		}
		
		EstatisticaContaContabilDomain stats = this.getCall(EstatisticaContaContabilDomain.class, "_stats/?contaContabil=" + (this.numeroConta3 + 5000), Status.OK);
		
		Assert.assertNull(stats.getMax());
		Assert.assertNull(stats.getMin());
		Assert.assertNull(stats.getSoma());
		Assert.assertNull(stats.getMedia());
		Assert.assertNull(stats.getQtde());
	}
	
	/**
	 * Chamada para método Post.
	 * @param contaContabilDomain {@link ContaContabilDomain}
	 * @param errorMessages Lista de erros que podem retornar
	 * @throws Exception
	 */
	private void postCall(final ContaContabilDomain contaContabilDomain, final List<String> errorMessages) {
		WebTarget target = this.getTarget(null);
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(contaContabilDomain), Response.class);
		
		if (errorMessages == null) {
			ContaContabilHashDomain hash = response.readEntity(ContaContabilHashDomain.class);
			Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
			
			this.hash = hash.getId();
			
			Assert.assertNotNull(this.hash);
		} else {
			Assert.assertEquals(Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
			String error = response.readEntity(String.class);
			for (String errorMessage : errorMessages) {
				Assert.assertTrue(error.contains(errorMessage));
			}
		}
	}
	
	/**
	 * Chamada padrão para métodos Get.
	 * @param type {@link Class}
	 * @param parameter Parâmetro da consulta
	 * @param status {@link Status}
	 * @return Tipo de Retorno
	 */
	private <T> T getCall(final Class<T> type, final String parameter, final Status status) {
		WebTarget target = this.getTarget(parameter);
		
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		
		Assert.assertEquals(status.getStatusCode(), response.getStatus());
		
		return response.readEntity(type);
	}
	
	/**
	 * Chamada padrão para métodos Get.
	 * @param type {@link Class}
	 * @param parameter Parâmetro da consulta
	 * @param status {@link Status}
	 * @return Tipo de Retorno
	 */
	private <T> List<T> getCallForList(final Class<T> type, final String parameter, final Status status) {
		WebTarget target = this.getTarget(parameter);
		
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.get(Response.class);
		
		Assert.assertEquals(status.getStatusCode(), response.getStatus());
		
		return response.readEntity(new GenericType<List<T>>() {});
	}
	
	/**
	 * Retorna webTarget com a url
	 * @param complement
	 * @return {@link WebTarget}
	 */
	private WebTarget getTarget(final String complement) {
		Client client = ClientBuilder.newClient();
		
		final String url = "http://localhost:8080/contaContabil/lancamentos-contabeis/";
		if (StringUtils.isEmpty(complement)) {
			return client.target(url);
		}
		return client.target(url + complement);
	}
	
	/**
	 * Realiza uma chamada Post default para auxílio nos métodos GET
	 * @return
	 * @throws Exception
	 */
	private ContaContabilDomain defaultPostOk() throws Exception {
		ContaContabilDomain contaContabilDomain = new ContaContabilDomain();
		contaContabilDomain.setContaContabil(this.numeroConta2);
		contaContabilDomain.setData(data);
		contaContabilDomain.setValor(this.valor2);
		
		this.postCall(contaContabilDomain, null);
		
		return contaContabilDomain;
	}
	

	
	/**
	 * Obtém uma lista padrão de Lançamentos Contábeis
	 * @return {@link List<#ContaContabilDomain>}
	 * @throws Exception
	 */
	private List<ContaContabilDomain> getList() throws Exception {
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
		
		List<ContaContabilDomain> list = new ArrayList<ContaContabilDomain>();
		list.add(domain1);
		list.add(domain2);
		list.add(domain3);
		list.add(domain4);
		list.add(domain5);
		list.add(domain6);
		list.add(domain7);
		list.add(domain8);
		
		WebTarget target = this.getTarget("clear");
		
		target.request(MediaType.APPLICATION_JSON).get();
		
		return list;
	}
}
