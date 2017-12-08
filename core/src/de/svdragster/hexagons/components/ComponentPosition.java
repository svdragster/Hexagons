package de.svdragster.hexagons.components;

/**
 * Created by z003p2yd on 08.12.2017.
 */

public class ComponentPosition extends Component {

    public double X = 0;
    public double Y = 0;

    public ComponentPosition(double X,double Y) {
        super.setType(ComponentType.POSITION);
        this.X = X;
        this.Y = Y;
    }

}
