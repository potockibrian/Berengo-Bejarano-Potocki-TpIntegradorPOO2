package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Comprobante;

class ComprobanteTest {

	Comprobante comprobante;
	
	@BeforeEach
	void setUp() throws Exception {
		
		comprobante = new Comprobante(300f, 200f, 100f);
		
	}

	@Test
	void testCostoTotalReserva() {

		assertEquals(300f, comprobante.getCostoTotalReserva());
		
	}
	
	@Test
	void testCostoServicios() {

		assertEquals(200f, comprobante.getCostoServicios());
		
	}
	
	@Test
	void testCostoTasasTraslado() {

		assertEquals(100f, comprobante.getCostoTasasTraslado());
		
	}

}
