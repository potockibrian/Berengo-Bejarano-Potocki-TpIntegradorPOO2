package unq.travel.catalogo;

public class TipoTexto implements TipoAtributo<String> {

	@Override
	public boolean esValorValido(String valor) throws ClassCastException {

		return true;

	}

}
