package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Profesores {
	private int capacidad;
	private int tamano;
	private Profesor [] coleccionProfesores;
	
	public Profesores(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionProfesores = new Profesor[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al CAPACIDAD 
		//que coincide con el parametro pasado capacidad
	}
	
	public Profesor[] get() {
		return copiaProfundaProfesores();
	}
	
	private Profesor [] copiaProfundaProfesores() {
		Profesor[] coleccionCopia= new Profesor[capacidad];
		for (int i=0; i<tamano;i++) {
			coleccionCopia[i]= new Profesor(coleccionProfesores[i]);
		}
		return coleccionCopia;
	}
	
	public int getTamano() {
		return tamano;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException{
		if (profesor==null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(profesor)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}
		
		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más profesores.");
		}else {
			coleccionProfesores[tamano]=new Profesor(profesor); 
			System.out.println("Profesor introducido correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Profesor profesor) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		for (int i=0; i<tamano;i++) {
			if (coleccionProfesores[i].equals(profesor)){ 
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
	
	public Profesor buscar(Profesor profesor) {
		if(profesor==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		int indice;
		Profesor encontrado = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(profesor);
		if (!tamanoSuperado(indice)) {
			encontrado=new Profesor(coleccionProfesores[indice]);//obtengo una copia de la cita
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice;
		indice=buscarIndice(profesor);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("Profesor borrado correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionProfesores[i-1]=coleccionProfesores[i];
			}
		}
		coleccionProfesores[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
	
}
