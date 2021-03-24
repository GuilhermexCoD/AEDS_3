package CrudPack;

import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import EventPack.*;

public class CRUD<T extends Registro> implements EventListener {

    public static final byte VALIDO = 1;
    public static final byte INVALIDO = 0;

    private Constructor<T> construtor;
    private String caminhoArquivo = "";
    private RandomAccessFile raf;

    private EventHandler eventHandler;

    public CRUD(Constructor<T> construtor, String caminhoArquivo) {
        this.construtor = construtor;
        this.caminhoArquivo = caminhoArquivo;

        this.eventHandler = new EventHandler(this);
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
            long pos = raf.getFilePointer();
            createRegistro(objeto);
            callPointerChanged(objeto, pos);
            raf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

        }
        return objeto.getID();
    }

    public List<T> read(Predicate<T> condition) throws Exception {
        List<T> objetos = new LinkedList<T>();

        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            if (raf.length() > 0) {

                raf.seek(Integer.BYTES);
                long pointer = raf.getFilePointer();
                while (pointer < raf.length()) {
                    T objeto = this.construtor.newInstance();
                    // ler registro
                    byte lapide = raf.readByte();
                    if (isRegistroValido(lapide)) {
                        tamanhoRegistro = raf.readShort();
                        ba = new byte[tamanhoRegistro];
                        raf.read(ba);
                        objeto.fromByteArray(ba);

                        if (condition.test(objeto)) {
                            objetos.add(objeto);
                        }
                    } else {
                        tamanhoRegistro = raf.readShort();
                        raf.seek(raf.getFilePointer() + tamanhoRegistro);
                    }
                    pointer = raf.getFilePointer();
                }
            } else {
                System.out.println("Arquivo vazio");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

            System.out.println("Registro nao encontrado");
        }

        raf.close();

        return objetos;
    }

    public T read(int id) throws Exception {
        Predicate<T> predicate = obj -> (obj.getID() == id);

        T objeto = null;
        List<T> objetos = read(predicate);

        if (objetos.size() > 0) {
            objeto = objetos.get(0);
        }

        return objeto;
    }

    public T read(Long pos) throws Exception {
        T objeto = this.construtor.newInstance();

        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            raf.seek(pos);

            byte lapide = raf.readByte();
            if (isRegistroValido(lapide)) {

                tamanhoRegistro = raf.readShort();
                ba = new byte[tamanhoRegistro];
                raf.read(ba);
                objeto.fromByteArray(ba);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

            System.out.println("Registro nao encontrado");
        }

        raf.close();

        return objeto;
    }

    public boolean update(T novoObjeto, Long pos) throws Exception {
        boolean updated = false;

        int tamanhoRegistro = 0;
        byte[] ba;

        T objeto = createInstance();

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            raf.seek(pos);

            byte lapide = raf.readByte();
            if (isRegistroValido(lapide)) {
                tamanhoRegistro = raf.readShort();
                ba = new byte[tamanhoRegistro];
                raf.read(ba);
                objeto.fromByteArray(ba);

                if (objeto.getID() == novoObjeto.getID()) {
                    int tamanhoNovoRegistro = getTamanhoRegistro(novoObjeto);
                    raf.seek(pos);
                    if (tamanhoNovoRegistro <= tamanhoRegistro) {
                        createRegistro(novoObjeto, tamanhoRegistro);
                        updated = true;
                    } else {
                        // Escrevendo lapide INVALIDO
                        raf.writeByte(INVALIDO);
                        long pointer = raf.length();
                        raf.seek(pointer);

                        createRegistro(novoObjeto);

                        callPointerUpdated(novoObjeto, pointer);
                        updated = true;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

            System.out.println("Registro nao encontrado");
        }

        raf.close();

        return updated;
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
                            raf.seek(pointer);
                            if (tamanhoNovoRegistro <= tamanhoRegistro) {
                                createRegistro(novoObjeto, tamanhoRegistro);
                                raf.close();
                                return true;
                            } else {
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

    public boolean delete(Long pos, int id) throws Exception {
        boolean deleted = false;

        int tamanhoRegistro = 0;
        byte[] ba;

        try {
            raf = new RandomAccessFile(this.caminhoArquivo, "rw");

            raf.seek(pos);

            byte lapide = raf.readByte();
            if (isRegistroValido(lapide)) {
                tamanhoRegistro = raf.readShort();
                T objeto = createInstance();
                ba = new byte[tamanhoRegistro];
                raf.read(ba);
                objeto.fromByteArray(ba);

                if (objeto.getID() == id) {
                    raf.seek(pos);
                    callPointerRemoved(objeto, pos);
                    raf.writeByte(INVALIDO);
                    deleted = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());

            System.out.println("Registro nao encontrado");
        }

        raf.close();

        return deleted;
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

    public T createInstance() throws Exception {
        return this.construtor.newInstance();
    }

    public void callPointerChanged(T objeto, long pointer) {
        eventHandler.CallEvent(new EventArgsPointerChanged(this, objeto, pointer));
    }

    public void callPointerUpdated(T objeto, long pointer) {
        eventHandler.CallEvent(new EventArgsPointerUpdated(this, objeto, pointer));
    }

    public void callPointerRemoved(T objeto, long pointer) {
        eventHandler.CallEvent(new EventArgsPointerRemoved(this, objeto, pointer));
    }

    @Override
    public void AddListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        eventHandler.AddListener(eventArgsClass, observer, method);
    }

    @Override
    public void RemoveListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        eventHandler.RemoveListener(eventArgsClass, observer, method);
    }
}