package unq.travel.reportes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.*;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class VisitanteOcupacionYCupoDisponibleTest {

	private ServicioIndividual servicio1;
	private ServicioIndividual servicio2;
	private PaqueteTuristico paquete;
	private Catalogo catalogo;
	private VisitanteOcupacionYCupoDisponible visitante;
	private RangoDeFechas periodo;
	private Cupo cupo;
	
	@BeforeEach
	void setUp() throws Exception {
		
		paquete = mock(PaqueteTuristico.class);
		servicio1 = mock(ServicioIndividual.class);
		servicio2 = mock(ServicioIndividual.class);
		periodo = mock(RangoDeFechas.class);
		cupo = mock(Cupo.class);
		catalogo = new Catalogo();
		
		visitante = new VisitanteOcupacionYCupoDisponible();
		
	}

	@Test
	void testProcesarConCatalogoSinElementos() {

		List<ItemCatalogo> items = new ArrayList<>();
		
		ReporteOcupacionYCupoDisponible reporte = visitante.procesar(catalogo, periodo);
		
		assertTrue(reporte.getCuposPorCategoria().isEmpty());
		assertTrue(reporte.getCuposPorPeriodo().isEmpty());
		
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
		
		ReporteOcupacionYCupoDisponible reporte = visitante.procesar(catalogo, periodo);

		verify(servicio1).exportar(visitante);
		verify(paquete).exportar(visitante);		
		
	}
	
	@Test
	void testVisitarComoPaqueteTuristico() {
		
		visitante.visitar(paquete);
		
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
		
		verify(servicio1, times(2)).getCategoria();
		verify(servicio1).cuposPorPeriodo();
		
		when(servicio2.getCategoria()).thenReturn(Categoria.ALOJAMIENTO);
		when(servicio2.cuposPorPeriodo()).thenReturn(cuposPorPeriodo);
		when(servicio2.cupos()).thenReturn(cupos);
		
		visitante.visitar(servicio2);
		
		when(servicio2.getCategoria()).thenReturn(Categoria.ALOJAMIENTO);
		when(servicio2.cuposPorPeriodo()).thenReturn(cuposPorPeriodo);
		when(servicio2.cupos()).thenReturn(cupos);
		
	}

}
