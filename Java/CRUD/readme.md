# AEDS III - Algoritimo e Estrutura de Dados III

## CRUD Indexado

## 💻 Disciplina

- AEDS III

## 👨🏼‍🎓 Aluno

<ul>
  <li>
    Nome: Guilherme Fróes Camba de Freitas
  </li>
  <li>
    Matricula: 718116
  </li>
</ul>

## 🌐Links

<ul>
  <li>
    <a href="https://github.com/GuilhermexCoD/AEDS_3/tree/main/Java/CRUD"> GitHub Repository
    </a>
  </li>
</ul>

## 💾 Como utilizar o CRUD

1. Crie uma classe que implemente a classe `Registro`

#### Ex.

```
  public class Usuario implements Registro{
    ...
  }
```

2. Implemente os metodos requeridos da interface `Registro`

#### Ex.

```
  @Override
  public int getID();

  @Override
  public void setID(int id);

  @Override
  public byte[] toByteArray() throws IOException;

  @Override
  public void fromByteArray(byte[] ba) throws IOException;
```

3. Implemente um construtor para sua classe

#### Ex.

```
  public Usuario() {
    this(-1, "", "", "");
  }
```

## 💾 Como utilizar o CRUD Indexado

1. Crie uma classe que implemente a classe `RegistroHashExtensivelId`

#### Ex.

```
  public class pcvUsuarioDireto implements RegistroHashExtensivelId<pcvLivroDireto>{
    ...
  }
```

2. Implemente os metodos requeridos da interface `RegistroHashExtensivelId`

#### Ex.

```
    public void setId(int id);

    public int getId();

    public void setPos(long pos);

    public long getPos();

    public int hashCode();

    public short size();

    public byte[] toByteArray() throws IOException;

    public void fromByteArray(byte[] ba) throws IOException;
```

---

## Estrutura do arquivo

### Cabeçalho

- int ultimo ID registrado

### Registro

- byte Lapide [0 - Invalido,1 - Valido]
- short tamanho do registro
- byte[] array de byte do objeto

## Estrutura do Indice
 - id (chave)
 - pos (valor)
