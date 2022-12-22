package model;

import java.util.ArrayList;

import utils.Color;
import utils.Vector3;

public class Camera extends Object3D{
	// ********************************************************************
	// This class rapresents a virtual camera, is used to take 
	// pictures of a scene.
	// ********************************************************************

	// Instance Variables
	private Vector3 up, right, lookAtPoint;
	private double fov;
	private int width, height;
	private double virtualScreenDimension;
	private double ambientLight;

	// Constants
	private final double ARCBALL_SPEED = 0.01;
	private final double SHIFT_SPEED = 10.0;
	private final double VIRTUAL_SCREEN_DIMENSION = 2.0;
	private final double MIN_CAMERA_LOOK_AT_DISTANCE = 1;

	// Constructor
	public Camera(Vector3 position, Vector3 lookAtPoint) {
		super(position, Vector3.NULL_VECTOR3);
		this.lookAtPoint = lookAtPoint;
		setCorrectDirection();
		fov = Math.PI/2;
		ambientLight = 0.5;
	}

	public Camera() {
		super(Vector3.ONES_VECTOR3.scale(100), Vector3.NULL_VECTOR3);
		this.lookAtPoint = Vector3.NULL_VECTOR3;
		setCorrectDirection();
		fov = Math.PI/2;
		ambientLight = 0.5;
	}

	// Instance Methods
	public int getObjectIdAt(int x, int y, ArrayList<Renderable> objects) {
		double pixelDim = virtualScreenDimension/this.width;
		double aspectRatio = (double)this.width/this.height;
		double virtualScreenX = x*pixelDim-virtualScreenDimension/2;
		double virtualScreenY = -y*pixelDim+virtualScreenDimension/(2*aspectRatio);
		Vector3 dir = calcRayDirection(virtualScreenX, virtualScreenY, pixelDim);
		Ray ray = new Ray();
		ray.setPosition(position);
		ray.setDirection(dir);
		// Find the closest hit
		HitRecord closestHit = new HitRecord();
		Renderable o = null;
		for(Renderable obj : objects){
			HitRecord hit = obj.intersection(ray);
			if(hit.getDistance() < closestHit.getDistance()){
				closestHit = hit;
				o = obj;
			}
		}
		if(o != null)
			return o.getId();
		else
			return -1;
	}

	// This method set the camera direction towards the loolAtPoint vector
	private void setCorrectDirection(){
		direction = lookAtPoint.subtract(this.position).normalize();
		this.right = direction.cross(Vector3.UNIT_VECTOR_Z).normalize();
		this.up = right.cross(direction);
	}

	public int getPixelColor(int x, int y, int samples, ArrayList<Renderable> objects) {
		if(x<0 || x>this.width || y<0 || y>this.height)
			return -1;

		double pixelDim = virtualScreenDimension/this.width;
		double aspectRatio = (double)this.width/this.height;
		double virtualScreenX = x*pixelDim-virtualScreenDimension/2;
		double virtualScreenY = -y*pixelDim+virtualScreenDimension/(2*aspectRatio);

		Ray ray = new Ray();
		ray.setPosition(this.position);
		ray.setColor(Color.WHITE);

		Color pixelColor = Color.BLACK;
		for(int s=0; s<samples; s++) {
			// Reset ray properties
			ray.setColor(Color.WHITE);
			ray.setPosition(this.position);
			ray.setDirection(this.calcRayDirection(virtualScreenX, virtualScreenY, pixelDim));
			ray.resetBounces();

			// RayTrace the ray through the scene
			pixelColor = pixelColor.average(rayTrace(ray, objects), samples);
		}
		
		return pixelColor.getRGB();
	}

	// This method is used to calculate the correct ray direction towards (x,y)
	// pixel of the virtual camera
	private Vector3 calcRayDirection(double x, double y, double pixelDim) {
		double virtualScreenDistance = VIRTUAL_SCREEN_DIMENSION/(2*Math.tan(this.fov/2));
		Vector3 pixelPosition = this.direction.scale(virtualScreenDistance)
				.add(this.up.scale(y + pixelDim*Math.random())
						.add(right.scale(x + pixelDim*Math.random())));
		return pixelPosition.normalize();
	}

	private Color rayTrace(Ray ray, ArrayList<Renderable> objects) {
		// Find the closest hit
		HitRecord closestHit = new HitRecord();
		for(Renderable obj : objects){
			HitRecord hit = obj.intersection(ray);
			if(hit.getDistance() < closestHit.getDistance())
				closestHit = hit;
		}

		if(closestHit.isHitted() && ray.canBounce()) {
			// Object hit
			if(closestHit.getMaterial().getEmission() == 0) {
				// The hitted object is not a light source
				ray.incrementBounces();
				Color attenuation = closestHit.getMaterial().lightMaterialInteraction(ray, closestHit); // Scatter and get attenuation
				ray.setColor(ray.getColor().product(attenuation));
				rayTrace(ray, objects);
			}
			else{
				// The hitted object is a light source => return the light color
				ray.setColor(ray.getColor().product(closestHit.getMaterial().getColor().scale(closestHit.getMaterial().getEmission())));
			}
		}
		else{
			// No object hit
			float a = (float)ambientLight;
			float c = (float)(0.5*(ray.getDirection().getZ() + 1.0));
			Color col = (Color.WHITE.scale(1-c).add((new Color(0.5f, 0.7f, 1f)).scale(c))).scale(a);
			//ray.setColor(ray.getColor().product(new Color(0.4*c,0.7*c,c)));
			ray.setColor(ray.getColor().product(col));
		}
		return ray.getColor();
	}

	// Camera Controller
	public void arcball(int arcballRight, int arcballUp) {
		Vector3 relativePosition = this.position.subtract(lookAtPoint);
		double radius = relativePosition.magnitude();

		Vector3 sum = up.scale(arcballUp * ARCBALL_SPEED * radius).add(right.scale(arcballRight * ARCBALL_SPEED * radius));
		this.position = relativePosition.add(sum).normalize().scale(radius).add(lookAtPoint);
		setCorrectDirection();
	}

	public void lookDirectionShift(int unitsToScroll) {
		if(unitsToScroll<0) {
			Vector3 newPos = this.position.add(this.direction.scale(SHIFT_SPEED));
			if((newPos.subtract(lookAtPoint)).magnitude()>MIN_CAMERA_LOOK_AT_DISTANCE)
				this.position = newPos;
		}
		else {
			this.position = this.position.add(this.direction.scale(-SHIFT_SPEED));
		}
		setCorrectDirection();
	}


	// Getter-Setter
	public void setLookAtPoint(Vector3 lookAtPoint) {
		this.lookAtPoint = lookAtPoint;
		setCorrectDirection();
	}

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setVirtualDim(double widthOccupation) {
		this.virtualScreenDimension = widthOccupation*VIRTUAL_SCREEN_DIMENSION;
	}

    public void setAmbientLight(double value) {
		this.ambientLight = value;
    }

}
