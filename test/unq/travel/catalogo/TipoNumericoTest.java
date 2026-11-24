package unq.travel.catalogo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoNumericoTest {

	private TipoAtributo tipo = new TipoNumerico();

	@Test
	void testAceptaNumerosEnteros() {

		assertTrue(tipo.esValorValido(4));

	}

	@Test
	void testAceptaNumerosConDecimales() {

		assertTrue(tipo.esValorValido(4.5f));
		assertTrue(tipo.esValorValido(4.5));

	}

	@Test
	void testNoAceptaUnTextoAunqueSeaUnNumero() {

		assertThrows(ClassCastException.class, () -> tipo.esValorValido("4"));

	}

	@Test
	void testNoAceptaUnBooleano() {

		assertThrows(ClassCastException.class, () -> tipo.esValorValido(true));

	}

}
