package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SucursalTest {

	Sucursal sucursal;
	
	@BeforeEach
	void setUp() throws Exception {
	
		sucursal = new Sucursal(0);
	
	}

	@Test
	void testCuandoNoHayGuiasNoHayDisponibilidad() {

		sucursal.cantidadDeGuias = 0;
		
		assertFalse(sucursal.hayDisponibilidad());
		
	}
	
	@Test
	void testCuandoHayGuiasHayDisponibilidad() {

		sucursal.cantidadDeGuias = 1;
		
		assertTrue(sucursal.hayDisponibilidad());
		
		
	}

}
