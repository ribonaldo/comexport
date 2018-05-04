package br.com.comexport.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Classe para as estatísticas de consultas de lançamentos contábeis
 * @author Ricardo Bonaldo
 */
public class EstatisticaContaContabilDomain implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -306653104533996340L;

	@Expose
	private BigDecimal soma;

	@Expose
	private BigDecimal min;

	@Expose
	private BigDecimal max;

	@Expose
	private BigDecimal media;

	@Expose
	private BigDecimal qtde;

	public void collect(List<ContaContabilHashDomain> list) {
		if (list != null && !list.isEmpty()) {
			this.qtde = new BigDecimal(list.size());
			boolean isFirst = true;
			for (ContaContabilHashDomain contaHash : list) {
				BigDecimal valor = contaHash.getConta().getValor();
				if (isFirst) {
					isFirst = false;
					this.soma = BigDecimal.ZERO;
					this.min = valor;
					this.max = valor;
				}
				if (valor.compareTo(this.max) > 0) {
					this.max = valor;
				}
				if (valor.compareTo(this.min) < 0) {
					this.min = valor;
				}
				this.soma = soma.add(valor);
			}
			this.media = this.soma.divide(this.qtde, 2, RoundingMode.HALF_UP);
		}
	}

	/**
	 * @return the soma
	 */
	public final BigDecimal getSoma() {
		return soma;
	}

	/**
	 * @param soma
	 *            the soma to set
	 */
	public final void setSoma(BigDecimal soma) {
		this.soma = soma;
	}

	/**
	 * @return the min
	 */
	public final BigDecimal getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public final void setMin(BigDecimal min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public final BigDecimal getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public final void setMax(BigDecimal max) {
		this.max = max;
	}

	/**
	 * @return the media
	 */
	public final BigDecimal getMedia() {
		return media;
	}

	/**
	 * @param media
	 *            the media to set
	 */
	public final void setMedia(BigDecimal media) {
		this.media = media;
	}

	/**
	 * @return the qtde
	 */
	public final BigDecimal getQtde() {
		return qtde;
	}

	/**
	 * @param qtde
	 *            the qtde to set
	 */
	public final void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

}
