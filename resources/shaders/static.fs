#version 330 core

flat in int instanceID;
in vec2 texCoordsVarying;

uniform float mouse_position;
uniform sampler2D tex;

void main()
{
    if(instanceID == mouse_position)
    {
      gl_FragColor = texture(tex, texCoordsVarying) + vec4(0.2, 0.2, 0.2, 0.5);
    }
    else
    {
      gl_FragColor = texture2D(tex, texCoordsVarying);
    }
}