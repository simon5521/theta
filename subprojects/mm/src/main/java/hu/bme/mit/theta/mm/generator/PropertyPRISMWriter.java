package hu.bme.mit.theta.mm.generator;

public class PropertyPRISMWriter {

    private PropertyPRISMWriter(){}

    public static PropertyPRISMWriter getInstance() {
        return LazyHolder.propertyPRISMWriter;
    }

    private static class LazyHolder {
        public static final PropertyPRISMWriter propertyPRISMWriter=new PropertyPRISMWriter();
    }


}
