
package pad;

/**
 *
 * @author Jeroen
 */
public class Cargo {

    public int cargoNumber;
    public String product;
    public int weight;
    public int volume;
    public int klantId;
    public String foh;

    public Cargo(int cargoNumber, String product, int weight, int volume, int klantId, String foh) {
        this.cargoNumber = cargoNumber;
        this.product = product;
        this.weight = weight;
        this.volume = volume;
        this.klantId = klantId;
        this.foh = foh;
    }

    
}
