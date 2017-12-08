package de.svdragster.hexagons.components;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class ComponentMovement extends Component {

    double dX = 0;
    double dY = 0;

    ComponentMovement(double dX,double dY) {
        super.setType(ComponentType.MOVEMENT);
        this.dX = dX;
        this.dY = dY;
    }

}
