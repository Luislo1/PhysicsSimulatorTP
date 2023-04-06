package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body> {
	private String gid;
	private ForceLaws forces;
	private List<Body> list, listRO;
	
	public BodiesGroup(String gid, ForceLaws forces) {
		if (gid == null || forces == null || gid.trim().length() <= 0)
			throw new IllegalArgumentException("One of the parameters of your bodies group is invalid");
		this.gid = gid;
		this.forces = forces;
		list = new ArrayList<Body>();
		listRO = Collections.unmodifiableList(list);
	}
	
	public String getId() {
		return gid;
	}
	void setForceLaws(ForceLaws fl) {
		if (fl == null)
			throw new IllegalArgumentException("An invalid force law has been passed to the body group");
		forces = fl;
	}
	
	void addBody(Body b) { 
		if (b == null || list.contains(b))
			throw new IllegalArgumentException("Invalid body cannot be added");
		list.add(b);
	}
	void advance(double dt) {//reset all + apply laws + advance all
		if (dt <= 0)
			throw new IllegalArgumentException("Delta time must be a positive value");
		
		for (Body body : list) {
			body.resetForce();
			
		}
		forces.apply(list);
		for (Body body : list) {
			body.advance(dt);
		}
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", gid);
		JSONArray ja = new JSONArray();
		for (Body body : list) {
			ja.put(body.getState());
		}
		jo.put("bodies", ja);
		return jo;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public String getForceLawsInfo() {
		return forces.toString();
	}
	
	public List<Body> getBodiesList() { // TODO unused yet.
		return listRO;
	}

	@Override
	public Iterator<Body> iterator() {
		return new Iterator<Body>() {
			Iterator<Body> it = list.iterator();

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Body next() {
				return it.next();
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException("Can't remove from unmodifiable list");
			}
			
		};
	}
}
