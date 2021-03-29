algoritmo create(objeto)

    ultimoPar <- ultimo par do arquivo de índices

    mover o ponteiro para o ultimoPar.posicao

    extrair bloco

    objeto.id <- ultimoPar.getChave() + bloco.quantidadeRegistros

    se bloco.quantidadeRegistros < 63

        bloco.quantidadeRegistros = bloco.quantidadeRegistros + 1
        bloco.registros.add(objeto)

    senao

        ler 32 registros
        pos <- posição do ponteiro
        inserir par (objeto.id, pos) no arquivo de índice

        create(objeto)

    fim-senao

    escrever bloco

fim-algoritmo

algoritmo read(ID)

    chaveBloco <- indice.next()
    enquanto ID < chaveBloco
        chaveBloco  <- indice.next()
    fim-enquanto

    indiceObjeto <- indice.read(chaveBloco)

    se indiceObjeto.pos != -1

        mover ponteiro para indiceObjeto.posição

        quantidadeRegistros <- ler byte
        ler outros dados de controle

        enquanto não atingir o final do bloco

            ler próximo registro

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
