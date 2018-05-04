package br.com.comexport.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;

/**
 * Classe que armazena o HASH gerado para um lançamento contábil.
 * @author Ricardo Bonaldo
 */
public class ContaContabilHashDomain implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6120805491477569760L;
	
	private static final int PRIME_SEVENTEEN = 17;
	
	private static final int PRIME_THIRTY_ONE = 31;
	
	@Expose(serialize = true)
	private String id;
	
	@Expose(serialize = false)
	private ContaContabilDomain conta;

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the conta
	 */
	public final ContaContabilDomain getConta() {
		return conta;
	}

	/**
	 * @param conta the conta to set
	 */
	public final void setConta(ContaContabilDomain conta) {
		this.conta = conta;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
		}
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ContaContabilHashDomain)) {
            return false;
        }

        ContaContabilHashDomain c = (ContaContabilHashDomain) obj;
        return new EqualsBuilder()
        	.append(c.getId(), this.id).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(PRIME_SEVENTEEN, PRIME_THIRTY_ONE)
			.append(this.id).hashCode();
	}
}
