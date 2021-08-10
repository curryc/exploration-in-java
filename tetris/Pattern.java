package tetris;
/*
图案没有绘制，其实是一个二维数组，用来表现图案中的block的布局即可
有七种图案所有的图案都是四个小方块的，可以用一个4x2的数组来表示图案
information: E:\\Program\\Java\\material\\information\\俄罗斯方块方块类型
 */
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.Random;

public class Pattern extends Pane {
    //表示图案的二维数组，表示一个pattern的block布局
    private int[][] pattern = new int[4][2];
    //一个随机类，用来随机生成一个图案
    private static Random random = new Random();
    //记录是哪种图案
    private int value = 0;
    //记录图案颜色
    private Color color = new Color(0,0,0,0);
    //记录图案的位置
    private int[] position = {5,0};

    //构造器
    public Pattern(){
        init();
    }

    //init有两个作用:1,用来第一次创建对象的时候进行对象的初始化  2,用来每次需要重新初始化这个图案
    public void init(){
        //在这里确定这个图案的各个属性
        value = random.nextInt(6);

        //不同的值意味着这个图案为information上的第几个图案
        paint();
    }

    //如果我们已经确定这个图案的value
    public void init(int value){
        this.value = value;
        paint();
    }

    //图案移动的方法, 只需要写一个设置位置的方法既可以表示所有像素点的位置,改变了position就改变了pattern
    public void setPosition(int[] position) {
        for(int i = 1; i < 4; i++) {
            pattern[i][0] = pattern[i][0] - pattern[0][0] + position[0];
            pattern[i][1] = pattern[i][1] - pattern[0][1] + position[1];
        }
        pattern[0][0] = position[0];
        pattern[0][1] = position[1];
        this.position = position;
    }

    //获取图案的颜色
    public Color getColor(){
        return this.color;
    }

    //获取图案的的位置
    public int[] getPosition(){
        return this.position;
    }

    //获取图案的每个方块
    public int[][] getPattern() {
        return this.pattern;
    }

    //设置和获得value
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    //实现图案的旋转
    public void rotate(){
        if(Math.abs(this.pattern[1][0] - position[0]) <= position[1] &&
                Math.abs(this.pattern[2][0] - position[0]) <= position[1] &&
                Math.abs(this.pattern[3][0] - position[0]) <= position[1]){
            for(int i = 1;i<4;i++) {
                int tmp = pattern[i][0];
                this.pattern[i][0] = position[0] + position[1] - pattern[i][1];
                this.pattern[i][1] = position[1] - position[0] + tmp;
            }
        }
    }

    private void paint(){
        if(value == 0) {
            this.color = Color.RED;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0] + 1;pattern[1][1] = position[1];
            pattern[2][0] = position[0];pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] + 1;pattern[3][1] = position[1] + 1;

        }else if(value == 1) {
            this.color = Color.DARKRED;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0];pattern[1][1] = position[1] + 1;
            pattern[2][0] = position[0] - 1;pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] - 2;pattern[3][1] = position[1] + 1;

        }else if(value == 2) {
            this.color = Color.BROWN;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0] + 1;pattern[1][1] = position[1];
            pattern[2][0] = position[0] + 1;pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] + 2;pattern[3][1] = position[1] + 1;

        }else if(value == 3) {
            this.color = Color.GREEN;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0] + 1;pattern[1][1] = position[1];
            pattern[2][0] = position[0];pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] - 1;pattern[3][1] = position[1] + 1;

        }else if(value == 4) {
            this.color = Color.BLUE;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0];pattern[1][1] = position[1] + 1;
            pattern[2][0] = position[0] + 1;pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] + 2;pattern[3][1] = position[1] + 1;

        }else if(value == 5) {
            this.color = Color.GRAY;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0] - 1;pattern[1][1] = position[1] + 1;
            pattern[2][0] = position[0];pattern[2][1] = position[1] + 1;
            pattern[3][0] = position[0] + 1;pattern[3][1] = position[1] + 1;

        }else if(value == 6) {
            this.color = Color.PURPLE;
            pattern[0][0] = position[0];pattern[0][1] = position[1];
            pattern[1][0] = position[0] + 1;pattern[1][1] = position[1];
            pattern[2][0] = position[0] + 2;pattern[2][1] = position[1];
            pattern[3][0] = position[0] + 3;pattern[3][1] = position[1];
        }
    }
}
