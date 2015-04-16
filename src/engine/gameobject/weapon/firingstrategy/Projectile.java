package engine.gameobject.weapon.firingstrategy;

import java.util.HashSet;
import java.util.Set;
import engine.gameobject.GameObject;
import engine.gameobject.GameObjectSimple;
import engine.gameobject.PointSimple;
import engine.gameobject.units.Buff;
import engine.gameobject.units.Buffable;
import engine.gameobject.units.BuffableUnit;
import engine.gameobject.weapon.Weapon;
import engine.pathfinding.EndOfPathException;
import gameworld.ObjectCollection;


public class Projectile extends GameObjectSimple implements Buffer {
    protected Set<String> collidedID;
    protected Set<Buff> onCollision;
    protected Explosion onDeath;
    
    public Projectile () {
        collidedID = new HashSet<String>();
        onCollision = new HashSet<Buff>();
        onDeath = new Explosion();
    }

    public void addCollisionBehavior(Buff newBuff){
        removeDuplicateBuff(newBuff);
        onCollision.add(newBuff);
    }
    
    public void setOnDeathRadius(double radius){
        onDeath.setRadius(radius);
    }
    
    public void addOnDeath(Buff newBuff){
        onDeath.addBuff(newBuff);;
    }
    
    @Override
    public void impartBuffs (Buffable obstacle) {
        onCollision(obstacle);
    }
    
    private void removeDuplicateBuff(Buff newBuff){
        for (Buff b: onCollision){
            if (b.getClass().equals(newBuff.getClass())){
                onCollision.remove(b);
            }
        }
    }
    
    public Projectile clone(){
        Projectile clone = (Projectile) super.clone();
        return clone;
    }
    
    // Many conditions have to be met for an projectile to impart its effects.
    // For example, it must be on the "other side", it must not be another projectile, etc.
    private boolean effectiveCollision (GameObject obstacle) {
        //TODO: Enable the following code by adding "object identification" and "teams"
        /*if (!collidedID.contains(obstacle.getID())){
            if(obstacle.getTeam()!= getTeam())
                return true;
        }*/
        return true;
    }

    private void onCollision (Buffable obstacle) {
        if (effectiveCollision(obstacle)) {
            for (Buff b: onCollision){
                obstacle.addBuff(b);
            }
            changeHealth(-1);
        }
    }

    @Override
    public void update (ObjectCollection world) {
        try {
            move();
        }
        catch (EndOfPathException e) {
            //TODO: Implement end of path behavior
            changeHealth(-100);
        }
    }

    public void onDeath(ObjectCollection world){
        onDeath.explode(world, getPoint().add(new PointSimple(20, 20)));
    }

}
