package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoBooleanoTest {

	private TipoAtributo tipo = new TipoBooleano();

	@Test
	void testAceptaVerdaderoYFalso() {

		assertTrue(tipo.esValorValido(true));
		assertTrue(tipo.esValorValido(false));

	}

	@Test
	void testNoAceptaUnTextoAunqueParezcaBooleano() {

		assertFalse(tipo.esValorValido("si"));

	}

}
