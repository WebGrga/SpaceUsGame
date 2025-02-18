package main;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_key;

public class UI extends JFrame implements ActionListener {
    GamePanel gp;
    Graphics2D g2;
    Font arial;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    public String currentDialogue = "";
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public JTextField textField;
    public JTextArea textArea;
    public int commandNum = 0;
    public int voteNum = 0;
    public int titleScreenState = 0;
    public int subState = 0;
    BufferedImage heart_live, heart_ghost;
    public boolean isAccepting = true;
    public String textS = "Server Started...";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial = new Font("Arial", Font.PLAIN, 40);

        Entity heart = new OBJ_Heart(gp);
        heart_live = heart.image;
        heart_ghost = heart.image2;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial);
        g2.setColor(Color.WHITE);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        if (gp.gameState == gp.pauseState) {
            // PAUSE STATE
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.dialogueState) {
            // VOTING STATE
            drawPlayerLife();
            drawDialogueScreen();

        }
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }

    // game over
    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));
        text = "You've been killed";
        // shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        // main text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);
        g2.setFont(g2.getFont().deriveFont(50f));
    }

    // options
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                break;
            case 2:
                options_control(frameX, frameY);
                break;
        }

        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                if (gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn == true) {
                    gp.fullScreenOn = false;
                }
            }
        }
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
        }
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
        }

        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }
        // music
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;

        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Interact", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Report", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Exit Task", textX, textY);

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        textY += gp.tileSize;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("R", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Q", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("BACK", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
            }
        }
    }

    // lives
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        if (gp.player.Life == 1) {
            g2.drawImage(heart_live, x, y, null);
        } else if (gp.player.Life == gp.player.Death) {
            g2.drawImage(heart_ghost, x, y, null);
        }
    }

    // title screens
    public void drawTitleScreen() {
        // Load the background image
        if (titleScreenState == 0) {
            BufferedImage backgroundImage = null;
            try {
                backgroundImage = ImageIO.read(getClass().getResourceAsStream("dec.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Draw the background image
            g2.drawImage(backgroundImage, 0, 0, null);

            // Draw the title text
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 66F));
            String text = "Galactic Deception";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            y += gp.tileSize * 4;
            text = "NEW GAME";
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            y += gp.tileSize;
            text = "LOAD";
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            y += gp.tileSize;
            text = "QUIT";
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            y += gp.tileSize;
            text = "Start Server";
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        if (titleScreenState == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42f));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Red";
            y += gp.tileSize * 3;
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Purple";
            y += gp.tileSize;
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Yellow";
            y += gp.tileSize;
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Green";
            y += gp.tileSize;
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            y += gp.tileSize * 2;
            x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == 4) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

    }

    public void drawPauseScreen() {

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void drawDialogueScreen() {
        // Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 10;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22));
        x += gp.tileSize;
        y += gp.tileSize;

        // for (String line : currentDialogue.split("\n")) {
        // g2.drawString(line, x, y);
        // y += 40;
        // }
        String text = "Select the impostor";
        int xvote = getXforCenteredText(text);
        int yvote = gp.tileSize * 3;
        g2.drawString(text, x, y);

        text = "Red";
        yvote += gp.tileSize * 2;
        xvote = getXforCenteredText(text);
        g2.drawString(text, xvote, yvote);
        if (voteNum == 0) {
            g2.drawString(">", xvote - gp.tileSize, yvote);
        }

        text = "Purple";
        yvote += gp.tileSize;
        xvote = getXforCenteredText(text);
        g2.drawString(text, xvote, yvote);
        if (voteNum == 1) {
            g2.drawString(">", xvote - gp.tileSize, yvote);
        }

        text = "Yellow";
        yvote += gp.tileSize;
        xvote = getXforCenteredText(text);
        g2.drawString(text, xvote, yvote);
        if (voteNum == 2) {
            g2.drawString(">", xvote - gp.tileSize, yvote);
        }

        text = "Green";
        yvote += gp.tileSize;
        xvote = getXforCenteredText(text);
        g2.drawString(text, xvote, yvote);
        if (voteNum == 3) {
            g2.drawString(">", xvote - gp.tileSize, yvote);
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
