package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class ServicioIndividualTest {

	private ServicioIndividual hotelSinDescuento;
	private ServicioIndividual hotelConDescuento;
	private RangoDeFechas periodo;
	private CalendarioDeCupos calendario;
	private AtributoDinamico obligatorio;
	private AtributoDinamico opcional;
	private int valor;
	private TipoAtributo tipo;
	
	@BeforeEach
	void setUp() {

		valor = 10;
		tipo = new TipoNumerico();
		periodo = mock(RangoDeFechas.class);
		calendario = mock(CalendarioDeCupos.class);
		
		obligatorio = new AtributoDinamico("CapacidadMaxima", true, tipo, valor);
		opcional = new AtributoDinamico("RatingEstrellas", false, tipo, valor);
		
		hotelSinDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, calendario);

		hotelConDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0.20f, calendario);
	
	}

	@Test
	void testElPrecioFinalSinDescuentoEsIgualAlPrecioBase() {

		assertEquals(15000f, hotelSinDescuento.getPrecioBase(), 0.01f);
		assertEquals(15000f, hotelSinDescuento.getPrecioFinal(), 0.01f);

	}

	@Test
	void testElPrecioFinalConDescuentoAplicaElPorcentaje() {

		assertEquals(12000f, hotelConDescuento.getPrecioFinal(), 0.01f);

	}

	@Test
	void testElDescuentoNoModificaElPrecioBase() {

		assertEquals(15000f, hotelConDescuento.getPrecioBase(), 0.01f);

	}

	@Test
	void testEsDeLaCategoriaQueTieneAsignada() {

		assertTrue(hotelSinDescuento.esDeCategoria(Categoria.ALOJAMIENTO));

	}

	@Test
	void testNoEsDeUnaCategoriaDistinta() {

		assertFalse(hotelSinDescuento.esDeCategoria(Categoria.EXCURSION));

	}

	@Test
	void testTieneDisponibilidadCuandoSuCalendarioTieneCupoEnElPeriodo() {

		when(calendario.tieneDisponibilidad(periodo)).thenReturn(true);

		assertTrue(hotelSinDescuento.tieneDisponibilidad(periodo));

	}

	@Test
	void testNoTieneDisponibilidadCuandoSuCalendarioNoTieneCupo() {

		when(calendario.tieneDisponibilidad()).thenReturn(false);

		assertFalse(hotelSinDescuento.tieneDisponibilidad());

	}

	@Test
	void testAlReservarDelegaEnSuCalendario() {

		hotelSinDescuento.reservar(periodo);

		verify(calendario).reservar(periodo);

	}

	@Test
	void testAlLiberarDelegaEnSuCalendario() {

		hotelSinDescuento.liberar(periodo);

		verify(calendario).liberar(periodo);

	}

	@Test
	void testNoSePuedeCrearConDescuentoMayorAUno() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 20f, calendario));

	}

	@Test
	void testNoSePuedeCrearConDescuentoNegativo() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, -0.10f, calendario));

	}

	@Test
	void testNoSePuedeDefinirDosVecesElMismoAtributo() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);

		assertThrows(IllegalArgumentException.class,
				() -> hotelSinDescuento.definirAtributo("CapacidadMaxima", false, new TipoTexto(), "a"));

	}

	@Test
	void testNoSePuedeCrearConPrecioBaseNegativo() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, -500f, 0f, calendario));

	}

	@Test
	void testNoSePuedeDefinirUnAtributoSinNombre() {

		assertThrows(IllegalArgumentException.class,
				() -> hotelSinDescuento.definirAtributo(null, false, new TipoTexto(), "a"));

	}

	@Test
	void testDevuelveSuNombreYSuDescripcion() {

		assertEquals("Noche de Hotel Central", hotelSinDescuento.getNombre());
		assertEquals("Habitación doble con desayuno", hotelSinDescuento.getDescripcion());

	}

	@Test
	void testDevuelveSuProveedor() {

		assertEquals("Hoteles Centro SA", hotelSinDescuento.getProveedor());

	}

	@Test
	void testDevuelveElValorDeUnAtributoQueNoEsElPrimeroDefinido() {

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano(), true);
		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertEquals(4, hotelSinDescuento.getValor("CapacidadMaxima"));

	}

	@Test
	void testDevuelveElValorAsignadoAUnAtributoDinamico() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertEquals(4, hotelSinDescuento.getValor("CapacidadMaxima"));

	}

	@Test
	void testAsignarValorAUnAtributoNoDefinidoLanzaExcepcion() {

		assertThrows(AtributoInexistenteException.class, () -> hotelSinDescuento.asignarValor("PoliticaMascotas", true));

	}

	@Test
	void testConsultarUnAtributoNoDefinidoLanzaExcepcion() {

		assertThrows(AtributoInexistenteException.class, () -> hotelSinDescuento.getValor("PoliticaMascotas"));

	}

	/*
	@Test
	void testCumpleConLaValidacionSinAtributosDinamicos() {

		assertTrue(hotelSinDescuento.esValido());

	}
	*/

	/*
	@Test
	void testCumpleConLaValidacionConObligatorioDelTipoAdecuadoYOpcionalSinValor() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);
		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano(), true);

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertTrue(hotelSinDescuento.esValido());

	}
	*/

	/*
	@Test
	void testCumpleConLaValidacionConOpcionalDelTipoAdecuado() {

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano(), true);

		hotelSinDescuento.asignarValor("PoliticaMascotas", true);

		assertTrue(hotelSinDescuento.esValido());

	}
	*/

	@Test
	void testNoCumpleConLaValidacionSinNombre() {

		assertThrows(AtributoSinValorException.class, () -> new ServicioIndividual(null, "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, calendario));

	}

	@Test
	void testNoCumpleConLaValidacionSinCodigo() {

		assertThrows(AtributoSinValorException.class, () -> new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				null, "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, calendario));

	}

	/*
	@Test
	void testNoCumpleConLaValidacionConObligatorioSinValor() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);

		assertTrue(hotelSinDescuento.esValido());

	}
	*/

	/*
	@Test
	void testNoCumpleConLaValidacionConObligatorioDeTipoEquivocado() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico(), 1);

		assertThrows(ValorDeAtributoInvalidoException.class, () -> hotelSinDescuento.asignarValor("CapacidadMaxima", "cuatro"));

		assertTrue(hotelSinDescuento.esValido());

	}
	*/

	/*
	@Test
	void testNoCumpleConLaValidacionConOpcionalDeTipoEquivocado() {

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano(), false);

		assertThrows(ValorDeAtributoInvalidoException.class, () -> hotelSinDescuento.asignarValor("PoliticaMascotas", "si"));

		assertTrue(hotelSinDescuento.esValido());

	}
	*/
	
	@Test
	void testCategoria() {
		
		assertEquals(Categoria.ALOJAMIENTO, hotelConDescuento.getCategoria());
		
	}
	
	@Test
	void testCategorias() {
		
		assertEquals(hotelConDescuento.categorias(), Set.of(Categoria.ALOJAMIENTO));
		
	}
	
	@Test
	void testProveedores() {

		assertEquals(1, hotelConDescuento.proveedores().proveedores().size());
		assertEquals(1, hotelConDescuento.proveedores().cantidadDeProveedores());
		assertEquals(List.of("Hoteles Centro SA"), hotelConDescuento.proveedores().proveedores());
		assertEquals(1, hotelConDescuento.proveedores().ocurrenciasDeProveedor("Hoteles Centro SA"));
		
	}
	
	/*
	@Test
	void testValidarServicioSinNombreEsInvalido() throws Exception{
		
		setCampo(hotelSinDescuento, "nombre", null);
		
		assertFalse(hotelSinDescuento.esValido());
		
	}
	
	@Test
	void testValidarServicioSinCodigoEsInvalido() throws Exception{
		
		setCampo(hotelSinDescuento, "codigoReserva", null);
		
		assertFalse(hotelSinDescuento.esValido());
		
	}
	
	@Test
	void testValidarServicioConTodosLosAtributosValidosEsValido() {
		
		hotelSinDescuento.definirAtributo("Cantidad estrellas", true, tipo, 5);
		
		assertTrue(hotelSinDescuento.esValido());
		
	}
	
	@Test
	void testValidarServicioConUnAtributoDinamicoInvalidoEsInvalido() throws Exception{
		
		List<AtributoDinamico> atributosDinamicos = new ArrayList<>();
		setCampo(obligatorio, "valor", null);
		atributosDinamicos.add(obligatorio);
		setCampo(hotelSinDescuento, "atributosDinamicos", atributosDinamicos);
		
		assertFalse(hotelSinDescuento.esValido());
		
	}
	*/
	
	/*
	@Test
	void testCupos() {
		
		List<Cupo> cupos = new ArrayList<>();
		when(calendario.cupos()).thenReturn(cupos);
		
		assertEquals(cupos, hotelConDescuento.cupos());
		
	}
	*/
	
	/*
	@Test
	void testCuposPorPeriodo() {
		
		Map<RangoDeFechas, List<Cupo>> cupos = new HashMap<>();
		when(calendario.cuposPorPeriodo()).thenReturn(cupos);
		
		assertEquals(cupos, hotelConDescuento.cuposPorPeriodo());
		
	}
	*/
	
	/*
	@Test
	void testCantidadDeVentasEnPeriodo() {
		
		when(calendario.cantidadDeVentasEnPeriodo(periodo)).thenReturn(100);
		
		assertEquals(100, hotelConDescuento.cantidadDeVentasEnPeriodo(periodo));
		
	}
	*/

	private static void setCampo(Object objeto, String nombre, Object valor) throws Exception {
		
		Field campo;
		try { campo = objeto.getClass().getDeclaredField(nombre); }
		catch (Exception e) { campo = objeto.getClass().getSuperclass().getDeclaredField(nombre); }
		campo.setAccessible(true);
		campo.set(objeto, valor);
		
	}
	
}
