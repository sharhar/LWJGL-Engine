#version 400 core

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;
out vec2 pixelPosition;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

void main(void) {
	vec4 pos = transformationMatrix * vec4(position.xy,0.0,1.0);
	gl_Position = projectionMatrix * pos;
	pass_textureCoords = textureCoords;
	pixelPosition = vec2(pos.xy);
}