#version 130

uniform sampler2D image;
uniform bool horizontal;

void main() {
    vec2 tex_offset = 1.0 / textureSize(image, 0);
    vec3 result = texture(image, gl_TexCoord[0].xy).rgb * 0.4;

    if (horizontal) {
        result += texture(image, gl_TexCoord[0].xy + vec2(tex_offset.x, 0.0)).rgb * 0.3;
        result += texture(image, gl_TexCoord[0].xy - vec2(tex_offset.x, 0.0)).rgb * 0.3;
    } else {
        result += texture(image, gl_TexCoord[0].xy + vec2(0.0, tex_offset.y)).rgb * 0.3;
        result += texture(image, gl_TexCoord[0].xy - vec2(0.0, tex_offset.y)).rgb * 0.3;
    }

    gl_FragColor = vec4(result, 1.0);
}
