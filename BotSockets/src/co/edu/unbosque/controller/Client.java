package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread{

	private final int HOST_PORT;
	private final String HOST = "127.0.0.1";

	private Socket messageSocket; // Socket para mandar y recibir mensajes Servidor-Cliente
	private DataInputStream dataInput; // Recibir datos
	private DataOutputStream dataOutput; // Enviar datos

	private Scanner sc;

	public Client(int HOST_PORT) {
		this.HOST_PORT = HOST_PORT;
		this.messageSocket = null;
		this.dataInput = null;
		this.dataOutput = null;
		sc = new Scanner(System.in);
	}

	@Override
	public void run() {

		try {
			this.messageSocket = new Socket(HOST, HOST_PORT); // Genera conexion al puerto especificado

			// Recibe input del server
			this.dataInput = new DataInputStream(new BufferedInputStream(messageSocket.getInputStream()));

			// Manda output al server
			this.dataOutput = new DataOutputStream(messageSocket.getOutputStream());

			readfS();
			readfS();
			readfS();
			readfS();
			
			String postre;
			do {
				postre = sc.nextLine();
			}while(!send2S(postre, true, 3));
			
			switch (Integer.parseInt(postre)) {
			case 1: {
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				
				String helado;
				do {
					helado = sc.nextLine();
				}while(!send2S(helado, true, 8));
				
				break;
			}
			case 2: {
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				
				String torta;
				do {
					torta = sc.nextLine();
				}while(!send2S(torta, true, 7));
				
				break;
			}
			case 3: {
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				readfS();
				
				String malteada;
				do {
					malteada = sc.nextLine();
				}while(!send2S(malteada, true, 7));
				
				break;
			}
			default:
			}
			readfS();
			
			String ordenes;
			do {
				ordenes = sc.nextLine();
			}while(!send2S(ordenes, true, 100));
			
			readfS();
			readfS();
			readfS();
			
			String entrega;
			do {
				entrega = sc.nextLine();
			}while(!send2S(entrega, true, 2));
			
			switch (Integer.parseInt(entrega)) {
			case 1: {
				readfS();
				break;
			}
			case 2: {
				
				readfS();
				
				String direccion;
				do {
					direccion = sc.nextLine();
				}while(!send2S(direccion, false, 0));
				
				readfS();
				
				String nombre;
				do {
					nombre = sc.nextLine();
				}while(!send2S(nombre, false, 0));
				
				readfS();
				
				String numero;
				do {
					numero = sc.nextLine();
				}while(!send2S(numero, false, 0));
				
				break;
			}
			}
			readfS();

		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	public void readfS() {
		try {
			System.out.println(this.dataInput.readUTF());
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public boolean send2S(String entrada, boolean numero, int numOpciones) {
		try {

			if (numero) {
				if (entrada.matches(".*[^0-9].*")) {
					this.dataOutput.writeUTF("-1");
					readfS();
					return false;
				}
				else {
					if(Integer.parseInt(entrada) < 0 || Integer.parseInt(entrada) > numOpciones) {
						this.dataOutput.writeUTF("-1");
						readfS();
						return false;
					}
				}
				this.dataOutput.writeUTF(entrada+"|N");
				return true;
			}else {
				this.dataOutput.writeUTF(entrada+"|C");
				return true;
			}

		} catch (IOException e) {
			System.err.println(e);
		}
		return true;
	}

	public static void main(String[] args) {
		Client clt = new Client(5000);
		clt.start();
	}
	
	
}
