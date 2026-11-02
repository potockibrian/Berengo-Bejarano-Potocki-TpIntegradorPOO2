package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoRecepcionTest {
	
	private EstrategiaTraslado estrategia;
	private Sucursal sucursal;
	private Reserva reserva;
	
	@BeforeEach
	void setUp() {
		
		sucursal = mock(Sucursal.class);
		reserva = mock(Reserva.class);
		estrategia = new TrasladoRecepcion(sucursal);
		
	}
	/*
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
	*/
	@Test
	void testCostoTrasladoConDisponibilidad() {
		
		when(sucursal.hayDisponibilidad()).thenReturn(true);
		
		assertEquals(0, estrategia.costoTraslado(reserva));
		
	}
	
	@Test
	void testCostoTrasladoSinDisponibilidad() {
		
		when(sucursal.hayDisponibilidad()).thenReturn(false);
		
		assertThrows(NoHayDisponibilidadException.class, () -> estrategia.costoTraslado(reserva));
		
	}
	
}