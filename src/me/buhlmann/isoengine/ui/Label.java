package me.buhlmann.isoengine.ui;

import me.buhlmann.isoengine.IsoEngine;
import me.buhlmann.isoengine.gamestate.GamestateGame;
import me.buhlmann.isoengine.gfx.shader.Shader;
import me.buhlmann.isoengine.gfx.util.InstancedRenderingVertexArray;
import me.buhlmann.isoengine.ui.text.TrueTypeFont;

public class Label extends GUIObject
{
  private static final Shader textShader = new Shader("text");

  private String text;
  private TrueTypeFont font;

  private InstancedRenderingVertexArray vao;

  @Override
  public void render() {
    textShader.setUniform("pr_matrix", ((GamestateGame) IsoEngine.GAME_MANAGER.getActiveGamestate()).getPlayer().getCamera().project());
    textShader.setUniform("scale", font.getScale());
    textShader.setUniform("window_w", IsoEngine.getWindow().getWidth() / 2.0f);
    textShader.setUniform("window_h", IsoEngine.getWindow().getHeight() / 2.0f);

    textShader.bind();
    font.bind();
    vao.render();
    font.unbind();
    textShader.unbind();
  }

  public void setText(String text)
  {
    this.text = text;
    font.generateMesh(this.text, position.x, position.y, vao);
  }

  public Label(String text, TrueTypeFont font, int x, int y)
  {
    super(x, y);
    this.text = text;
    this.font = font;

    vao = new InstancedRenderingVertexArray();
    // Indices
    vao.addIndices(new int[] {0, 1, 2, 2, 3, 0});
    // Vertex Positions
    vao.addAttribute(0, 2, new float[] {-0.2f, -0.2f, 0.2f, -0.2f, 0.2f, 0.2f, -0.2f, 0.2f});
    // Texture Positions
    vao.addAttribute(1, 2, new float[] {0.0f,  1.0f, 1.0f,  1.0f, 1.0f,  0.0f, 0.0f,  0.0f});
    // Instance World Positions
    vao.addInstancedAttribute(2, Short.MAX_VALUE, 3, 1);
    // Instance Texture Positions
    vao.addInstancedAttribute(3, Short.MAX_VALUE, 2, 1);

    setText(text);
  }
}
