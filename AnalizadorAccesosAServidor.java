import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AnalizadorAccesosAServidor
{
    private ArrayList<Acceso> accesos;// -------------- almacena los datos, las líneas de cada archivo.
    private ArrayList<Integer> numEntradas;// --------- almacena el nº de acceso exitosos que ha tenido cada una de las ips.
    private String nombreDelArchivo;//----------------- para mostrar en pantalla el nombre del archivo

    private ArrayList<Acceso> accesosSinError;// ------ almacena los accesos que han tenido respuesta  200.
    private HashMap <String, String> ipExitosasHm;// -- la últma cifra de la ip es la clave, la ip es el valor de la clave.

    private ArrayList<String> clavesEnUnArrayList;// -- almacena las claves del HashMap descendentemente.
    private ArrayList<String> ipEnUnArrayList;// ------ almacena el valor de las claves del HashMap descendentemente.
    public AnalizadorAccesosAServidor() 
    {
        accesos = new ArrayList<>();
        numEntradas = new ArrayList<>();

        accesosSinError = new ArrayList<>();
        ipExitosasHm = new HashMap<>();
        clavesEnUnArrayList = new ArrayList<>();
        ipEnUnArrayList = new ArrayList<>();
    }

    public void analizarArchivoDeLog(String archivo)
    {
        nombreDelArchivo = archivo; //------ para mostrar en pantalla el nombre del archivo
        accesos.clear();
        clavesEnUnArrayList.clear();
        ipEnUnArrayList.clear();
        accesosSinError.clear();

        File archivoALeer = new File(archivo);
        try {
            Scanner sc = new Scanner(archivoALeer);
            while (sc.hasNextLine()) {
                String lineaLeida = sc.nextLine();               
                Acceso accesoActual = new Acceso(lineaLeida);               
                accesos.add(accesoActual);
                //--- 2º colección en la que todas las entrAdas han sido exitosas.
                if(accesoActual.getRespuesta().equals("200")){
                    accesosSinError.add(accesoActual);
                }
            }
            sc.close();
        }
        catch (Exception e) {
            System.out.println("Ocurrio algun error al leer el archivo.");
        }
    }

    /**
     * devuelve un objeto de tipo String conteniendo la dirección del cliente que ha realizado mayor número de accesos 
     * exitosos al servidor. En caso de que se invoque este método sin haberse invocado el método analizarArchivoDeLog 
     * el método informa por pantalla de que no tiene datos, devuelve null y no hace nada más. En caso de empate se muestra
     * el cliente con la IP más alta.
     */
    public String clienteConMasAccesosExitosos()
    {
        clavesEnUnArrayList.clear();
        ipEnUnArrayList.clear();
        numEntradas.clear();
        String ipMasSolicitada = null;

        if(!accesos.isEmpty()){
            //SE CARGA EL HASHMAP CON LA ÚLTIMA CIFRA DE LAS IP COMO CLAVE Y LAS IP COMO VALOR.
            // TODAS LAS IPS ALMACENADAS SOLO HAN TENIDO ACCESOS EXITOSOS.
            for(int i = 0; i < accesos.size(); i ++){
                String respuestaAccesos = accesos.get(i).getRespuesta();
                if(respuestaAccesos.equals("200")){
                    String[] ultimaCifraIp = accesos.get(i).getIp().split("\\.");
                    String ultima = ultimaCifraIp[3];
                    String ip = accesos.get(i).getIp();
                    ipExitosasHm.put(ultima, ip);     
                }
            }

            // SE CREAN DOS ARRAYLIST, UNO CON LOS VALORES Y EL OTRO CON LAS CLAVES, AMBOS SON DEL HASHMAP.
            Iterator<String> it = ipExitosasHm.keySet().iterator();
            while(it.hasNext() ){
                String claveHashM = it.next();       
                String valorClaves = ipExitosasHm.get(claveHashM); // --- cifraFinal es cada clave del HashMap.

                ipEnUnArrayList.add(valorClaves);
                clavesEnUnArrayList.add(claveHashM);
            }

            // ----ORDENA LOS ARRAYLIST DE LAS CLAVES Y DE LAS IPS ASCENDENTEMENTE.
            boolean encontrado = false;
            while(!encontrado){
                encontrado = true;
                for(int i = 0; i < clavesEnUnArrayList.size() -1; i ++){
                    // pasa un String a entero.
                    String clave1 = clavesEnUnArrayList.get(i);
                    int clave11 = Integer.valueOf(clave1);
                    String clave2 = clavesEnUnArrayList.get(i +1);
                    int clave22 = Integer.valueOf(clave2);
                    if(clave11 > clave22){
                        encontrado = false;
                        String claveMayor = clavesEnUnArrayList.get(i +1);
                        clavesEnUnArrayList.set(i +1, clavesEnUnArrayList.get(i));
                        clavesEnUnArrayList.set(i , claveMayor);

                        String claveMayor2 = ipEnUnArrayList.get(i +1);
                        ipEnUnArrayList.set(i +1, ipEnUnArrayList.get(i));
                        ipEnUnArrayList.set(i , claveMayor2);
                    }
                }
            }

            // -- CONTROLA EL Nº DE VECES QUE SE REPITE UNA DE LAS IPS ORDENADAS, EN EL ARCHIVO DE LOG. 
            // -- ESTE Nº DE VECES ES ALMACENADO EN EL ARRAYLIST numEntradas.
            for(int i = 0; i < ipEnUnArrayList.size(); i ++){
                String ips1 = ipEnUnArrayList.get(i);
                int cuentaAccesos = 0;
                for(int z = 0; z < accesosSinError.size(); z ++){
                    String ips2 = accesosSinError.get(z).getIp();
                    if(ips1.equals(ips2)){
                        cuentaAccesos ++;
                    }
                }
                numEntradas.add(cuentaAccesos);
            }

            // -- EL ÍNDICE DE numEntradas CON MAYOR Nº DE ENTRADAS COINCIDE CON EL ÍNDICE DE
            // -- ipEnUnArrayList CON MAYOR ENTRADAS.
            int auxiliar = 0;
            int solucion = 0;
            for(int i = 0; i < numEntradas.size(); i ++){
                if(numEntradas.get(i) >= auxiliar){
                    auxiliar = numEntradas.get(i);
                    solucion = i;
                }
                ipMasSolicitada = ipEnUnArrayList.get(solucion);
            }
        }
        else{
            System.out.println("  Sin datos. ???");
        }
        return ipMasSolicitada;
    }

    //////////////////////////////////////******************************////////////////
    public void zzzMuestra_ClavesY_Ip_ExitosasHm(){
        Iterator<String> it = ipExitosasHm.keySet().iterator();
        while(it.hasNext()){
            String cifraFinal = it.next();
            System.out.println( "Clave --> " +cifraFinal+ " valor --> " +ipExitosasHm.get(cifraFinal));
        }
    }

    public void zzzMuestra_Claves_EnUnArrayList(){
        for(int i = 0; i < ipEnUnArrayList.size(); i ++){
            System.out.println( (i +1)+ " --> " +clavesEnUnArrayList.get(i).toString());

        }
    }

    public void zzzMuestra_Ips_EnUnArrayList_YNum_Accesos(){
        System.out.println(" Nombre del archivo ---> "+ nombreDelArchivo);
        System.out.println( ""); 

        for(int i = 0; i < ipEnUnArrayList.size(); i ++){
            System.out.println( (i +1)+ " --> Nº ip. " +ipEnUnArrayList.get(i).toString()+ " nº accesos. " +numEntradas.get(i).toString());

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
        HashMap<String, String> losNombresWeb = new HashMap<>();  //  tiene como clave y como valor el nombre de las pg, web.
        ArrayList<String> nombresPaginas = new ArrayList<>();     //  almacena el nombre de las pg, web.
        ArrayList<Integer> vecesSolicitada = new ArrayList<>();   //  almacena el nº de veces que una pg, es visitada.
        //para almacenar el índice con mayor nº de visitas del ArrayList conMasVisitas, (en el 3º for).
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
            System.out.println( nombreDelArchivo+ "   NOMBRE DE PÁGINA WEB MÁS VISITADA.  " +nombresPaginas.get(solucion) );
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

    /**
     * devuelve un objeto de tipo String conteniendo la dirección del cliente que ha realizado mayor número de accesos 
     * exitosos al servidor. En caso de que se invoque este método sin haberse invocado el método analizarArchivoDeLog 
     * el método informa por pantalla de que no tiene datos, devuelve null y no hace nada más. En caso de empate se muestra
     * el cliente con la IP más alta.
     */
    public String clienteConMasAccesosExitosos777()
    {
        String ipMasSolicitada = null;
        HashMap<String, String> ipClientes = new HashMap<>();  //  tiene como clave y como valor las ip del cliente.
        ArrayList<String>  ipPaginas = new ArrayList<>();     //  almacena las direcciones ip de los clientes.
        ArrayList<Integer> accesosDelCliente = new ArrayList<>();   //  almacena el nº de veces que una ip ha visitado  pgWebs.

        if(!accesos.isEmpty()){
            //hashMap con las ip de los clientes, como claves y como valores.
            for(int i = 0; i < accesos.size(); i ++){
                String ip = accesos.get(i).getIp();
                ipClientes.put(ip, ip);
            }
            //recorro el hashMap y almaceno las ip en el ArrayList ipPaginas.
            Iterator<String> it = ipClientes.keySet().iterator();
            while(it.hasNext()){
                String ip = it.next();
                ipPaginas.add(ip);
            }

            //recorro el ArrayList ipPaginas y lo comparo con las ip de los clientes del ArrayList accesos.
            for(int i = 0; i < ipPaginas.size(); i ++){
                String ipW = ipPaginas.get(i);
                int acum = 0;//--acumula el nº de veces que una ip  es repetida en el archivo.
                for(int z = 0; z <  accesos.size(); z ++){
                    if(ipW.equals(accesos.get(z).getIp())){
                        acum ++;
                    }
                }
                //el nº de veces que una ip accede a una pgWeb es almacenado en el ArrayList accesosDelCliente.    
                accesosDelCliente.add(acum);
            }

            int con = 0;
            int solucion =0;
            for(int z = 0; z <  accesosDelCliente.size(); z ++){
                if(con <= accesosDelCliente.get(z)){
                    con = accesosDelCliente.get(z);
                    solucion = z;
                }
            }
            System.out.println( nombreDelArchivo+ "   IP COM MÁS ENTRADAS.  " +ipPaginas.get(solucion) );
            ipMasSolicitada = ipPaginas.get(solucion);// valor a devolver como solución final.
        }
        else{
            System.out.println("------ sin datos !!!!.");
        }
        System.out.println("  " );
        for(int i = 0; i < ipPaginas.size(); i ++){
            System.out.println( (i +1)+" -> " +ipPaginas.get(i)+ " _________________ ---> " +
                accesosDelCliente.get(i)+ " visitas." );
        }
        ipPaginas.clear();
        accesosDelCliente.clear();
        ipClientes.clear();
        return ipMasSolicitada;
    }

    public void zzzMuestraAccesosSinError(){
        for(int i = 0; i < accesosSinError.size(); i ++){
            System.out.println( (i +1)+ " --> " +accesosSinError.get(i).toString());
        }

    }
}
