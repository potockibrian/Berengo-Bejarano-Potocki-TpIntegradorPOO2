package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;

public class CriterioCategoria implements Criterio {

	private Categoria categoria;

	public CriterioCategoria(Categoria categoria) {

		this.categoria = categoria;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return item.esDeCategoria(categoria);

	}

}
