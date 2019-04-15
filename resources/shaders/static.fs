#version 330 core

flat in int instanceID;
in vec2 fs_texture_coordinates;
in vec2 fs_texture_position;

uniform float mouse_position;
uniform sampler2D tex;

void main()
{
    if(instanceID == mouse_position)
    {
      vec4 tex0;    // tile texture
      vec4 tex1;    // selection mask

      tex0 = texture2D(tex, fs_texture_coordinates);
      tex1 = texture2D(tex, vec2(fs_texture_coordinates.x, fs_texture_coordinates.y + (15 - (fs_texture_position.y * 16)) * 0.0625), 1.0);

      gl_FragColor = mix(tex0, tex1, tex1.a);
    }
    else
    {
      gl_FragColor = texture2D(tex, fs_texture_coordinates);
    }
}