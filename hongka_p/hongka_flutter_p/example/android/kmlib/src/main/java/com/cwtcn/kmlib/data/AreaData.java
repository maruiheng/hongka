package com.cwtcn.kmlib.data;

import java.util.List;

/***
 * 电子栅栏
 * @author Allen
 *
 */
public class AreaData {
	//{"id":318515887856832512,"lut":"161115163245","type":0}
	private String id = "0";
	private String name;
	private String lut;
	private int type;
	//此处应为坐标点，请调试时更改
	public List<List<Double>> cors;
	/** 是否开启 */
	private boolean enabled = false;
	public List<Geometries> geometries;

	public class Geometries {
		public List<List<Double>> coordinates;

		public String shape;
	}

	public AreaData(String id){
		this.id = id;
	}

	public AreaData(String name,  List<List<Double>> cors, boolean enabled) {
		this.name = name;
		this.cors = cors;
		this.enabled = enabled;
	}

	public AreaData(String id, String name,  List<List<Double>> cors, boolean enabled) {
		this.id = id;
		this.name = name;
		this.cors = cors;
		this.enabled = enabled;
	}

	public AreaData(String id, String name,  List<List<Double>> cors) {
		this.id = id;
		this.name = name;
		this.cors = cors;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLut() {
		return lut;
	}

	public void setLut(String lut) {
		this.lut = lut;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Geometries> getGeometries() {
		return geometries;
	}

	public List<List<Double>> getCors(){
		return cors;
	}

	public void setGeometries(List<Geometries> geometries) {
		this.geometries = geometries;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
