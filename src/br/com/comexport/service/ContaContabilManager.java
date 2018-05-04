/**
 * 
 */
package br.com.comexport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.comexport.domain.ContaContabilDomain;
import br.com.comexport.domain.ContaContabilHashDomain;
import br.com.comexport.domain.EstatisticaContaContabilDomain;

/**
 * Classe responsável por gerenciar as informações de Lançamento Contábil.
 * 
 * @author Ricardo Bonaldo
 *
 */
public class ContaContabilManager {

	private static ContaContabilManager manager;

	private static List<ContaContabilHashDomain> list;
	
	private final String SALT = "abcdef1234567890";

	private final int EIGHT = 8;

	private final int THREE = 3;

	private final int FOUR = 4;

	private final int TWELVE = 12;

	private final String TRACE = "-";

	/**
	 * Construtor privado para garantir singleton.
	 */
	private ContaContabilManager() {
	}

	/**
	 * Cria um Singleton
	 * @return {@link ContaContabilManager}
	 */
	public static ContaContabilManager getManager() {
		synchronized (ContaContabilManager.class) {
			if (manager == null) {
				manager = new ContaContabilManager();
				list = new ArrayList<ContaContabilHashDomain>();
			}
		}
		return manager;
	}

	/**
	 * Adiciona um Lançamento Contábil
	 * @param data {@link ContaContabilDomain}
	 * @throws Exception
	 */
	public ContaContabilHashDomain addData(final ContaContabilDomain data) throws Exception {
		synchronized (ContaContabilManager.class) {
			this.validate(data);
			ContaContabilHashDomain domain = this.generateHash(data);
			list.add(domain);
			return domain;
		}
	}
	
	/**
	 * Obtém um Lançamento Contábil
	 * @param hash chave para busca
	 * @return {@link ContaContabilHashDomain}
	 * @throws Exception
	 */
	public ContaContabilHashDomain get(final String hash) throws Exception {
		List<ContaContabilHashDomain> result = list.stream()
			     .filter(item -> item.getId().equals(hash))
			     .collect(Collectors.toList());
		if (result == null || result.isEmpty()) {
			throw new Exception("Not Find");
		}
		return result.get(0);
	}
	
	/**
	 * Obtém os lançamentos contábeis de uma conta
	 * @param contaContabil Conta Contábil
	 * @return {@link ContaContabilHashDomain}
	 * @throws Exception
	 */
	public List<ContaContabilHashDomain> get(final Long contaContabil) {
		List<ContaContabilHashDomain> result = list.stream()
			     .filter(item -> item.getConta().getContaContabil().equals(contaContabil))
			     .collect(Collectors.toList());
		return result;
	}
	
	/**
	 * Retorna todos os lançamentos contábeis
	 * @return {@link List<#ContaContabilHashDomain>}
	 */
	public List<ContaContabilHashDomain> findAll() {
		return list;
	}
	
	/**
	 * Retorna todos os lançamentos contábeis de uma conta
	 * @param contaContabil Conta Contábil
	 * @return {@link List<#ContaContabilHashDomain>}
	 */
	public EstatisticaContaContabilDomain getStats(Long contaContabil) throws Exception {
		EstatisticaContaContabilDomain estatisticas = new EstatisticaContaContabilDomain();
		if (contaContabil == null) {
			estatisticas.collect(list);
		} else {
			estatisticas.collect(this.get(contaContabil));
		}
		return estatisticas;
	}
	
	/**
	 * Limpa a lista de Lançamentos Contábeis.
	 */
	public void clear() {
		list = new ArrayList<ContaContabilHashDomain>();
	}

	/**
	 * Valida as informações de Conta Contabil.
	 * @param data {@link ContaContabilDomain}
	 * @throws Exception
	 */
	private void validate(final ContaContabilDomain data) throws Exception {
		String message = StringUtils.EMPTY;
		String separator = "";
		if (data.getContaContabil() == null || data.getContaContabil().equals(0L)) {
			message += "O campo conta contábil é obrigatório";
			separator = "\r\n";
		}
		if (data.getDataN() == null) {
			message += separator + "O campo data é obrigatório";
			separator = "\r\n";
		}
		if (data.getValor() == null) {
			message += separator + "O campo valor é obrigatório";
		}
		
		if (StringUtils.isNotBlank(message)) {
			throw new Exception(message);
		}
	}

	/**
	 * Gera um Lançamento Contábil com Hash.
	 * @param data {@link ContaContabilDomain}
	 * @return {@link ContaContabilHashDomain}
	 */
	private ContaContabilHashDomain generateHash(final ContaContabilDomain data) {
		ContaContabilHashDomain contaHash = new ContaContabilHashDomain();
		contaHash.setConta(data);
		
		contaHash.setId(this.makeHash());
		
		while (list.contains(contaHash)) {
			contaHash.setId(this.makeHash());
		}
		
		return contaHash;
	}
	
	/**
	 * Cria um Hash.
	 * @return Hash
	 */
	private String makeHash() {
		StringBuilder hash = new StringBuilder();
		Random rnd = new Random();
		for (int i = 0; i < EIGHT; i++) {
			int index = (int) (rnd.nextFloat() * SALT.length());
			hash.append(SALT.charAt(index));
		}
		hash.append(TRACE);
		for (int j = 0; j < THREE; j++) {
			for (int i = 0; i < FOUR; i++) {
				int index = (int) (rnd.nextFloat() * SALT.length());
				hash.append(SALT.charAt(index));
			}
			hash.append(TRACE);
		}
		for (int i = 0; i < TWELVE; i++) {
			int index = (int) (rnd.nextFloat() * SALT.length());
			hash.append(SALT.charAt(index));
		}
		return hash.toString();
	}
}
