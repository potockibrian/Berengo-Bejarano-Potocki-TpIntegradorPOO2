package unq.travel.catalogo;

public class TipoBooleano implements TipoAtributo {

	@Override
	public boolean esValorValido(Object valor) {

		return valor instanceof Boolean;

	}

}
