import java.util.Scanner;
import java.util.ArrayList;

public class sorteo_bolas {
    public static void main(String[] args) {
        double saldo = 0.0;
        int opcion = 0;
        int numero_particpaciones;

        saldo += recargar_saldo(saldo);
        if (saldo != 0.0) {
            do {
                try {
                    menu(saldo);
                    opcion = pedir_numero(1, 4, "Elige una opcion desde ");
                    switch (opcion) {
                        case 1:
                            saldo += recargar_saldo(saldo);
                            break;
                        case 2:
                            numero_particpaciones = pedir_numero(0, 99999,
                                    "Cuantos numeros quieres? (Puedes elegir hasta el numero 99999)");
                            if (saldo >= numero_particpaciones * 3) {
                                saldo -= numero_particpaciones * 3;
                                saldo += numeros(1, 99999, numero_particpaciones);

                            } else {
                                System.out.println("No tiene suficiente saldo");
                            }
                            break;
                        case 3:
                            imprmir_lista_premios();
                            break;
                        case 4:

                            break;
                    }
                } catch (Exception e) {
                    System.out.println("No se permite ese tipo de dato.");
                }

            } while (opcion != 4);
        }
    }

    public static double recargar_saldo(double saldo) {
        System.out.println("¿Cuanto saldo quiere introducir?");
        System.out.println("Introduce un 0, si no tienes dinero para introducir");
        Scanner sc = new Scanner(System.in);
        saldo = sc.nextDouble();
        return saldo;
    }

    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }

    public static int pedir_numero(int desde, int hasta, String texto) {
        int N = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(texto + " entre " + desde + " hasta " + hasta);
            N = sc.nextInt();
        } while (N < desde || N > hasta);
        return N;
    }

    public static void menu(double saldo) {
        System.out.println("Tiene un saldo de " + saldo + "$");
        System.out.println("1. Recargar saldo");
        System.out.println("2. Escoger numeros");
        System.out.println("3. Ver premios");
        System.out.println("4. Salir");
    }

    public static void imprmir_lista_premios() {
        System.out.println("EL PRIMER PREMIO SON 3 000 000 $");
        System.out.println("DEL SEGUNDO AL CUARTO PREMIO SON 60 000 $");
        System.out.println("DEL QUINTO AL NOVENO PREMIO SON 20 000 $");
        System.out.println("EL RESTO DE PREMIOS SON 100 $ CADA UNO");
        System.out.println(
                "Si alguno de sus numeros seleccionados termina en la misma cifra que el numero premiado, se le entregará un reintegro de 20$");

    }

    public static int numeros(int desde, int hasta, int numeros_escoger) {
        String opcion;
        Scanner sc = new Scanner(System.in);
        int[] array = new int[numeros_escoger];
        int premio = 0;
        for (int i = 0; i < numeros_escoger; i++) {
            do {
                System.out.println("Desea elegir sus numeros o los quiere de manera aleatoria?");
                System.out.println("Escriba 'ELEGIR' o 'ALEATORIA'");
                opcion = sc.next();
            } while (opcion.equalsIgnoreCase("ALEATORIA") == false && opcion.equalsIgnoreCase("ELEGIR") == false);
            if (opcion.equalsIgnoreCase("ALEATORIA") == true) {
                array[i] = (randomizador(desde, hasta));
            } else {
                do {
                    System.out.println("Elige un numero (pueden repetirse los numeros)");
                    array[i] = sc.nextInt();
                } while (array[i] < desde || array[i] > hasta);
            }
        }
        premio = sacar_bolas(array);
        return premio;
    }

    public static int sacar_bolas(int[] numeros_elegidos) {
        ArrayList<Integer> numeros_sacados_grande = new ArrayList<Integer>();
        ArrayList<Integer> numeros_sacados_premios = new ArrayList<Integer>();
        int variable_aux;
        int premio = 0;

        for (int i = 0; i < 1009; i++) {
            do {
                variable_aux = randomizador(1, 99999);
            } while (numeros_sacados_grande.contains(variable_aux));
            numeros_sacados_grande.add(variable_aux);

            do {
                variable_aux = randomizador(1, 1009);
            } while (numeros_sacados_grande.contains(variable_aux));
            numeros_sacados_premios.add(variable_aux);
        }

        imprimir_premios(numeros_sacados_grande, numeros_sacados_premios, 3000000, 0, 1);
        imprimir_premios(numeros_sacados_grande, numeros_sacados_premios, 60000, 1, 4);
        imprimir_premios(numeros_sacados_grande, numeros_sacados_premios, 20000, 4, 9);
        imprimir_premios(numeros_sacados_grande, numeros_sacados_premios, 100, 9, numeros_sacados_premios.size());
        premio += comprobar(numeros_elegidos, numeros_sacados_grande);
        return premio;

    }

    public static int comprobar(int[] numeros_elegidos, ArrayList<Integer> numeros_sacados_grande) {
        int premio = 0;
        for (int i = 0; i < numeros_elegidos.length; i++) {
            if (numeros_sacados_grande.get(0) % 10 == numeros_elegidos[i] % 10) {
                System.out.println("Has ganado un reintegro de 20$ por el numero " + numeros_elegidos[i]);
                premio += 20;
            }
            if (numeros_sacados_grande.contains(numeros_elegidos[i])) {

                if (numeros_sacados_grande.indexOf(numeros_elegidos[i]) == 0) {
                    System.out.println("El numero " + numeros_elegidos[i] + " ha ganado 3000000$");
                    premio += 3000000;
                }
                if (numeros_sacados_grande.indexOf(numeros_elegidos[i]) > 0
                        && numeros_sacados_grande.indexOf(numeros_elegidos[i]) < 3) {
                    System.out.println("El numero " + numeros_elegidos[i] + " ha ganado 60000$");
                    premio += 60000;
                }
                if (numeros_sacados_grande.indexOf(numeros_elegidos[i]) > 3
                        && numeros_sacados_grande.indexOf(numeros_elegidos[i]) < 9) {
                    System.out.println("El numero " + numeros_elegidos[i] + " ha ganado 20000%");
                    premio += 20000;
                }
                if (numeros_sacados_grande.indexOf(numeros_elegidos[i]) > 8) {
                    System.out.println("El numero " + numeros_elegidos[i] + " ha ganado 100%");
                    premio += 100;
                }

            } else {

                System.out.println("El numero " + numeros_elegidos[i] + " no ha sido premiado");

            }

        }
        return premio;
    }

    public static void imprimir_premios(ArrayList<Integer> numeros_sacados_grande,
            ArrayList<Integer> numeros_sacados_premios, int premio, int i, int limite) {
        for (int j = i; j < limite; j++) {
            System.out.println("El premio de " + premio + " es para el numero " + numeros_sacados_grande.get(j)
                    + " con la bola premiada " + numeros_sacados_premios.get(j));
        }

    }

}
