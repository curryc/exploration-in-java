package snake;
/*
身体的每个结点在速度规定的时间移动到它的前一个结点的位置
方向表示头部的在下一个关键帧移动的方向，这样结点都向前移动，不需考虑身体其他部分的移动
简单考虑为贪食蛇的速度大小并不会改变
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.LinkedList;

public class Snake extends Pane {
    //游戏开始暂停的标志
    private boolean gameFlag = false;
    //长度为身体的长度不包括头部
    private int length = 0;
    //头部的半径和身体的半径
    private int headRadius = 12;
    private int bodyRadius = 10;
    //头部的运动方向
    private int[] direction = new int[2];
    //头部很身体的创建
    private Circle head = new Circle(headRadius);
    private LinkedList<Circle> body = new LinkedList<Circle>();
    //头部的位置
    private double headCenterX = 1;
    private double headCenterY = 1;
    //动画的创建
    private Timeline timeline = new Timeline(new KeyFrame(Duration.millis(60), event -> move()));

    public Snake(){
        init();
    }

    public void init(){
        //在这里进行贪食蛇的初始化
        this.gameFlag = false;
        this.length = 4;
        direction[0] = 10;
        direction[1] = 0;
        headCenterX = Farm.width / 2;
        headCenterY = Farm.height / 2;
        timeline.setCycleCount(Timeline.INDEFINITE);

        //绘制画面
        paint();
    }

    //绘制贪食蛇的方法
    private void paint(){
        //头部的设置和加入
        head.setFill(Color.PINK);
        head.setStroke(Color.BLACK);
        head.setCenterX(headCenterX);
        head.setCenterY(headCenterY);
        this.getChildren().add(head);

        //身体的设置和加入
        for(int i = 1; i<= length; i++){
            Circle circle = new Circle(10);
            circle.setFill(Color.PINK);
            circle.setStroke(Color.BLACK);
            circle.setCenterX(headCenterX - i*10);
            circle.setCenterY(headCenterY);

            body.add(circle);
            this.getChildren().add(circle);
        }
    }

    //贪食蛇移动的方法
    private void move(){
        //因为我们需要获取每个身体部分的前面的身体部分的位置,以便这个身体部分可以在下一个关键帧移动到这个位置,所以我们需要从后往前操作身体部分
        //先移动身体
        for(int i = body.size() - 1; i >= 0 ; i--){
            //如果这是紧跟头部的身体部分,它需要获取头部的位置来移动,否则直接获取身体的前一个部分的位置就行
            if(i==0){
                body.get(i).setCenterX(head.getCenterX());
                body.get(i).setCenterY(head.getCenterY());
            }else{
                //System.out.println(body.get(i));
                body.get(i).setCenterX(body.get(i - 1).getCenterX());
                body.get(i).setCenterY(body.get(i - 1).getCenterY());
            }
        }
        //再移动头部
        head.setCenterX(head.getCenterX() + direction[0]);
        head.setCenterY(head.getCenterY() + direction[1]);
        //System.out.println("move successfully");

        //判断各种情况，移动过后可能成长，可能死亡
        if(gameFlag) {
            isDie();
            isGrow();
        }
    }

    //判断贪食蛇是否死亡,当头触碰到范围边界的时候死亡
    public boolean isDie(){
        if(head.getCenterX() < headRadius || head.getCenterX() > Main.farm.getWidth() - headRadius ||
                head.getCenterY() < headRadius || head.getCenterY() > Main.farm.getHeight() - headRadius){
            timeline.stop();
            Main.text.setText("you die!");
            gameFlag = false;
            return true;
        }
        return false;
    }

    private boolean isGrow(){
        boolean growFlag = false;
        //System.out.println("size/NUM: " + Main.farm.food.size() + "/" + Farm.getNUM());
        for(int i = 0; i < Farm.getNUM(); i++){
            Circle circle = Main.farm.food.get(i);
            if(Math.pow(circle.getCenterX() - head.getCenterX(), 2) + Math.pow(circle.getCenterY() - head.getCenterY(), 2)
                    <= Math.pow(headRadius + circle.getRadius(), 2)){
                grow();
                Main.farm.remove(i);
                growFlag = true;
            }
        }
        return growFlag;
    }

    //贪食蛇逐渐变长的方法,当贪食蛇吃到食物的时候会变长
    private void grow(){
        length ++;
        Circle circle = new Circle(10);
        circle.setFill(Color.PINK);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(body.getLast().getCenterX() + bodyRadius);
        circle.setCenterY(body.getLast().getCenterY());

        body.add(circle);
        this.getChildren().add(circle);
    }

    //事件处理器,使用键盘来控制direction
    public void handler(KeyCode keyCode){
        //空格键暂停或者开始，四个字母键进行方向控制
        switch (keyCode){
            case UP:{
                direction[0] = 0;
                direction[1] = -10;
                break;
            }
            case LEFT:{
                direction[0] = -10;
                direction[1] = 0;
                break;
            }
            case DOWN:{
                direction[0] = 0;
                direction[1] = 10;
                break;
            }
            case RIGHT:{
                direction[0] = 10;
                direction[1] = 0;
                break;
            }
            case SPACE:{
                if(gameFlag == true) {
                    gameFlag = false;
                    timeline.pause();
                }
                else if(gameFlag == false){
                    gameFlag = true;
                    timeline.play();
                }
                break;
            }
            default:{

            }
        }
        return;
    }

    //获取游戏的开始和暂停状态
    public boolean getGameFlag(){
        return this.gameFlag;
    }
}
