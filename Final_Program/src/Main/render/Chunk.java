/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.render;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

//Added to enable texture 
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {
    
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int VBOTextureHandle;
    
    private Texture texture;
    
    private int vertexCount;
    
    private int StartX;
    private int StartY;
    private int StartZ;
    
    public void render(){
        glPushMatrix();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3,GL_FLOAT,0,0L);
        glBindBuffer(GL_ARRAY_BUFFER,VBOColorHandle);
        glColorPointer(3,GL_FLOAT,0,0L);
        
        // Added for texture mapping
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        
        glDrawArrays(GL_QUADS, 0, vertexCount);
        glPopMatrix();
    }
    
    public void rebuildMesh(float startX, float startY, float startZ){
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        
        FloatBuffer VertexPositionData = 
                BufferUtils.createFloatBuffer(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 12);
        FloatBuffer VertexColorData = 
                BufferUtils.createFloatBuffer(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 12);
        FloatBuffer VertexTextureData =
                BufferUtils.createFloatBuffer(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 12);
        
        vertexCount = 0;

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                for (int y = 0; y < CHUNK_SIZE; y++) {

                    Block block = Blocks[x][y][z];

                    // null represents air, so don't create a cube.
                    if (block == null) {
                        continue;
                    }

                    VertexPositionData.put(createCube(
                            startX + x * CUBE_LENGTH,
                            startY + y * CUBE_LENGTH + (int) (CHUNK_SIZE * 0.8),
                            startZ + z * CUBE_LENGTH
                    ));

                    VertexColorData.put(
                            createCubeVertexCol(getCubeColor(block))
                    );

                    VertexTextureData.put(
                            createTexCube(0, 0, block)
                    );

                    // Six faces with four vertices each.
                    vertexCount += 24;
                }
            }
        }
        
        
        VertexPositionData.flip();
        VertexColorData.flip();
        VertexTextureData.flip();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        // Bind texture buffers
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    private float[] createCubeVertexCol(float[] CubeColorArray){
        float[] cubeColors = new float [CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++){
            cubeColors[i] = CubeColorArray[i % CubeColorArray.length];
        }
        return cubeColors;
    }
    
    public static float[] createCube(float x, float y, float z){
        int offset = CUBE_LENGTH / 2;
        return new float[]{
            
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,

            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,

            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,

            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,

            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,

            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z
        };
    } 
    
    private float[] getCubeColor(Block block){
        return new float[] {1, 1, 1};
    }
    
    private void generateTerrain(int startX, int startZ) {
        // Arguments: largest feature, persistence, seed.
        int seed = 12345;
        SimplexNoise noise = new SimplexNoise(32, 0.5, seed);

        int baseHeight = 15;
        int heightVariation = 12;

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {

                // Convert the chunk's start position to block coordinates.
                int worldX = startX / CUBE_LENGTH + x;
                int worldZ = startZ / CUBE_LENGTH + z;

                double noiseValue = noise.getNoise(worldX, worldZ);

                int surfaceHeight = baseHeight
                        + (int) Math.round(noiseValue * heightVariation);

                // Keep the surface within this chunk.
                surfaceHeight = Math.max(
                        1,
                        Math.min(surfaceHeight, CHUNK_SIZE - 1)
                );

                for (int y = 0; y < CHUNK_SIZE; y++) {
                    if (y > surfaceHeight) {
                        Blocks[x][y][z] = null; // Air
                    } else if (y == 0) {
                        Blocks[x][y][z] =
                                new Block(Block.BlockType.BlockType_Bedrock);
                    } else if (y == surfaceHeight) {
                        Blocks[x][y][z] =
                                new Block(Block.BlockType.BlockType_Grass);
                    } else if (y >= surfaceHeight - 3) {
                        Blocks[x][y][z] =
                                new Block(Block.BlockType.BlockType_Dirt);
                    } else {
                        Blocks[x][y][z] =
                                new Block(Block.BlockType.BlockType_Stone);
                    }
                }
            }
        }
    }
    
    public Chunk(int startX, int startY, int startZ){
        try{
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("lib/terrain.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        generateTerrain(startX, startZ);
            
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;

        rebuildMesh(startX, startY, startZ);
    }
    
    public static float[] createTexCube(float x, float y, Block block){
        float offset = (1024/16)/1024f;
        
        switch (block.getID()){
            case 0: // Grass texture
                return new float[]{
                    // Bottom Quad (DOWN =+ y)
                    x + offset * 3, y + offset * 10,
                    x + offset * 2, y + offset * 10, 
                    x + offset * 2, y + offset * 9,
                    x + offset * 3, y + offset * 9,
                    // Top
                    x + offset * 3, y + offset * 1, 
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 0, 
                    x + offset * 3, y + offset * 0,
                    // Front Quad
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1, 
                    // Back Quad
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1, 
                    x + offset * 3, y + offset * 0, 
                    x + offset * 4, y + offset * 0,
                    // Left Quad
                    x + offset * 3, y + offset * 0,
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1,
                    // Right Quad
                    x + offset * 3, y + offset * 0,
                    x + offset * 4, y + offset * 0, 
                    x + offset * 4, y + offset * 1, 
                    x + offset * 3, y + offset * 1
                };
            case 1: // Sand texture
                return new float[] {
                    // Bottom 
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12,
                    // Top
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12, 
                    // Front
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12,
                    // Back
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12,
                    // Left
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12,
                    // Left 
                    x + offset * 0, y + offset * 11,
                    x + offset * 1, y + offset * 11,
                    x + offset * 1, y + offset * 12,
                    x + offset * 0, y + offset * 12,        
                };
            case 2: // water
                return new float[] {
                    // Top
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,
                    // Bottom
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,
                    // Front
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,
                    // Back
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,
                    // Left
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,
                    // Right
                    x + offset * 0, y + offset * 9,
                    x + offset * 1, y + offset * 9,
                    x + offset * 1, y + offset * 10,
                    x + offset * 0, y + offset * 10,    
                }; 
            case 3: // Dirt Texture
                return new float[] {
                    // Bottom
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                    // Top
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                    // Front
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                    // Back
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                    // Left
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                    // Right
                    x + offset * 2, y + offset * 0,
                    x + offset * 3, y + offset * 0, 
                    x + offset * 3, y + offset * 1,
                    x + offset * 2, y + offset * 1,
                };
            case 4: // Stone
                return new float[] {
                    // Top
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1,

                    // Bottom
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1,

                    // Front
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1,

                    // Back
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1,

                    // Left
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1,

                    // Right
                    x + offset * 1, y + offset * 0,
                    x + offset * 2, y + offset * 0,
                    x + offset * 2, y + offset * 1,
                    x + offset * 1, y + offset * 1
                };
            default: // Bedrock
                return new float[]{
                    // Bottom Quad (DOWN =+ y)
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2,
                    // Top
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2,
                    // Front Quad
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2,
                    // Back Quad
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2,
                    // Left Quad
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2,
                    // Right Quad
                    x + offset * 1, y + offset * 1,
                    x + offset * 2, y + offset * 1, 
                    x + offset * 2, y + offset * 2,
                    x + offset * 1, y + offset * 2
                };                
        }
        
    }
}
