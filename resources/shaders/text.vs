#version 330 core

in vec3 position;
in vec2 texture;
in vec3 world_pos;
in vec2 texture_position;

out vec2 fs_texture_coordinates;
out vec2 fs_texture_position;

uniform mat4 pr_matrix;
uniform float window_w;
uniform float window_h;
uniform float scale;

void main()
{
  int atlas_size = 16;
  float worldx;
  float worldy;

  if (world_pos.x > 0)
  {
    worldx = -window_w + world_pos.x;
  }
  else
  {
    worldx = window_w + world_pos.x;
  }

  if (world_pos.y > 0)
  {
    worldy = window_h - world_pos.y;
  }
  else
  {
    worldy = -window_h + world_pos.y;
  }

  mat4 pos_matrix = mat4(
    vec4(scale, 0, 0, 0),
    vec4(0, scale, 0, 0),
    vec4(0, 0, scale, 0),
    vec4(worldx, worldy, world_pos.z, 1)
  );

  fs_texture_coordinates = texture / atlas_size + texture_position;
  fs_texture_position = texture_position;

  gl_Position = pr_matrix * pos_matrix * vec4(position.x, position.y, position.z, 1.0);
}