package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	PhysicsSimulator simulator;
	Factory<ForceLaws> forceLawsFactory;
	Factory<Body> bodyFactory;
	
	public Controller(PhysicsSimulator simulator, Factory<ForceLaws> forceLawsFactory, Factory<Body> bodyFactory) {
		this.simulator = simulator;
		this.forceLawsFactory = forceLawsFactory;
		this.bodyFactory = bodyFactory;
	}
	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray ja = jsonInput.getJSONArray("groups");
		for (int i = 0; i < ja.length(); i++) {
			simulator.addGroup(ja.getString(i));
		}
		if (jsonInput.has("laws")) { //You may use default laws, in which case it's not in the json Input
			ja = jsonInput.getJSONArray("laws");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				simulator.setForceLaws(jo.getString("id") , forceLawsFactory.createInstance(jo.getJSONObject("laws")));
			}
		}
		ja = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < ja.length(); i++) {
			simulator.addBody(bodyFactory.createInstance(ja.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(simulator.toString());
		for (int i = 0; i < n; i++) {
			p.println(",");
			simulator.advance();
			p.println(simulator.toString());
		}
		
		p.println("]");
		p.println("}");
	}
	
	
}
