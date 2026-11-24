package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

class TrasladoRecepcionTest {
	
	private TrasladoRecepcion estrategia;
	private Reserva reserva;
	private Sucursal sucursal;
	private RangoDeFechas periodo;
	
	@BeforeEach
	void setUp() {
		
		periodo = mock(RangoDeFechas.class);
		sucursal = mock(Sucursal.class);
		reserva = mock(Reserva.class);
		estrategia = new TrasladoRecepcion(sucursal);
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalNoTieneDisponibilidad() {
		
		when(sucursal.hayDisponibilidad(periodo)).thenReturn(false);
		
		assertFalse(estrategia.hayDisponibilidad(periodo));
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalTieneDisponibilidad() {
		
		when(sucursal.hayDisponibilidad(periodo)).thenReturn(true);
		
		assertTrue(estrategia.hayDisponibilidad(periodo));
		
	}
	
	@Test
	void testCostoTraslado() {
		
		assertEquals(0, estrategia.costoTasasTraslado(reserva));
		
	}
	
	@Test
	void testReservar() {
		
		estrategia.reservar(periodo);
		
		verify(sucursal).reservar(periodo);
		
	}
	
	@Test
	void testLiberar() {
		
		estrategia.liberar(periodo);
		
		verify(sucursal).liberar(periodo);
		
	}
	
}