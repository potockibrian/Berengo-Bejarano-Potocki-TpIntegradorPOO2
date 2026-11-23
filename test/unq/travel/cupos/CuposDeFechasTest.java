package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.SinStockException;

class CuposDeFechasTest {
	
	private LocalDate DIA_1 = LocalDate.of(2026, 1, 1);
	private LocalDate DIA_2 = LocalDate.of(2026, 1, 2);
	private LocalDate DIA_3 = LocalDate.of(2026, 1, 3);
	private LocalDate DIA_4 = LocalDate.of(2026, 1, 4);

	private CuposDeFechas cuposDeFechas;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cuposDeFechas = new CuposDeFechas();
		
	}

	@Test
	void testAgregarCantidadDeCuposAFecha() {

		cuposDeFechas.agregarCantidadDeCuposAFecha(DIA_1, 1);
		
		assertTrue(cuposDeFechas.tieneDisponibilidad(DIA_1));
		assertFalse(cuposDeFechas.tieneDisponibilidad(DIA_2));
		
	}
	
	@Test
	void testTieneDisponibilidad() {
		
		assertFalse(cuposDeFechas.tieneDisponibilidad());
		assertFalse(cuposDeFechas.tieneDisponibilidad(DIA_1));
		
		cuposDeFechas.agregarCantidadDeCuposAFecha(DIA_1, 1);
		
		assertTrue(cuposDeFechas.tieneDisponibilidad());
		assertTrue(cuposDeFechas.tieneDisponibilidad(DIA_1));
				
	}
	
	@Test
	void testReservarSinCuposLevantaExcepcion() {
		
		assertThrows(SinStockException.class, () -> cuposDeFechas.reservar(DIA_1));
		
	}
	
	/*
	@Test
	void testCantidadDeVentasEnFecha() {
		
		cuposDeFechas.agregarCantidadDeCuposAFecha(DIA_1, 1);
		
		cuposDeFechas.reservar(DIA_1);
		
		assertEquals(1, cuposDeFechas.cantidadDeVentasEnFecha(DIA_1));
		
	}
	*/
	
	@Test
	void testCuposEnFechas() {
		
		Cupo cupo = cuposDeFechas.cuposEnFechas(List.of(DIA_1, DIA_2, DIA_4));
		
		assertEquals(0, cupo.cantidadDeCuposDisponibles());
		assertEquals(0, cupo.cantidadDeVentas());
		assertFalse(cupo.tieneDisponibilidad());

		cuposDeFechas.agregarCantidadDeCuposAFecha(DIA_1, 10);
		
		cupo = cuposDeFechas.cuposEnFechas(List.of(DIA_1, DIA_2, DIA_4));
		
		assertEquals(10, cupo.cantidadDeCuposDisponibles());
		assertEquals(0, cupo.cantidadDeVentas());
		assertTrue(cupo.tieneDisponibilidad());
		
		
	}

}
