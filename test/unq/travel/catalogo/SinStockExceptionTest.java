package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SinStockExceptionTest {

	@Test
	void testGuardaElMensajeRecibido() {

		SinStockException excepcion = new SinStockException("No hay cupo disponible");

		assertEquals("No hay cupo disponible", excepcion.getMessage());

	}

}
