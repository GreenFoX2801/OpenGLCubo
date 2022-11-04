//Leonardo Ferreira Rocha
package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

/**
 * Essa classe demonstra como desenhar um triangulo na tela utilizando a OpenGL.
 */
public class RotatingTriangle implements Scene {
	private Keyboard keys = Keyboard.getInstance();

	/** Esta variável guarda o identificador da malha (Vertex Array Object) do triângulo */
	private int vao;

	/** Guarda o id do shader program, após compilado e linkado */
	private int shader;

	/** Angulo que o triangulo está */
	private float angleY;
	private float angleX;


	@Override
	public void init() {
		//---------------------------------
		//Configurações iniciais
		//---------------------------------

		//Habilita o teste de profundidade
	//	glEnable(GL_DEPTH_TEST);

		//Impede o desenho quando os triângulos estiverem de costas
	//	glEnable(GL_CULL_FACE);

		//Descomente para desenhar só as bordas do triângulo
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		//Define a cor de limpeza da tela
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		//------------------------------
		//Carga/Compilação dos shaders
		//------------------------------
		shader = Shader.loadProgram("basic");

		//------------------
		//Criação da malha
		//------------------

		//O processo de criação da malha envolve criar um Vertex Array Object e associar a ele um buffer, com as
		// posições dos vértices do triangulo.

		//Criação do Vertex Array Object (VAO)
		vao = glGenVertexArrays();

		//Informamos a OpenGL que iremos trabalhar com esse VAO
		glBindVertexArray(vao);

		//Criação do buffer de posições
		//------------------------------
		var vertexData = new float[] {
				0.0f,-0.5f,
				0.0f,0.4f,
				-0.5f,-0.4f,

				0.0f,-0.5f,
				0.0f,0.4f,
				0.5f,-0.4f,

				-0.5f,-0.4f,
				-0.5f,0.5f,
				0.0f,0.4f,

				0.5f,-0.4f,
				0.0f,0.4f,
				0.5f,0.5f,

				0.0f,0.4f,
				0.0f,0.6f,
				0.5f,0.5f,

				0.0f,0.4f,
				0.0f,0.6f,
				-0.5f,0.5f,

				0.0f,-0.3f,
				0.5f,-0.4f,
				0.0f,-0.5f,

				0.0f,-0.3f,
				0.0f,-0.5f,
				0.5f,-0.4f,
		};


		//Solicitamos a criação de um buffer na OpenGL, onde esse array será guardado
		var positions = glGenBuffers();
		//Informamos a OpenGL que iremos trabalhar com esse buffer
		glBindBuffer(GL_ARRAY_BUFFER, positions);

		//Damos o comando para carregar esses dados na placa de vídeo
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

		//Procuramos o identificador do atributo de posição
		var aPosition = glGetAttribLocation(shader, "aPosition");

		//Chamamos uma função que associa a posição ao buffer.
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);

		//Criação do buffer de cores
		//------------------------------
		var colorData = new float[] {
				1.0f,  0.0f, 0.0f,  //Vértice 0
				1.0f,  1.0f, 0.0f,  //Vértice 1
				1.0f,  0.0f, 1.0f,  //Vértice 2
				1.0f,  0.0f, 0.0f,  //Vértice 3
				0.0f,  1.0f, 0.0f,  //Vértice 4
				0.0f,  1.0f, 1.0f,  //Vértice 5
				1.0f,  1.0f, 0.0f,  //Vértice 6
				0.0f,  1.0f, 0.0f,  //Vértice 7
				0.0f,  0.0f, 1.0f,  //Vértice 8
				1.0f,  0.0f, 1.0f,  //Vértice 9
				0.0f,  1.0f, 1.0f,  //Vértice 10
				0.0f,  0.0f, 1.0f,  //Vértice 11
				0.0f,  0.0f, 0.0f,  //Vértice 12
				1.0f,  1.0f, 1.0f,  //Vértice 13
				0.0f,  0.0f, 1.0f,  //Vértice 14
				1.0f,  0.0f, 0.0f,  //Vértice 15
				0.0f,  0.0f, 1.0f,  //Vértice 16
				0.0f,  0.0f, 1.0f,  //Vértice 17
				1.0f,  0.0f, 1.0f,  //Vértice 18
				0.0f,  1.0f, 0.0f,  //Vértice 19
				0.0f,  0.0f, 1.0f,  //Vértice 20
				1.0f,  0.0f, 0.0f,  //Vértice 21
				0.0f,  1.0f, 0.0f,  //Vértice 22
				0.0f,  0.0f, 1.0f,  //Vértice 23
		};

		//Solicitamos a criação de um buffer na OpenGL, onde esse array será guardado
		var colors = glGenBuffers();
		//Informamos a OpenGL que iremos trabalhar com esse buffer
		glBindBuffer(GL_ARRAY_BUFFER, colors);

		//Damos o comando para carregar esses dados na placa de vídeo
		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);

		//Informamos a OpenGL que iremos trabalhar com essa variável
		glEnableVertexAttribArray(aPosition);

		//Associação do buffer cores a variável aColor
		//---------------------------------------------------
		//Procuramos o identificador do atributo de posição
		var aColor = glGetAttribLocation(shader, "aColor");

		//Chamamos uma função que associa a cor ao shader.
		// Observe que o size mudou para 3, já que as cores são um vec3 com 3 floats para os componentes r, g e b
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 0, 0);

		//Informamos a OpenGL que iremos trabalhar com essa variável
		glEnableVertexAttribArray(aColor);

		//Faxina
		//Finalizamos o nosso VAO, portanto, informamos a OpenGL que não iremos mais trabalhar com ele
		glBindVertexArray(0);

		//Como já finalizamos a carga, informamos a OpenGL que não estamos mais usando esse buffer.
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void update(float secs) {
		//Testa se a tecla ESC foi pressionada
		if (keys.isPressed(GLFW_KEY_ESCAPE)) {
			//Fecha a janela, caso tenha sido
			glfwSetWindowShouldClose(glfwGetCurrentContext(), true);
			return;
		}

		//Somamos alguns graus de modo que o angulo mude 180 graus por segundo
		if(keys.isDown(GLFW_KEY_A)){
			angleY += Math.toRadians(180) * secs;
		}
		if(keys.isDown(GLFW_KEY_D)){
			angleY += Math.toRadians(-180) * secs;
		}
		if(keys.isDown(GLFW_KEY_W)){
			angleX += Math.toRadians(180) * secs;
		}
		if(keys.isDown(GLFW_KEY_S)){
			angleX += Math.toRadians(-180) * secs;
		}
	}

	@Override
	public void draw() {
		//Solicita a limpeza da tela e do buffer de profundidade
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		//Precisamos dizer qual VAO iremos desenhar
		glBindVertexArray(vao);

		//E qual shader program irá ser usado durante o desenho
		glUseProgram(shader);

		//Associação da variável World ao shader
		//--------------------------------------
		try (var stack = MemoryStack.stackPush()) {
			//Criamos uma matriz de rotação e a enviamos para o buffer transform
			var transformY = new Matrix4f()
					.rotateY(angleY).rotateX(angleX)
					.get(stack.mallocFloat(16));
			//Procuramos pelo id da variável uWorld, dentro do shader
			var uWorld = glGetUniformLocation(shader, "uWorld");
			// Copiamos os dados do buffer para a variável que está no shader
			glUniformMatrix4fv(uWorld, false, transformY);

		}

		//Comandamos o desenho de 3 vértices
		glDrawArrays(GL_TRIANGLES, 0, 24);

		//Faxina
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingTriangle()).show();
	}
}