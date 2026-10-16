package taller22;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class taller22 {
    static Participante[] listaParticipante=new Participante[200];
    static int cantParticipante=0;

    static Choripan[] listaChoripanes=new Choripan[200];
    static int cantChoripanes=0;

    static Terremoto[] listaTerremotos=new Terremoto[200];
    static int cantTerremotos=0;

    static Scanner scanner=new Scanner(System.in);

    public static void main(String[] args) {
        leerParticipantes();
        leerConsumido();

        String opcion;
        do {
            int cantConsumido=cantChoripanes+cantTerremotos;
            System.out.print("============================================\r\n"
                    + "           LA FONDA DEL PAPU\r\n"
                    + "============================================\r\n"
                    + "  Registros cargados: "+cantConsumido+"\r\n"
                    + "--------------------------------------------\r\n"
                    + "1) Ver registros\r\n"
                    + "2) Actualizar registros\r\n"
                    + "3) Calcular metricas\r\n"
                    + "4) Salir\r\n"
                    + "============================================\r\n"
                    + "Seleccione una opcion: ");

            opcion=scanner.nextLine();

            switch (opcion) {
                case "1":
                    verRegistros();
                    break;
                case "2":
                    actualizarRegistros();
                    break;
                case "3":
                    calcularMetricas();
                    break;
                case "4":
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
                    break;
            }

        } while (!opcion.equals("4"));
    }

    private static void leerParticipantes() {
        try {
            File arch=new File("Participantes.txt");
            Scanner lector=new Scanner(arch);

            while (lector.hasNextLine()) {
                String nombre=lector.nextLine();
                if (!nombre.equals("")) {
                    Participante participante=new Participante(nombre);

                    if (cantParticipante<200) {
                        listaParticipante[cantParticipante]=participante;
                        cantParticipante++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("no existe el archivo Participantes.txt");
        }
    }

    private static void leerConsumido() {
        try {
            File arch=new File("Consumido.txt");
            Scanner lector=new Scanner(arch);

            while (lector.hasNextLine()) {
                String linea=lector.nextLine();
                String[] partes=linea.split(";");

                String tipo=partes[0];
                String consumidor=partes[1];

                Participante participante=null;
                for (int i=0;i<cantParticipante;i++) {
                    if (listaParticipante[i].getNombre().equalsIgnoreCase(consumidor)) {
                        participante=listaParticipante[i];
                        break;
                    }
                }

                if (tipo.equalsIgnoreCase("Choripan")) {
                    int largo=Integer.parseInt(partes[2]);
                    int ancho=Integer.parseInt(partes[3]);
                    String fecha=partes[4];

                    Choripan choripan=new Choripan(participante,fecha,largo,ancho);

                    if (cantChoripanes<200) {
                        listaChoripanes[cantChoripanes]=choripan;
                        cantChoripanes++;
                    }

                } else if (tipo.equalsIgnoreCase("Terremoto")) {
                    double litros=Double.parseDouble(partes[2]);
                    String fecha=partes[3];

                    Terremoto terremoto=new Terremoto(participante,fecha,litros);

                    if (cantTerremotos<200) {
                        listaTerremotos[cantTerremotos]=terremoto;
                        cantTerremotos++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("no existe el archivo Consumido.txt");
        }
    }

    private static void guardarConsumido() {
        try {
            FileWriter fw=new FileWriter("Consumido.txt",false);
            BufferedWriter bw=new BufferedWriter(fw);

            for (int i=0;i<cantChoripanes;i++) {
                Choripan c=listaChoripanes[i];
                String nom="";
                if (c.getConsumidor()!=null) {
                    nom=c.getConsumidor().getNombre();
                }
                bw.write("Choripan;"+nom+";"+c.getLargo()+";"+c.getAncho()+";"+c.getFecha());
                bw.newLine();
            }

            for (int i=0;i<cantTerremotos;i++) {
                Terremoto t=listaTerremotos[i];
                String nom="";
                if (t.getConsumidor()!=null) {
                    nom=t.getConsumidor().getNombre();
                }
                bw.write("Terremoto;"+nom+";"+t.getLitros()+";"+t.getFecha());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo Consumido.txt");
        }
    }

    private static void verRegistros() {
        String opcion;

        do {
            System.out.print("============ VER REGISTROS =================\r\n"
                    + "1) Choripanes\r\n"
                    + "2) Terremotos\r\n"
                    + "3) Volver al menu principal\r\n"
                    + "============================================\r\n"
                    + "Seleccione una opcion: ");

            opcion=scanner.nextLine();

            switch (opcion) {
                case "1":
                    mostrarInfoChoripanes();
                    break;
                case "2":
                    mostrarInfoTerremotos();
                    break;
            }
        } while (!opcion.equals("3"));
    }

    private static void mostrarInfoChoripanes() {
        System.out.println("=============== CHORIPANES =================\r\n"
                + "  #   Consumidor    Largo   Ancho   Fecha         KCal");

        for (int i=0;i<cantChoripanes;i++) {
            Choripan choripan=listaChoripanes[i];
            double kcal=choripan.getLargo()*choripan.getAncho()/3.0;

            String nom="Desconocido";
            if (choripan.getConsumidor()!=null) {
                nom=choripan.getConsumidor().getNombre();
            }

            System.out.println((i+1)+"   "+nom+"        "
                    + choripan.getLargo()+" cm    "+choripan.getAncho()+" cm   "
                    + choripan.getFecha()+"    "+kcal);
        }

        System.out.println("--------------------------------------------------\r\n"
                + "  Total: "+cantChoripanes+" choripanes registrados\r\n"
                + "==================================================");

        System.out.println("Presione ENTER para continuar");
        scanner.nextLine();
    }

    private static void mostrarInfoTerremotos() {
        System.out.println("=============== TERREMOTOS =================\r\n"
                + "  #   Consumidor    Litros   Fecha         KCal");

        double totalLitros=0;
        for (int i=0;i<cantTerremotos;i++) {
            Terremoto terremoto=listaTerremotos[i];
            double kcal=terremoto.getLitros()*10.0/3.0;
            totalLitros+=terremoto.getLitros();

            String nom="Desconocido";
            if (terremoto.getConsumidor()!=null) {
                nom=terremoto.getConsumidor().getNombre();
            }

            System.out.println((i+1)+"   "+nom+"     "
                    + terremoto.getLitros()+" L   "+terremoto.getFecha()+"    "+kcal);
        }

        System.out.println("--------------------------------------------------\r\n"
                + "  Total: "+cantTerremotos+" terremotos registrados ("+totalLitros+" L)\r\n"
                + "==================================================");

        System.out.println("Presione ENTER para continuar");
        scanner.nextLine();
    }

    private static void actualizarRegistros() {
        String opcion;

        do {
            System.out.print("========== ACTUALIZAR REGISTROS ============\r\n"
                    + "1) Agregar\r\n"
                    + "2) Editar\r\n"
                    + "3) Eliminar\r\n"
                    + "4) Volver al menu principal\r\n"
                    + "============================================\r\n"
                    + "Seleccione una opcion: ");

            opcion=scanner.nextLine();
            switch (opcion) {
                case "1":
                    Agregar();
                    break;
                case "2":
                    Editar();
                    break;
                case "3":
                    Eliminar();
                    break;
            }

        } while (!opcion.equals("4"));
    }

    private static void Agregar() {
        System.out.print("Tipo de registro:\r\n"
                + "1) Choripan\r\n"
                + "2) Terremoto\r\n"
                + "3) Cancelar\r\n"
                + "Seleccione una opcion: ");
        String opcion=scanner.nextLine();

        if (cantParticipante==0) {
            System.out.println("No hay participantes cargados para asociar un consumo");
            return;
        }

        if (opcion.equals("1")) {
            System.out.println("Consumidores disponibles: ");

            for (int i=0;i<cantParticipante;i++) {
                System.out.println("["+(i+1)+"] "+listaParticipante[i].getNombre());
            }

            int numero=-1;
            do {
                System.out.print("Seleccione el consumidor (1 a "+cantParticipante+"): ");
                try {
                    numero=Integer.parseInt(scanner.nextLine())-1;
                    if (numero<0 || numero>=cantParticipante) {
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (numero<0 || numero>=cantParticipante);

            int largo=-1;
            do {
                System.out.print("Largo del choripan (cm): ");
                try {
                    largo=Integer.parseInt(scanner.nextLine());
                    if (largo<=0) {
                        System.out.println("El largo debe ser mayor a 0");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (largo<=0);

            int ancho=-1;
            do {
                System.out.print("Ancho del choripan (cm): ");
                try {
                    ancho=Integer.parseInt(scanner.nextLine());
                    if (ancho<=0) {
                        System.out.println("El ancho debe ser mayor a 0");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (ancho<=0);

            String fecha="";
            do {
                System.out.print("Fecha (YYYY-MM-DD): ");
                fecha=scanner.nextLine();
                if (fecha.equals("")) {
                    System.out.println("La fecha no puede estar vacia");
                }
            } while (fecha.equals(""));

            Choripan choripan=new Choripan(listaParticipante[numero],fecha,largo,ancho);
            double kcals=largo*ancho/3.0;

            if (cantChoripanes<200) {
                listaChoripanes[cantChoripanes]=choripan;
                cantChoripanes++;

                guardarConsumido();

                System.out.println("Registro agregado correctamente y guardado en Consumido.txt");
                System.out.println("Choripan | "+listaParticipante[numero].getNombre()+" | "+largo+" cm x "+ancho+" cm | "+fecha+" | "+kcals+" kcal");
            } else {
                System.out.println("No hay espacio suficiente para agregar mas choripanes");
            }

        } else if (opcion.equals("2")) {
            System.out.println("Consumidores disponibles: ");

            for (int i=0;i<cantParticipante;i++) {
                System.out.println("["+(i+1)+"] "+listaParticipante[i].getNombre());
            }

            int numero=-1;
            do {
                System.out.print("Seleccione el consumidor (1 a "+cantParticipante+"): ");
                try {
                    numero=Integer.parseInt(scanner.nextLine())-1;
                    if (numero<0||numero>=cantParticipante){
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch(Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            }while(numero<0 || numero>=cantParticipante);

            double litros=-1;
            do {
                System.out.print("Litros del terremoto: ");
                try {
                    litros=Double.parseDouble(scanner.nextLine());
                    if (litros<=0) {
                        System.out.println("Los litros deben ser mayor a 0");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (litros<=0);

            String fecha="";
            do {
                System.out.print("Fecha (YYYY-MM-DD): ");
                fecha=scanner.nextLine();
                if (fecha.equals("")) {
                    System.out.println("La fecha no puede estar vacia");
                }
            } while (fecha.equals(""));

            Terremoto terremoto=new Terremoto(listaParticipante[numero],fecha,litros);
            double kcals=litros*10.0/3.0;

            if (cantTerremotos<200) {
                listaTerremotos[cantTerremotos]=terremoto;
                cantTerremotos++;

                guardarConsumido();

                System.out.println("Registro agregado correctamente y guardado en Consumido.txt");
                System.out.println("Terremoto | "+listaParticipante[numero].getNombre()+" | "+litros+" L | "+fecha+" | "+kcals+" kcal");
            } else {
                System.out.println("No hay espacio suficiente para agregar mas terremotos");
            }
        }
    }

    private static void Editar() {
        System.out.print("Tipo de registro a editar:\r\n"
                + "1) Choripan\r\n"
                + "2) Terremoto\r\n"
                + "3) Cancelar\r\n"
                + "Seleccione una opcion: ");
        String opcion=scanner.nextLine();

        if (opcion.equals("1")) {
            if (cantChoripanes==0) {
                System.out.println("No hay choripanes registrados para editar");
                return;
            }

            System.out.println("=============== CHORIPANES =================");
            for (int i=0;i<cantChoripanes;i++) {
                Choripan c=listaChoripanes[i];
                String nom="Desconocido";
                if (c.getConsumidor()!=null) {
                    nom=c.getConsumidor().getNombre();
                }
                System.out.println((i+1)+"   "+nom+"   "+c.getLargo()+" cm x "+c.getAncho()+" cm   "+c.getFecha());
            }

            int indice=-2;
            do {
                System.out.print("Seleccione el registro a editar (0 para cancelar): ");
                try {
                    indice=Integer.parseInt(scanner.nextLine())-1;
                    if (indice<-1 || indice>=cantChoripanes) {
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (indice<-1 || indice>=cantChoripanes);

            if (indice==-1) {
                System.out.println("Edicion cancelada");
                return;
            }

            Choripan actual=listaChoripanes[indice];
            String nom="Desconocido";
            if (actual.getConsumidor()!=null) {
                nom=actual.getConsumidor().getNombre();
            }

            System.out.println("Registro actual -> Choripan | "+nom+" | "+actual.getLargo()+" cm x "+actual.getAncho()+" cm | "+actual.getFecha());
            System.out.println("Deje el campo vacio para mantener el valor actual");

            int nuevoLargo=actual.getLargo();
            boolean largoValido=false;
            do {
                System.out.print("Largo ["+actual.getLargo()+"]: ");
                String largoStr=scanner.nextLine();
                if (largoStr.equals("")) {
                    largoValido=true;
                } else {
                    try {
                        int v=Integer.parseInt(largoStr);
                        if (v>0) {
                            nuevoLargo=v;
                            largoValido=true;
                        } else {
                            System.out.println("El largo debe ser mayor a 0");
                        }
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un numero valido");
                    }
                }
            } while (!largoValido);

            int nuevoAncho=actual.getAncho();
            boolean anchoValido=false;
            do {
                System.out.print("Ancho ["+actual.getAncho()+"]: ");
                String anchoStr=scanner.nextLine();
                if (anchoStr.equals("")) {
                    anchoValido=true;
                } else {
                    try {
                        int v=Integer.parseInt(anchoStr);
                        if (v>0) {
                            nuevoAncho=v;
                            anchoValido=true;
                        } else {
                            System.out.println("El ancho debe ser mayor a 0");
                        }
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un numero valido");
                    }
                }
            } while (!anchoValido);

            System.out.print("Fecha ["+actual.getFecha()+"]: ");
            String nuevaFecha=scanner.nextLine();
            if (nuevaFecha.equals("")) {
                nuevaFecha=actual.getFecha();
            }

            listaChoripanes[indice]=new Choripan(actual.getConsumidor(),nuevaFecha,nuevoLargo,nuevoAncho);
            guardarConsumido();

            double kcal=nuevoLargo*nuevoAncho/3.0;
            System.out.println("Registro actualizado correctamente y guardado en Consumido.txt");
            System.out.println("Choripan | "+nom+" | "+nuevoLargo+" cm x "+nuevoAncho+" cm | "+nuevaFecha+" | "+kcal+" kcal");

        } else if (opcion.equals("2")) {
            if (cantTerremotos==0) {
                System.out.println("No hay terremotos registrados para editar");
                return;
            }

            System.out.println("=============== TERREMOTOS =================");
            for (int i=0;i<cantTerremotos;i++) {
                Terremoto t=listaTerremotos[i];
                String nom="Desconocido";
                if (t.getConsumidor()!=null) {
                    nom=t.getConsumidor().getNombre();
                }
                System.out.println((i+1)+"   "+nom+"   "+t.getLitros()+" L   "+t.getFecha());
            }

            int indice=-2;
            do {
                System.out.print("Seleccione el registro a editar (0 para cancelar): ");
                try {
                    indice=Integer.parseInt(scanner.nextLine())-1;
                    if (indice<-1 || indice>=cantTerremotos) {
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (indice<-1 || indice>=cantTerremotos);

            if (indice==-1) {
                System.out.println("Edicion cancelada");
                return;
            }

            Terremoto actual=listaTerremotos[indice];
            String nom="Desconocido";
            if (actual.getConsumidor()!=null) {
                nom=actual.getConsumidor().getNombre();
            }

            System.out.println("Registro actual -> Terremoto | "+nom+" | "+actual.getLitros()+" L | "+actual.getFecha());
            System.out.println("Deje el campo vacio para mantener el valor actual");

            double nuevosLitros=actual.getLitros();
            boolean litrosValido=false;
            do {
                System.out.print("Litros ["+actual.getLitros()+"]: ");
                String litrosStr=scanner.nextLine();
                if (litrosStr.equals("")) {
                    litrosValido=true;
                } else {
                    try {
                        double v=Double.parseDouble(litrosStr);
                        if (v>0) {
                            nuevosLitros=v;
                            litrosValido=true;
                        } else {
                            System.out.println("Los litros deben ser mayor a 0");
                        }
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un numero valido");
                    }
                }
            } while (!litrosValido);

            System.out.print("Fecha ["+actual.getFecha()+"]: ");
            String nuevaFecha=scanner.nextLine();
            if (nuevaFecha.equals("")) {
                nuevaFecha=actual.getFecha();
            }

            listaTerremotos[indice]=new Terremoto(actual.getConsumidor(),nuevaFecha,nuevosLitros);
            guardarConsumido();

            double kcal=nuevosLitros*10.0/3.0;
            System.out.println("Registro actualizado correctamente y guardado en Consumido.txt");
            System.out.println("Terremoto | "+nom+" | "+nuevosLitros+" L | "+nuevaFecha+" | "+kcal+" kcal");
        }
    }

    private static void Eliminar() {
        System.out.print("Tipo de registro a eliminar:\r\n"
                + "1) Choripan\r\n"
                + "2) Terremoto\r\n"
                + "3) Cancelar\r\n"
                + "Seleccione una opcion: ");
        String opcion=scanner.nextLine();

        if (opcion.equals("1")) {
            if (cantChoripanes==0) {
                System.out.println("No hay choripanes registrados para eliminar");
                return;
            }

            System.out.println("=============== CHORIPANES =================");
            for (int i=0;i<cantChoripanes;i++) {
                Choripan c=listaChoripanes[i];
                String nom="Desconocido";
                if (c.getConsumidor()!=null) {
                    nom=c.getConsumidor().getNombre();
                }
                System.out.println((i+1)+"   "+nom+"   "+c.getLargo()+" cm x "+c.getAncho()+" cm   "+c.getFecha());
            }

            int indice=-2;
            do {
                System.out.print("Seleccione el registro a eliminar (0 para cancelar): ");
                try {
                    indice=Integer.parseInt(scanner.nextLine())-1;
                    if (indice<-1 || indice>=cantChoripanes) {
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (indice<-1 || indice>=cantChoripanes);

            if (indice==-1) {
                System.out.println("Eliminacion cancelada");
                return;
            }

            Choripan c=listaChoripanes[indice];
            String nom="Desconocido";
            if (c.getConsumidor()!=null) {
                nom=c.getConsumidor().getNombre();
            }

            System.out.print("Confirma la eliminacion de \"Choripan | "+nom+" | "+c.getLargo()+" cm x "+c.getAncho()+" cm | "+c.getFecha()+"\"? (s/n): ");
            String conf=scanner.nextLine();

            if (conf.equalsIgnoreCase("s")) {
                for (int i=indice;i<cantChoripanes-1;i++) {
                    listaChoripanes[i]=listaChoripanes[i+1];
                }
                cantChoripanes--;
                guardarConsumido();
                System.out.println("Registro eliminado correctamente. Cambios guardados en Consumido.txt");
            }

        } else if (opcion.equals("2")) {
            if (cantTerremotos==0) {
                System.out.println("No hay terremotos registrados para eliminar");
                return;
            }

            System.out.println("=============== TERREMOTOS =================");
            for (int i=0;i<cantTerremotos;i++) {
                Terremoto t=listaTerremotos[i];
                String nom="Desconocido";
                if (t.getConsumidor()!=null) {
                    nom=t.getConsumidor().getNombre();
                }
                System.out.println((i+1)+"   "+nom+"   "+t.getLitros()+" L   "+t.getFecha());
            }

            int indice=-2;
            do {
                System.out.print("Seleccione el registro a eliminar (0 para cancelar): ");
                try {
                    indice=Integer.parseInt(scanner.nextLine())-1;
                    if (indice<-1 || indice>=cantTerremotos) {
                        System.out.println("Opcion fuera de rango, intente nuevamente");
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un numero valido");
                }
            } while (indice<-1 || indice>=cantTerremotos);

            if (indice==-1) {
                System.out.println("Eliminacion cancelada");
                return;
            }

            Terremoto t=listaTerremotos[indice];
            String nom="Desconocido";
            if (t.getConsumidor()!=null) {
                nom=t.getConsumidor().getNombre();
            }

            System.out.print("Confirma la eliminacion de \"Terremoto | "+nom+" | "+t.getLitros()+" L | "+t.getFecha()+"\"? (s/n): ");
            String conf=scanner.nextLine();

            if (conf.equalsIgnoreCase("s")) {
                for (int i=indice;i<cantTerremotos-1;i++) {
                    listaTerremotos[i]=listaTerremotos[i+1];
                }
                cantTerremotos--;
                guardarConsumido();
                System.out.println("Registro eliminado correctamente. Cambios guardados en Consumido.txt");
            }
        }
    }

    private static void calcularMetricas() {
        String opcion;

        do {
            System.out.print("============ CALCULAR METRICAS =============\r\n"
                    + "1) Choripanes comidos en total\r\n"
                    + "2) Terremotos tomados en total\r\n"
                    + "3) Choripanes comidos por usuario\r\n"
                    + "4) Terremotos tomados por usuario\r\n"
                    + "5) KCal consumidas por usuario\r\n"
                    + "6) Dias ebrio por usuario\r\n"
                    + "7) Volver al menu principal\r\n"
                    + "============================================\r\n"
                    + "Seleccione una opcion: ");

            opcion=scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Total de choripanes comidos: "+cantChoripanes);
                    break;

                case "2":
                    double totalL=0;
                    for (int i=0;i<cantTerremotos;i++) {
                        totalL+=listaTerremotos[i].getLitros();
                    }
                    System.out.println("Total de terremotos tomados: "+cantTerremotos+" ("+totalL+" litros en total)");
                    break;

                case "3":
                    System.out.println("======= CHORIPANES POR USUARIO =============");
                    System.out.println("Usuario       Cantidad");
                    for (int i=0;i<cantParticipante;i++) {
                        String nombre=listaParticipante[i].getNombre();
                        int cant=0;
                        for (int j=0;j<cantChoripanes;j++) {
                            if (listaChoripanes[j].getConsumidor()!=null
                                    && listaChoripanes[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)) {
                                cant++;
                            }
                        }
                        System.out.println(nombre+"               "+cant);
                    }
                    break;

                case "4":
                    System.out.println("======= TERREMOTOS POR USUARIO =============");
                    System.out.println("Usuario       Cantidad    Litros");
                    for (int i=0;i<cantParticipante;i++) {
                        String nombre=listaParticipante[i].getNombre();
                        int cant=0;
                        double lit=0;
                        for (int j=0;j<cantTerremotos;j++) {
                            if (listaTerremotos[j].getConsumidor()!=null
                                    && listaTerremotos[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)) {
                                cant++;
                                lit+=listaTerremotos[j].getLitros();
                            }
                        }
                        System.out.println(nombre+"               "+cant+"     "+lit+" L");
                    }
                    break;

                case "5":
                    System.out.println("============ KCAL POR USUARIO ==============");
                    System.out.println("Usuario       Choripan    Terremoto    Total");
                    double sumaTotalKcal=0;
                    for (int i=0;i<cantParticipante;i++) {
                        String nombre=listaParticipante[i].getNombre();
                        double kcalChori=0;
                        for (int j=0;j<cantChoripanes;j++) {
                            if (listaChoripanes[j].getConsumidor()!=null
                                    && listaChoripanes[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)) {
                                kcalChori+=listaChoripanes[j].getLargo()*listaChoripanes[j].getAncho()/3.0;
                            }
                        }
                        double kcalTerre=0;
                        for (int j=0;j<cantTerremotos;j++) {
                            if (listaTerremotos[j].getConsumidor()!=null
                                    && listaTerremotos[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)) {
                                kcalTerre+=listaTerremotos[j].getLitros()*10.0/3.0;
                            }
                        }
                        double totalUsr=kcalChori+kcalTerre;
                        sumaTotalKcal+=totalUsr;
                        System.out.println(nombre+"           "+kcalChori+"         "+kcalTerre+"    "+totalUsr);
                    }
                    System.out.println("Consumo total de la fonda: "+sumaTotalKcal+" kcal");
                    break;

                case "6":
                    System.out.println("========= DIAS EBRIO POR USUARIO ===========");
                    System.out.println("(se cuenta un dia ebrio con 2 L o mas en la misma fecha)");
                    System.out.println("Usuario       Dias ebrio   Detalle");

                    for (int i=0;i<cantParticipante;i++) {
                        String nombre=listaParticipante[i].getNombre();

                        String[] fechasUsr=new String[200];
                        int cantFechasUsr=0;

                        for (int j=0;j<cantTerremotos;j++) {
                            if (listaTerremotos[j].getConsumidor()!=null
                                    && listaTerremotos[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)) {
                                String f=listaTerremotos[j].getFecha();
                                boolean existe=false;
                                for (int k=0;k<cantFechasUsr;k++) {
                                    if (fechasUsr[k].equals(f)) {
                                        existe=true;
                                        break;
                                    }
                                }
                                if (!existe) {
                                    fechasUsr[cantFechasUsr]=f;
                                    cantFechasUsr++;
                                }
                            }
                        }

                        int diasEbrio=0;
                        String detalle="-";

                        for (int k=0;k<cantFechasUsr;k++) {
                            String f=fechasUsr[k];
                            double sumaLitrosDia=0;

                            for (int j=0;j<cantTerremotos;j++) {
                                if (listaTerremotos[j].getConsumidor()!=null
                                        && listaTerremotos[j].getConsumidor().getNombre().equalsIgnoreCase(nombre)
                                        && listaTerremotos[j].getFecha().equals(f)) {
                                    sumaLitrosDia+=listaTerremotos[j].getLitros();
                                }
                            }

                            if (sumaLitrosDia>=2.0) {
                                diasEbrio++;
                                if (detalle.equals("-")) {
                                    detalle=f+" ("+sumaLitrosDia+" L)";
                                } else {
                                    detalle+=", "+f+" ("+sumaLitrosDia+" L)";
                                }
                            }
                        }
                        System.out.println(nombre+"                 "+diasEbrio+"   "+detalle);
                    }
                    break;
            }

        } while(!opcion.equals("7"));
    }
}