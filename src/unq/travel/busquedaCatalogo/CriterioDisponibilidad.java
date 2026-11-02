package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioDisponibilidad implements Criterio {

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return item.tieneDisponibilidad();

	}

}
