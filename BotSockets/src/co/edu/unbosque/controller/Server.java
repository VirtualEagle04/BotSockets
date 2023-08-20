package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import co.edu.unbosque.model.persistance.FileHandler;

public class Server extends Thread {

	private final int PORT;

	private ServerSocket serverSocket;
	private Socket messageSocket; // Socket para mandar y recibir mensajes Servidor-Cliente
	private DataInputStream dataInput; // Recibir datos
	private DataOutputStream dataOutput; // Enviar datos

	private ArrayList<String> resp;

	public Server(int PORT) {
		this.PORT = PORT;
		this.messageSocket = null;
		this.dataInput = null;
		this.dataOutput = null;
		this.serverSocket = null;

		resp = FileHandler.cargarDesdeArchivo("respuestas.csv");
	}

	@Override
	public void run() {

		try {
			this.serverSocket = new ServerSocket(this.PORT); // Inicia el servidor en el puerto establecido
			System.out.println("Server started in port: " + this.PORT);
			System.out.println("Waiting for connection...");
			this.messageSocket = serverSocket.accept();
			System.out.println("Client online! IP: " + messageSocket.getInetAddress() + ":" + messageSocket.getPort());

			// Recibe input del cliente
			this.dataInput = new DataInputStream(new BufferedInputStream(messageSocket.getInputStream()));

			// Manda output al cliente
			this.dataOutput = new DataOutputStream(messageSocket.getOutputStream());

			send2C(0);
			send2C(1);
			send2C(2);
			send2C(3);
			switch ((int) readfC()) {
			case 1: {
				send2C(4);
				send2C(5);
				send2C(6);
				send2C(7);
				send2C(8);
				send2C(9);
				send2C(10);
				send2C(11);
				
				int helado = (int) readfC();

				break;
			}
			case 2: {
				send2C(12);
				send2C(13);
				send2C(14);
				send2C(15);
				send2C(16);
				send2C(17);
				send2C(18);
				
				int torta = (int) readfC();

				break;
			}
			case 3: {
				send2C(19);
				send2C(20);
				send2C(21);
				send2C(22);
				send2C(23);
				send2C(24);
				send2C(25);
				
				int malteada = (int) readfC();

				break;
			}
			default:
			}
			send2C(26);
			
			int ordenes = (int) readfC();
			
			send2C(27);
			send2C(28);
			send2C(29);
			
			int entrega = (int) readfC();
			
			switch (entrega) {
			case 1: {
				send2C(34);
				break;
			}
			case 2: {
				send2C(30);
				String direccion = (String) readfC();
				send2C(31);
				String nombre = (String) readfC();
				send2C(32);
				String numero = (String) readfC();
				break;
			}
			}
			send2C(33);

		} catch (IOException e) {
			System.err.println(e);
		}

	}

	public void send2C(int index) {

		try {
			this.dataOutput.writeUTF(resp.get(index));
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	public Object readfC() {
		try {
			String aux = this.dataInput.readUTF();

			if (aux.equals("-1")) {

				send2C(35);
				return readfC();
			} else {

				for (int i = 0; i < aux.length(); i++) {

					if (aux.charAt(i) == '|') {
						if (aux.charAt(i + 1) == 'N') {
							//Numero
							String soloNum = aux.split("|")[0];
							System.out.println(soloNum);
							
							return Integer.parseInt(soloNum);
						} else {
							//Caracter
							String perfe = aux.split("|")[0];
							return perfe;
						}
					}

				}

			}

		} catch (IOException e) {
			System.err.println(e);
		}
		return "";
	}

	public static void main(String[] args) {
		Server srv = new Server(5000);
		srv.start();
	}

}
