public class Transicion{
    public Estado origen, destino;
    public char simbolo;
    public Transicion(Estado origen,Estado destino,char simbolo){
        this.origen=origen;
        this.destino=destino;
        this.simbolo=simbolo;
    }

}