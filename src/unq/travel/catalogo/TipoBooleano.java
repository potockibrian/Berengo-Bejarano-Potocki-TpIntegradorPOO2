package unq.travel.catalogo;

public class TipoBooleano implements TipoAtributo<Boolean> {

	@Override
	public boolean esValorValido(Boolean valor) throws ClassCastException {

		return true;

	}

}
