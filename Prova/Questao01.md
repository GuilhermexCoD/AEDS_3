Entidade: CarteriaMotorista

|tipo   | atributo                | tamanho (Bytes)     | Write     | Read      | Possivel valor
|int    | numeroRegistro          | 4                   | writeInt  | readInt   | 5068423899
|String | nome                    | 2 + (string.length) | writeUTF  | readUTF   | Guilherme Froes
|String | CPF                     | 13                  | writeUTF  | readUTF   | 02454687815
|long   | dataNascimento          | 8                   | writeLong | readLong  | 628627392
|long   | dataValidade            | 8                   | writeLong | readLong  | 987456745
|long   | dataPrimeiraHabilitacao | 8                   | writeLong | readLong  | 999456745


Cada registro possui um Byte para simbolizar se o registro esta ativo ou não, Possui um Short armazenar o tamanho do Registro armazenado em sequencia e em seguida armazenamos os bytes da entidade CarteiraMotorista.

|tipo   | atributo  | tamanho (Bytes)                | Write      | Read      | Possivel valor
|byte   | lapide    | 1                              | writeByte  | readByte  | 1
|short  | tamanho   | 2                              | writeShort | readShort | 45
|byte[] | entidade  | carteriaMotorista.toByteArray()| write      | read      | vetor de bytes da entidade em questão

