package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoTextoTest {

	private TipoAtributo tipo = new TipoTexto();

	@Test
	void testAceptaUnTexto() {

		assertTrue(tipo.esValorValido("Se permiten mascotas"));

	}

	@Test
	void testNoAceptaUnNumero() {

		assertFalse(tipo.esValorValido(4));

	}

}
