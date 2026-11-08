package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.CalendarioDeCupos;

class ConsultasDelEnunciadoTest {

	private ServicioIndividual hotel;
	private ServicioIndividual resortSinCupo;
	private ServicioIndividual museo;
	private ServicioIndividual tourEnOferta;
	private ServicioIndividual traslado;
	private PaqueteTuristico packEscapadaUrbana;
	private PaqueteTuristico packRutaDelVino;
	private Buscador buscador;

	@BeforeEach
	void setUp() {
		hotel = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 10);
		resortSinCupo = this.crearServicio("Resort Playa Dorada", Categoria.ALOJAMIENTO, 30000f, 0);
		museo = this.crearServicio("Excursion Guiada Museo", Categoria.EXCURSION, 4000f, 10);
		tourEnOferta = this.crearServicio("Tour Nocturno en Oferta", Categoria.EXCURSION, 18000f, 10);
		traslado = this.crearServicio("Traslado Aeropuerto-Hotel", Categoria.TRASLADO, 2000f, 10);

		packEscapadaUrbana = new PaqueteTuristico("Pack Escapada Urbana", "Hotel, museo y traslado", 0.10f);
		packEscapadaUrbana.agregarItem(hotel);
		packEscapadaUrbana.agregarItem(museo);
		packEscapadaUrbana.agregarItem(traslado);

		packRutaDelVino = new PaqueteTuristico("Pack Ruta del Vino", "Alquiler de auto por bodegas", 0.10f);
		packRutaDelVino.agregarItem(this.crearServicio("Alquiler de Auto", Categoria.ALQUILER_AUTO, 20000f, 10));

		Catalogo catalogo = new Catalogo();
		catalogo.agregarItem(hotel);
		catalogo.agregarItem(resortSinCupo);
		catalogo.agregarItem(museo);
		catalogo.agregarItem(tourEnOferta);
		catalogo.agregarItem(traslado);
		catalogo.agregarItem(packEscapadaUrbana);
		catalogo.agregarItem(packRutaDelVino);

		buscador = new Buscador(catalogo);
	}

	@Test
	void testAlojamientoDisponibleOConOfertaEnElNombre() {

		Criterio criterio = new CriterioOr(
				new CriterioAnd(new CriterioCategoria(Categoria.ALOJAMIENTO), new CriterioDisponibilidad()),
				new CriterioNombre("oferta"));

		assertEquals(List.of(hotel, tourEnOferta, packEscapadaUrbana), buscador.buscar(criterio));

	}

	@Test
	void testHastaQuinceMilQueSeaAlojamientoOExcursion() {

		Criterio criterio = new CriterioAnd(
				new CriterioPrecioMax(15000f),
				new CriterioOr(new CriterioCategoria(Categoria.ALOJAMIENTO), new CriterioCategoria(Categoria.EXCURSION)));

		assertEquals(List.of(hotel, museo), buscador.buscar(criterio));

	}

	@Test
	void testPackSinExcursionesDeHastaVeinticincoMil() {

		Criterio criterio = new CriterioAnd(
				new CriterioAnd(new CriterioNombre("pack"), new CriterioNot(new CriterioCategoria(Categoria.EXCURSION))),
				new CriterioPrecioMax(25000f));

		assertEquals(List.of(packRutaDelVino), buscador.buscar(criterio));

	}

	private ServicioIndividual crearServicio(String nombre, Categoria categoria, float precio, int stock) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, 0f, stock, new CalendarioDeCupos());
	}

}
