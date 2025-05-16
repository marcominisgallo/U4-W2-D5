package catalogo;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    private Map<String, ElementoCatalogo> elementi = new HashMap<>();

    public void aggiungiElemento(ElementoCatalogo elemento) {
        if (elementi.containsKey(elemento.getIsbn())) {
            throw new IllegalArgumentException("Elemento con ISBN già presente.");
        }
        elementi.put(elemento.getIsbn(), elemento);
    }

    public ElementoCatalogo ricercaPerIsbn(String isbn) throws ElementoNonTrovatoException {
        ElementoCatalogo e = elementi.get(isbn);
        if (e == null) throw new ElementoNonTrovatoException("Elemento non trovato con ISBN: " + isbn);
        return e;
    }

    public void rimuoviElemento(String isbn) throws ElementoNonTrovatoException {
        if (elementi.remove(isbn) == null) {
            throw new ElementoNonTrovatoException("Elemento da rimuovere non trovato con ISBN: " + isbn);
        }
    }

    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return elementi.values().stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<Libro> ricercaPerAutore(String autore) {
        return elementi.values().stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws ElementoNonTrovatoException {
        if (!elementi.containsKey(isbn)) {
            throw new ElementoNonTrovatoException("Elemento da aggiornare non trovato per ISBN: " + isbn);
        }
        elementi.put(isbn, nuovoElemento);
    }

    public void statistiche() {
        long numLibri = elementi.values().stream().filter(e -> e instanceof Libro).count();
        long numRiviste = elementi.values().stream().filter(e -> e instanceof Rivista).count();
        Optional<ElementoCatalogo> maxPagine = elementi.values().stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine));
        double mediaPagine = elementi.values().stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average().orElse(0);

        System.out.println("Numero libri: " + numLibri);
        System.out.println("Numero riviste: " + numRiviste);
        System.out.println("Elemento con più pagine: " +
                (maxPagine.isPresent() ? maxPagine.get().getTitolo() + " (" + maxPagine.get().getNumeroPagine() + " pagine)" : "Nessuno"));
        System.out.println("Media pagine: " + mediaPagine);
    }

    public Collection<ElementoCatalogo> getTuttiGliElementi() {
        return elementi.values();
    }
}