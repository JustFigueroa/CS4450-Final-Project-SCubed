/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.render;

/**
 *
 * @author justinfigueroa
 */
public class Block {
    
    private boolean isActive;
    private BlockType Type;
    private float x;
    private float y;
    private float z;
    
    public enum BlockType{
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        
        private int BlockID;   

        BlockType(int i){
            BlockID = i;
        }

        public int GetID(){
            return BlockID;
        }

        public void SetID (int i){
            BlockID = i;
        }
    }
    public Block(BlockType type){
        Type = type;
    }
    
    public void SetCoords (float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean IsActive(){
      return isActive;  
    }
    
    public void SetActive(boolean active){
        isActive = active;
    }

    public int getID(){
        return Type.GetID();
    }
}
