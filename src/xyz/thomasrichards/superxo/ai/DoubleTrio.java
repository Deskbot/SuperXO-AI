//class exists to allow generic to return something with no generics

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.Trio;

class DoubleTrio extends Trio<Double> {
	public DoubleTrio(Double a, Double b, Double c) {
		super(a,b,c);
	}
}
