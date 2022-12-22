package utils;

public class ObjectProperties {
    
    // -----------Object3D-------------
    private Vector3 position;
    private Vector3 rotation;
    // ----------Renderable------------
    private String name; // This is used like an ID (must be unique)
    // Material
	private float transparency, roughness, emission, refractiveIndex;
    // Material-color
    private Color color;
	private String type;
    // ----------Sphere---------------
    private double radius;
    // -----------ID-------------
    private int id;

    // Static Field
    public static String SPHERE_OBJ_TYPE = "Sphere";
    public static String PLANE_OBJ_TYPE = "Plane";
    public static final String DEFAULT_NAME = "NewObject";

    public static final String POSITION_STRING = "Position";
    public static final String POSITION_X_STRING = "PositionX";
    public static final String POSITION_Y_STRING = "PositionY";
    public static final String POSITION_Z_STRING = "PositionZ";
    public static final String ROTATION_STRING = "Rotation";
    public static final String ROTATION_X_STRING = "RotationX";
    public static final String ROTATION_Y_STRING = "RotationY";
    public static final String ROTATION_Z_STRING = "RotationZ";
    public static final String NAME_STRING = "Name";
    public static final String TRANSPARENCY_STRING = "Transparency";
    public static final String ROUGHNESS_STRING = "Roughness";
    public static final String EMISSION_STRING = "Emission";
    public static final String REFRACTIVE_INDEX_STRING = "Refractive Index";
    public static final String COLOR_STRING = "Color";
    public static final String COLOR_R_STRING = "ColorR";
    public static final String COLOR_G_STRING = "ColorG";
    public static final String COLOR_B_STRING = "ColorB";
    public static final String TYPE_STRING = "Type";
    public static final String RADIUS_STRING = "Radius";
    public static final String ID_STRING = "ID";

    public static final double MAX_POSITION_COMPONENT = 10000;
    public static final double MIN_POSITION_COMPONENT = -MAX_POSITION_COMPONENT;
    public static final double POSITION_COMPONENT_STEP = 1;

    public static final double MAX_ROTATION_COMPONENT = 2*Math.PI;
    public static final double MIN_ROTATION_COMPONENT = -MAX_ROTATION_COMPONENT;
    public static final double ROTATION_COMPONENT_STEP = 0.1;

    public static final double MIN_RADIUS = 0;
    public static final double MAX_RADIUS = 1000;
    public static final double RADIUS_STEP = 1;

    public static final double MIN_TRANSPARENCY = 0;
    public static final double MAX_TRANSPARENCY = 1;
    public static final double MIN_ROUGHNESS = 0;
    public static final double MAX_ROUGHNESS = 1;
    public static final double MIN_EMISSION = 0;
    public static final double MAX_EMISSION = 5;
    public static final double MIN_REFRACTIVE_INDEX = 1;
    public static final double MAX_REFRACTIVE_INDEX = 100;
    public static final double MATERIAL_PROP_STEP = 0.1;

    public static final int MAX_NAME_CHAR = 20;

    public static final String[] EXCLUDED_FROM_ROTATION_SETTER = {SPHERE_OBJ_TYPE};
    public static final int SPHERE_COMPLEXITY = 5;
    public static final double  PLANE_COMPLEXITY = 3;
    public static final int MAX_COMPLEXITY = 100;

    public ObjectProperties(Vector3 position, Vector3 rotation,
        String name, 
        float transparency, float roughness, float emission, float refractiveIndex,
        Color color, String type,
        double radius,
        int id) {

        this.position = position;
        this.rotation = rotation;
        this.name = name;
        this.transparency = transparency;
        this.roughness = roughness;
        this.emission = emission;
        this.refractiveIndex = refractiveIndex;
        this.color = color;
        this.type = type;
        this.radius = radius;
        this.id = id;
    }

    public ObjectProperties(){
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.name = DEFAULT_NAME;
        this.transparency = 0f;
        this.roughness = 1f;
        this.emission = 0f;
        this.refractiveIndex = 1f;
        this.color = new Color();
        this.type = SPHERE_OBJ_TYPE;
        this.radius = 0;
        this.id = -1;
    }

    public ObjectProperties(String type){
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.name = DEFAULT_NAME;
        this.transparency = 0f;
        this.roughness = 1f;
        this.emission = 0f;
        this.refractiveIndex = 1f;
        this.color = new Color();
        this.type = type;
        this.radius = 0;
        this.id = -1;
    }

    // GetterSetter
    public Vector3 getPosition() {
        return this.position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRotation() {
        return this.rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTransparency() {
        return this.transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public float getRoughness() {
        return this.roughness;
    }

    public void setRoughness(float roughness) {
        this.roughness = roughness;
    }

    public float getEmission() {
        return this.emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }

    public float getRefractiveIndex() {
        return this.refractiveIndex;
    }

    public void setRefractiveIndex(float refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String[] getObjectsTypes() {
        String[] list = {SPHERE_OBJ_TYPE, PLANE_OBJ_TYPE};
        return list;
    }
}
