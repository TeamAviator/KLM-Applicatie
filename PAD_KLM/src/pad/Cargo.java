/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pad;

/**
 *
 * @author florian_2
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