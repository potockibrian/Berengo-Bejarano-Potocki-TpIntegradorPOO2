package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

class ServicioIndividualTest {

	private ServicioIndividual hotelSinDescuento;
	private ServicioIndividual hotelConDescuento;

	@BeforeEach
	void setUp() {
		hotelSinDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10, new CalendarioDeCupos(0));

		hotelConDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0.20f, 10, new CalendarioDeCupos(0));
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
	void testTieneDisponibilidadCuandoHayStock() {

		assertTrue(hotelSinDescuento.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadCuandoNoHayStock() {

		ServicioIndividual hotelSinStock = this.crearHotelConStock(0);

		assertFalse(hotelSinStock.tieneDisponibilidad());

	}

	@Test
	void testAlDecrementarElUltimoCupoDejaDeTenerDisponibilidad() {

		ServicioIndividual hotel = this.crearHotelConStock(1);

		hotel.reservar(mock(RangoDeFechas.class));

		assertFalse(hotel.tieneDisponibilidad());

	}

	@Test
	void testAlIncrementarElCupoVuelveATenerDisponibilidad() {

		ServicioIndividual hotel = this.crearHotelConStock(0);

		hotel.liberar(mock(RangoDeFechas.class));

		assertTrue(hotel.tieneDisponibilidad());

	}

	@Test
	void testDecrementarCupoSinDisponibilidadLanzaExcepcion() {

		ServicioIndividual hotelSinStock = this.crearHotelConStock(0);

		assertThrows(SinStockException.class, () -> hotelSinStock.reservar(mock(RangoDeFechas.class)));

	}

	@Test
	void testNoSePuedeCrearConDescuentoMayorAUno() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 20f, 10, new CalendarioDeCupos(0)));

	}

	@Test
	void testNoSePuedeCrearConDescuentoNegativo() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, -0.10f, 10, new CalendarioDeCupos(0)));

	}

	@Test
	void testNoSePuedeCrearConStockNegativo() {

		assertThrows(IllegalArgumentException.class, () -> this.crearHotelConStock(-1));

	}

	@Test
	void testNoSePuedeDefinirDosVecesElMismoAtributo() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		assertThrows(IllegalArgumentException.class,
				() -> hotelSinDescuento.definirAtributo("CapacidadMaxima", false, new TipoTexto()));

	}

	@Test
	void testNoSePuedeCrearConPrecioBaseNegativo() {

		assertThrows(IllegalArgumentException.class, () -> new ServicioIndividual("Noche de Hotel Central",
				"Habitación doble con desayuno", "HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, -500f, 0f, 10, new CalendarioDeCupos(0)));

	}

	@Test
	void testNoSePuedeDefinirUnAtributoSinNombre() {

		assertThrows(IllegalArgumentException.class,
				() -> hotelSinDescuento.definirAtributo(null, false, new TipoTexto()));

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

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano());
		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertEquals(4, hotelSinDescuento.getValor("CapacidadMaxima"));

	}

	@Test
	void testDevuelveElValorAsignadoAUnAtributoDinamico() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertEquals(4, hotelSinDescuento.getValor("CapacidadMaxima"));

	}

	@Test
	void testConsultarUnAtributoDefinidoSinValorLanzaExcepcion() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		assertThrows(AtributoSinValorException.class, () -> hotelSinDescuento.getValor("CapacidadMaxima"));

	}

	@Test
	void testAsignarValorAUnAtributoNoDefinidoLanzaExcepcion() {

		assertThrows(AtributoInexistenteException.class, () -> hotelSinDescuento.asignarValor("PoliticaMascotas", true));

	}

	@Test
	void testConsultarUnAtributoNoDefinidoLanzaExcepcion() {

		assertThrows(AtributoInexistenteException.class, () -> hotelSinDescuento.getValor("PoliticaMascotas"));

	}

	@Test
	void testCumpleConLaValidacionSinAtributosDinamicos() {

		assertTrue(hotelSinDescuento.esValido());

	}

	@Test
	void testCumpleConLaValidacionConObligatorioDelTipoAdecuadoYOpcionalSinValor() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());
		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano());

		hotelSinDescuento.asignarValor("CapacidadMaxima", 4);

		assertTrue(hotelSinDescuento.esValido());

	}

	@Test
	void testCumpleConLaValidacionConOpcionalDelTipoAdecuado() {

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano());

		hotelSinDescuento.asignarValor("PoliticaMascotas", true);

		assertTrue(hotelSinDescuento.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinNombre() {

		ServicioIndividual hotelSinNombre = new ServicioIndividual(null, "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10, new CalendarioDeCupos(0));

		assertFalse(hotelSinNombre.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinCodigo() {

		ServicioIndividual hotelSinCodigo = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				null, "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10, new CalendarioDeCupos(0));

		assertFalse(hotelSinCodigo.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionConObligatorioSinValor() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		assertFalse(hotelSinDescuento.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionConObligatorioDeTipoEquivocado() {

		hotelSinDescuento.definirAtributo("CapacidadMaxima", true, new TipoNumerico());

		hotelSinDescuento.asignarValor("CapacidadMaxima", "cuatro");

		assertFalse(hotelSinDescuento.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionConOpcionalDeTipoEquivocado() {

		hotelSinDescuento.definirAtributo("PoliticaMascotas", false, new TipoBooleano());

		hotelSinDescuento.asignarValor("PoliticaMascotas", "si");

		assertFalse(hotelSinDescuento.esValido());

	}

	private ServicioIndividual crearHotelConStock(int stock) {
		return new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, stock, new CalendarioDeCupos(stock));
	}

}
