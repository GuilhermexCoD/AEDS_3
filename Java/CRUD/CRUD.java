import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class CRUD<T extends Registro> {

    public static final byte VALIDO = 1;
    public static final byte INVALIDO = 0;

    private Constructor<T> construtor;
    private String caminhoArquivo = "";
    private RandomAccessFile raf;

    public CRUD(Constructor<T> construtor, String caminhoArquivo) {
        this.construtor = construtor;
        this.caminhoArquivo = caminhoArquivo;
    }

    public int create(T objeto) {
        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");
            raf.seek(0);
            int ultimoId = 0;
            if (raf.length() > 0) {
                ultimoId = raf.readInt();
                ultimoId++;
                raf.seek(0);
            }

            objeto.setID(ultimoId);
            // Escrevendo ultimo ID no cabecalho do arquivo
            raf.writeInt(objeto.getID());
            // ir para final do arquivo
            raf.seek(raf.length());
            createRegistro(objeto);
            raf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

        }
        return objeto.getID();
    }

    public T read(int id) throws Exception {
        T objeto = this.construtor.newInstance();
        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            if (raf.length() > 0) {

                raf.seek(Integer.BYTES);
                long pointer = raf.getFilePointer();
                while (pointer < raf.length()) {
                    // ler registro
                    byte lapide = raf.readByte();
                    if (isRegistroValido(lapide)) {
                        tamanhoRegistro = raf.readShort();
                        ba = new byte[tamanhoRegistro];
                        raf.read(ba);
                        objeto.fromByteArray(ba);

                        if (objeto.getID() == id) {
                            raf.close();
                            return objeto;
                        }
                    } else {
                        tamanhoRegistro = raf.readShort();
                        raf.seek(raf.getFilePointer() + tamanhoRegistro);
                    }
                    pointer = raf.getFilePointer();
                }
            } else {

                System.out.println("Arquivo vazio");
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        System.out.println("Registro nao encontrado");

        return null;
    }

    public boolean update(T novoObjeto) throws Exception {

        T objeto = this.construtor.newInstance();
        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            if (raf.length() > 0) {

                raf.seek(Integer.BYTES);
                long pointer = raf.getFilePointer();

                while (pointer < raf.length()) {
                    pointer = raf.getFilePointer();
                    // ler registro
                    byte lapide = raf.readByte();
                    if (isRegistroValido(lapide)) {
                        tamanhoRegistro = raf.readShort();
                        ba = new byte[tamanhoRegistro];
                        raf.read(ba);
                        objeto.fromByteArray(ba);

                        if (objeto.getID() == novoObjeto.getID()) {
                            int tamanhoNovoRegistro = getTamanhoRegistro(novoObjeto);
                            if (tamanhoNovoRegistro <= tamanhoRegistro) {
                                System.out.println("Registro com tamanho menor");
                                raf.seek(pointer);
                                createRegistro(novoObjeto, tamanhoRegistro);
                                raf.close();
                                return true;
                            } else {
                                raf.seek(pointer);
                                System.out.println("Registro com tamanho maior criando outro no final do arquivo");
                                // Escrevendo lapide INVALIDO
                                raf.writeByte(INVALIDO);
                                raf.seek(raf.length());
                                createRegistro(novoObjeto);
                                raf.close();
                                return true;
                            }
                        }
                    } else {
                        tamanhoRegistro = raf.readShort();
                        raf.seek(raf.getFilePointer() + tamanhoRegistro);
                    }
                }
            } else {
                System.out.println("Arquivo vazio");
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        System.out.println("Registro nao encontrado");

        return false;
    }

    public boolean delete(int id) throws Exception {
        T objeto = this.construtor.newInstance();
        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            if (raf.length() > 0) {
                raf.seek(Integer.BYTES);
                long pointer = raf.getFilePointer();
                while (pointer < raf.length()) {
                    // ler registro
                    byte lapide = raf.readByte();
                    if (isRegistroValido(lapide)) {
                        tamanhoRegistro = raf.readShort();
                        ba = new byte[tamanhoRegistro];
                        raf.read(ba);
                        objeto.fromByteArray(ba);

                        if (objeto.getID() == id) {
                            raf.seek(pointer);
                            raf.writeByte(INVALIDO);
                            raf.close();
                            return true;
                        }
                    } else {
                        tamanhoRegistro = raf.readShort();
                        raf.seek(raf.getFilePointer() + tamanhoRegistro);
                    }
                    pointer = raf.getFilePointer();

                }
            } else {
                System.out.println("Arquivo vazio");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        System.out.println("Registro nao encontrado");

        return false;
    }

    private void createRegistro(T objeto) throws Exception {
        if (raf != null) {
            // Escrevendo o registro do objeto
            // Escrevendo o valor da lapide do objeto criado
            raf.writeByte(VALIDO);
            byte[] ba = objeto.toByteArray();
            // Escrevendo o tamanho da entidade
            raf.writeShort(ba.length);
            // Escrevendo os bytes da entidade
            raf.write(ba);
        }
    }

    private void createRegistro(T objeto, int tamanho) throws Exception {
        if (raf != null) {
            // Escrevendo o registro do objeto
            // Escrevendo o valor da lapide do objeto criado
            raf.writeByte(VALIDO);
            byte[] ba = objeto.toByteArray();
            // Escrevendo o tamanho da entidade
            raf.writeShort(tamanho);
            // Escrevendo os bytes da entidade
            raf.write(ba);
        }
    }

    private int getTamanhoRegistro(T objeto) throws Exception {
        return objeto.toByteArray().length;
    }

    public boolean isRegistroValido(byte b) {
        return (b == VALIDO);
    }
}