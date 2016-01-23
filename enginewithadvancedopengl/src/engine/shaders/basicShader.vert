#version 400 core

in vec2 position;

out vec3 color;

void main(void) {
	gl_Position = vec4(position.xy,0.0,1.0);
	color = vec3(position.x+0.5, 0.5, position.y+0.5);
}