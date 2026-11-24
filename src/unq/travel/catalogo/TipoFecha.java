package unq.travel.catalogo;

import java.time.LocalDate;

public class TipoFecha implements TipoAtributo<LocalDate> {

	@Override
	public boolean esValorValido(LocalDate valor) throws ClassCastException {

		return true;

	}

}
