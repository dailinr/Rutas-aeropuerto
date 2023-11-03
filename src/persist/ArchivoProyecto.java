package persist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datos.Ciudad;
import datos.Viaje;
import grafo.Grafo;


public class ArchivoProyecto {
    public void write(Grafo<Ciudad, Viaje> x) throws FileNotFoundException, IOException {
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("grafo.obj"));
        salida.writeObject(x);
        salida.close();
    }

    public Grafo<Ciudad, Viaje> read() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("grafo.obj"));
        Grafo<Ciudad, Viaje> grafo = (Grafo<Ciudad, Viaje>) entrada.readObject();
        entrada.close();
        return grafo;
    }
}
