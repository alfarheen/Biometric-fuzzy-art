import java.io.*;

public class FuzArt {
 double data[];
 double wts[][];
 double chFun[];
 int cnt;
 int clusters;
 int numberOfSig;
 float alpha;
 float vigThrs;
 float beta;
 RandomAccessFile rm;
 public FuzArt(int a) {
       int i;
       int j;

     data=new double[20];
     wts=new double[a][20];
     chFun=new double[40];
     cnt=0;
     clusters=0;
     numberOfSig=a;
     alpha=0.1f;
     vigThrs=0.85f;
     beta=0.5f;
     for(i=0;i<a;i++)
        for(j=0;j<20;j++)
           wts[i][j]=1.0f;
 }
 public void conWts(char ch,double factor) {
   int p,minCnt;
   double dt;
   short fit[]=new short[20];
   try{
    if(ch=='a')	{
	rm=new RandomAccessFile("data\\fitAng.dat","r");
	for(int j=0;j<10;j++)
		fit[j]=rm.readShort();
    }
    if(ch=='d')	{
	rm=new RandomAccessFile("data\\fitDis.dat","r");
	for(int j=0;j<10;j++)
		fit[j]=rm.readShort();
    } 

						
    for(int i=1;i<=numberOfSig;i++)
    {
      p=i+20;
	  switch(ch)
	  {
	   	case 'a':
                          
			  rm=new RandomAccessFile("data\\Ang"+p+".dat","r");
			  minCnt=rm.readInt();
			  p=0;
			  for(int j=1;j<=minCnt;j++)
        	                                 {
					dt=rm.readDouble();
					if(fit[p]==j)
				               {
						dt=dt/factor;
						data[p]=dt;
            			                                                     data[p+10]=1-data[p];
						p++;
				             }
					if(p==10) break;
        	                             }
      		         break;		  
	   	case 'd':
                                                       vigThrs=0.95f;
			  rm=new RandomAccessFile("data\\Dis"+p+".dat","r");
			  minCnt=rm.readInt();
			  p=0;
			  for(int j=1;j<=minCnt;j++)
        	                                 {
					dt=rm.readDouble();
		                                                      if(fit[p]==j)
				     {
					dt=dt/factor;
					data[p]=dt;
		       			data[p+10]=1-data[p];
					p++;
				   }
					if(p==10) break;
        	                             }
      		          break;		  
	   	case 'l':
                                                       vigThrs=0.94f;
                      		  rm=new RandomAccessFile("data\\ltime"+p+".dat","r");
			  for(int j=0;j<10;j++)
        	                                            {
				data[j]=(double)rm.readInt()/factor;
           			                  data[j+10]=1-data[j];
			           }
      		        break;		  
	     }
                       rm.close();
                      boolean match=false;        
        for(int j=0;j<clusters;j++)
       {
            double nr=0.0,dr=0.0;
            for(int k=0;k<20;k++)
            {
              nr+=Math.min(data[k],wts[j][k]);
              dr+=wts[j][k];
            }
            chFun[j]=nr/(alpha+dr);
      }

      for(int j=0;j<clusters && match==false;j++)
      {
           int pos=find(chFun,clusters);
           double nr=0.0,dr=0.0;
           for(int k=0;k<20;k++)
           {
              nr+=Math.min(data[k],wts[pos][k]);
              dr+=data[k];
           }
           if(nr/dr >= vigThrs)
           {
                match=true;
                for(int k=0;k<20;k++)
                  wts[j][k]=beta*(Math.min(data[k],wts[pos][k]))+(1-beta)*wts[pos][k];
           }
           else
                chFun[j]=0;
      }
        
       if(match==false)
       {
            for(int k=0;k<20;k++)
                wts[clusters][k]=data[k];
            clusters++;
       }
  }
  }
  catch(IOException ex)
  {
    System.out.println(ex);
  }
 }



 public int check(char ch,double factor)
 {
  int p;
  boolean match=false;        
  try{
        switch(ch)
	   {
			case 'a':
				rm=new RandomAccessFile("data\\ang2.dat","r");
				p=rm.readInt();
			                 for(int j=0;j<10;j++)
			                 {
					 data[j]=rm.readDouble()/factor;
					 data[j+10]=1-data[j];
				}
				break;
			case 'd': 
                                                                         vigThrs=0.95f;
				rm=new RandomAccessFile("data\\dis2.dat","r");
				p=rm.readInt();
				for(int j=0;j<10;j++)
			                  {
					 data[j]=rm.readDouble()/factor;
					 data[j+10]=1-data[j];
				}
				break;
			case 'l':vigThrs=0.94f;
				rm=new RandomAccessFile("data\\ltimev.dat","r");
				p=10;
				for(int j=0;j<10;j++)
			                   {
					 data[j]=(double)rm.readInt()/factor;
					 data[j+10]=1-data[j];
				}
				break;
	   }
         rm.close();
        System.out.println("Number of clusters are:"+clusters);
      for(int j=0;j<clusters;j++)
      {
           double nr=0.0,dr=0.0;
           for(int k=0;k<20;k++)
           {
              nr+=Math.min(data[k],wts[j][k]);
              dr+=data[k];
           }
            System.out.println("nr/dr="+nr/dr+" VigThrs="+vigThrs);
           if(nr/dr >= vigThrs)
           {
                match=true;
                break;
           }
      }
            
    }
  catch(IOException ex)
  {
    System.out.println(ex);
  }
     if(match==false)
            return 0;   //		System.out.println("The signature is not matched");
    else
           return 1;   //     System.out.println("The signature is matched");
 }


 public int find(double chF[],int clsts)
    {
        double big;
        int pos=0;
        big=chF[0];
        for(int i=1;i<clsts;i++)
        {
          if(big>chF[i]) { big=chF[i];pos=i; }
        }
        return pos;
    }
 }