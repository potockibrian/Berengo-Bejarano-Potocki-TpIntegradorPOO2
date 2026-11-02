package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioNombre implements Criterio {

	private String texto;

	public CriterioNombre(String texto) {

		this.texto = texto;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return item.getNombre().toLowerCase().contains(texto.toLowerCase());

	}

}
