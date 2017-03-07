package com.example.simon.bubble_level.Model;

/**
 * Created by Guillaume on 2016-09-22.
 */

public class NavDrawerItem {

    private boolean affichageNotif;
    private String titre;


    public NavDrawerItem()
    {

    }

    public NavDrawerItem(boolean affichageNotif, String titre)
    {
        this.affichageNotif = affichageNotif;
        this.titre = titre;
    }

    public boolean isShowNotify()
    {
        return affichageNotif;
    }

    public void setShowNotify(boolean affichageNotif)
    {
        this.affichageNotif = affichageNotif;
    }

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

}
