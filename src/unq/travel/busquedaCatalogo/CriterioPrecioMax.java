package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioPrecioMax implements Criterio {

	private float precioMaximo;

	public CriterioPrecioMax(float precioMaximo) {

		this.precioMaximo = precioMaximo;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return item.getPrecioBase() <= precioMaximo;

	}

}
