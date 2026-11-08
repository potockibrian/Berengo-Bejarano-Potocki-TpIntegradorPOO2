package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangoDeFechasTest {

	private LocalDate fechaInicio;
	private LocalDate fechaFinal;
	private RangoDeFechas periodo;
	private RangoDeFechas periodo1;
	
	@BeforeEach
	void setUp() throws Exception {
		
		fechaInicio = LocalDate.of(2026, 1, 10);
		fechaFinal = LocalDate.of(2026, 2, 10);
		periodo1 = mock(RangoDeFechas.class);
		
		periodo = new RangoDeFechas(fechaInicio, fechaFinal);
		
	}

	private static void setCampo(Object objeto, String nombre, Object valor) throws Exception {
		
		Field campo = objeto.getClass().getDeclaredField(nombre);
		campo.setAccessible(true);
		campo.set(objeto, valor);
		
	}
	
	@Test
	void testFechaInicio() {
		
		assertEquals(fechaInicio, periodo.getFechaInicio());
		
	}

	@Test
	void testFechaFinal() {
		
		assertEquals(fechaFinal, periodo.getFechaFinal());
		
	}
	
	@Test
	void testEquals() {

		when(periodo1.getFechaInicio()).thenReturn(fechaInicio);
		when(periodo1.getFechaFinal()).thenReturn(fechaFinal);
		
		assertTrue(periodo.equals(periodo1));
		
		when(periodo1.getFechaInicio()).thenReturn(fechaInicio);
		when(periodo1.getFechaFinal()).thenReturn(LocalDate.of(2025, 2, 1));
		
		assertFalse(periodo.equals(periodo1));
		
		when(periodo1.getFechaInicio()).thenReturn(LocalDate.of(3000, 1, 1));
		
		assertFalse(periodo.equals(periodo1));
		
	}
	
	/*
	@Test
	void testInterseccion() {
		
		fechaInicio = mock(LocalDate.class);
		when(fechaInicio.compareTo(periodo1.getFechaFinal())).thenReturn(-1);
		when(periodo1.getFechaInicio()).thenReturn(LocalDate.of(2025, 2, 1));
		when(periodo1.getFechaFinal()).thenReturn(fechaFinal);
		
		assertTrue(periodo.interseccion(periodo1));
		
		fechaFinal = LocalDate.of(3000, 1, 1);
		when(periodo1.getFechaInicio()).thenReturn(fechaInicio);
		when(periodo1.getFechaFinal()).thenReturn(fechaFinal);
		
		assertFalse(periodo.interseccion(periodo1));
		
		fechaInicio = LocalDate.of(3000, 1, 1);
		when(periodo1.getFechaInicio()).thenReturn(fechaInicio);
		when(periodo1.getFechaFinal()).thenReturn(fechaFinal);
		
		assertFalse(periodo.interseccion(periodo1));
		
	}
	
	@Test
	void testUnion() {
		
		when(periodo1.getFechaInicio()).thenReturn(LocalDate.of(2025, 2, 1));
		when(periodo1.getFechaFinal()).thenReturn(LocalDate.of(2027, 2, 1));
		
		RangoDeFechas nuevo = periodo.union(periodo1);
		
		assertEquals(nuevo.getFechaInicio(), min(fechaInicio, periodo1.getFechaInicio()));
		assertEquals(nuevo.getFechaFinal(), max(fechaFinal, periodo1.getFechaFinal()));

	}
	*/
	
	@Test
	void testHashCode() {
		
		assertEquals(Objects.hash(fechaInicio, fechaFinal), periodo.hashCode());
		
	}
	
	@Test
	void testFechas() {
		
		assertEquals(Stream.iterate(fechaInicio, fecha -> !fecha.isAfter(fechaFinal), fecha -> fecha.plusDays(1))
		        .toList(), periodo.fechas());
		
	}
	
	private LocalDate min(LocalDate fecha1, LocalDate fecha2) {
		
		return (fecha1.compareTo(fecha2) <= 0) ? fecha1 : fecha2;
		
	}
	
	private LocalDate max(LocalDate fecha1, LocalDate fecha2) {
		
		return (fecha1.compareTo(fecha2) >= 0)? fecha1 : fecha2;
		
	}

}
