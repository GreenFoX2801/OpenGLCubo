#version 330


//Atributos do vértice: posição e cor
//São variáveis de entrada do shader, portanto, devem ser associadas a buffers pelo java
in vec2 aPosition;
in vec3 aColor;

//Matriz de transformação World. Deve ser carregada pelo Java.
uniform mat4 uWorld;


//Variável de saída, para repassar a cor para o fragment shader
out vec4 vColor;

void main(){
    //Transforma a posição do triangulo coordenadas do modelo para coordenadas do mundo
    gl_Position = uWorld * vec4(aPosition, 0.0, 1.0);

    //Repassar a cor do vértice para o fragment shader. Acrescenta um alfa de 1.0.
    vColor = vec4(aColor, 1.0);
}