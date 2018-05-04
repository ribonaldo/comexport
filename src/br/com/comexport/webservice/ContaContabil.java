/**
 * 
 */
package br.com.comexport.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.comexport.domain.ContaContabilDomain;
import br.com.comexport.domain.ContaContabilHashDomain;
import br.com.comexport.domain.EstatisticaContaContabilDomain;
import br.com.comexport.service.ContaContabilManager;
import br.com.comexport.util.JsonUtil;

/**
 * Classe de Web Services para Lançamentos Contábeis
 * @author Ricardo Bonaldo
 *
 */
@Path("/")
public class ContaContabil {
	
	private ContaContabilManager manager = ContaContabilManager.getManager();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(ContaContabilDomain contaContabilDomain) {
		String message;
		Status status;
		try {
			ContaContabilHashDomain domain = this.manager.addData(contaContabilDomain);
			message = JsonUtil.getJson(domain);
			status = Status.CREATED;
		} catch (Exception e) {
			message = JsonUtil.getJson(e.getMessage());
			status = Status.EXPECTATION_FAILED;
		}
		
		return Response.status(status).entity(message).build();
	}
	
	@Path("{hash}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getByHash(@PathParam("hash") String hash) {
		String message;
		Status status;
		try {
			ContaContabilHashDomain contaHash = this.manager.get(hash);
			message = JsonUtil.getJson(contaHash.getConta());
			status = Status.OK;
		} catch (Exception e) {
			message = JsonUtil.getJson("Lançamento contábil não encontrado");
			status = Status.NO_CONTENT;
			//status = Status.NOT_FOUND;
		}
		return Response.status(status).entity(message).build();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getByConta(@QueryParam("contaContabil") Long conta) {
		List<ContaContabilHashDomain> contasHash = this.manager.get(conta);
		List<ContaContabilDomain> contas = new ArrayList<ContaContabilDomain>();
		for (ContaContabilHashDomain contaHash : contasHash) {
			contas.add(contaHash.getConta());
		}
		return Response.status(Status.OK).entity(JsonUtil.getJson(contas)).build();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/_stats")
	public Response getStats(@QueryParam("contaContabil") Long conta) {
		String message;
		Status status;
		try {
			EstatisticaContaContabilDomain estatisticas = this.manager.getStats(conta);
			message = JsonUtil.getJson(estatisticas);
			status = Status.OK;
		} catch (Exception e) {
			message = JsonUtil.getJson("Não há dados a serem calculados");
			//status = Status.NOT_FOUND;
			status = Status.NO_CONTENT;
		}
		return Response.status(status).entity(message).build();
	}
	
	@GET
	@Path("/clear")
	public void clear() {
		this.manager.clear();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/make")
	public Response make() {
		ContaContabilDomain domain1 = new ContaContabilDomain();
		ContaContabilDomain domain2 = new ContaContabilDomain();
		ContaContabilDomain domain3 = new ContaContabilDomain();
		ContaContabilDomain domain4 = new ContaContabilDomain();
		ContaContabilDomain domain5 = new ContaContabilDomain();
		ContaContabilDomain domain6 = new ContaContabilDomain();
		ContaContabilDomain domain7 = new ContaContabilDomain();
		
		domain1.setContaContabil(123456L);
		domain2.setContaContabil(223456L);
		domain3.setContaContabil(323456L);
		domain4.setContaContabil(123456L);
		domain5.setContaContabil(423456L);
		domain6.setContaContabil(223456L);
		domain7.setContaContabil(123456L);
		
		domain1.setDataN(new Date());
		domain2.setDataN(new Date());
		domain3.setDataN(new Date());
		domain4.setDataN(new Date());
		domain5.setDataN(new Date());
		domain6.setDataN(new Date());
		domain7.setDataN(new Date());
		
		domain1.setValor(new BigDecimal("8"));
		domain2.setValor(new BigDecimal("16.56"));
		domain3.setValor(new BigDecimal("49.44"));
		domain4.setValor(new BigDecimal("13.55"));
		domain5.setValor(new BigDecimal("12.02"));
		domain6.setValor(new BigDecimal("8.9"));
		domain7.setValor(new BigDecimal("14.99"));
		
		try {
			manager.addData(domain1);
			manager.addData(domain2);
			manager.addData(domain3);
			manager.addData(domain4);
			manager.addData(domain5);
			manager.addData(domain6);
			manager.addData(domain7);
		} catch (Exception e) {
			System.out.println("Erro");
		}
		
		return Response.status(Status.OK).entity(JsonUtil.getJson(this.manager.findAll())).build();
	}
}
