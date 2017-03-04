import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AnalizadorAccesosAServidor
{
    private ArrayList<Acceso> accesos;
    //nuevo ArrayList para almacenar nombres de páginas web.
    private ArrayList<String> www;

    public AnalizadorAccesosAServidor() 
    {
        accesos = new ArrayList<>();
        www = new ArrayList<>();
    }

    public void analizarArchivoDeLog(String archivo)
    {
        accesos.clear();
        File archivoALeer = new File(archivo);
        try {
            Scanner sc = new Scanner(archivoALeer);
            while (sc.hasNextLine()) {
                String lineaLeida = sc.nextLine();               
                Acceso accesoActual = new Acceso(lineaLeida);               
                accesos.add(accesoActual);
            }
            sc.close();
        }
        catch (Exception e) {
            System.out.println("Ocurrio algun error al leer el archivo.");
        }
    }

    public int obtenerHoraMasAccesos() 
    {
        int valorADevolver = -1;

        if (!accesos.isEmpty()) {
            int[] accesosPorHora = new int[24];

            for (Acceso accesoActual : accesos) {
                int horaAccesoActual = accesoActual.getHora();
                accesosPorHora[horaAccesoActual] = accesosPorHora[horaAccesoActual] + 1;
            }
            int numeroDeAccesosMasAlto = accesosPorHora[0];
            int horaDeAccesosMasAlto = 0;
            for (int i = 0; i < accesosPorHora.length; i++) {
                if (accesosPorHora[i] >= numeroDeAccesosMasAlto) {
                    numeroDeAccesosMasAlto = accesosPorHora[i];
                    horaDeAccesosMasAlto = i;
                }
            }
            valorADevolver = horaDeAccesosMasAlto;                      
        }
        return valorADevolver;
    }

    /**
     *no recibe parámetros y que únicamente devuelve un objeto de tipo String conteniendo el nombre de la 
     *página web más solicitada por los clientes. En caso de que se invoque este método sin haberse invocado
     *el método analizarArchivoDeLog el método informa por pantalla de que no tiene datos, devuelve nully no
     *hace nada más. En caso de empate es válido devolver cualquiera de las páginas.
     */
    public String paginaWebMasSolicitada() 
    {
        String paginaWebMasSolicitada = null;
        HashMap<String, String> losNombresWeb = new HashMap<>();  //  tiena como clave y como valor el nombre de las pg, web.
        ArrayList<String> nombresPaginas = new ArrayList<>();     //  almacena el nombre de las pg, web.
        ArrayList<Integer> vecesSolicitada = new ArrayList<>();   //  almacena el nº de veces que una pg, es visitada.

        //para almacenar el índice del ArrayList conMasVisitas, (en el 3º for).
        int solucion = 0;

        if(!accesos.isEmpty()){
            //hashMap con los nombres de las páginas web, como claves y como valores.
            for(int i = 0; i < accesos.size(); i ++){
                String nombreWeb = accesos.get(i).getPaginaWeb();
                losNombresWeb.put(nombreWeb, nombreWeb);
            }
            //recorro el hashMap y almaceno las claves en el ArrayList nombresPaginas.
            Iterator<String> it = losNombresWeb.keySet().iterator();
            while(it.hasNext()){
                String nombre = it.next();
                nombresPaginas.add(nombre);
            }
            //recorro el ArrayList nombresPaginas y lo comparo con el nombre de las pgWeb del ArrayList accesos.
            for(int i = 0; i < nombresPaginas.size(); i ++){
                String nombreW = nombresPaginas.get(i);
                int acum = 0;//--acumula el nº de veces que una página web es solicitada por los clientes.
                for(int z = 0; z <  accesos.size(); z ++){
                    if(nombreW.equals(accesos.get(z).getPaginaWeb())){
                        acum ++;
                    }
                }
                //el nº de veces que se repite una pg, web es almacenado en el ArrayList vecesSolicitada.    
                vecesSolicitada.add(acum);
            }
            int conMasVisitas = 0;// en cada iteración aumenta de valor si el índice de conMasVisitas tiene un valor superior.
            for(int z = 0; z <  vecesSolicitada.size(); z ++){
                if(conMasVisitas <= vecesSolicitada.get(z)){
                    conMasVisitas = vecesSolicitada.get(z);
                    solucion = z;
                }
            }
            System.out.println("   NOMBRE DE PÁGINA WEB MÁS VISITADA.  " +nombresPaginas.get(solucion) );
            paginaWebMasSolicitada = nombresPaginas.get(solucion);// valor a devolver como solución final.
        }
        else{
            System.out.println("------ sin datos !!!!.");
        }
        System.out.println("  " );
        for(int i = 0; i < nombresPaginas.size(); i ++){
            System.out.println( (i +1)+" -> " +nombresPaginas.get(i)+ " _________________ ---> " +
                vecesSolicitada.get(i)+ " visitas." );
        }
        nombresPaginas.clear();
        vecesSolicitada.clear();
        losNombresWeb.clear();
        return paginaWebMasSolicitada;
    }

    public String clienteConMasAccesosExitosos()
    {
        return "";
    }

    /////////////////////////////////////////////////
    /**
     * muestra los datos del ArrayList paginasWeb.  access01.log    access02.log    access03.log    access04.log
     */
    public void zzzMuestraPaginasWeb(){
        if(!www.isEmpty()){
            for(int i = 0; i < www.size(); i ++){
                System.out.println( (i +1) + ".-  " +www.get(i).toString());
            }
        }
    }
}

