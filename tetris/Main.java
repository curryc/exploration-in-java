package tetris;
/*
实现俄罗斯方块小游戏
右边提示下一图案，中间不断下降小方块，如果一排方块排满，这一排方块都下消失
方块类型：“E:\\Program\\Java\\material\\information"，发现所有的图案都是四个小方块的，可以用一个4x2的数组来表示图案
游戏界面是一个棋盘,10x25,但是为了提前暂时我们要操作的图案,我们设置这个面板未15x25,右边来展示即将出现的图案
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    //游戏面板宽度
    private final int width = 20;
    //游戏面板高度
    private final int height = 25;
    //操作面板宽度
    private final int playWidth = 10;
    //游戏面板
    Block[][] blocks = new Block[width][height];
    //记录正在操作的pattern
    Pattern patternToShift = new Pattern();
    //记录将要到来的pattern
    Pattern patternToCome = new Pattern();
    //展示下一个方块的位置
    private final int[] positionToShow = {15, 13};
    //方块刚出来的时候的位置
    private final int[] positionToInit = {5, 0};
    //图案移动的动画
    Duration duration = new Duration(500);
    Timeline timelineForMoving = new Timeline(new KeyFrame(duration, event -> play()));
    //屏幕刷新的动画
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> refresh()));
    //游戏正在进行或者暂停的标志
    private int gameFlag = 0;
    //游戏结束标志
    private boolean dieFlag = false;
    //玩家得分
    private int score = 0;
    //提示信息
    Text notice = new Text();
    //玩家得分显示
    Text scoreShow = new Text();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        BorderPane borderPane = new BorderPane();
        Text scoreNotice = new Text("SCORE:");
        Text nextNotice = new Text("NEXT:");
        Font font  = Font.font("Helvetica Neue", FontPosture.ITALIC, 19);

        //先画出棋盘
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pane.add(blocks[i][j] = new Block(), i, j);
            }
        }

        //设置初始的patternToShift和patternToCome的位置
        patternToShift.setPosition(positionToInit);
        patternToCome.setPosition(positionToShow);

        //初始化界面
        paint();
        notice.setText("Press Space To Play");
        notice.setFont(font);
        scoreNotice.setFont(font);
        nextNotice.setFont(font);
        scoreShow.setFont(font);
        scoreShow.setText(String.valueOf(score));

        //这个动画是用来实现方块的移动的
        timelineForMoving.setCycleCount(Timeline.INDEFINITE);
        //这个动画是用来实现随时更新方块位置和状态的
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //设置scene
        Scene scene = new Scene(borderPane);
        //设置键盘事件,空格暂停,还可以左右移动
        scene.setOnKeyPressed(event -> keyHandler(event.getCode()));
        //设置鼠标事件,点击鼠标可以旋转图案
        scene.setOnMouseClicked(event -> mouseHandler(event.getButton()));

        pane.add(scoreNotice, positionToShow[0] - 3, positionToShow[1] - 7, 3,1);
        pane.add(scoreShow, positionToShow[0] - 3, positionToShow[1] - 6, 3, 1);
        pane.add(nextNotice, positionToShow[0] - 3, positionToShow[1] - 2, 3,1);
        //pane.setGridLinesVisible(true);
        borderPane.setCenter(pane);
        borderPane.setBottom(notice);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TETRIS");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //键盘事件处理器
    private void keyHandler(KeyCode keyCode){
            switch (keyCode){
                case SPACE:
                    if(gameFlag == 1 && dieFlag == false){
                        //游戏正在进行中
                        gameFlag = 0;
                        timelineForMoving.pause();
                        notice.setText("Press Space To CONTINUE");
                    }else if(gameFlag == 0 && dieFlag == false){
                        //游戏正在暂停中
                        gameFlag = 1;
                        timelineForMoving.play();
                        notice.setText("Press Space To PAUSE");
                    }
                    break;
                case A:
                    if(gameFlag == 1 && dieFlag == false){
                        int moveFlag = 1;
                        for(int a[]:patternToShift.getPattern()){
                            if(a[0] == 0 || blocks[a[0] - 1][a[1]].getState() == 2){
                                //此时不能左移
                                moveFlag = 0;
                            }
                        }
                        if (moveFlag == 1 && dieFlag == false) {
                            int[] positionToMoveTo = {patternToShift.getPosition()[0] - 1, patternToShift.getPosition()[1]};
                            patternToShift.setPosition(positionToMoveTo);
                        }
                    }
                    break;
                case D:
                    if(gameFlag == 1 && dieFlag == false){
                        int moveFlag = 1;
                        for(int a[]:patternToShift.getPattern()){
                            if(a[0] == playWidth || blocks[a[0] + 1][a[1]].getState() == 2){
                                //此时不能右移
                                moveFlag = 0;
                            }
                        }
                        if (moveFlag == 1 && dieFlag == false) {
                            int[] positionToMoveTo = {patternToShift.getPosition()[0] + 1, patternToShift.getPosition()[1]};
                            patternToShift.setPosition(positionToMoveTo);
                        }
                    }
                    break;
                case S:
                    //duration = duration.multiply(1.5);
                    timelineForMoving.setDelay(duration.multiply(1.5));
                    break;
                default:
                    break;
            }
    }

    //鼠标事件处理器
    private void mouseHandler(MouseButton button) {
        switch (button) {
            case PRIMARY:
                //实现图案的旋转
                if(gameFlag == 1 && dieFlag == false) {
                    patternToShift.rotate();
                }
                break;
            case SECONDARY:

            default:
                break;
        }
    }

    //移动方块的方法
    public void play(){
        //检查这个pattern是否可以运动,如果可以移动,就移动,如果不可以移动,将每个块都设置为2,检查1,游戏是否结束(如果结束需要给提示信息)2,是否可以消除一行
        int stopflag = 0;  //为0表示不停继续走,为1表示要停下来
        for (int[] a:patternToShift.getPattern()){
            if(a[1] == height - 1){
                stopflag = 1;
                break;
            }
            if(blocks[a[0]][a[1] + 1].getState() == 2){
                stopflag = 1;
                break;
            }
        }
        //根据是否要停止来操作
        switch (stopflag){
            case 1:
                //如果这个图案要停止向下移动,
                //需要进行的操作:设置图案所在方块的状态,将那个正在显示的方块移动到即将下落的位置,生成一个下一个落下的图案,检查是否有得分并操作
                //设置方块的状态
                for(int[] a: patternToShift.getPattern()){
                    blocks[a[0]][a[1]].setState(2);
                }

                //检查是否有得分并操作
                checkAndScore();

                //将显示的方块移动到即将下落的位置
                patternToShift.setValue(patternToCome.getValue());
                patternToShift.init(patternToShift.getValue());
                patternToShift.setPosition(positionToInit);

                //生成一个下一个要落下的图案
                patternToCome.init();
                patternToCome.setPosition(positionToShow);

                //在这里检查游戏是否已经结束
                for(int[] a: patternToShift.getPattern()){
                    if(blocks[a[0]][a[1]].getState() == 2){
                        dieFlag = true;
                        timeline.stop();
                        timelineForMoving.stop();
                        notice.setText("GAME OVER");
                    }
                }


                break;
            case 0:
                //如果这个图案要继续向下移动
                //需要进行的操作:移动这个图案
                int[] positionToMoveTo = {patternToShift.getPosition()[0], patternToShift.getPosition()[1] + 1};
                patternToShift.setPosition(positionToMoveTo);
                break;
            default:
                break;
        }
    }

    //画面随时刷新的方法
    public void refresh(){
        //检查所有的方块state为1的,将其设置为0
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(blocks[i][j].getState() == 1){
                    blocks[i][j].setState(0);
                    blocks[i][j].setColor(Block.initColor);
                }
            }
        }
        //根据两个图案的position进行绘制图案
        for(int[] a:patternToShift.getPattern()){
            blocks[a[0]][a[1]].setState(1);
            blocks[a[0]][a[1]].setColor(patternToShift.getColor());
        }
        for(int[] a: patternToCome.getPattern()){
            blocks[a[0]][a[1]].setState(1);
            blocks[a[0]][a[1]].setColor(patternToCome.getColor());
        }
    }

    //检查是否有得分并根据情况进行操作的方法
    private void checkAndScore(){
        //通过循环检查,如果有得分,需要进行所有这条线方块之上的方块的下落,如果没有,不操作
        for(int j = height - 1;j >= 0; j--){
            int i = 0;
            for(; i < playWidth; i++){
                if(blocks[i][j].getState() != 2){
                    break;
                }
            }
            //根据i的值来判断是否得分
            if(i == playWidth){
                //表示这一排是可以得分的
                this.score += 100;
                scoreShow.setText(String.valueOf(score));
                drop(j);//j为这一排的数组下标
                j ++;//为了使这一排之上的方块落下后,还可以检测到这一排
            }else{
                //表示这一排没有得分
                continue;
            }
        }
    }

    //所有方块下坠的函数
    private void drop(int row){
        //注意这里最上面的一行要重新设置,这个row是要消除的横排的数组下标
        for(int j = row;j > 0;j--){
            for(int i = 0;i < playWidth; i++){
                blocks[i][j].setState(blocks[i][j-1].getState());
                blocks[i][j].setColor(blocks[i][j-1].getColor());
            }
        }
        for(int i = 0;i < playWidth; i++){
            blocks[i][0].setColor(Block.initColor);
            blocks[i][0].setState(0);
        }
    }

    //绘制面板的函数,先设置面板中每个block的state,再根据state进行染色
    public void paint(){
        //改变需要改变方块状态的状态
        for(int[] pos: patternToShift.getPattern()){
            blocks[pos[0]][pos[1]].setState(1);
        }
        for(int[] pos: patternToCome.getPattern()){
            blocks[pos[0]][pos[1]].setState(1);
        }

        //根据状态设置方块颜色
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i >10 && (j<positionToShow[1] - 1 || j>positionToShow[1] + 2)) || i == 11 || i == 19) {
                    //如果这是右边的空闲区域
                    blocks[i][j].setColor(Color.PINK);
                    //continue;
                } else if (i > 10 && blocks[i][j].getState() == 1) {
                    //如果这是右边要展示即将到来的方块的区域
                    blocks[i][j].setColor(patternToCome.getColor());
                } else if(i <= 10 && blocks[i][j].getState() == 1){
                    //如果这是正在操作的图案区域
                    blocks[i][j].setColor(patternToShift.getColor());
                }
            }
        }
    }

}
