import javax.swing.JOptionPane;
import java.io.*;

public class Fitness {
    RandomAccessFile rm;
    double data[][];
    int st,en;
    int minCnt;
    public Fitness(int a,int b) {
        st=a;
        en=b;
        minCnt=0;
    }
    public void readDisData() {
        try{
            rm=new RandomAccessFile("data\\dis"+st+".dat","r");
            minCnt=rm.readInt();
            System.out.println("Minumum count in fitness"+minCnt);
            rm.close();
            data=new double[en-st+1][minCnt];
            for(int i=st;i<=en;i++)
            {
                rm=new RandomAccessFile("data\\dis"+i+".dat","r");
                int d=rm.readInt();
                for(int j=0;j<minCnt;j++)
                {
                  data[i-st][j]=rm.readDouble();
                }
                rm.close();
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
    public void writeFit(int k) {
        double fitValues[]=new double[minCnt];
        short fitPos[]=new short[minCnt];
        double sumdis,sumdissq;

        for(int i=0;i<minCnt;i++) {
            fitPos[i]=(short)(i+1);
            sumdis=0;
            sumdissq=0;
            for(int m=0;m<en-st+1;m++)
            {
                sumdis+=data[m][i];
                sumdissq+=Math.pow(data[m][i],2);
            }
            fitValues[i]=Math.sqrt(((en-st+1)*sumdissq - sumdis * sumdis ) / ((en-st+1) * (en-st)) );
        }
        for(int i=0;i<minCnt-1;i++)
        {
          for(int j=i+1;j<minCnt;j++)
            if(fitValues[i]<fitValues[j]) {
                double temp=fitValues[i];
                fitValues[i]=fitValues[j];
                fitValues[j]=temp;
                short tmp=fitPos[i];
                fitPos[i]=fitPos[j];
                fitPos[j]=tmp;
            }
        }
	  if(minCnt<9){
		JOptionPane.showMessageDialog(null, "The Signature is too short", "alert",JOptionPane.ERROR_MESSAGE); 
		return;
	  }
        for(int i=0;i<9;i++)
        {
          for(int j=i+1;j<10;j++)
            if(fitPos[i]>fitPos[j]) {
                short tmp=fitPos[i];
                fitPos[i]=fitPos[j];
                fitPos[j]=tmp;
            }
        }

        try{
            if(k==1)  
				rm=new RandomAccessFile("data\\fitDis.dat","rw");
			else
				rm=new RandomAccessFile("data\\fitAng.dat","rw");
            for(int i=0;i<10;i++)
                rm.writeShort(fitPos[i]);
            rm.close();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public void readAngData() {
        try{
            rm=new RandomAccessFile("data\\ang"+st+".dat","r");
            minCnt=rm.readInt();
            rm.close();
            data=new double[en-st+1][minCnt];
            for(int i=st;i<=en;i++)
            {
                rm=new RandomAccessFile("data\\ang"+i+".dat","r");
                int d=rm.readInt();
                for(int j=0;j<minCnt;j++)
                {
                  data[i-st][j]=rm.readDouble();
                }
                rm.close();
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
}