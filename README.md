# Aula 2 completa

Este projeto contém o código completo da aula 2.

Neste código, nossa malha agora possuirá um atributo adicional, a cor. Isso causa as seguintes modificações:

1. Criação de uma nova variável de entrada no Vertex Shader, chamada `aColor` do tipo `vec3`
1. Criação de um novo buffer no Java, chamado `positions`
1. Associação desse buffer a variável no método draw

Além disso, adicionamos ao Vertex Shader a capacidade de transformar as posições da malha de acordo com uma matriz de 
rotação para isso:

1. Incluímos no Vertex Shader um uniform chamado uWorld
1. Criamos uma variável para o angulo e a atualizamos no método update
1. Criamos uma matriz de transformação 4x4 e a associamos a variável uWorld no método draw