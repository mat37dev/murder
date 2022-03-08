package frm.mat_37.murder.murder.gameplay;
import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;



public class GameManager implements Listener {

    private int playerMin;
    private int playerMax;


    private main main;
    public GameManager(main main) {
        this.main= main;
        playerMin = 1;
        playerMax = 1;
    }



    public void playerJoin(Player player, MArena arene){

        if(arene.getListPlayers().size() < playerMax){
            //on ajoute le joueur a la liste de joueur de l'arene
            arene.joinArene(player);
            //on ajoute le joueur et l'arene au dico
            main.joueurInArene.put(player, arene);
            player.sendMessage("[Murder] Vous avez rejoint l'arène: "+ arene.getName());
            player.teleport(arene.getLobby());
            if(arene.getListPlayers().size() >= playerMin && !arene.getTimerStatue()){
                arene.setTimerStatue(true);
                arene.timerStart();
            }
        }
        else{
            player.sendMessage("Arène complète");
        }

    }
    public void pLayerLeave(Player player, MArena arene){
        //on supprime le joueur et on reset son niveau
        arene.leaveArene(player);
        player.teleport(main.hub);
        main.joueurInArene.remove(player);
        player.setLevel(0);
        player.sendMessage("[Murder] Vous avez quitté l'arène" + arene.getName());
        //on check si il y a toujours le nombres minimum de joueurs pour jouer
        if(arene.getListPlayers().size() < playerMin){
            arene.timerStop();
            for (Player pls:arene.getListPlayers()) {
                pls.sendMessage("Partie annulé: pas assez de monde");
            }
        }
    }


}
