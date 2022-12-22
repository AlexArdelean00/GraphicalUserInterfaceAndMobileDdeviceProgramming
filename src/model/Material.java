package model;

import utils.Color;
import utils.Vector3;

public class Material {

	// ********************************************************************
	// This class is used to represent the material properties of a
	// Renderable object, it also handle the light-material interaction.
	// ********************************************************************

	// Instance Attribute
	private Color color;
	private float transparency, roughness, emission, refractiveIndex;

	// Constructor
	public Material(Color color, float transparency, float roughness, float emission, float refractiveIndex) {
		this.color = color;
		this.transparency = transparency;
		this.roughness = roughness;
		this.emission = emission;
		if(refractiveIndex<1f)
			this.refractiveIndex=1f;
		else
			this.refractiveIndex=refractiveIndex;
	}

	public Material() {
		this.color = Color.WHITE;
		this.transparency = 0f;
		this.roughness = 1f;
		this.emission = 0f;
		this.refractiveIndex=1f;
	}

	public Color lightMaterialInteraction(Ray ray, HitRecord hit) {
		Color attenuation = this.color;
		ray.setPosition(hit.getPoint());
		Vector3 incident = ray.getDirection();
		Vector3 normal = hit.getNormal();
		double isReflected = Math.random();

		if(isReflected >= transparency){
			ray.setDirection(reflect(normal, incident));
		}
		else{
			attenuation = Color.WHITE;
			double n1, n2;
			if(normal.dot(incident) < 0){
				n1 = 1;
				n2 = this.refractiveIndex;
			}
			else{
				n1 = this.refractiveIndex;
				n2 = 1;
				normal = normal.scale(-1);
			}
			Vector3 refract = refract(normal, incident, n1, n2);
			if(refract == null || reflectance(incident, normal, n1, n2) > Math.random()) // Total internal reflection
				ray.setDirection(reflect(normal, incident));
			else
				ray.setDirection(refract(normal, incident, n1, n2));
				
		}
		return attenuation;
	}

	private Vector3 reflect(Vector3 normal, Vector3 incident) {
		double reflectionType = Math.random();
		Vector3 refl;
		if(reflectionType>roughness) {
			// Specular Reflection [Metal Behaviour]
			refl = specReflect(normal, incident);
		}
		else{
			// Diffuse Reflection [Diffuse Behaviour]
			refl = normal.add(Vector3.randomUnitVector()).normalize();
		}
		return refl;
	}

	private Vector3 specReflect(Vector3 normal, Vector3 incident) {
		double cosI = -normal.dot(incident);
		return incident.add(normal.scale(2*cosI));
	}

	private Vector3 refract(Vector3 normal, Vector3 incident, double n1, double n2){
		double n = n1/n2;
		double cosI = -normal.dot(incident);
		double sinT2 = n*n*(1-cosI*cosI);
		if(sinT2 > 1)
			return null; // Total internal reflection
		double cosT = Math.sqrt(1 - sinT2);
		return incident.scale(n).add(normal.scale(n*cosI - cosT));
	}

	private double reflectance(Vector3 incident, Vector3 normal, double n1, double n2){
		// Using shlick apprx
		double cos = -incident.dot(normal);
		double n = n1/n2;
		double r0 = (1-n)/(1+n);
		r0 *= r0;
		return r0 + (1-r0)*Math.pow((1-cos), 5);
	}

	// Getter-Setter
	public float getEmission() {
		return emission;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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
	public void setEmission(float emission) {
		this.emission = emission;
	}

	public float getRefractiveIndex() {
		return this.refractiveIndex;
	}

	public void setRefractiveIndex(float refractiveIndex) {
		this.refractiveIndex = refractiveIndex;
	}


	// To String
	@Override
	public String toString() {
		return "Material{" +
				"color=" + color +
				", transparency=" + transparency +
				", roughness=" + roughness +
				", emission=" + emission +
				", refractiveIndex=" + refractiveIndex +
				'}';
	}
}
