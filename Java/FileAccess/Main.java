import java.io.RandomAccessFile;

public class Main {

    static String _filePath = "dados/livros.db";

    public static void main(String[] args) {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.9f);
        Livro l2 = new Livro(2, "Eu Sou A Lenda", "Richard Matheson", 21.99f);

        System.out.println("Writing in file " + _filePath);
        System.out.println(l1);
        System.out.println(l2);

        byte[] ba;

        RandomAccessFile raf;

        try {
            /* Escrita do arquivo */

            raf = new RandomAccessFile(_filePath, "rw");

            long p1 = raf.getFilePointer();

            ba = l1.toByteArray();
            raf.writeInt(ba.length);
            raf.write(ba);

            long p2 = raf.getFilePointer();

            ba = l2.toByteArray();
            raf.writeInt(ba.length);
            raf.write(ba);

            /* Leitura do arquivo */

            Livro l3 = new Livro();
            Livro l4 = new Livro();
            int tamanhoByte;

            raf.seek(p2);

            tamanhoByte = raf.readInt();
            ba = new byte[tamanhoByte];
            raf.read(ba);
            l3 = new Livro(ba);

            raf.seek(p1);

            tamanhoByte = raf.readInt();
            ba = new byte[tamanhoByte];
            raf.read(ba);
            l4 = new Livro(ba);

            raf.close();

            System.out.println("\nReading from file " + _filePath);
            System.out.println(l3);
            System.out.println(l4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
