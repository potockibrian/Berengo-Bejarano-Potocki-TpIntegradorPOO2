package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaqueteTuristicoTest {

	private ServicioIndividual hotel;
	private ServicioIndividual museo;
	private ServicioIndividual traslado;
	private PaqueteTuristico packEscapadaUrbana;

	@BeforeEach
	void setUp() {
		hotel = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0f);
		museo = this.crearServicio("Excursion Guiada Museo", Categoria.EXCURSION, 4000f, 0f);
		traslado = this.crearServicio("Traslado Aeropuerto-Hotel", Categoria.TRASLADO, 2000f, 0f);

		packEscapadaUrbana = new PaqueteTuristico("Pack Escapada Urbana", "Hotel, museo y traslado", 0.10f);
		packEscapadaUrbana.agregarItem(hotel);
		packEscapadaUrbana.agregarItem(museo);
		packEscapadaUrbana.agregarItem(traslado);
	}

	@Test
	void testElPrecioBaseEsLaSumaDeLosPreciosDeSusItems() {

		assertEquals(21000f, packEscapadaUrbana.getPrecioBase(), 0.01f);

	}

	@Test
	void testElPrecioFinalAplicaElDescuentoDelPaquete() {

		assertEquals(18900f, packEscapadaUrbana.getPrecioFinal(), 0.01f);

	}

	@Test
	void testUnPaqueteAnidadoAportaSuPrecioFinal() {

		ServicioIndividual tourGastronomico = this.crearServicio("Tour Gastronomico", Categoria.EXCURSION, 5000f, 0f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y tour", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(tourGastronomico);

		assertEquals(23900f, kitGranTour.getPrecioBase(), 0.01f);
		assertEquals(19120f, kitGranTour.getPrecioFinal(), 0.01f);

	}

	@Test
	void testElDescuentoPromocionalDeUnServicioSeAcumulaConElDelPaquete() {

		ServicioIndividual hotelConPromo = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0.20f);
		PaqueteTuristico paquete = new PaqueteTuristico("Escapada con promo", "Hotel con promo y museo", 0.10f);

		paquete.agregarItem(hotelConPromo);
		paquete.agregarItem(museo);

		assertEquals(14400f, paquete.getPrecioFinal(), 0.01f);

	}

	@Test
	void testEsDeUnaCategoriaSiAlgunoDeSusItemsLoEs() {

		assertTrue(packEscapadaUrbana.esDeCategoria(Categoria.EXCURSION));

	}

	@Test
	void testNoEsDeUnaCategoriaSiNingunoDeSusItemsLoEs() {

		assertFalse(packEscapadaUrbana.esDeCategoria(Categoria.ALQUILER_AUTO));

	}

	@Test
	void testEsDeUnaCategoriaSiUnPaqueteAnidadoLaTiene() {

		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);

		assertTrue(kitGranTour.esDeCategoria(Categoria.ALOJAMIENTO));

	}

	@Test
	void testTieneDisponibilidadSiTodosSusItemsTienen() {

		assertTrue(packEscapadaUrbana.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadSiAlgunServicioNoTieneStock() {

		packEscapadaUrbana.agregarItem(this.crearServicioSinStock("Alquiler de Auto", Categoria.ALQUILER_AUTO));

		assertFalse(packEscapadaUrbana.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadSiUnPaqueteAnidadoNoTiene() {

		PaqueteTuristico packSinStock = new PaqueteTuristico("Pack sin stock", "Servicio agotado", 0.10f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y pack agotado", 0.20f);

		packSinStock.agregarItem(this.crearServicioSinStock("Tour Gastronomico", Categoria.EXCURSION));
		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(packSinStock);

		assertFalse(kitGranTour.tieneDisponibilidad());

	}

	@Test
	void testCumpleConLaValidacionSiTieneNombreYTodosSusItemsSonValidos() {

		assertTrue(packEscapadaUrbana.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiTieneUnServicioInvalido() {

		ServicioIndividual servicioSinCodigo = new ServicioIndividual("Alquiler de Auto", "Auto compacto",
				null, "Operador SA", Categoria.ALQUILER_AUTO, 8000f, 0f, 10);

		packEscapadaUrbana.agregarItem(servicioSinCodigo);

		assertFalse(packEscapadaUrbana.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinNombre() {

		PaqueteTuristico paqueteSinNombre = new PaqueteTuristico(null, "Hotel y museo", 0.10f);

		paqueteSinNombre.agregarItem(hotel);
		paqueteSinNombre.agregarItem(museo);

		assertFalse(paqueteSinNombre.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiEstaVacio() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f);

		assertFalse(paqueteVacio.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiTieneUnPaqueteAnidadoInvalido() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y paquete vacio", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(paqueteVacio);

		assertFalse(kitGranTour.esValido());

	}

	private ServicioIndividual crearServicio(String nombre, Categoria categoria, float precio, float descuento) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, descuento, 10);
	}

	private ServicioIndividual crearServicioSinStock(String nombre, Categoria categoria) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, 1000f, 0f, 0);
	}

}
