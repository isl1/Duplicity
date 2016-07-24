#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[16];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;


uniform vec3 lightColor[16];
uniform vec3 attenuation[16];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

void main(void){

	vec4 blendMapColor = texture(blendMap, pass_textureCoords);
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledTextureCoords = pass_textureCoords * 128.0;
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledTextureCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledTextureCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledTextureCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledTextureCoords) * blendMapColor.b;
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitCameraVector = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for(int i = 0; i < 16; i++){
		float distance = length(toLightVector[i]);
		float attenFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal, unitLightVector);
		float brightness = max(nDot1, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitCameraVector);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attenFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i])/attenFactor;
	}
	
	totalDiffuse = max(totalDiffuse, 0.2);
	
	vec4 textureColor = totalColor;
	
	if(textureColor.a < 0.5){
		discard;
	}

	out_Color = vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);

}




