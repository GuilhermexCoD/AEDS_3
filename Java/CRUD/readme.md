# AEDS III - Algoritimo e Estrutura de Dados III

## CRUD Genérico

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

---

## Estrutura do arquivo

### Cabeçalho

- int ultimo ID registrado

### Registro

- byte Lapide [0 - Invalido,1 - Valido]
- short tamanho do registro
- byte[] array de byte do objeto
