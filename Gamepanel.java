package erstellen.My2DGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    // Skalierung
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenColumn; // 48 x 16 = 768
    final int screenHeight = tileSize * maxScreenRow;   // 48 x 12 = 576

    KeyHandler keyH = new KeyHandler();
    // Macht, dass das Spiel durchgehend läuft
    Thread gameThread;

    // Set player's position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // FPS Anzahl
    int FPS = 60;

    public Gamepanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    // // // // Hiermit lagt es immer wenn ich die Richtung wechsel auf meinem Laptop
    // public void run() {

    //     double drawInterval = 1_000_000_000 / FPS;
    //     double nextDrawTime = System.nanoTime() + drawInterval;
        
    //     while (gameThread != null) {
    //         // neues Bild erstellen
    //         update();
    //         // Erneutes zeichnen des Bildes
    //         repaint();

    //         try {

    //             double remainingTime = nextDrawTime - System.nanoTime();
    //             remainingTime = remainingTime / 1_000_000;

    //             if (remainingTime < 0) {
    //                 remainingTime = 0;
    //             }

    //             Thread.sleep((long) remainingTime);

    //             nextDrawTime += drawInterval;

    //         } catch (InterruptedException ex) {
    //             ex.printStackTrace();
    //         }
    //     }
    // }

    // // // // Eine andere Methode für die Bewegungen
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            // FPS im Terminal
            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        }
        
        if (keyH.downPressed == true) {
            playerY += playerSpeed;
        } 
        
        if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        }
        
        if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
