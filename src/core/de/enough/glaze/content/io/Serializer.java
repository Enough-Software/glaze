/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.content.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * <p>
 * The serializer class is used for serializing and de-serializing objects in a
 * unified way.
 * </p>
 * <p>
 * High level serialization components like the RmsStorage use this helper class
 * for the actual serialization.
 * </p>
 * 
 * <p>
 * Copyright Enough Software 2006 - 2009
 * </p>
 * 
 * <pre>
 * history
 *        Mar 31, 2006 - rob creation
 * </pre>
 * 
 * @author Robert Virkus, j2mepolish@enough.de
 */
public final class Serializer {

	private static final byte VERSION = 1; // version starts at 1 and is then
											// increased up to 127 when
											// incompatible changes occur
	private static final byte TYPE_EXTERNALIZABLE = 0;
	private static final byte TYPE_EXTERNALIZABLE_ARRAY = 1;
	private static final byte TYPE_OBJECT_ARRAY = 2;
	private static final byte TYPE_BYTE = 3;
	private static final byte TYPE_SHORT = 4;
	private static final byte TYPE_INTEGER = 5;
	private static final byte TYPE_LONG = 6;
	private static final byte TYPE_FLOAT = 7;
	private static final byte TYPE_DOUBLE = 8;
	private static final byte TYPE_STRING = 9;
	private static final byte TYPE_STRING_BUFFER = 10;
	private static final byte TYPE_CHARACTER = 11;
	private static final byte TYPE_BOOLEAN = 12;
	private static final byte TYPE_DATE = 13;
	private static final byte TYPE_CALENDAR = 14;
	private static final byte TYPE_RANDOM = 15;
	private static final byte TYPE_HASHTABLE = 16;
	private static final byte TYPE_STACK = 17;
	private static final byte TYPE_VECTOR = 18;
	private static final byte TYPE_BYTE_ARRAY = 22;
	private static final byte TYPE_SHORT_ARRAY = 23;
	private static final byte TYPE_INT_ARRAY = 24;
	private static final byte TYPE_LONG_ARRAY = 25;
	private static final byte TYPE_FLOAT_ARRAY = 26;
	private static final byte TYPE_DOUBLE_ARRAY = 27;
	private static final byte TYPE_CHAR_ARRAY = 28;
	private static final byte TYPE_BOOLEAN_ARRAY = 29;
	private static final byte TYPE_STRING_ARRAY = 30;

	private Serializer() {
		// no instantiation allowed
	}

	/**
	 * Serializes the specified object. Any class implementing Serializable can
	 * be serialized, additionally classes like java.lang.Integer,
	 * java.util.Date, javax.util.Vector, javax.microedition.lcdui.Image etc.
	 * can be serialized.
	 * 
	 * @param object
	 *            the object
	 * @param out
	 *            the stream into which the object should be serialized
	 * @throws IOException
	 *             when serialization data could not be written or when
	 *             encountering an object that cannot be serialized
	 * @see #deserialize(DataInputStream) for deseralizing objects
	 */
	public static void serialize(Object object, DataOutputStream out)
			throws IOException {
		serialize(object, out, true);
	}

	/**
	 * WARNING: Can only be used in JavaSE environments! Serializes the
	 * specified object.
	 * 
	 * @param object
	 *            the object
	 * @param out
	 *            the stream into which the object should be serialized
	 * @param useObfuscation
	 *            true when classnames are obfuscated
	 * @throws IOException
	 *             when serialization data could not be written or when
	 *             encountering an object that cannot be serialized
	 */
	public static void serialize(Object object, DataOutputStream out,
			boolean useObfuscation) throws IOException {
		out.writeByte(VERSION);
		boolean isNull = (object == null);
		out.writeBoolean(isNull);
		if (!isNull) {
			if (object instanceof Externalizable) {
				out.writeByte(TYPE_EXTERNALIZABLE);
				String className = object.getClass().getName();
				out.writeUTF(className);
				((Externalizable) object).write(out);
			} else if (object instanceof Externalizable[]) {
				out.writeByte(TYPE_EXTERNALIZABLE_ARRAY);
				String cn = object.getClass().getName();
				cn = cn.substring(cn.lastIndexOf('[') + 2, cn.length() - 1);
				out.writeUTF(cn);
				Externalizable[] externalizables = (Externalizable[]) object;
				out.writeInt(externalizables.length);
				Hashtable classNames = new Hashtable();
				Class lastClass = null;
				byte lastId = 0;
				byte idCounter = 0;
				for (int i = 0; i < externalizables.length; i++) {
					Externalizable externalizable = externalizables[i];
					Class currentClass = externalizable.getClass();
					if (currentClass == lastClass) {
						out.writeByte(lastId);
					} else {
						Byte knownId = (Byte) classNames.get(currentClass);
						if (knownId != null) {
							out.writeByte(knownId.byteValue());
						} else {
							// this is a class that has not yet been
							// encountered:
							out.writeByte(0);
							idCounter++;
							String className = currentClass.getName();
							out.writeUTF(className);
							lastClass = currentClass;
							lastId = idCounter;
							classNames.put(currentClass, new Byte(lastId));
						}
					}
					externalizable.write(out);
				}
			} else if (object instanceof Object[]) {
				out.writeByte(TYPE_OBJECT_ARRAY);
				Object[] objects = (Object[]) object;
				out.writeInt(objects.length);
				for (int i = 0; i < objects.length; i++) {
					Object obj = objects[i];
					serialize(obj, out);
				}
			} else if (object instanceof Byte) {
				out.writeByte(TYPE_BYTE);
				out.writeByte(((Byte) object).byteValue());
			} else if (object instanceof Short) {
				out.writeByte(TYPE_SHORT);
				out.writeShort(((Short) object).shortValue());
			} else if (object instanceof Integer) {
				out.writeByte(TYPE_INTEGER);
				out.writeInt(((Integer) object).intValue());
			} else if (object instanceof Long) {
				out.writeByte(TYPE_LONG);
				out.writeLong(((Long) object).longValue());
			} else if (object instanceof Float) {
				out.writeByte(TYPE_FLOAT);
				out.writeFloat(((Float) object).floatValue());
			} else if (object instanceof Double) {
				out.writeByte(TYPE_DOUBLE);
				out.writeDouble(((Double) object).doubleValue());
			} else if (object instanceof String) {
				out.writeByte(TYPE_STRING);
				out.writeUTF((String) object);
			} else if (object instanceof StringBuffer) {
				out.writeByte(TYPE_STRING_BUFFER);
				out.writeUTF(((StringBuffer) object).toString());
			} else if (object instanceof Character) {
				out.writeByte(TYPE_CHARACTER);
				out.writeChar(((Character) object).charValue());
			} else if (object instanceof Boolean) {
				out.writeByte(TYPE_BOOLEAN);
				out.writeBoolean(((Boolean) object).booleanValue());
			} else if (object instanceof Date) {
				out.writeByte(TYPE_DATE);
				out.writeLong(((Date) object).getTime());
			} else if (object instanceof Calendar) {
				out.writeByte(TYPE_CALENDAR);
				out.writeLong(((Calendar) object).getTime().getTime());
			} else if (object instanceof Random) {
				out.writeByte(TYPE_RANDOM);
			} else if (object instanceof Hashtable) {
				out.writeByte(TYPE_HASHTABLE);
				Hashtable table = (Hashtable) object;
				out.writeInt(table.size());
				Enumeration enumeration = table.keys();
				while (enumeration.hasMoreElements()) {
					Object key = enumeration.nextElement();
					serialize(key, out);
					Object value = table.get(key);
					serialize(value, out);
				}
			} else if (object instanceof Vector) { // also serializes stacks
				if (object instanceof Stack) {
					out.writeByte(TYPE_STACK);
				} else {
					out.writeByte(TYPE_VECTOR);
				}
				Vector vector = (Vector) object;
				int size = vector.size();
				out.writeInt(size);
				for (int i = 0; i < size; i++) {
					serialize(vector.elementAt(i), out);
				}
			} else if (object instanceof byte[]) {
				out.writeByte(TYPE_BYTE_ARRAY);
				byte[] numbers = (byte[]) object;
				out.writeInt(numbers.length);
				out.write(numbers, 0, numbers.length);
			} else if (object instanceof short[]) {
				out.writeByte(TYPE_SHORT_ARRAY);
				short[] numbers = (short[]) object;
				out.writeInt(numbers.length);
				for (int i = 0; i < numbers.length; i++) {
					short number = numbers[i];
					out.writeShort(number);
				}
			} else if (object instanceof int[]) {
				out.writeByte(TYPE_INT_ARRAY);
				int[] numbers = (int[]) object;
				out.writeInt(numbers.length);
				for (int i = 0; i < numbers.length; i++) {
					int number = numbers[i];
					out.writeInt(number);
				}
			} else if (object instanceof long[]) {
				out.writeByte(TYPE_LONG_ARRAY);
				long[] numbers = (long[]) object;
				out.writeInt(numbers.length);
				for (int i = 0; i < numbers.length; i++) {
					long number = numbers[i];
					out.writeLong(number);
				}
			} else if (object instanceof float[]) {
				out.writeByte(TYPE_FLOAT_ARRAY);
				float[] numbers = (float[]) object;
				out.writeInt(numbers.length);
				for (int i = 0; i < numbers.length; i++) {
					float number = numbers[i];
					out.writeFloat(number);
				}
			} else if (object instanceof double[]) {
				out.writeByte(TYPE_DOUBLE_ARRAY);
				double[] numbers = (double[]) object;
				out.writeInt(numbers.length);
				for (int i = 0; i < numbers.length; i++) {
					double number = numbers[i];
					out.writeDouble(number);
				}
			} else if (object instanceof char[]) {
				out.writeByte(TYPE_CHAR_ARRAY);
				char[] characters = (char[]) object;
				out.writeInt(characters.length);
				for (int i = 0; i < characters.length; i++) {
					char c = characters[i];
					out.writeChar(c);
				}
			} else if (object instanceof boolean[]) {
				out.writeByte(TYPE_BOOLEAN_ARRAY);
				boolean[] bools = (boolean[]) object;
				out.writeInt(bools.length);
				for (int i = 0; i < bools.length; i++) {
					boolean b = bools[i];
					out.writeBoolean(b);
				}
			} else if (object instanceof String[]) {
				out.writeByte(TYPE_STRING_ARRAY);
				String[] strings = (String[]) object;
				out.writeInt(strings.length);
				for (int i = 0; i < strings.length; i++) {
					String s = strings[i];
					out.writeUTF(s);
				}
			} else {
				throw new IOException("Cannot serialize "
						+ object.getClass().getName());
			}
		}

	}

	/**
	 * Deserializes an object from the given stream.
	 * 
	 * @param in
	 *            the data input stream, from which the object is deserialized
	 * @return the serializable object
	 * @throws IOException
	 *             when serialization data could not be read or the Serializable
	 *             class could not get instantiated
	 */
	public static Object deserialize(DataInputStream in) throws IOException {
		byte version = in.readByte();
		if (version > VERSION) {
			System.out
					.println("Warning: trying to deserialize class that has been serialized with a newer version ("
							+ version + ">" + VERSION + ").");
		}
		boolean isNull = in.readBoolean();
		if (isNull) {
			return null;
		}
		byte type = in.readByte();
		switch (type) {
		case TYPE_EXTERNALIZABLE:
			String className = in.readUTF();
			Externalizable extern = null;
			try {
				extern = (Externalizable) Class.forName(className)
						.newInstance();
			} catch (Exception e) {
				System.out.println("Unable to instantiate serializable \""
						+ className + "\"" + e);
				throw new IOException(e.toString());
			}
			extern.read(in);
			return extern;
		case TYPE_EXTERNALIZABLE_ARRAY:
			int length = in.readInt();
			Externalizable[] externalizables = new Externalizable[length];

			Class[] classes = new Class[Math.min(length, 7)];
			Class currentClass;
			byte idCounter = 0;
			for (int i = 0; i < externalizables.length; i++) {
				int classId = in.readByte();
				if (classId == 0) { // new class name
					className = in.readUTF();
					try {
						currentClass = Class.forName(className);
					} catch (ClassNotFoundException e) {
						System.out
								.println("Unable to load Serializable class \""
										+ className + "\"" + e);
						throw new IOException(e.toString());
					}
					if (idCounter > classes.length) {
						Class[] newClasses = new Class[classes.length + 7];
						System.arraycopy(classes, 0, newClasses, 0,
								classes.length);
						classes = newClasses;
					}
					classes[idCounter] = currentClass;
					idCounter++;
				} else {
					currentClass = classes[classId - 1];
				}
				Externalizable externalizable;
				try {
					externalizable = (Externalizable) currentClass
							.newInstance();
					externalizable.read(in);
					externalizables[i] = externalizable;
				} catch (Exception e) {
					System.out.println("Unable to instantiate Serializable \""
							+ currentClass.getName() + "\"" + e);
					throw new IOException(e.toString());
				}
			}
			return externalizables;
		case TYPE_OBJECT_ARRAY:
			length = in.readInt();
			Object[] objects = new Object[length];
			for (int i = 0; i < objects.length; i++) {
				objects[i] = deserialize(in);
			}
			return objects;
		case TYPE_BYTE:
			return new Byte(in.readByte());
		case TYPE_SHORT:
			return new Short(in.readShort());
		case TYPE_INTEGER:
			return new Integer(in.readInt());
		case TYPE_LONG:
			return new Long(in.readLong());
		case TYPE_FLOAT:
			return new Float(in.readFloat());
		case TYPE_DOUBLE:
			return new Double(in.readDouble());
		case TYPE_STRING:
			return in.readUTF();
		case TYPE_STRING_BUFFER:
			return new StringBuffer(in.readUTF());
		case TYPE_CHARACTER:
			return new Character(in.readChar());
		case TYPE_BOOLEAN:
			return new Boolean(in.readBoolean());
		case TYPE_DATE:
			return new Date(in.readLong());
		case TYPE_CALENDAR:
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(in.readLong()));
			return calendar;
		case TYPE_RANDOM:
			return new Random();
		case TYPE_HASHTABLE:
			int size = in.readInt();
			Hashtable hashtable = new Hashtable(size);
			for (int i = 0; i < size; i++) {
				Object key = deserialize(in);
				Object value = deserialize(in);
				hashtable.put(key, value);
			}
			return hashtable;
		case TYPE_STACK:
		case TYPE_VECTOR:
			size = in.readInt();
			Vector vector;
			if (type == TYPE_STACK) {
				vector = new Stack();
			} else {
				vector = new Vector(size);
			}
			for (int i = 0; i < size; i++) {
				Object value = deserialize(in);
				vector.addElement(value);
			}
			return vector;
		case TYPE_BYTE_ARRAY:
			length = in.readInt();
			byte[] byteNumbers = new byte[length];
			in.readFully(byteNumbers);
			return byteNumbers;
		case TYPE_SHORT_ARRAY:
			length = in.readInt();
			short[] shortNumbers = new short[length];
			for (int i = 0; i < length; i++) {
				shortNumbers[i] = in.readShort();
			}
			return shortNumbers;
		case TYPE_INT_ARRAY:
			length = in.readInt();
			int[] intNumbers = new int[length];
			for (int i = 0; i < length; i++) {
				intNumbers[i] = in.readInt();
			}
			return intNumbers;
		case TYPE_LONG_ARRAY:
			length = in.readInt();
			long[] longNumbers = new long[length];
			for (int i = 0; i < length; i++) {
				longNumbers[i] = in.readLong();
			}
			return longNumbers;
		case TYPE_FLOAT_ARRAY:
			length = in.readInt();
			float[] floatNumbers = new float[length];
			for (int i = 0; i < length; i++) {
				floatNumbers[i] = in.readFloat();
			}
			return floatNumbers;
		case TYPE_DOUBLE_ARRAY:
			length = in.readInt();
			double[] doubleNumbers = new double[length];
			for (int i = 0; i < length; i++) {
				doubleNumbers[i] = in.readDouble();
			}
			return doubleNumbers;
		case TYPE_CHAR_ARRAY:
			length = in.readInt();
			char[] characters = new char[length];
			for (int i = 0; i < length; i++) {
				characters[i] = in.readChar();
			}
			return characters;
		case TYPE_BOOLEAN_ARRAY:
			length = in.readInt();
			boolean[] bools = new boolean[length];
			for (int i = 0; i < length; i++) {
				bools[i] = in.readBoolean();
			}
			return bools;
		case TYPE_STRING_ARRAY:
			length = in.readInt();
			String[] strings = new String[length];
			for (int i = 0; i < length; i++) {
				strings[i] = in.readUTF();
			}
			return strings;
		default:
			throw new IOException("Unknown type: " + type);
		}
	}

}
