/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author aubin
 */
public class PriceLocalisationEntity {
    private float  codePostal;
    
    private float prix;

    public PriceLocalisationEntity(float codePostal, float prix ){
    this.codePostal = codePostal;
    this.prix = prix;
}
    
    public float getCodePostal() {
        return codePostal;
    }

    public float getPrix() {
        return prix;
    }
}
