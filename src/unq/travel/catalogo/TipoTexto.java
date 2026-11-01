package unq.travel.catalogo;

public class TipoTexto implements TipoAtributo {

	@Override
	public boolean esValorValido(Object valor) {

		return valor instanceof String;

	}

}
