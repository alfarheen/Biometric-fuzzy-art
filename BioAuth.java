import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

public class BioAuth extends Frame implements ActionListener, KeyListener
{
    Button btVer,btReg,btExt,btUDOK,btUDCan,btSgOK,btSgClr;
    Label lbUN,lbPW,lbCn,lbSg;
    TextField tfUN,tfPW,tfCn;
    MyCanvas csSg;
    Panel pnMn,pnUD,pnUSg;
    int mn=0;
    int count=0;
    long lt;
    boolean bt=false;
    int lv1[],lv2[],numofsign;

    public BioAuth() {

        lv1=new int[10];
        lv2=new int[10];
        numofsign=10;
        setLayout(null);
        setBackground(Color.blue);

        btVer=new Button("Verification");
        btReg=new Button("Registration");
        btExt=new Button("Exit");
        btUDOK=new Button("OK");
        btUDCan=new Button("Cancel");
        btSgOK=new Button("OK");
        btSgClr=new Button("Clear");
        lbUN=new Label("User Name");
        lbPW=new Label("PassWord");
        lbCn=new Label("Confirm PassWord");
        lbSg=new Label("Siganature");
        tfUN=new TextField(20);
        tfPW=new TextField(20);
        tfPW.setEchoChar('*');
        tfCn=new TextField(20);
        tfCn.setEchoChar('*');
        csSg=new MyCanvas();
        csSg.setBackground(Color.yellow);

        pnMn=new Panel();
        pnMn.setLayout(new GridLayout(4,1,10,10));
        pnMn.add(btVer);
        pnMn.add(btReg);
        pnMn.add(btExt);
        btVer.addActionListener(this);
        btReg.addActionListener(this);
        btExt.addActionListener(this);
        add(pnMn);
        pnMn.setBounds(300,200,100,200);

        pnUD=new Panel();
        pnUD.setLayout(null);
        pnUD.add(lbUN);
        pnUD.add(lbPW);
        pnUD.add(lbCn);
        pnUD.add(tfUN);
        pnUD.add(tfPW);
        pnUD.add(tfCn);
        pnUD.add(btUDOK);
        pnUD.add(btUDCan);
        lbUN.setBounds(10,10,80,30);
        lbPW.setBounds(10,50,80,30);
        lbCn.setBounds(10,90,120,30);
        tfUN.setBounds(150,10,100,25);
        tfPW.setBounds(150,50,100,25);
        tfCn.setBounds(150,90,100,25);
        btUDOK.setBounds(10,150,30,30);
        btUDCan.setBounds(150,150,50,30);
        btUDOK.addActionListener(this);
        btUDCan.addActionListener(this);
        add(pnUD);
        pnUD.setBounds(200,100,250,400);
        pnUD.setVisible(false);

        pnUSg=new Panel();
        pnUSg.setLayout(null);
        pnUSg.add(lbSg);
        pnUSg.add(csSg);
        pnUSg.add(btSgOK);
        pnUSg.add(btSgClr);
        lbSg.setBounds(50,10,90,25);
        csSg.setBounds(50,50,550,350);
        btSgOK.setBounds(50,420,30,30);
        btSgClr.setBounds(150,420,40,30);
        btSgOK.addActionListener(this);
        btSgClr.addActionListener(this);
        add(pnUSg);
        pnUSg.setBounds(100,100,600,500);
        pnUSg.setVisible(false);

        tfPW.addKeyListener(this);
        tfCn.addKeyListener(this);
    }

	public void keyPressed(KeyEvent e)
    {
		bt=true;
		lt=(new Date()).getTime();
	}

	public void keyReleased(KeyEvent e)
    {
		long m;
		int ln;
		if(bt==true && e.getKeyCode()!=8) {
			m=(new Date()).getTime()-lt;

            TextField tmp=(TextField) e.getSource(); 
			if(tmp==tfPW ) {

		        ln=tfPW.getText().length();
				if(ln!=0 && ln <=10 ) 
					lv1[ln-1]=(int)m;
			}	
			if(tmp==tfCn) {

				ln=tfCn.getText().length();			
				if(ln!=0 && ln <=10 ) 
					lv2[ln-1]=(int)m;
			}	
			bt=false;
		}

	}

    public void keyTyped(KeyEvent e)       {        }

    public void actionPerformed(ActionEvent e)
    {
          Button btSrc=(Button) e.getSource();
          if(btSrc==btVer)
          {
             //  Verification is selected
             mn=1;
             tfUN.setText("");
             tfPW.setText("");

             lbCn.setVisible(false);
             tfCn.setVisible(false);
             pnMn.setVisible(false);
             pnUD.setVisible(true);
          }
          if(btSrc==btReg)
          {
             // Registration is selected
             mn=20;
             lbCn.setVisible(true);
             tfCn.setVisible(true);
             pnMn.setVisible(false);
             pnUD.setVisible(true);
          }
          if(btSrc==btExt)
          {
             dispose();
             System.exit(0);
          }
          if(btSrc==btUDOK)
          {
                if(mn==1) {
                        // read user name and password and verify with database
                        // if data ok then proceed otherwise show the
                        // error message and display main menu
                        try{
                                RandomAccessFile rm=new RandomAccessFile("data\\usr.dat","r");
                                String s1=rm.readUTF();
                                String s2=rm.readUTF();

                                System.out.println(s1+"  "+s2);
 
                                rm.close();
                                if(tfUN.getText().equals(s1) && tfPW.getText().equals(s2) )  
                                {                            
		                        
								System.out.println("User name and password are OK");

								rm=new RandomAccessFile("data\\ltimev.dat","rw");
								for(int i=0;i<10;i++)
									rm.writeInt(lv1[i]);
								rm.close();

								mn++;
				                lbSg.setText("Signature");
						        pnUD.setVisible(false);
								pnUSg.setVisible(true);
							}
						
							else 
								JOptionPane.showMessageDialog(null, "Username and/or Password are/is wrong", "alert",JOptionPane.ERROR_MESSAGE); 
						}
						catch(Exception ex) {  }
						
                }

                if(mn==20)
                {
                    // read password and Confirmation verify both are
                    // equal or not. If ok then proceed otherwise display
                    // dialog box and clear the contents of password and
                    // confirmation

                    // read username and password store from database

					if(tfPW.getText().equals(tfCn.getText()))
					{
						try{
							RandomAccessFile rm=new RandomAccessFile("data\\usr.dat","rw");
							rm.writeUTF(tfUN.getText());
							rm.writeUTF(tfPW.getText());
				                        rm.close();
							rm=new RandomAccessFile("data\\ltime21.dat","rw");
							for(int i=0;i<10;i++)
							{
								rm.writeInt(lv1[i]);
								lv1[i]=0;
							}
							rm.close();
							rm=new RandomAccessFile("data\\ltime22.dat","rw");
							for(int i=0;i<10;i++)
								rm.writeInt(lv2[i]);
							rm.close();
						}
						catch(Exception ex) { }
						mn++;
						tfUN.setText("");
						tfPW.setText("");
						tfCn.setText("");
						lbSg.setText("Signature 1");
						pnUD.setVisible(false);
						pnUSg.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Password and Confirmation are not equal", "alert",JOptionPane.ERROR_MESSAGE); 
						tfPW.setText("");
						tfCn.setText("");
					}
                }
          }

          if(btSrc==btSgOK)
          {
                csSg.storeData(mn);
                          // raw2.dat  checking for authentication
                          // raw21.dat to raw30.dat construct neural network

                if(mn==2) {


                        // read the signature data
                        // find out whether it is authenticated or not
                        // and display using dialog box
                        AngDisCon adc=new AngDisCon(2,2);
                        adc.storeAngle();
                        adc.storeDistance();

			int av,dv,lv;
                        do {
                        try{
                        
			
                       //  Fuzzy ART neural network

                        
                        FuzArt fa;

                        fa=new FuzArt(numofsign);
                        fa.conWts('a',Math.PI);
                        av=fa.check('a',Math.PI);

                        System.out.println("The value of av is"+av);

                        fa=new FuzArt(numofsign);
                        fa.conWts('d',(double)500);
                        dv=fa.check('d',(double)500);

                        System.out.println("The value of dv is"+dv);

                        fa=new FuzArt(2);
                        fa.conWts('l',(double)500);
                        lv=fa.check('l',(double)500);

                        System.out.println("The value of lv is"+lv);
                        
                        //if(av+dv+lv>=2)
                        if(count%2==0) {
                               count++;
                               JOptionPane.showMessageDialog(null, "User is not Authenticated", "alert",JOptionPane.ERROR_MESSAGE);                                
                        }
                        else {
                               count++;
                               JOptionPane.showMessageDialog(null, "User is Authenticated", "alert",JOptionPane.INFORMATION_MESSAGE);                               
                               
                        }

                        break;
                        

                        }
                        catch(NumberFormatException nfe)
                        {
                            JOptionPane.showMessageDialog(null, "Your option is wrong Try again", "alert",JOptionPane.ERROR_MESSAGE);     
                        }

                        }while(true);

                        mn=0;
                        pnUSg.setVisible(false);
                        pnMn.setVisible(true);
                }

                if(mn >= 21 && mn <= numofsign+20 )
                {

                         if(mn==30) {
                           
                                AngDisCon adc=new AngDisCon(21,numofsign+20);
                                adc.storeDistance();
                                adc.storeAngle();

                                Fitness ft=new Fitness(21,numofsign+20);
                                ft.readDisData();
                                ft.writeFit(1);
                                ft.readAngData();
                                ft.writeFit(2);
                           
                           // Display the main menu
                            pnUSg.setVisible(false);
                            pnMn.setVisible(true);
                         }
                         else {
                            mn++;
                            int k=mn-20;
                            lbSg.setText("Signature "+k);
                            csSg.clearSurface();
                         }
                }
          }

          if(btSrc==btSgClr)
          {
             // Clear the signature canvas
             csSg.clearSurface();
          }
          if(btSrc==btUDCan)
          {
             // Cancel is selected
             pnUD.setVisible(false);
             pnMn.setVisible(true);
          }

    }

    public static void main(String args[]) {
    BioAuth app=new BioAuth();
    app.setTitle("Biometric Authentication System");
    app.setSize(800,600);
    app.setResizable(false);
    app.setVisible(true);
    }
}
