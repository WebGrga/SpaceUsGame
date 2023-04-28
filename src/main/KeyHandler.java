package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed;
    public boolean pausePressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {

                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum == -1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum == 4) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                        // gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {

                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 2;
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum == -1) {
                        gp.ui.commandNum = 4;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum == 5) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        // red
                        // System.out.println("red");
                        gp.characterIndex = 0;
                        gp.player.getPlayerImage();
                        gp.gameState = gp.playState;

                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        // System.out.println("purple");
                        gp.characterIndex = 1;
                        gp.player.getPlayerImage();
                        gp.gameState = gp.playState;
                        // purple
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 2) {
                        // System.out.println("yellow");
                        gp.characterIndex = 2;
                        gp.player.getPlayerImage();
                        gp.gameState = gp.playState;
                        // yellow
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 3) {
                        // System.out.println("green");
                        gp.characterIndex = 3;
                        gp.player.getPlayerImage();
                        gp.gameState = gp.playState;
                        // green
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 4) {
                        gp.ui.commandNum = 0;
                        gp.ui.titleScreenState = 0;

                    }
                }
            }

        }
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionsState;

            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;

            }
            if (code == KeyEvent.VK_Q) {
                gp.gameState = gp.optionsState;
            }

        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {

                gp.gameState = gp.playState;
            }
        } else if (gp.gameState == gp.dialogueState) {

            if (code == KeyEvent.VK_W) {
                gp.ui.voteNum--;
                if (gp.ui.voteNum == -1) {
                    gp.ui.voteNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.voteNum++;
                if (gp.ui.voteNum == 4) {
                    gp.ui.voteNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.voteNum == 0) {
                    // red
                    // System.out.println("red");
                    gp.gameState = gp.playState;
                }
                if (gp.ui.voteNum == 1) {
                    // System.out.println("purple");
                    gp.gameState = gp.playState;
                    // purple

                }
                if (gp.ui.voteNum == 2) {
                    // System.out.println("yellow");
                    gp.gameState = gp.playState;
                    // yellow

                }
                if (gp.ui.voteNum == 3) {
                    // System.out.println("green");
                    gp.gameState = gp.playState;
                    // green

                }

            }
        } else if (gp.gameState == gp.optionsState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;

            }
            int maxCommandNum = 0;
            switch (gp.ui.subState) {
                case 0:
                    maxCommandNum = 4;
                    break;
                case 1:

                    break;

            }
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = maxCommandNum;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > maxCommandNum) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_A) {
                if (gp.ui.subState == 0) {
                    if (gp.ui.commandNum == 1 && gp.sound.volumeScale > 0) {
                        gp.sound.volumeScale--;
                        gp.sound.checkVolume();

                    }
                }
            }
            if (code == KeyEvent.VK_D) {
                if (gp.ui.subState == 0) {
                    if (gp.ui.commandNum == 1 && gp.sound.volumeScale < 5) {
                        gp.sound.volumeScale++;
                        gp.sound.checkVolume();

                    }
                }
            }
        }

        if (code == KeyEvent.VK_ESCAPE) {

            gp.gameState = gp.playState;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

}
