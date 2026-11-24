package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.SinStockException;
import unq.travel.traslado.NoHayDisponibilidadException;

class CalendarioDeCuposTest {

	private LocalDate DIA_1 = LocalDate.of(2026, 1, 1);
	private LocalDate DIA_2 = LocalDate.of(2026, 1, 2);
	private LocalDate DIA_3 = LocalDate.of(2026, 1, 3);
	private LocalDate DIA_4 = LocalDate.of(2026, 1, 4);

	private CalendarioDeCupos calendario;
	private CuposDeFechas cuposDeFechas;

	@BeforeEach
	void setUp() throws Exception {
		
		cuposDeFechas = new CuposDeFechas();
		calendario = new CalendarioDeCupos();
		setCampo(calendario, "cuposDeFechas", cuposDeFechas);
		
	}

	private static void setCampo(Object objeto, String nombre, Object valor) throws Exception {
		
		Field campo = objeto.getClass().getDeclaredField(nombre);
		campo.setAccessible(true);
		campo.set(objeto, valor);
		
	}

	@SuppressWarnings("unchecked")
	private static <T> T getCampo(Object objeto, String nombre) throws Exception {
		
		Field campo = objeto.getClass().getDeclaredField(nombre);
		campo.setAccessible(true);
		return (T) campo.get(objeto);
		
	}
	
	private static RangoDeFechas rango(LocalDate... fechas) {
		
		RangoDeFechas rango = spy(new RangoDeFechas(fechas[0], fechas[fechas.length - 1]));
		doReturn(List.of(fechas)).when(rango).fechas();
		return rango;
		
	}

	private static Cupo cupo(boolean disponible, int ventas) {
		
		Cupo cupo = mock(Cupo.class);
		when(cupo.tieneDisponibilidad()).thenReturn(disponible);
		when(cupo.cantidadDeVentas()).thenReturn(ventas);
		return cupo;
		
	}

	private CuposDeFechas cuposDeFechas() throws Exception {
		
		return getCampo(calendario, "cuposDeFechas");
		
	}

	@Test
	void unCalendarioNuevoNoTieneDisponibilidad() {
		
		assertFalse(calendario.tieneDisponibilidad());
		
	}

	@Test
	void tieneDisponibilidadSiAlMenosUnCupoTieneDisponibilidad() throws Exception {
		
		cuposDeFechas().agregarCantidadDeCuposAFecha(DIA_1, 5);
		cuposDeFechas().agregarCantidadDeCuposAFecha(DIA_2, 1);

		assertTrue(calendario.tieneDisponibilidad());
		
	}

	@Test
	void noTieneDisponibilidadSiTodosLosCuposEstanAgotados() throws Exception {
		
		cuposDeFechas().agregarCantidadDeCuposAFecha(DIA_1, 5);
		cuposDeFechas().agregarCantidadDeCuposAFecha(DIA_2, 5);
		
		for(int i=0; i<5; i++) calendario.reservar(new RangoDeFechas(DIA_1, DIA_2));

		assertFalse(calendario.tieneDisponibilidad());
		
	}

	@Test
	void cuposEnPeriodoDevuelveSoloLosCuposDeLasFechasDelRango() throws Exception {
		
		calendario.agregarCupoParaPeriodo(new RangoDeFechas(DIA_1, DIA_2), 1);
		calendario.agregarCupoParaPeriodo(new RangoDeFechas(DIA_2, DIA_3), 0);

		Cupo cupo = calendario.cuposEnPeriodo(new RangoDeFechas(DIA_2, DIA_3));
		
		assertEquals(1, cupo.cantidadDeCuposDisponibles());
		
	}

	@Test
	void cuposEnPeriodoEsVacioSiNoHayCuposEnEsasFechas() {
		
		Cupo cupo = calendario.cuposEnPeriodo(new RangoDeFechas(DIA_1, DIA_2));
		
		assertEquals(0, cupo.cantidadDeCuposDisponibles());
		assertEquals(0, cupo.cantidadDeVentas());
		assertEquals(0, cupo.getTopeMaximo());
		assertFalse(cupo.tieneDisponibilidad());
		
	}

	@Test
	void tieneDisponibilidadEnUnRangoSiTodosSusCuposTienenDisponibilidad() throws Exception {
		
		calendario.agregarCupoParaPeriodo(rango(DIA_1, DIA_2), 1);
		calendario.agregarCupoParaPeriodo(rango(DIA_3, DIA_4), 0);

		assertTrue(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
		assertFalse(calendario.tieneDisponibilidad(rango(DIA_3, DIA_4)));
		
	}

	@Test
	void noTieneDisponibilidadEnUnRangoSiAlgunCupoEstaAgotado() throws Exception {
	
		calendario.agregarCupoParaPeriodo(rango(DIA_1, DIA_2), 1);
		
		calendario.reservar(new RangoDeFechas(DIA_1, DIA_1));

		assertFalse(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
	
	}

	@Test
	void unRangoSinCuposNoTieneDisponibilidad() {
		
		assertFalse(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
		
	}

	@Test
	void testReservarSinDisponibilidadLevantaExcepcion() {
		
		assertThrows(SinStockException.class, () -> calendario.reservar(new RangoDeFechas(DIA_1, DIA_2)));
		
	}
	
	@Test
	void unCupoAgotadoFueraDelRangoNoAfectaLaDisponibilidadDelRango() throws Exception {
		
		calendario.agregarCupoParaPeriodo(new RangoDeFechas(DIA_1, DIA_4), 1);

		calendario.reservar(new RangoDeFechas(DIA_4, DIA_4));
		
		assertTrue(calendario.tieneDisponibilidad(new RangoDeFechas(DIA_1, DIA_3)));
		
	}

	@Test
	void liberarEnUnCalendarioSinCuposNoHaceNada() {
		
		calendario.liberar(new RangoDeFechas(DIA_1, DIA_2));

		assertFalse(calendario.tieneDisponibilidad());
		
	}
	
	@Test
	void liberarLiberaElCupoAsociadoAlRango() throws Exception {
		
		RangoDeFechas rango = new RangoDeFechas(DIA_1, DIA_1);
		Cupo cupo = cupo(true, 1);
		Map<LocalDate, Cupo> mapaConClaveRango = new HashMap<LocalDate, Cupo>();
		mapaConClaveRango.put(DIA_1, cupo);
		setCampo(cuposDeFechas, "cuposConFecha", mapaConClaveRango);

		calendario.liberar(rango);

		verify(cupo).liberar();
		
	}

}