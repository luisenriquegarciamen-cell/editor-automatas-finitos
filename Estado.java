import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
public class Estado{
    public String nombre ;
    private int x,y;
    private int radio =25;
    private boolean esInicial;
    private boolean esFinal;
    public Estado(String nombre,int x,int y){
        this.nombre= nombre;
        this.x=x;
        this.y=y;   
    }
    public void dibujado(Graphics2D g2d){
        g2d.
    }
    //Geters y Seters
    public String getNombre(){ 
        return nombre; 
    }
    public int getX(){ 
        return x;
    }
    public void setX(){
        this.x=x;
    }
    public int getY(){
        return y;
    }
    public void setY(){
        this.y=y;
    }
    public boolean getEsInicial(){
        return esInicial;
    }
    public void setEsIncial(boolean esInicial){
        this.esInicial=esInicial
    }
    public boolean getEsInicial(){
        return esFinal;
    }
    public void setEsFinal(boolean esFinal){
        this.esFinal=esFinal;
    }
    
}