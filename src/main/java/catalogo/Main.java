package catalogo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Aggiungi elemento\n2. Ricerca per ISBN\n3. Statistiche\n0. Esci");
            System.out.print("Scegli: ");
            String scelta = scanner.nextLine();

            try {
                switch (scelta) {
                    case "1":
                        System.out.print("Libro (L) o Rivista (R)? ");
                        String tipo = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Titolo: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Anno pubblicazione: ");
                        int anno = Integer.parseInt(scanner.nextLine());
                        System.out.print("Numero pagine: ");
                        int pagine = Integer.parseInt(scanner.nextLine());
                        if (tipo.equalsIgnoreCase("L")) {
                            System.out.print("Autore: ");
                            String autore = scanner.nextLine();
                            System.out.print("Genere: ");
                            String genere = scanner.nextLine();
                            archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
                        } else {
                            System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                            Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                            archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, periodicita));
                        }
                        System.out.println("Elemento aggiunto.");
                        break;
                    case "2":
                        System.out.print("ISBN: ");
                        String isbnRicerca = scanner.nextLine();
                        ElementoCatalogo trovato = archivio.ricercaPerIsbn(isbnRicerca);
                        System.out.println("Trovato: " + trovato.getTitolo());
                        break;
                    case "3":
                        archivio.statistiche();
                        break;
                    case "0":
                        System.out.println("Uscita.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Numero opzione non valido.");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }
}
