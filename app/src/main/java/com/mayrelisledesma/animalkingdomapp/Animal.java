package com.mayrelisledesma.animalkingdomapp;

public class Animal {
    private String codAnim;
    private String tipoAnim;
    private String descAnim;
    private int fotoResid; //ID de recurso de la imagen
    private int sonidoResid; //ID de recurso del sonido

    public Animal(String codAnim, String tipoAnim, String descAnim, int fotoResid, int sonidoResid){
        this.codAnim = codAnim;
        this.tipoAnim = tipoAnim;
        this.descAnim = descAnim;
        this.fotoResid = fotoResid;
        this.sonidoResid = sonidoResid;
    }

    public String getCodAnim() {return codAnim;}
    public String getTipoAnim() {return tipoAnim;}
    public String getDescAnim() {return descAnim;}
    public int getFotoResid() {return fotoResid;}
    public int getSonidoResid() {return sonidoResid;}
}
