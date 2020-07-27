package br.com.projectaula.program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public  class Game extends Canvas implements Runnable, KeyListener {
    public Node[] nodeSnake = new Node[5];
    public boolean left, right, up, down;

    public int score = 0;
    public int macaX = new Random().nextInt(240-10), macaY = new Random().nextInt(240-10);
    public int sped = 6;
    public int frameSpd = 5;


    public Game(){
        this.setPreferredSize(new Dimension(240,240));
        for (int i =0; i <nodeSnake.length; i++){
            nodeSnake[i] = new Node(0,0);
        }

        this.addKeyListener(this);
    }
    public void tick() {
        for (int i = nodeSnake.length - 1; i > 0; i--) {
            nodeSnake[i].x = nodeSnake[i - 1].x;
            nodeSnake[i].y = nodeSnake[i - 1].y;
        }
        if (nodeSnake[0].x + 10 < 0) {
            nodeSnake[0].x = 240;

        } else if (nodeSnake[0].x >= 240) {
            nodeSnake[0].x = -10;
        }
        if (nodeSnake[0].y + 10 < 0) {
            nodeSnake[0].y = 240;

        } else if (nodeSnake[0].y >= 240) {
            nodeSnake[0].y = -10;
        }

        if (right){
            nodeSnake[0].x+= sped;
            //collision();

        }else if (up){
            nodeSnake[0].y-=sped;
            //collision();
        }else if (down){
            nodeSnake[0].y+=sped;
            //collision();
        }else if (left){
            nodeSnake[0].x-=sped;
            //collision();
        }

        if(new Rectangle(nodeSnake[0].x,nodeSnake[0].y).intersects(macaX, macaY,10,10)){
            macaX = new Random().nextInt(240 - 10);
            macaY = new Random().nextInt(240 - 10);
            score ++;
            sped ++;
            frameSpd ++;
            System.out.println("Pontos: "+ score);
        }
    }

    //metodo de colisao.
  /*  public void collision(){
        for(int i = 0; i < nodeSnake.length; i++){
            if(i == 0) continue;
            {
                Rectangle box1 = new Rectangle(nodeSnake[0].x, nodeSnake[0].x, 10,10);
                Rectangle box2 = new Rectangle(nodeSnake[i].y, nodeSnake[0].y, 10,10);

                if(box1.intersects(box2)){
                    System.out.println("Game Over!");
                    frameSpd = 20;
                    score = 0;
                    right = false;
                    up = false;
                    down = false;
                    left = false;
                    for (int j =0; j <nodeSnake.length; j++){
                        nodeSnake[j] = new Node(0,0);
                }

            }
        }

    }
    }*/

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,240,240);
        for (int i = 0 ; i < nodeSnake.length; i++){
            g.setColor(Color.blue);
            g.fillRect(nodeSnake[i].x, nodeSnake[i].y,10,10);
        }
g.setColor(Color.red);
        g.fillRect(macaX, macaY, 10,10);
        //nao mexer na tela e ativar a mesma.
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Snake");
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
       //set relative fica embaixo do pack e isso fará que ele fique no centro.

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        new Thread(game).start();
    }
    @Override
    public void run() {
        while (true){
            tick();
            render();
            try {
                Thread.sleep(1000/frameSpd);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT){
            right = true;
            down = false;
            left = false;
            up = false;

        }else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
            right = false;
            up = false;
            down = false;
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_UP){
            up = true;
            right = false;
            left = false;
            down = false;
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            down = true;
            right = false;
            left = false;
            up = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
