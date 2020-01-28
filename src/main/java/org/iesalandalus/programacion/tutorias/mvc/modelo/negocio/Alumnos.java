package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;

public class Alumnos {
	private int capacidad;
	private int tamano;
	private Alumno [] coleccionAlumnos;
	
	public Alumnos(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionAlumnos = new Alumno[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al NUM_MAX_CITAS 
		//que coincide con el parametro pasado capacidad
	}
	
	public Alumno[] get() {
		return coleccionAlumnos;
	}
	
	public int getTamano() {
		return tamano;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Alumno alumno) throws OperationNotSupportedException{
		if (alumno==null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(alumno)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}
		
		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
		}else {
			coleccionAlumnos[tamano]=new Alumno(alumno); //añade un alumno justo despues del último almacenadoa última almacenada
			System.out.println("Alumno introducido correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Alumno alumno) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		for (int i=0; i<tamano;i++) {
			if (coleccionAlumnos[i].equals(alumno)){ 
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
	
	public Alumno buscar(Alumno alumno) {
		if(alumno==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		int indice;
		Alumno encontrado = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(alumno);
		if (!tamanoSuperado(indice)) {
			encontrado=new Alumno(coleccionAlumnos[indice]);//obtengo una copia de la cita
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		int indice;
		indice=buscarIndice(alumno);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("Alumno borrado correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionAlumnos[i-1]=coleccionAlumnos[i];
			}
		}
		coleccionAlumnos[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
	
}
