package co.edu.unbosque.model.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	private static Scanner lector;
	private static File archivo;
	private static PrintWriter escritor;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;
	private static FileOutputStream fos;
	
	public static void escribirSerializado(String nombre_archivo, Object obj) {

		try {
			oos = new ObjectOutputStream(
					new FileOutputStream("src/co/edu/unbosque/model/persistance/" + nombre_archivo));
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error de Entrada: No se ha encontrado el archivo. (Serializado/Entrada).");
		} catch (IOException e) {
			System.err.println("Error de Entrada: Revise Permisos. (Serializado/Entrada).");
		}

	}
	
	public static Object leerSerializado(String nombre_archivo) {
		try {
			ois = new ObjectInputStream(new FileInputStream("src/co/edu/unbosque/model/persistance/" + nombre_archivo));
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (FileNotFoundException e) {
			System.err.println("Error de Lectura: No se ha encontrado el archivo. (Serializado/Salida).");

		} catch (IOException e) {
			System.err.println("Error de Lectura: Revise Permisos. (Serializado/Salida).");
		} catch (ClassNotFoundException e) {
			System.err.println("Error de Lectura: No se ha encontrado el archivo. (Serializado/Salida).");
		}

		return null;
	}
	
	public static String abrirArchivo(String nombre_archivo) {

		archivo = new File("src/co/edu/unbosque/model/persistance/" + nombre_archivo);
		StringBuilder contenido = new StringBuilder();
		try {
			lector = new Scanner(archivo);
			while (lector.hasNext()) {
				contenido.append(lector.nextLine() + "\n");
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error de Lectura: Archivo no encontrado.");
		} catch (IOException e1) {
			System.err.println("Error de Lectura: Revise permisos.");
		}

		return contenido.toString();
	}
	
	public static void escribirArchivo(String nombre_archivo, String contenido) {
		archivo = new File("src/co/edu/unbosque/model/persistance/" + nombre_archivo);
		try {
			escritor = new PrintWriter(archivo);
			escritor.println(contenido);
			escritor.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error de Escritura: Archivo no encontrado.");
			try {
				archivo.createNewFile();
			} catch (IOException e1) {
			}
		} catch (IOException e2) {
			System.err.println("Error de Escritura: Revise permisos.");
		}
	}
	
	public static ArrayList<String> cargarDesdeArchivo(String nombre) {
		ArrayList<String> desde_archivo = new ArrayList<>();
		String contenido = FileHandler.abrirArchivo(nombre);
		String[] lineas = contenido.split("\n");
		for (String linea : lineas) {
			desde_archivo.add(linea);
		}
		return desde_archivo;
	}
	
}
