/**
 * 
 */
package br.com.comexport.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Classe de Utilitário para Json
 * 
 * @author Ricardo Bonaldo
 */
public final class JsonUtil {
	
	/**
	 * Retorna um JSon.
	 * @param object {@link Object}
	 * @return Json
	 */
	public static String getJson(final Object object) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		
		return gson.toJson(object);
	}

}
