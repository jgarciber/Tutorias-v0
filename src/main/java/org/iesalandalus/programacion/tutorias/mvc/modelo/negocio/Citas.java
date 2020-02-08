package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;

public class Citas {

	private int capacidad;
	private int tamano;
	private Cita [] coleccionCitas;
	
	public Citas(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionCitas = new Cita[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al CAPACIDAD 
		//que coincide con el parametro pasado capacidad
	}
	
	public Cita[] get() {
		return copiaProfundaCitas();
	}
	
	private Cita [] copiaProfundaCitas() {
		Cita[] coleccionCopia= new Cita[capacidad];
		for (int i=0; i<tamano;i++) {
			coleccionCopia[i]= new Cita(coleccionCitas[i]);
		}
	
		return coleccionCopia;
	}
	
	public Cita[] get(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		
		Cita[] coleccionCopia= new Cita[capacidad];
		for (int i=0; i<tamano;i++) {
			if(coleccionCitas[i].getSesion().equals(sesion)){
				coleccionCopia[i]= new Cita(coleccionCitas[i]);
			}
		}
	
		return coleccionCopia;
	}
	
	public Cita[] get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		Cita[] coleccionCopia= new Cita[tamano];
		for (int i=0; i<tamano;i++) {
			if(coleccionCitas[i].getAlumno().equals(alumno)){
				coleccionCopia[i]= new Cita(coleccionCitas[i]);
			}
		}
		return coleccionCopia;
	}
	public int getTamano() {
		return tamano;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Cita cita) throws OperationNotSupportedException{
		if (cita==null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		if(buscar(cita)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita con esa hora.");
		}

		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}else {
			coleccionCitas[tamano]=new Cita(cita); 
			System.out.println("Cita introducida correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Cita cita) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		for (int i=0; i<tamano;i++) {
			if (coleccionCitas[i].equals(cita)){ 
				//solo se comparan las fechas obviando los nombres.
				//se utiliza el método equals para el array ya que es un array de objetos
				indice=i;
			}
		}
		return indice ;
	}
	
	private boolean tamanoSuperado (int nuevoTamano) {
		boolean superado=false;
		if (nuevoTamano>=tamano) {
			superado=true;
		}
		return superado;
	}
	
	
	private boolean capacidadSuperada(int tamano) {
		boolean superado=false;
		if (tamano>=capacidad) {
			superado=true;
		}
		return superado;
	}
	
	public Cita buscar(Cita cita) {
		if(cita==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una cita nula.");
		}
		int indice;
		Cita encontrado = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(cita);
		if (!tamanoSuperado(indice)) {
			encontrado=new Cita(coleccionCitas[indice]);//obtengo una copia de la cita
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice;
		indice=buscarIndice(cita);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("Cita borrada correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita con esa hora.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionCitas[i-1]=coleccionCitas[i];
			}
		}
		coleccionCitas[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
}

