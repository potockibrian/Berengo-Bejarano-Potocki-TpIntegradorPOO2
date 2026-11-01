package unq.travel.reportes;

import unq.travel.catalogo.ServicioIndividual;
import unq.travel.catalogo.PaqueteTuristico;

public interface Visitor {
	
	void visitar(ServicioIndividual servicio);
	
	void visitar(PaqueteTuristico paquete);
}