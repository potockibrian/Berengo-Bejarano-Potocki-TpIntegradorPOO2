package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

class ConsultasDelEnunciadoTest {

	private ServicioIndividual hotel;
	private ServicioIndividual resortSinCupo;
	private ServicioIndividual museo;
	private ServicioIndividual tourEnOferta;
	private ServicioIndividual traslado;
	private PaqueteTuristico packEscapadaUrbana;
	private PaqueteTuristico packRutaDelVino;
	private Buscador buscador;
	private CalendarioDeCupos calendario1;
	private CalendarioDeCupos calendario2;
	private RangoDeFechas periodo;

	@BeforeEach
	void setUp() {
		periodo = mock(RangoDeFechas.class);
		calendario1 = mock(CalendarioDeCupos.class);
		calendario2 = mock(CalendarioDeCupos.class);
		hotel = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, calendario1);
		resortSinCupo = this.crearServicio("Resort Playa Dorada", Categoria.ALOJAMIENTO, 30000f, calendario2);
		museo = this.crearServicio("Excursion Guiada Museo", Categoria.EXCURSION, 4000f, calendario1);
		tourEnOferta = this.crearServicio("Tour Nocturno en Oferta", Categoria.EXCURSION, 18000f, calendario1);
		traslado = this.crearServicio("Traslado Aeropuerto-Hotel", Categoria.TRASLADO, 2000f, calendario1);

		packEscapadaUrbana = new PaqueteTuristico("Pack Escapada Urbana", "Hotel, museo y traslado", 0.10f);
		packEscapadaUrbana.agregarItem(hotel);
		packEscapadaUrbana.agregarItem(museo);
		packEscapadaUrbana.agregarItem(traslado);

		packRutaDelVino = new PaqueteTuristico("Pack Ruta del Vino", "Alquiler de auto por bodegas", 0.10f);
		packRutaDelVino.agregarItem(this.crearServicio("Alquiler de Auto", Categoria.ALQUILER_AUTO, 20000f, calendario1));

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

		when(hotel.tieneDisponibilidad(periodo)).thenReturn(true);
		when(museo.tieneDisponibilidad(periodo)).thenReturn(true);
		Criterio criterio = new CriterioOr(
				new CriterioAnd(new CriterioCategoria(Categoria.ALOJAMIENTO), new CriterioDisponibilidad(periodo)),
				new CriterioNombre("oferta"));

		assertEquals(List.of(hotel, tourEnOferta, packEscapadaUrbana), buscador.buscar(criterio));

	}

	@Test
	void testHastaQuinceMilQueSeaAlojamientoOExcursion() {

		when(hotel.tieneDisponibilidad(periodo)).thenReturn(true);
		when(museo.tieneDisponibilidad(periodo)).thenReturn(true);
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

	private ServicioIndividual crearServicio(String nombre, Categoria categoria, float precio, CalendarioDeCupos calendario) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, 0f, calendario);
	}

}
