package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioNot implements Criterio {

	private Criterio criterioEnvuelto;

	public CriterioNot(Criterio criterioEnvuelto) {

		this.criterioEnvuelto = criterioEnvuelto;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return !criterioEnvuelto.cumpleConCriterio(item);

	}

}
