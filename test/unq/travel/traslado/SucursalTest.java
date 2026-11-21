package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

class SucursalTest {

	private Sucursal sucursal;
	private CalendarioDeCupos calendario;
	private RangoDeFechas periodo;
	
	@BeforeEach
	void setUp() throws Exception {
	
		periodo = mock(RangoDeFechas.class);
		calendario = mock(CalendarioDeCupos.class);
		sucursal = new Sucursal(calendario);
	
	}

	@Test
	void testCuandoNoHayGuiasNoHayDisponibilidad() {

		when(calendario.tieneDisponibilidad(periodo)).thenReturn(false);
		
		assertFalse(sucursal.hayDisponibilidad(periodo));
		
	}
	
	@Test
	void testCuandoHayGuiasHayDisponibilidad() {

		when(calendario.tieneDisponibilidad(periodo)).thenReturn(true);
		
		assertTrue(sucursal.hayDisponibilidad(periodo));
		
		
	}
	
	@Test
	void testAgregarCupoParaPeriodo() {
		
		sucursal.agregarCupoParaPeriodo(periodo, 1);
		
		verify(calendario).agregarCupoParaPeriodo(periodo, 1);
		
	}
	
	@Test
	void testReservar() {
		
		sucursal.reservar(periodo);
		
		verify(calendario).reservar(periodo);
		
	}
	
	@Test
	void testLiberar() {
		
		sucursal.liberar(periodo);
		
		verify(calendario).liberar(periodo);
		
	}

}
