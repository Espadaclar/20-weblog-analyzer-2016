public class Acceso
{
    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minutos;
    private String ip;
    private String paginaWeb;
    private String respuesta; 
     public Acceso(String fecha)
    {
        String[] elementosLinea = fecha.split(" ");
        
        ip = elementosLinea[0];
        
        ano = Integer.parseInt(elementosLinea[1].replace("[", ""));

        
        //ano = Integer.parseInt(elementosLinea[1]);
        mes = Integer.parseInt(elementosLinea[2]);
        dia = Integer.parseInt(elementosLinea[3]);
        hora = Integer.parseInt(elementosLinea[4]);
        minutos = Integer.parseInt(elementosLinea[5].replace("]", ""));
        paginaWeb = elementosLinea[6];
        respuesta = elementosLinea[6];
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
    
    public String getIp()
    {
        return ip;
    }
    
    public String getPaginaWeb()
    {
        return paginaWeb;
    }
    
    public String getRespuesta()
    {
        return respuesta;
    }
}