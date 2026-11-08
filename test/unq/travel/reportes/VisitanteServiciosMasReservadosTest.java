package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class VisitanteServiciosMasReservadosTest {

	private RangoDeFechas periodo;
	private Cupo cupo;
	private ServicioIndividual servicio1;
	private ServicioIndividual servicio2;
	private VisitanteServiciosMasReservados visitante;
	private Catalogo catalogo;
	private PaqueteTuristico paquete;
	
	@BeforeEach
	void setUp() throws Exception {
		
		periodo = mock(RangoDeFechas.class);
		cupo = mock(Cupo.class);
		servicio1 = mock(ServicioIndividual.class);
		servicio2 = mock(ServicioIndividual.class);
		paquete = mock(PaqueteTuristico.class);
		catalogo = new Catalogo();
		
		visitante = new VisitanteServiciosMasReservados(periodo);
		
	}

	@Test
	void testProcesarCatalogoVacio() {
		
		ReporteServiciosMasReservados reporte = visitante.procesar(catalogo, periodo);
		
		assertTrue(reporte.getItemsOrdenados().isEmpty());
		
	}
	
	@Test
	void testProcesarConCatalogoConElementos() {

		List<Cupo> cupos = new ArrayList<>();
		cupos.add(cupo);
		Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo = new HashMap<>();
		cuposPorPeriodo.put(periodo, cupos);
		List<Cupo> cuposPorCategoria = new ArrayList<>();
		List<ItemCatalogo> items = new ArrayList<>();
		when(servicio1.getCategoria()).thenReturn(Categoria.ALOJAMIENTO);
		when(servicio1.cuposPorPeriodo()).thenReturn(cuposPorPeriodo);
		when(servicio1.cupos()).thenReturn(cuposPorCategoria);
		catalogo.agregarItem(servicio1);
		catalogo.agregarItem(paquete);
		
		ReporteServiciosMasReservados reporte = visitante.procesar(catalogo, periodo);

		verify(servicio1).aceptar(visitante);
		verify(paquete).aceptar(visitante);		
		
	}
	
	@Test
	void testVisitarComoPaqueteTuristico() {
		
		visitante.visitar(paquete);
		
		verify(paquete).cantidadDeVentasEnPeriodo(periodo);
		
	}
	
	@Test
	void testVisitarComoServicioIndividual() {

		List<Cupo> cupos = new ArrayList<>();
		cupos.add(cupo);
		Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo = new HashMap<>();
		cuposPorPeriodo.put(periodo, cupos);
		when(servicio1.getCategoria()).thenReturn(Categoria.ALOJAMIENTO);
		when(servicio1.cuposPorPeriodo()).thenReturn(cuposPorPeriodo);
		when(servicio1.cupos()).thenReturn(cupos);
		
		visitante.visitar(servicio1);
		
		verify(servicio1).cantidadDeVentasEnPeriodo(periodo);
		
	}

}
