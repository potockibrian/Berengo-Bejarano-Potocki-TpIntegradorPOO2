package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TipoFechaTest {

	private TipoAtributo tipo = new TipoFecha();

	@Test
	void testAceptaUnaFecha() {

		assertTrue(tipo.esValorValido(LocalDate.of(2026, 11, 12)));

	}

	@Test
	void testNoAceptaUnTextoConFormatoDeFecha() {

		assertFalse(tipo.esValorValido("12/11/2026"));

	}

}
