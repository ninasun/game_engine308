package engine.gameobject.weapon.upgradetree.upgradebundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import engine.fieldsetting.Settable;
import engine.gameobject.weapon.Upgrade;
import engine.gameobject.weapon.upgradable.Upgradable;
import engine.gameobject.weapon.upgradetree.UpgradeTree;
import engine.shop.UpgradeTag;


/**
 * Concrete implementation of BuildableBundle
 * 
 * @author Nathan Prabhu
 *
 */
@Settable
public class UpgradeBundleSimple implements BuildableBundle {

    private List<Upgrade> upgrades;
    private UpgradeBundleSimple next;
    private boolean isFinal;
    private UpgradeTree parent;
    private UpgradeTag myUpgradeTag;

    public UpgradeBundleSimple (Upgrade ... upgrades) {
        this.upgrades = new ArrayList<>(Arrays.asList(upgrades));
        isFinal = false;
    }

    @Override
    public void applyUpgrades (Map<Class<? extends Upgradable>, Upgradable> upgradables) {
        upgrades.forEach(upgrade -> {
            Class<? extends Upgradable> upgradeType = upgrade.getType();

            // TODO: add Optional to clean this up?
            if (upgradables.keySet().contains(upgradeType)) {
                // upgrade existing upgradable
                upgrade.setDecorated(upgradables.get(upgradeType));
            }
                else {
                    // create new upgradable
                    upgrade.setDefault();
                }
                // put new upgradable in the map
                upgradables.put(upgradeType, upgradeType.cast(upgrade));
                // TODO: must add listener to Behavior list to update
            });
    }

    @Override
    public void addChild (Buildable child) {
        next = (UpgradeBundleSimple) child;
    }

    @Override
    public boolean isFinalUpgrade () {
        return isFinal;
    }

    @Override
    public void markFinalUpgrade () {
        isFinal = true;
    }

    @Override
    public BuildableBundle getNext () {
        // if isFinal, shop will disallow further purchase and change graphics
        return (isFinal) ? this : next;
    }

    @Override 
    public UpgradeTree getParent () {
        return parent;
    }
    
    @Override
    public void setParent (UpgradeTree tree) {
        parent = tree;
    }

    @Override
    public UpgradeTag getTag () {
        return myUpgradeTag;
    }

    @Override
    public double getValue () {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Settable
    public void setUpgradeTag (UpgradeTag upgradeTag) {
        myUpgradeTag = upgradeTag;
    }

}
