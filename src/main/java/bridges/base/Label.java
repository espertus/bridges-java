package bridges.base;
import bridges.base.DataStruct;

import java.util.ArrayList;
import bridges.base.Color;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;

/**
 * @brief This class used to label symbols.
 *		Labels have  a text string, font size, width, height and location
 *
 * Basic styling such as stroke, color are defined in the superclass Symbol.
 *
 *
 * On a label the "stroke" refer to the outside of the of the letter, and the "fill" refers to the inside of the letters. In most case, you want no stroke but a fill. 
 *
 *
 * @sa An example tutorial can be found at 
 * 		http://bridgesuncc.github.io/tutorials/Symbol_Collection.html
 *
 * @author David Burlinson
 * @date 2018, 7/15/19
 */
public class Label extends Symbol {
	static final Integer DEFAULT_FONTSIZE = 12;

	private Integer width = 100;
	private Integer height = 50;
	private Integer fontSize = DEFAULT_FONTSIZE;
	private float rotation_angle = 0.0f;


	/**
	 *	Construct a default label
	 */
	public Label() {
		super();
		setStrokeWidth(0.0);
	}

	/**
	 *	Construct a label with the give text string
	 *  @param label the text of the label
	 */
	public Label(String label) {
		this();
		this.setLabel(label);
	}

	public Label setFontSize(Integer size) {
		if (size <= 0 || size > 200) {
			throw new IllegalArgumentException("Please use font size between 0 and 200");
		}
		else {
			fontSize = size;
		}
		return this;
	}

	/**
	 * @brief Set the rotation angle for the label
	 *
	 * Permits rotated text labels (only horiz and vertical
	 *  supported now.
	 *
	 * @param angle  rotation angle in dedgrees
	 *
	 */
	public void setRotationAngle (float angle) {
		// temporary - only horizontal or vertical labels
		rotation_angle = 0.0f;
		if (angle == 90.)
			rotation_angle = angle;
	}

	/**
	 * @brief Get the rotation angle for the label
	 *
	 *
	 * @return angle  rotation angle in degrees
	 *
	 */
	 float getRotationAngle () {
	 	return rotation_angle;
	 }

	/**
	 * @brief This method returns the bounding box dimensions of
	 *  the shape
	 *
	 *  A more accurate computation, takes into account
	 *  the label string content
	 *
	 *  @return bounding box of the label (min x, min y, max x, max y)
	 *
	 * @return vector of floats
	 */

	public float[] getBoundingBox() {

		// first get the width of the string by parsing it
		String str = getLabel();
		float length = 0.0f;
		for (char ch: str.toCharArray()) {
			if (ch == 'm' || ch == 'w')
				length +=  0.5;
			else if (ch == 'i' || ch == 'l' || ch == 'j')
				length +=  0.4;
			else length += 0.6;
		}
		length *= fontSize;

		float loc_x = this.getLocation()[0];
		float loc_y = this.getLocation()[1];

		float width = length;
		float height = fontSize;

		// order is xmin, ymin, xmax, ymax
		float[] bbox = new float[4];
		bbox[0] = loc_x - width/2.0f;
		bbox[1] = loc_y - height/2.0f;
		bbox[2] = loc_x + width/2.0f;
		bbox[3] = loc_y + height/2.0f;

		return bbox;
	}

	/**
	 *  Get the dimensions of the label object
	 *  @return bounding box of the label (min x, min y, max x, max y)
	 */
	public float[] getDimensions() {
		return getBoundingBox();
	}

	/**
	 * Get the JSON representation of the label object
	 *
	 * @returns the encoded JSON string
	 */
	public JSONObject getJSONRepresentation() {
		JSONObject json_builder = super.getJSONRepresentation();

		json_builder.put("name", JSONValue.escape(super.label));
		json_builder.put("shape", "text");

		json_builder.put("font-size", fontSize);
		json_builder.put("angle", rotation_angle);

		return json_builder;
	}
}
