package lectores;

import  java.io.*;
import  java.lang.String;
import  java.util.Scanner;

class node extends ninos{
    node next, previo;

    node(){

    }

    node(int id, String nombre, String apellido, int estado){
        this.id = id;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.estado = estado;
    }

    node(int id, String nombre, String apellido, int estado, node next, node previo){
        this.id = id;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.estado = estado;
        this.next = next;
        this.previo = previo;
    }

}

class lista {
    node L, aux, aux2, cabeza, cola;

    boolean isEmpty() {
            if (L == null) {
                return true;
            }else{
                return false;
            }
    }

    void jug() throws IOException{
        int ronda = 0;
        //coloca los datos de los lectores en la lista circular
        this.set();
        //Rondas acaban hasta que el contador sea cero
        while  (count() > 0) {

            System.out.println( "Ronda Nº " + ronda++ );
            if ( this.Bibliotecario1() == this.Bibliotecario2()){
                ronda++;
                System.out.println( "Estudiante empieza a leer un libro" );
                System.out.println( "...." );
                System.out.println( "...." );
                System.out.println( "...." );
                System.out.println( "...." );
                System.out.println( "...." );
                update(this.Bibliotecario1(),3);
            }else{
                System.out.println( "Siguiente Ronda no coincide los niños" );
                System.out.println( "......" );
                System.out.println( "......" );
                System.out.println( "......" );
                System.out.println( "......" );

            }
            System.out.println("Quedan " + count() + " Personas por leer");
        }
    }

    void set() throws IOException{
        String datos;
        file F = new file();
        datos = F.leer("lectores.in");

        String[] A = new String[F.count-1];
        A = datos.split("\n");

        System.out.println(A[1]);
        for ( int i =0; i < F.count ; i++){
            add(A[i]);
        }
    }

    void add( String linea) {
        String[] datos;
        datos = linea.split("/");

        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        String apellido = datos[2];
        int estado = Integer.parseInt(datos[3]);

        if (this.isEmpty()) {
            this.L = new node(id, nombre, apellido, estado);
            this.cabeza = L;
            this.L.next = this.L;
            this.L.previo = this.L;
        } else {
            this.aux = this.L;
            while (this.aux.next != this.L)
                this.aux = this.aux.next;
            this.aux.next = new node(id, nombre, apellido, estado, L,  null );

        }
    }

    int Bibliotecario1() throws IOException{
        boolean salir = false;
        int dado = 0;
        aux = L;
        while( aux.next != null && !salir ){
            dado = (int) Math.floor(Math.random() * 10 + 1);
            aux2 = aux;
            aux= aux.next;
            if (dado == 4 && aux2.estado == 0  ) {
                    update(aux2.id,1);
                    salir = true;
            }
        }
        return aux2.id;
    }

    int  Bibliotecario2() throws IOException{
        boolean salir = false;
        int dado = 0;
        aux = L;
        while( aux.next != null && !salir ){
            dado = (int) Math.floor(Math.random() * 10 + 1);
            aux2 = aux;
            aux= aux2.next;
            if (dado == 4 && aux2.estado == 0  ) {
                update(aux2.id,2);
                salir = true;
            }
        }
        return aux2.id;
    }

    void update(int id, int c) throws IOException{
        if (!this.isEmpty()) {

            if (c == 3) {
                update(id,4);
            }

            aux = L;
            node aux2 = aux;
            while (aux.next!=L && aux.id !=id){
                aux2 = aux;
                aux=aux.next;
            }
            if (aux.id == id){
                if (aux.estado == 1 ){
                    if (c == 1) {
                      //  System.out.println( "Bibliotecario 1 anuncio a:");
                    }else{
                      //  System.out.println( "Bibliotecario 2 anuncio a:");
                    }
                    //System.out.println( aux.Nombre + " " + aux.Apellido + "esta leyendo en la mesa");
                }else {
                    if (c== 4){
                        aux.estado = 1;
                        System.out.println("Finalizado la lectura");

                        // aqui ya puedo meter lectores Aut “lectores.out
                        this.meter(aux.id, aux.Nombre, aux.Apellido , aux.estado);
                    }
                    //Mensaje para

                    if (c == 1) {
                        System.out.println( "Bibliotecario 1 anuncio a:");
                    }
                    if ( c == 2 ){
                        System.out.println( "Bibliotecario 2 anuncio a:");
                    }

                    if ( c == 1 || c== 2) {
                        System.out.println(aux.Nombre + " " + aux.Apellido + " preparandose para leer");
                    }
                }
            }else{
                if (aux.next == cabeza)
                    System.out.println("Finalizado");
            }

        }else{
            System.out.println("Lista Vacia!!");
        }
    }

    String meter(int id, String nombre, String apellido, int estado) throws  IOException{
        ninos f = new ninos();
        String datos;
        datos = id+"/"+nombre+"/"+apellido+"/"+estado;
         f.guardar(datos,"lectores.out");
        return "Metido en lectores .out exitosamente";
    }

    int count(){
        if (!this.isEmpty()) {
            int contar = 0;
            aux = L;
            if (L.estado == 0){
                contar++;
            }
            while ( aux.next != cabeza ){
                aux=aux.next;
                if (aux.estado == 0){
                   contar = contar+1;
                }
            }
            return  contar;
        }else {
            return  0;
        }
    }

}


class ninos{
    public int id;
    public String Nombre;
    public String Apellido;
    public int estado;

    public ninos(){
    }

     void registrar() throws IOException{
        int contador = 1;
        int  o = 0;
        boolean end = false;
        while(!end) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Niño numero : " + contador);
            this.id = contador;
            System.out.print("Nombre del niño : ");
            this.Nombre = br.readLine();
            System.out.print("Apellido del niño : ");
            this.Apellido = br.readLine();
            this.estado = 0;

            String datos;
            datos = this.id+"/"+this.Nombre+"/"+this.Apellido+"/"+this.estado;
            this.guardar(datos,"lectores.in");

            System.out.println(" '0' para finalizar registros ");
            o = br.read();
            contador++;
            System.out.println(o);

            if ( o ==48 ){
                end = true;
            }
        }
    }

     void guardar(String datos, String archivito) throws IOException{
        file f =  new file();
        f.crear(archivito, datos);
    }

    void mostrar(int i) throws IOException{
        String datos;
        file F = new file();
        if (i == 1){
            datos = F.leer("lectores.in");
        }else{
            datos = F.leer("lectores.out");
        }

        System.out.println(datos);
    }

}



class file{
    PrintWriter pf;
    FileReader fr;
    int count;

    file(){}

        void crear( String nombreArchivo, String Datos) throws IOException{
            pf = new PrintWriter( new FileWriter(nombreArchivo,true));
            pf.println(Datos);
            pf.close();
            System.out.println("Lector añadido");
        }

        String leer (String nombreArchivo) throws IOException{
             this.count = 0;
             String datos = "";
             fr = new FileReader(nombreArchivo);
             BufferedReader  br = new BufferedReader(fr);
             String linea;

             while ( (linea= br.readLine())!= null){
                 datos += linea + "\n";
                 this.count++;
             }
            return  datos;
        }

        String actualizar( String datos){
            return "Termino de leer el carajito";
        }

}


class    menu{
    Scanner keying;
    boolean result;

    public void renderMenu() {

        System.out.println("1) Comenzar grupo de lectura)");
        System.out.println("2) Lectores de entrada)");
        System.out.println("3) Lectores de salida");
        System.out.println("4) No apretar la opcion 2 o 3 sino ha creado los archivos primero D:!");
        System.out.println("5) Salir del Sistema");
    }

    public void mostrarMenu() throws IOException{
        ninos n = new ninos();
        while(!this.result) {
            System.out.println("Escoge una opción");
            int e = this.keying.nextInt();
            switch (e) {
                case 1:
                      n.registrar();
                      lista L = new lista();
                      L.jug();
                      this.renderMenu();
                      break;
                case 2:
                      n.mostrar(1);
                      this.renderMenu();
                      break;
                case 3:
                      n.mostrar(2);
                      this.renderMenu();
                      break;
                case 4:


                case 5: System.out.println("Hasta luego");
                    this.result = true;
                    break;
                default: System.out.println("Opción invalida");
            }
        }
    }

    public menu(){
        this.keying = new Scanner(System.in);
        this.result = false;
    }
}


public class Main {

    public static void main(String[] args) throws IOException{
        menu men = new menu();
        men.renderMenu();
        men.mostrarMenu();
    }
}

/* int dado = 0;
        int ronda = 0;
        ninos n = new ninos();
        file f = new file();

        n.registrar();

        System.out.println();

        lista l = new lista();
        l.set();

        boolean end = true;
        while ( end){
            System.out.println(dado);
            dado = (int) Math.floor(Math.random() * 10 + 1);
            if (dado == 4){
                end = false;
                System.out.println(dado);
            }
        }*/