package com.karyasarma.toolkit.misc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("unused")
public class TapTapFish
{
    private final Queue<Loc> listOfLoc = new LinkedList<>();

    public TapTapFish()
    {
    }

    public static class Loc
    {
        public int x;
        public int y;

        public Loc(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    public void addLoc(int x, int y)
    {
        synchronized(listOfLoc)
        {
            listOfLoc.add(new Loc(x, y));
        }
    }

    public Loc getLoc()
    {
        Loc result = null;

        synchronized (listOfLoc)
        {
            if(!listOfLoc.isEmpty())
            {
                result = listOfLoc.remove();
            }
        }

        return result;
    }

    public class TapProducer extends Thread
    {
        public TapProducer()
        {
        }

        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run()
        {
            while(true)
            {
                tap(200, 300);
                delay(50);
            }
        }
    }

    public class CoralProducer extends Thread
    {
        public CoralProducer()
        {
        }

        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run()
        {
            int counter = 0;

            while(true)
            {
                counter++;
                tap(900, 1250);
                delay(1000);

                if(counter%15==0)
                {
                    tap(100, 1250);
                    delay(100);
                }
            }
        }
    }

    public synchronized void tap(int x, int y)
    {
        try
        {
            Runtime.getRuntime().exec(String.format("adb shell input tap %d %d", x, y));
            System.out.printf("Tap: [%s, %s]%n", x, y);
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    public void delay(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch(InterruptedException ignored)
        {
        }
    }

    @SuppressWarnings("CommentedOutCode")
    public void start()
    {
        new TapProducer().start();
        //new CoralProducer().start();

//        new Thread()
//        {
//            public void run()
//            {
//                try
//                {
//                    Robot robot = new Robot();
//
//                    while(true)
//                    {
//                        robot.mouseMove(0,0);
//                        Thread.sleep(30_000);
//                        robot.mouseMove(500,500);
//                        Thread.sleep(30_000);
//                    }
//
//                }
//                catch(AWTException|InterruptedException ex)
//                {
//                    ex.printStackTrace();
//                }
//            }
//        }.start();
    }

    public static void main(String[] args)
    {
        new TapTapFish().start();
    }
}