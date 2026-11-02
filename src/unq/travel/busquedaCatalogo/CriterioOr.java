package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioOr implements Criterio {

	private Criterio primerCriterio;
	private Criterio segundoCriterio;

	public CriterioOr(Criterio primerCriterio, Criterio segundoCriterio) {

		this.primerCriterio = primerCriterio;
		this.segundoCriterio = segundoCriterio;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return primerCriterio.cumpleConCriterio(item) || segundoCriterio.cumpleConCriterio(item);

	}

}
