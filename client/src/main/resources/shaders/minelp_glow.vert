#version 140

out vec4 vertColor;
out vec2 texCoord;

void main() {
    vertColor = gl_Color;
    texCoord = gl_MultiTexCoord0.xy;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
