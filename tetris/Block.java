package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends Rectangle {
    //方块的大小
    private final int size = 25;
    //方块的值,0代表空白区域,1表示正在操作的图案,0表示已经放好的图案
    private int state = 0;
    //方块的颜色
    private Color color;
    //方块最开始的颜色
    public static Color initColor = Color.WHITE;

    public Block(){
        this.state = 0;
        this.setStyle("-fx-border-color: pink");
        this.setFill(initColor);
        this.setWidth(size);
        this.setHeight(size);
    }

    //获取size的函数
    public int getSize() {
        return size;
    }

    //设置和获取state的函数
    public void setState(int state){
        this.state = state;
    }
    public int getState(){
        return this.state;
    }

    //设置和获取颜色的函数
    public void setColor(Color color){
        this.color = color;
        this.setFill(this.color);
    }
    public Color getColor(){
        return this.color;
    }

}
