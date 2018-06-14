import java.io.*;
public class AngDisCon {
    RandomAccessFile rm;
    short minCnt;
    int st,en;
    short datax[][];
    short datay[][];
    public AngDisCon()  { } 
    public AngDisCon(int a,int b) {
        st=a;
		en=b; 
        minCnt=1000;
        try{    
			if(a==b) {
				st=21;
				en=30;    //   because we have to read minCnt from 10 signatures 
			}
			for(int i=st;i<=en;i++)
			{
					rm=new RandomAccessFile("data\\raw"+i+".dat","r");
					short d=rm.readShort();
					rm.close();
					if(minCnt>d)  minCnt=d;
			}
			if(a==b) { 
				st=a;
				en=b;
			}
			System.out.println("Minimum Count is "+minCnt);
			datax=new short[b-a+1][minCnt];
			datay=new short[b-a+1][minCnt];
			for(int i=a;i<=b;i++)
			{
				rm=new RandomAccessFile("data\\raw"+i+".dat","r");
				short d=rm.readShort();
				for(int j=0;j<minCnt;j++)
				{
					datax[i-a][j]=rm.readShort();
					datay[i-a][j]=rm.readShort();
				}
				rm.close();
			}
		}
        catch(IOException e) { 
			System.out.println(e);
        }
    }
    public void storeDistance()
    {
            double d;
            try{
                for(int m=st;m<=en;m++) {
                    rm=new RandomAccessFile("data\\dis"+m+".dat","rw");
                    rm.writeInt((int)minCnt*(minCnt-1)/2);
                    for(int i=0;i<minCnt-1;i++)    
						for(int j=i+1;j<minCnt;j++)
						{			  
                           d=Math.sqrt(Math.pow((datax[m-st][j]-datax[m-st][i]),2)+Math.pow((datay[m-st][j]-datay[m-st][i]),2));
                       	   rm.writeDouble(d);
						}
					rm.close();
                }
            }
            catch(IOException e) {
                System.out.println(e);
            }
    }
    public void storeAngle()  
    {
            double d,nr,dr1,dr2;
            try{
		    int vx[]=new int [minCnt];
		    int vy[]=new int [minCnt];	
                for(int m=st;m<=en;m++) {
                    rm=new RandomAccessFile("data\\ang"+m+".dat","rw");
                    rm.writeInt((int)minCnt*(minCnt-1)/2);
			  vx[0]=datax[m-st][0]-datax[m-st][minCnt-1];
			  vy[0]=datay[m-st][0]-datay[m-st][minCnt-1];
			  for(int i=1;i<minCnt;i++)    {
				  vx[i]=datax[m-st][i]-datax[m-st][i-1];
			        vy[i]=datay[m-st][i]-datay[m-st][i-1];				
                    }
                    for(int i=0;i<minCnt-1;i++)
			  {		    
                        dr1=Math.sqrt(vx[i]*vx[i]+vy[i]*vy[i]);
				for(int j=i+1;j<minCnt;j++)
				{			  
                           nr=vx[i]*vx[j]+vy[i]*vy[j];
                           dr2=Math.sqrt(vx[j]*vx[j]+vy[j]*vy[j]);
				   d=Math.acos(nr/(dr1*dr2));
                       	   rm.writeDouble(d);
                        }
                    }
                    rm.close();
                }
            }
            catch(IOException e) {
                System.out.println(e);
            }
    }
}