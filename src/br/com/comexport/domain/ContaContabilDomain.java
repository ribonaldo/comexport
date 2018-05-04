package br.com.comexport.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.Expose;

/**
 * Classe para operações de Lançamentos Contábeis
 * 
 * @author Ricardo Bonaldo
 */
public class ContaContabilDomain implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 534084602325017306L;
	
	private final SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
	
	@Expose
	private Long contaContabil;
	
	@Expose(serialize = false)
	private Date dataNormal;
	
	@Expose
	private String data;
	
	@Expose
	private BigDecimal valor;

	/**
	 * @return the contaContabil
	 */
	public final Long getContaContabil() {
		return contaContabil;
	}

	/**
	 * @param contaContabil the contaContabil to set
	 */
	public final void setContaContabil(Long contaContabil) {
		this.contaContabil = contaContabil;
	}

	/**
	 * @return the dataNormal
	 */
	public final Date getDataN() {
		return dataNormal;
	}

	/**
	 * @param data the data to set
	 */
	public final void setDataN(Date data) {
		this.dataNormal = data;
		if (this.dataNormal != null) {
			this.data = fmt.format(this.dataNormal);
		}
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(String data) throws Exception {
		try {
			if (data != null) {
				this.data = data;
				this.fmt.setLenient(false);
				this.dataNormal = this.fmt.parse(data);
			}
		} catch (ParseException e) {
			throw new Exception("Formato de data inválido. Utilize o padrão 'AAAAMMDD'");
		}
	}

	/**
	 * @return the valor
	 */
	public final BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public final void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
