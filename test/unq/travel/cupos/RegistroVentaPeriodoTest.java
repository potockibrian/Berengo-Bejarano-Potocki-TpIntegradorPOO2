package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroVentaPeriodoTest {

	private LocalDate DIA_1 = LocalDate.of(2026, 1, 1);
	private LocalDate DIA_2 = LocalDate.of(2026, 1, 2);
	private LocalDate DIA_3 = LocalDate.of(2026, 1, 3);
	private LocalDate DIA_4 = LocalDate.of(2026, 1, 4);
	private LocalDate DIA_5 = LocalDate.of(2026, 1, 5);

	private RangoDeFechas periodo;
	private RegistroVentaPeriodo registro;
	
	@BeforeEach
	void setUp() throws Exception {
	
		periodo = new RangoDeFechas(DIA_1, DIA_4);
		registro = new RegistroVentaPeriodo(periodo);
		
	}

	@Test
	void testPrecioPromedio() {
		
		for(int i=0; i<10; i++) registro.agregarVenta(1000f);
		
		assertEquals((1000f * 10) / 10, registro.precioPromedio());
		
	}
	
	@Test
	void testPeriodo() {

		assertEquals(periodo, registro.periodo());
		
	}
	
	@Test
	void testAgregarVenta() {
		
		registro.agregarVenta(100f);
		
		assertEquals(100f, registro.precioPagado());
		assertEquals(1, registro.cantidadVentas());
		
	}
	
	@Test
	void testTieneMismoPeriodo() {
		
		assertTrue(registro.tieneMismoPeriodo(new RangoDeFechas(DIA_1, DIA_4)));
		assertFalse(registro.tieneMismoPeriodo(new RangoDeFechas(DIA_1, DIA_3)));
		
	}
	
	@Test
	void testPeriodoContiene() {
		
		assertTrue(registro.periodoContiene(new RangoDeFechas(DIA_1, DIA_3)));
		assertFalse(registro.tieneMismoPeriodo(new RangoDeFechas(DIA_1, DIA_5)));
		
	}
	
	@Test
	void testCombinarConRegistro() {
		
		registro.agregarVenta(100f);
		
		RegistroVentaPeriodo registro1 = registro.combinarConRegistro(registro);
		
		assertEquals(registro.cantidadVentas() + registro.cantidadVentas(), registro1.cantidadVentas());
		assertEquals(registro.precioPagado() + registro.precioPagado(), registro1.precioPagado());
		
	}
	
	@Test
	void testCombinarConRegistroRecursivo() {
		
		registro.agregarVenta(100f);
		
		RegistroVentaPeriodo registro1 = registro.combinarConRegistroRecursivo(registro);
		
		assertEquals(registro.cantidadVentas(), registro1.cantidadVentas());
		assertEquals(registro.precioPagado() + registro.precioPagado(), registro1.precioPagado());
		
	}

	@Test
	void testAplicarDescuento() {
		
		registro.agregarVenta(100f);
		
		registro.aplicarDescuento(0.2f);
		
		assertEquals(100f * (1 - 0.2f), registro.precioPagado());
		
	}
	

}
