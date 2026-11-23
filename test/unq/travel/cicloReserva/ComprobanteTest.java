package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComprobanteTest {

	private Comprobante comprobante;
	
	@BeforeEach
	void setUp() throws Exception {
	
		comprobante = new Comprobante(1000f, 100f, 2000f);
	
	}

	@Test
	void testCostoTotalReserva() {

		assertEquals(1000f, comprobante.getCostoTotalReserva());
		
	}
	
	@Test
	void testCostoServicios() {

		assertEquals(100f, comprobante.getCostoServicios());
		
	}
	
	@Test
	void testCostoTasasTraslado() {

		assertEquals(2000f, comprobante.getCostoTasasTraslado());
		
	}

}
