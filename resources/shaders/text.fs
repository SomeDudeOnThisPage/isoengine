#version 330 core

in vec2 fs_texture_coordinates;
in vec2 fs_texture_position;

uniform sampler2D tex;

void main()
{
  vec4 character = texture2D(tex, fs_texture_coordinates);
  vec4 color = vec4(1.0, 1.0, 1.0, 1.0);
  gl_FragColor = mix(character, color, character.a);
}