import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Floppy_Bird extends PApplet {

PImage bird;

float x = 0;
float y = 0;
float vel = 0;
float acc = 0.25f;
boolean intro = true;
boolean ded = false;
int menu = 0;
int hit = 255;
float gapX = 0;
float gapSize = width;
float gapY = 1000;
float flip = 0;
float tilt = 0;
float menuRed = random(255);
float menuGreen = random(255);
float menuBlue = random(255);


public void setup() {
  
  bird = loadImage("Bird.png");
  noStroke();
};

public void draw() {
  if (menu == -1) {
    if (!ded) {
      rectMode(CORNER);
      background(0xff40D7FF);


      fill(0, 255, 0);
      rect(0, gapY, width, 50);

      fill(0xff40D7FF);
      rect(gapX, gapY, gapSize, 50);

      fill(100);
      rect(width / 2, 0, width/2, height - y + 400);

      if (intro) {
        if (frameCount % 30 == 0) {
          vel = -3.75f;
        }
        x ++;

        if (x > width/2 - 50) {
          intro = false;
        }

        image(bird, x, y);
      } else {
        fill(255, 0, 0, hit);
        rect (0, 0, width, height);
        if (hit > 0) {
          hit -= 15;
        }

        if (y > 100) {
          fill(0);
          textSize(20);
          text(str(round((y-100)/100)), 10, 50);

          pushMatrix();
          rectMode(CENTER);
          translate(x + sin(frameCount/7.5f), 100 + 5*sin(frameCount/ 10));
          rotate(tilt + PI/2 + sin(frameCount)/100);

          if (!ded) {
            image (bird, 0, 0);
            gapY -= 2.5f;
          }

          if (gapY == 100) {
            if (x > gapX && x < gapX + gapSize) {
            } else {
              ded = true;
            }
          };


          popMatrix();

          rectMode(CORNER);

          if (gapY < -400) {
            gapY = height;
            gapX = random(50, width-10);
            gapSize = random(50, 100);
          };
        } else {
          pushMatrix();
          rectMode(CENTER);
          translate(x, y);
          rotate(flip);


          image(bird, 0, 0);
          if (flip < PI/2) {
            flip += 0.1f;
          } 

          popMatrix();
        }
        rectMode(CORNER);
      };

      if (!ded) {
        y += vel;
        vel += acc;
        if (vel > 20) {
          vel = 20;
        }
      }

      if (!keyPressed) {
        tilt -= tilt/2;
      };

      if (x < 50) {
        x = 50;
      };
      if (x>width - 50) {
        x = width - 50;
      };
    }

    if (ded) {
      fill(255, 0, 0, hit);
      rectMode(CORNER);
      rect(0, 0, width, height);

      hit ++;

      if (hit > 100) {
        textSize(50);
        fill(255);
        rectMode(CENTER);
        text("SCORE: " + str(round((y-100)/100)), width/2 - 200, height/2);
        text("CLICK TO RESTART", width/2 - 200, height/2- 100);
      };
    };
  } else if (menu == 0) {
    background(menuRed, menuGreen, menuBlue);
    menuRed += 5*sin(frameCount / 10);
    menuGreen += 5*sin(frameCount);
    menuBlue += 5*sin(frameCount * 10);


    fill(255);
    rectMode(CENTER);
    rect(width/2, height/2-100, 300, 100);

    rect(width/2, height/2 + 25, 200, 75);
    rect(width/2, height/2 + 125, 200, 75);

    fill(0);
    textSize(40);

    text("PLAY", 210, 250);
    text("CREDITS", 210, 350);

    text("Fl  " + "ppy Bird", 175, 125 );

    fill(0, 0, 0, 255 * (sin(frameCount / 10) + 1));
    text("a", 210, 125);

    fill(0, 0, 0, 255 - (255 * (sin(frameCount / 10) + 1)));
    text("o", 210, 125);
  } else if (menu == 1) {
    background(255);
    fill(0);

    textSize(30);
    text("CREDITS (Click to go back)", 175, 25);

    textSize(15);
    text("Flappy Bird Picture: https://www.pngkey.com/pngs/flappy-bird/", 10, 125);
    text("Literally Everything Else: Henry Ty", 175, 225);
    text("Why did I spend so much time on this", 175, 325);
  };
};

public void keyPressed() {
  if (!intro) {
    if (keyCode == LEFT) {
      x -= 15; 
      tilt = PI/8;
    }
    if (keyCode == RIGHT) {
      x += 15;  
      tilt = -PI/8;
    }
  }
};

public void mouseClicked() {
  if (ded) {
    x = 0;
    y = 0;
    vel = 0;
    acc = 0.25f;
    intro = true;
    ded = false;
    menu = 0;
    hit = 255;
    gapX = 0;
    gapSize = width;
    gapY = 1000;
    flip = 0;
    tilt = 0;

    rectMode(CORNER);
  }

  if (menu == 1) {
    menu = 0;
  }; 
  if (menu == 0) {
    if (190 < mouseY && mouseY < 260) {
      menu = -1;
    };
    if (290 < mouseY && mouseY < 360) {
      menu = 1;
    };
  };
};
  public void settings() {  size(600, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Floppy_Bird" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
