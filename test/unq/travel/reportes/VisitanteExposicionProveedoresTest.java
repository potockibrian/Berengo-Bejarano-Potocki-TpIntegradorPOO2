package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.catalogo.Catalogo;

public class VisitanteExposicionProveedoresTest {
	@Test
	public void contabilizaElProveedorDeUnServicioIndividual() {
		ServicioIndividual servicio = new ServicioIndividual(
				"Hotel",
				"Hotel en la playa",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		VisitanteExposicionProveedores visitor = 
				new VisitanteExposicionProveedores();
		
		servicio.aceptar(visitor);
		
		assertEquals(100.0, visitor.getPorcentajePorProveedor("Proveedor A"), 0.01);
	}
	
	@Test
	public void recorreLosServiciosDePaquetesAnidados() {
		ServicioIndividual servicioA1 = new ServicioIndividual(
				"Hotel",
				"Hotel",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioA2 = new ServicioIndividual(
				"Vuelo",
				"Vuelo",
				"RES002",
				"Proveedor A",
				Categoria.VUELO,
				2000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioB = new ServicioIndividual(
				"Excursion",
				"Excursion",
				"RES003",
				"Proveedor B",
				Categoria.EXCURSION,
				500,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		PaqueteTuristico paqueteInterno = new PaqueteTuristico(
				"Paquete interno", "Descripcion", 0);
		
		paqueteInterno.agregarItem(servicioA2);
		paqueteInterno.agregarItem(servicioB);
		
		PaqueteTuristico paquetePrincipal = new PaqueteTuristico(
				"Paquete principal", "Descripcion", 0);
		
		paquetePrincipal.agregarItem(servicioA1);
		paquetePrincipal.agregarItem(paqueteInterno);
		
		VisitanteExposicionProveedores visitor = 
				new VisitanteExposicionProveedores();
		
		paquetePrincipal.aceptar(visitor);
		
		// Exposicion = (Servicios proveedor/Total de servicios) x 100
		// 3 servicios: 2 de PrA (66.67%) y 1 de PrB (33.33%)
		// Se contabilizan serv. ind. de paquetes
		
		assertEquals(66.67, 
				visitor.getPorcentajePorProveedor("Proveedor A"), 0.01);
		
		assertEquals(33.33,
				visitor.getPorcentajePorProveedor("Proveedor B"), 0.01);
	}
	
	@Test
	public void procesaTodosLosItemsDelCatalogo() {
		Catalogo catalogo = new Catalogo();
		
		ServicioIndividual servicioA1 = new ServicioIndividual(
				"Hotel",
				"Hotel",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioB = new ServicioIndividual(
				"Vuelo",
				"Vuelo",
				"RES002",
				"Proveedor B",
				Categoria.VUELO,
				2000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		PaqueteTuristico paquete = new PaqueteTuristico(
				"Paquete", "Prueba", 0);
		
		ServicioIndividual servicioA2 = new ServicioIndividual(
				"Excursion",
				"Excursion",
				"RES003",
				"Proveedor A",
				Categoria.EXCURSION,
				500,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		paquete.agregarItem(servicioA2);
		
		catalogo.agregarItem(servicioA1);
		catalogo.agregarItem(servicioB);
		catalogo.agregarItem(paquete);
		
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		visitor.procesar(catalogo);
		
		assertEquals(66.67, 
				visitor.getPorcentajePorProveedor("Proveedor A"), 0.01);
		
		assertEquals(33.33,
				visitor.getPorcentajePorProveedor("Proveedor B"), 0.01);	
	}
	
	@Test
	public void devuelveCeroCuandoNoHayServiciosEnElCatalogo() {
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		visitor.procesar(new Catalogo());
		
		assertEquals(0,
				visitor.getPorcentajePorProveedor("Proveedor A"));
	}
	
	@Test
	public void procesarDosVecesElMismoCatalogoNoDuplicaLosResultados() {
		Catalogo catalogo = new Catalogo();
		
		ServicioIndividual servicioA = new ServicioIndividual(
				"Hotel",
				"Hotel",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioB = new ServicioIndividual(
				"Vuelo",
				"Vuelo",
				"RES002",
				"Proveedor B",
				Categoria.VUELO,
				2000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		catalogo.agregarItem(servicioA);
		catalogo.agregarItem(servicioB);
		
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		visitor.procesar(catalogo);
		visitor.procesar(catalogo);
		
		assertEquals(50.0,
				visitor.getPorcentajePorProveedor("Proveedor A"), 0.01);
	}
	
	@Test
	public void procesarDevuelveUnReporteConLaExposicionPorProveedor() {
		Catalogo catalogo = new Catalogo();
		
		ServicioIndividual servicioA1 = new ServicioIndividual(
				"Hotel",
				"Hotel",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioA2 = new ServicioIndividual(
				"Vuelo",
				"Vuelo",
				"RES002",
				"Proveedor B",
				Categoria.VUELO,
				2000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioB = new ServicioIndividual(
				"Excursion",
				"Excursion",
				"RES003",
				"Proveedor A",
				Categoria.EXCURSION,
				500,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		catalogo.agregarItem(servicioA1);
		catalogo.agregarItem(servicioA2);
		catalogo.agregarItem(servicioB);
		
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		ReporteExposicionProveedores reporte = 
				visitor.procesar(catalogo);
		
		assertEquals(66.67, 
				visitor.getPorcentajePorProveedor("Proveedor A"), 0.01);
		
		assertEquals(33.33,
				visitor.getPorcentajePorProveedor("Proveedor B"), 0.01);
	}
	
	@Test
	public void generaUnReporteVacioCuandoElCatalogoNoTieneServicios() {
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		ReporteExposicionProveedores reporte =
				visitor.procesar(new Catalogo());
		
		assertEquals(0,
				reporte.getPorcentajePorProveedor("Proveedor A"));
	}
	
	@Test
	public void unReporteGeneradoNoSeModificaAlProcesarOtroCatalogo() {
		Catalogo primerCatalogo = new Catalogo();
		
		ServicioIndividual servicioA = new ServicioIndividual(
				"Hotel",
				"Hotel",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		ServicioIndividual servicioB = new ServicioIndividual(
				"Vuelo",
				"Vuelo",
				"RES002",
				"Proveedor B",
				Categoria.VUELO,
				2000,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		primerCatalogo.agregarItem(servicioA);
		primerCatalogo.agregarItem(servicioB);
		
		VisitanteExposicionProveedores visitor =
				new VisitanteExposicionProveedores();
		
		ReporteExposicionProveedores primerReporte =
				visitor.procesar(primerCatalogo);
		
		Catalogo segundoCatalogo = new Catalogo();
		
		ServicioIndividual otroServicio = new ServicioIndividual(
				"Excursion",
				"Excursion",
				"RES003",
				"Proveedor A",
				Categoria.EXCURSION,
				500,
				0,
				10,
				new CalendarioDeCupos()
		);
		
		segundoCatalogo.agregarItem(otroServicio);
		
		visitor.procesar(segundoCatalogo);
		
		assertEquals(50.0,
				primerReporte.getPorcentajePorProveedor("Proveedor A"), 0.01);
		
		assertEquals(50.0,
				primerReporte.getPorcentajePorProveedor("Proveedor B"), 0.01);
	}
}
