package snake;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Farm extends Pane {
    private static final int NUM = 5;
    public static final double width = 500;
    public static final double height = 500;
    public ArrayList<Circle> food = new ArrayList<Circle>();//设置食物

    public static int getNUM(){
        return NUM;
    }


    public Farm(){
        init();
    }

    public void init(){
        this.setWidth(width);
        this.setHeight(height);
        //创建这个pane的时候在面板上随机生成食物
        for(int i = 0; i < NUM; i++){
            Circle circle = new Circle(5);
            Random random  = new Random();

            circle.setFill(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            circle.setCenterX(random.nextInt(500));
            circle.setCenterY(random.nextInt(500));
            circle.setStroke(Color.BLACK);

            food.add(circle);
            this.getChildren().add(circle);
        }
    }

    //在地图上添加一个食物
    public void add(){
        Circle item = new Circle(5);
        Random random = new Random();
        item.setFill(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        item.setCenterX(random.nextInt(500));
        item.setCenterY(random.nextInt(500));

        food.add(item);
        this.getChildren().add(item);
    }

    //当食物被蛇吃掉的时候,在地图上删除一个食物
    public void remove(int index){
        this.getChildren().remove(food.get(index));
        food.remove(index);
        add();
        //System.out.println("index/size: " + index + "/" + food.size());
        //System.out.println(food.get(index));
    }
}
