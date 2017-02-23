
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TimeServer {

    private static final String[] nomDies = {"Diumenge", "Dilluns", "Dimarts",
        "Dimecres", "Dijous", "Divendres", "Dissabte"};

    public static void main(String[] args) {

        try {
            ServerSocket srvSocket = new ServerSocket(10000);

            while (true) {
                Socket cliSocket = srvSocket.accept();
                Scanner in = new Scanner(cliSocket.getInputStream());
                int[] data = new int[3];
                boolean ok = true;
                for (int i = 0; i < data.length; i++) {
                    if (in.hasNextInt()) {
                        data[i] = in.nextInt();
                    } else {
                        ok = false;
                    }
                }

                PrintStream out = new PrintStream(cliSocket.getOutputStream());
                if (ok) {
                    data[1] -= 1;
                    GregorianCalendar cal = new GregorianCalendar(data[2],
                            data[1], data[0]);
                    int dia = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    out.println("Aquest dia era " + nomDies[dia] + ".");

                } else {
                    out.println("Format de les dades incorrecte.");
                }
                cliSocket.close();
            }
        } catch (Exception ex) {
            System.out.println("Error en les comuncacions: " + ex);
        }

    }

}
