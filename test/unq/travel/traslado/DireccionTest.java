package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DireccionTest {

	private Direccion direccion;
	
	@BeforeEach
	void setUp() throws Exception {
	
		direccion = new Direccion("calle", 10, "localidad");
	
	}

	@Test
	void testCamposConValoresCorrectos() {
		
		assertEquals(direccion.calle, "calle");
		assertEquals(direccion.numero, 10);
		assertEquals(direccion.localidad, "localidad");
		
	}

}
