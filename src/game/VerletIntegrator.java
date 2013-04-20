package game;

import java.util.ArrayList;
import java.util.List;

import main.Model;
import util.Vector;

import util.PhysicsObject;
import util.RectanglePositioned;

public class VerletIntegrator implements Timed{
	//TODO eliminate these constants
	static final int PLAYER_SPEED = 1;
	public List<PhysicsObject> movingObjects;
	public List<RectanglePositioned> staticObjects;
	private Model model;
	
	public void setModel(Model m){
		model = m;
	}
	public VerletIntegrator(){
		movingObjects = new ArrayList<PhysicsObject>();
		staticObjects = new ArrayList<RectanglePositioned>();
	}

	public void step(double timeStep){
		for( PhysicsObject o : movingObjects ){
			//o.getPosition().addInPlace(new Vector(0,-.001));
			integrateVerlet(o, timeStep);
		}
		satisfyConstraints();
	}

	public static final int ITERATION_COUNT = 3;

	public void satisfyConstraints(){
		for( int iterations = 0; iterations < ITERATION_COUNT; iterations++ ){
			for( PhysicsObject a : movingObjects ){
				Vector v = a.getPosition();
				Vector s = a.getSize();
				a.setIsOnGround(false);
				if( v.x <= model.MIN_X ){
					v.x = model.MIN_X;
				}
				if( v.x >= model.MAX_X - s.x ){
					v.x = model.MAX_X - s.x;
				}
				if( v.y <= model.MIN_Y ){
					v.y = model.MIN_Y;
					a.setIsOnGround(true);
				}
				if( v.y > model.MAX_Y - s.y ){
					v.y = model.MAX_Y - s.y;
				}
				for( PhysicsObject b : movingObjects ){
					if( !a.equals(b) ){
						Vector coords = b.collide(a);
						if( coords != null ){
							a.getPosition().setInPlace(coords);
						}
					}
				}
				for( RectanglePositioned b : staticObjects ){
					Vector coords = b.collide(a);
					if( coords != null ){
						a.getPosition().setInPlace(coords);
					}
				}
			}
		}
	}

	private void integrateVerlet(PhysicsObject o, double timeStep){
		Vector position = o.getPosition();
		Vector tempPosition = position.minus(new Vector());
		Vector oldPosition = o.getOldPosition();
		Vector a = acceleration(o, timeStep);
		
		//b.x += 0.99*x-0.99*oldX+ax*dt*dt;
		
		position.addInPlace(
			position
			.minus(oldPosition)
			.plus(a.scale(timeStep*timeStep))
		);
		o.setOldPosition(tempPosition);
	}

	private static final Vector gravity = new Vector(0,-15);
	private Vector acceleration(PhysicsObject o, double timeStep){
		return gravity.plus(o.getAcceleration());
	}
}
