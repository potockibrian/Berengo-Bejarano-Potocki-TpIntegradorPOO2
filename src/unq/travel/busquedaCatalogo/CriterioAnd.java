package unq.travel.busquedaCatalogo;

import unq.travel.catalogo.ItemCatalogo;

public class CriterioAnd implements Criterio {

	private Criterio primerCriterio;
	private Criterio segundoCriterio;

	public CriterioAnd(Criterio primerCriterio, Criterio segundoCriterio) {

		this.primerCriterio = primerCriterio;
		this.segundoCriterio = segundoCriterio;

	}

	@Override
	public boolean cumpleConCriterio(ItemCatalogo item) {

		return primerCriterio.cumpleConCriterio(item) && segundoCriterio.cumpleConCriterio(item);

	}

}
