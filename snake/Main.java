package snake;
/*
实现贪食蛇小游戏
这里是主函数,实现基本的面板展示功能,读取事件放在scene上,传到snake中的一个事件处理器用来控制蛇的动作
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application{
    public static Farm farm = new Farm();
    public static Snake snake = new Snake();
    public static Text text = new Text("enter space to start");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        BorderPane borderPane = new BorderPane();


        text.setFill(Color.RED);
        text.setFont(Font.font(null, FontWeight.NORMAL, FontPosture.ITALIC, 20));

        pane.getChildren().addAll(farm, snake);
        borderPane.setCenter(pane);
        borderPane.setBottom(text);
        borderPane.getCenter().setStyle("-fx-border-color:black");


        Scene scene = new Scene(borderPane, 500, 500);
        scene.setOnKeyPressed(event -> {
            //传递参数来控制蛇的运动状态
            if(!snake.isDie()) {
                snake.handler(event.getCode());
            }
            else{
                if(event.getCode() == KeyCode.SPACE){
                    snake.init();
                    farm.init();
                }
                return;
            }
            //修改面板的显示信息
            switch (event.getCode()){
                case UP:{
                    break;
                }
                case LEFT:{
                    break;
                }
                case DOWN:{
                    break;
                }
                case RIGHT:{
                    break;
                }
                case SPACE:{
                    if(snake.getGameFlag() == true) {
                        //现在是游戏继续状态
                        text.setText("press space to pause");
                    }
                    else {
                        //现在是暂停状态
                        text.setText("press space to start");
                    }
                    break;
                }
                default:{

                }
            }


        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("SNAKE");
        primaryStage.show();
    }
}
