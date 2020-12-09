package detector;

public enum IrisColor {
    //TODO implement method for converting numeric values (HSV) to color
    NIEBIESKI(0,180),
    BRAZOWY(0,180),
    ZIELONY(0,180),
    SZARY(0, 180);

    private int lowerBoundHue;
    private int upperBoundHue;

    IrisColor(int lowerBound, int upperBound){
        this.lowerBoundHue = lowerBound;
        this.upperBoundHue = upperBound;
    }

    public int getLowerBoundHue() {
        return lowerBoundHue;
    }

    public int getUpperBoundHue() {
        return upperBoundHue;
    }
}
