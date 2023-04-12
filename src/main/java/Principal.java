import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor que le objetos cliente no socket.
 *
 * @author osmar
 */
public class Principal {

    public static void main(String[] args) {

        try {
            ServerSocket servidor = new ServerSocket(4444, 100); //conexao(porta,maximo clientes)
            while (true) {
                Socket socket = servidor.accept(); // espera da conexao
                InputStream dadosentrada = socket.getInputStream(); //obtem o fluxo de entrada
                ObjectInputStream entrada = new ObjectInputStream(dadosentrada); // cria o objeto de entrada para o fluxo
                System.out.println("Inicio servidor");                
                try {
                    Cliente cliente = null;
                    cliente = (Cliente) entrada.readObject();
                    while (cliente.getCodigo() != -1) {
                        System.out.println("Objeto Cliente Chegou");
                        cliente.imprimir();
                        cliente = (Cliente) entrada.readObject();
                    }
                } catch (ClassNotFoundException cnfe) {
                    System.out.println("Excecao: " + cnfe.getMessage());
                }
                System.out.println("fim servidor");
                entrada.close();
                socket.close();
                servidor.close();
            }
        } catch (EOFException eoe) {//fim conexao cliente
            System.out.println("Excecao: " + eoe.getMessage());
        } catch (IOException ioe) {//problema de io
            System.out.println("Excecao: " + ioe.getMessage());
        }
    }
}