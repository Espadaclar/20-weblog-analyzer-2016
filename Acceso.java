public class Acceso
{
    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minutos;

     public Acceso(String fecha)
    {
        String[] elementosLinea = fecha.split(" ");
  
        ano = Integer.parseInt(elementosLinea[0]);
        mes = Integer.parseInt(elementosLinea[1]);
        dia = Integer.parseInt(elementosLinea[2]);
        hora = Integer.parseInt(elementosLinea[3]);
        minutos = Integer.parseInt(elementosLinea[4]);
    }
    public int getAno() 
    {
        return ano;
    }

    public int getMes()
    {
        return mes;
    }

    public int getDia()
    {
        return dia;
    }

    public int getHora()
    {
        return hora;
    }

    public int getMinutos()
    {
        return minutos;
    }
}