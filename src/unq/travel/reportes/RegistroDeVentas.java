package unq.travel.reportes;

import java.util.ArrayList;
import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

public class RegistroDeVentas {
	private List<RegistroVenta> ventas = new ArrayList<>();
	
	public void registrarVenta(RegistroVenta venta) {
		ventas.add(venta);
	}
	
	// Registros completos de ventas
	public List<RegistroVenta> ventasDe(
			ItemCatalogo item, 
			RangoDeFechas periodo) {
		return ventas.stream()
				.filter(venta -> venta.getItem() == item)
				.filter(venta -> !venta.getFecha().isBefore(periodo.getFechaInicio())
								&& !venta.getFecha().isAfter(periodo.getFechaFinal()))
				.toList();
	}
	
	// Cuantos registros hay
	public int cantidadDeVentasDe(
			ItemCatalogo item, RangoDeFechas periodo) {
		return ventasDe(item,periodo).size();
	}
	
	public float importeTotalDe(
			ItemCatalogo item, RangoDeFechas periodo) {
		return (float) ventasDe(item, periodo).stream()
				.mapToDouble(RegistroVenta::getImporteCobrado)
				.sum();
	}
	
	public float precioPromedioDe(
			ItemCatalogo item, RangoDeFechas periodo) {
		int cantidad = cantidadDeVentasDe(item, periodo);
		
		if(cantidad == 0) {
			return 0;
		}
		
		return importeTotalDe(item, periodo) / cantidad;
	}
}
