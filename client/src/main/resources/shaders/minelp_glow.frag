#version 130
uniform vec4 glowColor;
uniform sampler2D texture;

in vec4 vertColor;
in vec2 texCoord;

void main() {
    vec4 texColor = texture2D(texture, texCoord);

    if (texColor.a > 0.1) {
        float glowIntensity = texColor.a * 0.9;
        gl_FragColor = vec4(glowColor.rgb, glowIntensity);
    } else {
        discard; // Don't render transparent pixels
    }
}
