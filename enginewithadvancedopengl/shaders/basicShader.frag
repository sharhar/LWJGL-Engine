#version 400 core

in vec2 pass_textureCoords;
in vec2 pixelPosition;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec2 lightPosition[__lightNum__];
uniform vec3 lightAttenuation[__lightNum__];
uniform vec3 lightColor[__lightNum__];
uniform float lightIntensity[__lightNum__];
uniform float lightZ[__lightNum__];
uniform float lightRange[__lightNum__];
uniform float lightsOn;
uniform float texID;

void main(void) {
	if(lightsOn != 0) {
		vec4 tempLightColor[__lightNum__];
		
		for(int i = 0; i < __lightNum__;i++) {
			float distance = length(vec3(pixelPosition.xy, 0.0) - vec3(lightPosition[i].xy, lightZ[i]));
			float intensity = 1.0/(lightAttenuation[i].x + distance*lightAttenuation[i].y + distance*distance*lightAttenuation[i].z);
		
			intensity *= lightIntensity[i] * 100;
		
			intensity = max(intensity, 0.0);
			intensity = min(intensity, 1.0);
			
			float rangeOff = 1 - length(pixelPosition - lightPosition[i].xy)/lightRange[i];
			rangeOff = max(rangeOff, 0.0);
			rangeOff = min(rangeOff, 1.0);
			
			intensity *= rangeOff;
			
			tempLightColor[i].x = lightColor[i].x;
			tempLightColor[i].y = lightColor[i].y;
			tempLightColor[i].z = lightColor[i].z;
			tempLightColor[i].w = intensity;
		}
		
		vec4 totalIntensity = vec4(0.0);
		
		for(int i = 0; i < __lightNum__;i++) {
			totalIntensity.x += tempLightColor[i].x*tempLightColor[i].x*tempLightColor[i].w;
			totalIntensity.y += tempLightColor[i].y*tempLightColor[i].y*tempLightColor[i].w;
			totalIntensity.z += tempLightColor[i].z*tempLightColor[i].z*tempLightColor[i].w;
			totalIntensity.w += tempLightColor[i].w;
		}
		
		totalIntensity.x = sqrt(totalIntensity.x);
		totalIntensity.y = sqrt(totalIntensity.y);
		totalIntensity.z = sqrt(totalIntensity.z);
		
		totalIntensity.x = min(totalIntensity.x, 1.0);
		totalIntensity.y = min(totalIntensity.y, 1.0);
		totalIntensity.z = min(totalIntensity.z, 1.0);
		totalIntensity.w = min(totalIntensity.w, 1.0);
		
		vec4 tex = vec4(1);//= texture(textureSampler, pass_textureCoords);
		
		if(texID != 0) {
			tex = texture(textureSampler, pass_textureCoords);
		}
		
		out_color = vec4(((tex.xyz * totalIntensity.w) * vec3(totalIntensity.xyz)) , tex.w);
	} else {
		if(texID != 0) {
			out_color = texture(textureSampler, pass_textureCoords);
		} else {
			out_color = vec4(1);
		}
	}
}