#version 400 core

in vec2 pass_textureCoords;
in vec2 pixelPosition;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec2 lightPosition[__lightNum__];


void main(void) {
	float distance = length(pixelPosition - lightPosition[0]);
	float intensity = (1.0/(distance*2))/(1 + distance*1 + distance*distance*1);
	
	vec4 tex = texture(textureSampler, pass_textureCoords);
	
	out_color = vec4((tex.xyz * intensity) , tex.w);
}