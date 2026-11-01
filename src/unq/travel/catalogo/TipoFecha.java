package unq.travel.catalogo;

import java.time.LocalDate;

public class TipoFecha implements TipoAtributo {

	@Override
	public boolean esValorValido(Object valor) {

		return valor instanceof LocalDate;

	}

}
