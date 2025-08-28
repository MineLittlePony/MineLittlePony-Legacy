#version 130
uniform sampler2D glowTexture;
uniform vec2 resolution;
uniform float blurRadius;

in vec2 texCoord;

void main() {
    vec2 texelSize = 1.0 / resolution;
    vec4 result = vec4(0.0);
    float totalWeight = 0.0;
    
    // Gaussian weights for 9-tap filter (sigma = 1.0)
    float weights[5];
    weights[0] = 0.06136;
    weights[1] = 0.24477;
    weights[2] = 0.38774;
    weights[3] = 0.24477; 
    weights[4] = 0.06136; 
    
    // Horizontal Gaussian blur
    for (int i = -4; i <= 4; i++) {
        vec2 offset = vec2(float(i) * texelSize.x * blurRadius, 0.0);
        vec4 sample = texture2D(glowTexture, texCoord + offset);
        float weight = weights[abs(i)];
        result += sample * weight;
        totalWeight += weight;
    }
    
    // Vertical Gaussian blur
    vec4 finalResult = vec4(0.0);
    totalWeight = 0.0;
    
    for (int i = -4; i <= 4; i++) {
        vec2 offset = vec2(0.0, float(i) * texelSize.y * blurRadius);
        vec4 sample = texture2D(glowTexture, texCoord + offset);
        float weight = weights[abs(i)];
        finalResult += sample * weight;
        totalWeight += weight;
    }
    
    gl_FragColor = finalResult / totalWeight;
}
