package unq.travel.reportes;

import java.util.HashMap;
import java.util.Map;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.catalogo.Catalogo;

public class VisitanteExposicionProveedores implements Visitor {
	private Map<String, Integer> cantidadPorProveedor;
	private int cantidadTotalServicios;
	
	public VisitanteExposicionProveedores() {
		this.cantidadPorProveedor = new HashMap<String, Integer>();
		this.cantidadTotalServicios = 0;
	}
	
	@Override
	// Visitando un serv. ind. obtiene su proveedor,
	// incrementa el contador y el total de servicios
	public void visitar(ServicioIndividual servicio) {
		String proveedor = servicio.getProveedor();
		
		int cantidadActual = cantidadPorProveedor.getOrDefault(proveedor, 0);
		
		cantidadPorProveedor.put(proveedor, cantidadActual + 1);
		
		cantidadTotalServicios++;
	}

	@Override
	// Visitando paquete obtiene elementos del pq
	// recorre c/elemento y usa aceptar(this)
	public void visitar(PaqueteTuristico paquete) {
		for(ItemCatalogo item : paquete.getItems()) {
			item.aceptar(this);
		}
	}
	
	public double getPorcentajePorProveedor(String proveedor) {
		if(cantidadTotalServicios == 0) {
			return 0;
		}
		
		int cantidadProveedor = cantidadPorProveedor.getOrDefault(proveedor, 0);
		
		return (double) cantidadProveedor / cantidadTotalServicios * 100;
	}
	
	public ReporteExposicionProveedores procesar(Catalogo catalogo) {
		// reinicia contador para generar un calculo nuevo c/vez
		cantidadPorProveedor.clear();
		cantidadTotalServicios = 0;
		
		for(ItemCatalogo item : catalogo.getItems()) {
			item.aceptar(this);
		}
		
		Map<String, Double> porcentajes = new HashMap<String, Double>();
		
		for(String proveedor : cantidadPorProveedor.keySet()) {
			double porcentaje =
					getPorcentajePorProveedor(proveedor);
			
			porcentajes.put(proveedor, porcentaje);
		}
		
		return new ReporteExposicionProveedores(porcentajes);
	}
}
