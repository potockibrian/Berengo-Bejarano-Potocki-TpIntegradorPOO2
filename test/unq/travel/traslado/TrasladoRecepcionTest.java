package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoRecepcionTest {
	
	private EstrategiaTraslado estrategia;
	private Sucursal sucursal;
	
	@BeforeEach
	void setUp() {
		
		sucursal = mock(Sucursal.class);
		
		estrategia = new TrasladoRecepcion(sucursal);
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalNoTieneDisponibilidad() {
		
		when(sucursal.hayDisponibilidad()).thenReturn(false);
		
		assertFalse(estrategia.hayDisponibilidad());
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalTieneDisponibilidad() {
		
		when(sucursal.hayDisponibilidad()).thenReturn(true);
		
		assertTrue(estrategia.hayDisponibilidad());
		
	}
	
	@Test
	void testCostoTraslado() {
		
		assertEquals(0, estrategia.costoTraslado());
		
	}
	
}