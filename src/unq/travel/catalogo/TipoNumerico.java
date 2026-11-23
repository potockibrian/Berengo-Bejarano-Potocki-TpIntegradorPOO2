package unq.travel.catalogo;

public class TipoNumerico implements TipoAtributo<Number> {

	@Override
	public boolean esValorValido(Number valor) throws ClassCastException{

		return true;

	}

}
