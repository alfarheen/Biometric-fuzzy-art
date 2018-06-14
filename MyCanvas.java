import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
    int last_x,last_y;
    int cnt;
    short px[];
    short py[];
    double stTime;
    public MyCanvas() {
        px=new short [1000];
        py=new short [1000];
        setSize(500,400);
        last_x=last_y=cnt=0;
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void storeData(int m)  
    {

        double d;
        try{
        RandomAccessFile rm=new RandomAccessFile("data\\raw"+m+".dat","rw");
        rm.writeShort((short)cnt);
        for(int i=0;i<cnt;i++)
        {
           rm.writeShort(px[i]);
           rm.writeShort(py[i]);
        }
        rm.close();
        }
        catch(IOException e) {
          System.out.println(e);
        }
        System.out.println("Raw data Count is"+cnt);

    }

    public void clearSurface() {
        Graphics g=getGraphics();
        g.clearRect(0,0,getWidth(),getHeight());
        g.dispose();
        cnt=0;
        
    }
    public void mousePressed(MouseEvent event)    {
        if(!event.isMetaDown()) {
            last_x=event.getX();
            last_y=event.getY();
            px[cnt]=(short)last_x;
            py[cnt]=(short)last_y;
            cnt++;
            stTime=(new Date()).getTime();
        }
    }
    public void mouseClicked(MouseEvent event)   { }
    public void mouseEntered(MouseEvent event)   { }
    public void mouseExited(MouseEvent event)    { }
    public void mouseReleased(MouseEvent event)  { }

    public void mouseDragged(MouseEvent event)    {
        int x=event.getX();
        int y=event.getY();
        if(!event.isMetaDown()) {
            Graphics g=getGraphics();
            g.drawLine(last_x,last_y,x,y);
            last_x=x;
            last_y=y;
            g.dispose();
           // linear time wrapping is performed here 
            if((new Date()).getTime()-stTime>=40)  {
                stTime=(new Date()).getTime();
                px[cnt]=(short)x;
                py[cnt]=(short)y;
                cnt++;
            }
            else
                System.out.println("e");
        }
    }
    public void mouseMoved(MouseEvent event)    { }
}
