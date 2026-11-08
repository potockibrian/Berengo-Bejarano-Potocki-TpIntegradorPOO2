package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.SinStockException;

class CalendarioDeCuposTest {

	private static final LocalDate DIA_1 = LocalDate.of(2026, 1, 1);
	private static final LocalDate DIA_2 = LocalDate.of(2026, 1, 2);
	private static final LocalDate DIA_3 = LocalDate.of(2026, 1, 3);
	private static final LocalDate DIA_4 = LocalDate.of(2026, 1, 4);

	private CalendarioDeCupos calendario;

	@BeforeEach
	void setUp() throws Exception {
		
		calendario = new CalendarioDeCupos(5);
		// CalendarioDeCupos no inicializa 'periodos' en el constructor (NPE en reservar y cuposPorPeriodo):
		// se inicializa por reflexion para poder ejercitar esos metodos
		setCampo(calendario, "periodos", new ArrayList<RangoDeFechas>());
		
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

	private Map<LocalDate, Cupo> cuposConFecha() throws Exception {
		
		return getCampo(calendario, "cuposConFecha");
		
	}

	@Test
	void unCalendarioNuevoNoTieneCuposNiDisponibilidad() {
		
		assertTrue(calendario.cupos().isEmpty());
		assertFalse(calendario.tieneDisponibilidad());
		
	}

	@Test
	void cuposDevuelveUnaCopiaConTodosLosCupos() throws Exception {
		
		Cupo cupo1 = cupo(true, 0);
		Cupo cupo2 = cupo(true, 0);
		cuposConFecha().put(DIA_1, cupo1);
		cuposConFecha().put(DIA_2, cupo2);

		List<Cupo> cupos = calendario.cupos();
		cupos.clear();

		assertEquals(List.of(cupo2, cupo1), calendario.cupos());
		
	}

	@Test
	void tieneDisponibilidadSiAlMenosUnCupoTieneDisponibilidad() throws Exception {
		
		cuposConFecha().put(DIA_1, cupo(false, 5));
		cuposConFecha().put(DIA_2, cupo(true, 1));

		assertTrue(calendario.tieneDisponibilidad());
		
	}

	@Test
	void noTieneDisponibilidadSiTodosLosCuposEstanAgotados() throws Exception {
		
		cuposConFecha().put(DIA_1, cupo(false, 5));
		cuposConFecha().put(DIA_2, cupo(false, 5));

		assertFalse(calendario.tieneDisponibilidad());
		
	}

	@Test
	void cuposEnPeriodoDevuelveSoloLosCuposDeLasFechasDelRango() throws Exception {
		
		Cupo cupo1 = cupo(true, 0);
		Cupo cupo2 = cupo(true, 0);
		Cupo fueraDelRango = cupo(true, 0);
		cuposConFecha().put(DIA_1, cupo1);
		cuposConFecha().put(DIA_2, cupo2);
		cuposConFecha().put(DIA_4, fueraDelRango);

		assertEquals(List.of(cupo1, cupo2), calendario.cuposEnPeriodo(rango(DIA_1, DIA_2, DIA_3)));
		
	}

	@Test
	void cuposEnPeriodoEsVacioSiNoHayCuposEnEsasFechas() {
		
		assertTrue(calendario.cuposEnPeriodo(rango(DIA_1, DIA_2)).isEmpty());
		
	}

	@Test
	void cuposEnPeriodoConFechaDevuelveCadaCupoConSuFecha() throws Exception {
		
		Cupo cupo1 = cupo(true, 0);
		Cupo cupo2 = cupo(true, 0);
		cuposConFecha().put(DIA_1, cupo1);
		cuposConFecha().put(DIA_2, cupo2);
		cuposConFecha().put(DIA_4, cupo(true, 0));

		List<Map.Entry<LocalDate, Cupo>> entradas = calendario.cuposEnPeriodoConFecha(rango(DIA_1, DIA_2, DIA_3));

		assertEquals(2, entradas.size());
		assertEquals(DIA_1, entradas.get(0).getKey());
		assertEquals(cupo1, entradas.get(0).getValue());
		assertEquals(DIA_2, entradas.get(1).getKey());
		assertEquals(cupo2, entradas.get(1).getValue());
		
	}

	@Test
	void cuposEnPeriodoConFechaEsVacioSiNoHayCuposEnEsasFechas() {
		
		assertTrue(calendario.cuposEnPeriodoConFecha(rango(DIA_1, DIA_2)).isEmpty());
		
	}

	@Test
	void cantidadDeVentasEnPeriodoSumaLasVentasDeLosCuposDelRango() throws Exception {
		
		cuposConFecha().put(DIA_1, cupo(true, 2));
		cuposConFecha().put(DIA_2, cupo(true, 3));
		cuposConFecha().put(DIA_4, cupo(true, 100));

		assertEquals(5, calendario.cantidadDeVentasEnPeriodo(rango(DIA_1, DIA_2, DIA_3)));
		
	}

	@Test
	void cantidadDeVentasEnPeriodoSinCuposEsCero() {
		
		assertEquals(0, calendario.cantidadDeVentasEnPeriodo(rango(DIA_1, DIA_2)));
		
	}

	@Test
	void tieneDisponibilidadEnUnRangoSiTodosSusCuposTienenDisponibilidad() throws Exception {
		
		cuposConFecha().put(DIA_1, cupo(true, 1));
		cuposConFecha().put(DIA_2, cupo(true, 1));

		assertTrue(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
		
	}

	@Test
	void noTieneDisponibilidadEnUnRangoSiAlgunCupoEstaAgotado() throws Exception {
	
		cuposConFecha().put(DIA_1, cupo(true, 1));
		cuposConFecha().put(DIA_2, cupo(false, 5));

		assertFalse(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
	
	}

	@Test
	void unRangoSinCuposTieneDisponibilidad() {
		
		assertTrue(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
		
	}

	@Test
	void unCupoAgotadoFueraDelRangoNoAfectaLaDisponibilidadDelRango() throws Exception {
		
		cuposConFecha().put(DIA_1, cupo(true, 1));
		cuposConFecha().put(DIA_4, cupo(false, 5));

		assertTrue(calendario.tieneDisponibilidad(rango(DIA_1, DIA_2)));
		
	}

	@Test
	void reservarCreaUnCupoNuevoPorCadaFechaSinCupoPrevio() throws Exception {
		
		calendario.reservar(rango(DIA_1, DIA_2, DIA_3));

		assertEquals(3, calendario.cupos().size());
		assertEquals(List.of(DIA_3, DIA_2, DIA_1), new ArrayList<LocalDate>(cuposConFecha().keySet()));
		
	}

	@Test
	void reservarSobreFechasConCuposPrevioReservaEsosCuposYCreaSoloLosFaltantes() throws Exception {
		
		Cupo existente1 = cupo(true, 1);
		Cupo existente2 = cupo(true, 1);
		cuposConFecha().put(DIA_1, existente1);
		cuposConFecha().put(DIA_2, existente2);

		calendario.reservar(rango(DIA_1, DIA_2, DIA_3));

		verify(existente1).reservar();
		verify(existente2).reservar();
		assertEquals(3, calendario.cupos().size());
		assertTrue(cuposConFecha().containsKey(DIA_3));
		assertEquals(existente1, cuposConFecha().get(DIA_1));
		assertEquals(existente2, cuposConFecha().get(DIA_2));
		
	}

	@Test
	void reservarConTodasLasFechasConCupoPrevioNoCreaCuposNuevos() throws Exception {
		
		Cupo existente = cupo(true, 1);
		cuposConFecha().put(DIA_1, existente);

		calendario.reservar(rango(DIA_1));

		verify(existente).reservar();
		assertEquals(List.of(existente), calendario.cupos());
		
	}

	@Test
	void reservarRegistraElPeriodoReservado() {
		
		RangoDeFechas reservado = rango(DIA_1, DIA_2);

		calendario.reservar(reservado);

		assertEquals(List.of(reservado), new ArrayList<RangoDeFechas>(calendario.cuposPorPeriodo().keySet()));
		
	}

	@Test
	void reservarSinDisponibilidadLanzaSinStockYNoModificaNada() throws Exception {
		
		Cupo disponible = cupo(true, 1);
		Cupo agotado = cupo(false, 5);
		cuposConFecha().put(DIA_1, disponible);
		cuposConFecha().put(DIA_2, agotado);

		assertThrows(SinStockException.class, () -> calendario.reservar(rango(DIA_1, DIA_2, DIA_3)));

		verify(disponible, never()).reservar();
		verify(agotado, never()).reservar();
		assertEquals(2, calendario.cupos().size());
		assertFalse(cuposConFecha().containsKey(DIA_3));
		assertTrue(calendario.cuposPorPeriodo().isEmpty());
		
	}

	@Test
	void cuposPorPeriodoSinReservasEsVacio() {
		
		assertTrue(calendario.cuposPorPeriodo().isEmpty());
	
	}

	@Test
	void cuposPorPeriodoAsociaCadaPeriodoConSusCupos() throws Exception {
		
		Cupo cupo1 = cupo(true, 0);
		Cupo cupo2 = cupo(true, 0);
		Cupo cupo4 = cupo(true, 0);
		cuposConFecha().put(DIA_1, cupo1);
		cuposConFecha().put(DIA_2, cupo2);
		cuposConFecha().put(DIA_4, cupo4);
		RangoDeFechas primero = rango(DIA_1, DIA_2);
		RangoDeFechas segundo = rango(DIA_4);
		List<RangoDeFechas> periodos = getCampo(calendario, "periodos");
		periodos.add(segundo);
		periodos.add(primero);

		Map<RangoDeFechas, List<Cupo>> porPeriodo = calendario.cuposPorPeriodo();

		assertEquals(2, porPeriodo.size());
		assertEquals(List.of(cupo1, cupo2), porPeriodo.get(primero));
		assertEquals(List.of(cupo4), porPeriodo.get(segundo));
		
	}

	@Test
	void liberarEnUnCalendarioSinCuposNoHaceNada() {
		
		calendario.liberar(rango(DIA_1));

		assertTrue(calendario.cupos().isEmpty());
		
	}
	
	@Test
	void liberarLiberaElCupoAsociadoAlRango() throws Exception {
		
		RangoDeFechas rango = rango(DIA_1);
		Cupo cupo = cupo(true, 1);
		Map<LocalDate, Cupo> mapaConClaveRango = new HashMap<LocalDate, Cupo>();
		mapaConClaveRango.put(DIA_1, cupo);
		setCampo(calendario, "cuposConFecha", mapaConClaveRango);

		calendario.liberar(rango);

		verify(cupo).liberar();
		
	}

}