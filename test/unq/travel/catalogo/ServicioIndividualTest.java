package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServicioIndividualTest {

	private ServicioIndividual hotelSinDescuento;
	private ServicioIndividual hotelConDescuento;

	@BeforeEach
	void setUp() {
		hotelSinDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10);

		hotelConDescuento = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0.20f, 10);
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
	void testAlRestarLaUltimaUnidadDejaDeTenerDisponibilidad() {

		ServicioIndividual hotel = this.crearHotelConStock(1);

		hotel.restarUnidadStock();

		assertFalse(hotel.tieneDisponibilidad());

	}

	@Test
	void testAlSumarUnaUnidadVuelveATenerDisponibilidad() {

		ServicioIndividual hotel = this.crearHotelConStock(0);

		hotel.sumarUnidadStock();

		assertTrue(hotel.tieneDisponibilidad());

	}

	@Test
	void testRestarStockSinDisponibilidadLanzaExcepcion() {

		ServicioIndividual hotelSinStock = this.crearHotelConStock(0);

		assertThrows(SinStockException.class, () -> hotelSinStock.restarUnidadStock());

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
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10);

		assertFalse(hotelSinNombre.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinCodigo() {

		ServicioIndividual hotelSinCodigo = new ServicioIndividual("Noche de Hotel Central", "Habitación doble con desayuno",
				null, "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, 10);

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
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, stock);
	}

}
