/*****************************************************************************
 *   File: Block.java
 *   Author: Justin Figeuroa
 *   Contributors: Keanu Arao
 *   Class: CS 4450 - Computer Graphics
 *
 *   Assignment: Final Program 
 *   Date last modified: 11/5/2026
 *
 *   Purpose: This class represents an individual voxel block in 3D space.
 *            Maintains the block's active rendering state, type, coordinates,
 *            and provides access to its texture atlas ID
 **************************************************************************** */
package Main.render;

public class Block {

    // Instance Variables
    private boolean isActive;
    private BlockType Type;
    private float x;
    private float y;
    private float z;
    
    // Public enum Type for block Types
    public enum BlockType{
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        
        private int BlockID;   

        // Method: BlockType
        // Purpose: Constructor for the BlockType enum that assigns the numerical texture/type ID
        BlockType(int i){
            BlockID = i;
        }

        // Public method: getID
        // Purpose: returns the ID
        public int getID(){
            return BlockID;
        }

        // setID
        // Purpose: sets the ID
        public void setID (int i){
            BlockID = i;
        }
    }
    
    // Public Method: Block
    // Purpose: Constructor that initializing the block with specified type,
    //          sets its active rendering state so true, and intialize coordinates
    public Block(BlockType type){
        Type = type;
        
    }
    
    // Public Method: setCoords
    // Purpose: sets the 3D coordinates for the block
    public void setCoords (float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Public Method: isActive
    // Purpose: checks if the block is active
    public boolean isActive(){
      return isActive;  
    }
    
    // Public Method: setActive
    // Purpose: sets the blocks active status
    public void setActive(boolean active){
        isActive = active;
    }

    // Public Method: getID
    // Purpose: gets the block's ID
    public int getID(){
        return Type.getID();
    }
}
