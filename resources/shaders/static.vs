#version 330 core

in vec3 position;
in vec2 texCoords;
in vec3 world_pos;
in vec2 texture_position;        // position of the texture on the texture atlas

out vec2 fs_texture_coordinates;
out vec2 fs_texture_position;
flat out int instanceID;

uniform mat4 pr_matrix;
uniform mat4 cam_pos;
uniform float scale;
uniform float atlas_size;

void main() {

  mat4 pos_matrix = mat4(
    vec4(scale, 0, 0, 0),          // vec4(scale, 0, 0, 0)
    vec4(0, scale, 0, 0),          // vec4(0, scale, 0, 0)
    vec4(0, 0, scale, 0),          // vec4(0, 0, scale, 0)
    vec4(world_pos.x * scale, world_pos.y * scale, world_pos.z, 1)             // vec4(x, y, (z) = 0, 1)
  );

  fs_texture_coordinates = texCoords / atlas_size + texture_position;
  fs_texture_position = texture_position;

  instanceID = gl_InstanceID;

  gl_Position = pr_matrix * pos_matrix * cam_pos * vec4(position, 1.0);
}