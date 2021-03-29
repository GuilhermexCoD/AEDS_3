algoritmo create(objeto)

    indiceObjeto <- ultimo par do arquivo de índices

    mover o ponteiro para o início do ultimo bloco

    extrair bloco

    quantidadeRegistros <- ler byte do bloco

    objeto.id <- indiceObjeto.getChave() + quantidadeRegistros

    se quantidadeRegistros < 63

        mover para início do ultimo bloco
        escrever (quantidadeRegistros + 1) no bloco
        mover para o fim do ultimo bloco
        escrever (objeto) no bloco

        escrever bloco no arquivo

    senao

        ler Dados de controle
        ler 32 registros do bloco
        escrever bloco (32,dados de controle,32 registros) no arquivo
        pos <- posição do ponteiro
        ler 31 registros do bloco
        escrever bloco (31, dados de controle, 31 registros) no arquivo

        indice.create(objeto.id, pos) no arquivo de índice

        this.create(objeto)

    fim-se

fim-algoritmo

algoritmo read(ID)

    chaveBloco <- indice.next()
    enquanto ID < chaveBloco
        chaveBloco  <- indice.next()
    fim-enquanto

    pos <- indice.read(chaveBloco)

    se pos != -1

        mover ponteiro para pos no bloco

        quantidadeRegistros <- ler byte no bloco
        ler Dados de controle no bloco

        enquanto não atingir o final do bloco

            ler próximo registro no bloco

            se !(registro excluído)
                extrair objeto
                se objeto.id = ID
                    retorne objeto
                fim-se
            fim-se
        fim-enquanto

    fim-se

    retorna nulo

fim-algoritmo

algoritmo update(novoObjeto)

    chaveBloco <- indice.next()
    enquanto (ID < chaveBloco) e (chaveBloco != null)
        chaveBloco  <- indice.next()
    fim-enquanto

    se (chaveBloco != null)
        pos <- indice.read(chaveBloco)
        mover ponteiro para pos
        extrair bloco
        quantidadeRegistros <- ler byte do bloco
        ler Dados de controle

        enquanto não atingir o final do bloco
            objeto <- extrair objeto
            se !(objeto excluído) e objeto.id = novoObjeto.id
                pos <- posição do ponteiro
                se objeto.id = novoObjeto.id
                    atualizar novoObjeto
                fim-se
            fim-se
        fim-enquanto
        mover ponteiro para pos no bloco
        escrever bloco (quantidadeRegistros, Dados de controle, registros) no arquivo
        retorna true
    fim-se

    retorna false

fim-algoritmo

algoritmo delete(ID)

    chaveBloco <- indice.next()
    enquanto ID < chaveBloco e chaveBloco != null
        chaveBloco  <- indice.next()
    fim-enquanto

    se !(chaveBloco = null)
        pos <- read(chaveBloco)
        mover ponteiro para pos
        extrair bloco
        quantidadeRegistros <- ler byte no bloco
        ler Dados de controle no bloco

        enquanto não atingir o final do bloco
            extrair objeto
            se !(objeto excluído) e objeto.id = ID
                marcar objeto como excluído
                quantidadeRegistros <- quantidadeRegistros - 1
                se quantidadeRegistros = 0
                    delete(chaveBloco)
                fim-se
            fim-se
        fim-enquanto
        mover ponteiro para pos no bloco
        escrever bloco (quantidadeRegistros, Dados de controle, registros)
    fim-se

    retorna nulo

fim-algoritmo
