package com.project.calculator_java;

//KC Arante
//For clarifications, suggestions and comments you can email me at kc.arante@gmail.com

import java.awt.*;  
import java.awt.event.*;

public class Calculator extends Frame  
{  
  
public boolean setClear=true;  
double number, memValue;  
char op;  
  
String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };  
String operatorButtonText[] = {"/", "sqrt", "*", "%", "-", "1/X", "+", "=" };  
String specialButtonText[] = {"C", "CE","<--"};  
  
DigitButton digitButton[]=new DigitButton[digitButtonText.length];  
OperatorButton operatorButton[]=new OperatorButton[operatorButtonText.length];  
SpecialButton specialButton[]=new SpecialButton[specialButtonText.length];  
  
Label displayLabel=new Label("0",Label.RIGHT);  
  
final int FRAME_WIDTH=260,FRAME_HEIGHT=300;  
final int HEIGHT=30, WIDTH=30, H_SPACE=10,V_SPACE=10;  
final int TOPX=30, TOPY=50;  

Calculator(String frameText)  
{  
super(frameText);  
  
int tempX=TOPX, y=TOPY;  
displayLabel.setBounds(tempX,y,200,HEIGHT);  
displayLabel.setBackground(new Color(254,200,216));  
displayLabel.setForeground(Color.BLACK);  
add(displayLabel);  
   
tempX=TOPX+1*(WIDTH+H_SPACE)-40; y=TOPY+1*(HEIGHT+V_SPACE);  
for(int i=0;i<specialButton.length;i++)  
{  
specialButton[i]=new SpecialButton(tempX,y,WIDTH*2,HEIGHT,specialButtonText[i], this);  
specialButton[i].setForeground(Color.RED);  
tempX=tempX+2*WIDTH+H_SPACE;  
}  
  
int digitX=TOPX+WIDTH+H_SPACE-40;  
int digitY=TOPY+2*(HEIGHT+V_SPACE);  
tempX=digitX;  y=digitY;  
for(int i=0;i<digitButton.length;i++)  
{  
digitButton[i]=new DigitButton(tempX,y,WIDTH,HEIGHT,digitButtonText[i], this);  
digitButton[i].setForeground(Color.BLUE);  
tempX+=WIDTH+H_SPACE;  
if((i+1)%3==0){tempX=digitX; y+=HEIGHT+V_SPACE;}  
}  
  
int opsX=digitX+2*(WIDTH+H_SPACE)+H_SPACE;  
int opsY=digitY;  
tempX=opsX;  y=opsY;  
for(int i=0;i<operatorButton.length;i++)  
{  
tempX+=WIDTH+H_SPACE;  
operatorButton[i]=new OperatorButton(tempX,y,WIDTH,HEIGHT,operatorButtonText[i], this);  
operatorButton[i].setForeground(Color.RED);  
if((i+1)%2==0){tempX=opsX; y+=HEIGHT+V_SPACE;}  
}  
  
addWindowListener(new WindowAdapter()  
{  
public void windowClosing(WindowEvent ev)  
{System.exit(0);}  
});  
  
setLayout(null);  
setSize(FRAME_WIDTH,FRAME_HEIGHT);  
setVisible(true);  
}  

static String getFormattedText(double temp)  
{  
String resText=""+temp;  
if(resText.lastIndexOf(".0")>0)  
    resText=resText.substring(0,resText.length()-2);  
return resText;  
}  

public static void main(String []args)  
{  
new Calculator("Calculator");  
}  
}  
  
 
class DigitButton extends Button implements ActionListener  
{  
Calculator cl;  
  
DigitButton(int x,int y, int width,int height,String cap, Calculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  

static boolean isInString(String s, char ch)  
{  
for(int i=0; i<s.length();i++) if(s.charAt(i)==ch) return true;  
return false;  
}  

public void actionPerformed(ActionEvent ev)  
{  
String tempText=((DigitButton)ev.getSource()).getLabel();  
  
if(tempText.equals("."))  
{  
 if(cl.setClear)   
    {cl.displayLabel.setText("0.");cl.setClear=false;}  
 else if(!isInString(cl.displayLabel.getText(),'.'))  
    cl.displayLabel.setText(cl.displayLabel.getText()+".");  
 return;  
}  
  
int index=0;  
try{  
        index=Integer.parseInt(tempText);  
    }catch(NumberFormatException e){return;}  
  
if (index==0 && cl.displayLabel.getText().equals("0")) return;  
  
if(cl.setClear)  
            {cl.displayLabel.setText(""+index);cl.setClear=false;}  
else  
    cl.displayLabel.setText(cl.displayLabel.getText()+index);  
}
}
   
class OperatorButton extends Button implements ActionListener  
{  
Calculator cl;  
  
OperatorButton(int x,int y, int width,int height,String cap, Calculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  

public void actionPerformed(ActionEvent ev)  
{  
String opText=((OperatorButton)ev.getSource()).getLabel();  
  
cl.setClear=true;  
double temp=Double.parseDouble(cl.displayLabel.getText());  
  
if(opText.equals("1/x"))  
    {  
    try  
        {double tempd=1/(double)temp;  
        cl.displayLabel.setText(Calculator.getFormattedText(tempd));}  
    catch(ArithmeticException excp)  
                        {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(opText.equals("sqrt"))  
    {  
    try  
        {double tempd=Math.sqrt(temp);  
        cl.displayLabel.setText(Calculator.getFormattedText(tempd));}  
            catch(ArithmeticException excp)  
                    {cl.displayLabel.setText("Divide by 0.");}  
    return;  
    }  
if(!opText.equals("="))  
    {  
    cl.number=temp;  
    cl.op=opText.charAt(0);  
    return;  
    }  

switch(cl.op)  
{  
case '+':  
    temp+=cl.number;break;  
case '-':  
    temp=cl.number-temp;break;  
case '*':  
    temp*=cl.number;break;  
case '%':  
    try{temp=cl.number%temp;}  
    catch(ArithmeticException excp)  
        {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
case '/':  
    try{temp=cl.number/temp;}  
        catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
    break;  
}
  
cl.displayLabel.setText(Calculator.getFormattedText(temp));  

}
}  
  
class SpecialButton extends Button implements ActionListener  
{  
Calculator cl;  
  
SpecialButton(int x,int y, int width,int height,String cap, Calculator clc)  
{  
super(cap);  
setBounds(x,y,width,height);  
this.cl=clc;  
this.cl.add(this);  
addActionListener(this);  
}  

static String backSpace(String s)  
{  
String Res="";  
for(int i=0; i<s.length()-1; i++) Res+=s.charAt(i);  
return Res;  
}  
 
public void actionPerformed(ActionEvent ev)  
{  
String opText=((SpecialButton)ev.getSource()).getLabel();  

if(opText.equals("<--"))  
{  
String tempText=backSpace(cl.displayLabel.getText());  
if(tempText.equals(""))   
    cl.displayLabel.setText("0");  
else   
    cl.displayLabel.setText(tempText);  
return;  
}  

if(opText.equals("C"))   
{  
cl.number=0.0; cl.op=' '; cl.memValue=0.0;  
}  
  
cl.displayLabel.setText("0");cl.setClear=true;  
} 
} 
