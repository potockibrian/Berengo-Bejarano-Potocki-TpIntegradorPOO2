package unq.travel.catalogo;

public class TipoNumerico implements TipoAtributo {

	@Override
	public boolean esValorValido(Object valor) {

		return valor instanceof Number;

	}

}
