package org.dikhim.jclicker.jsengine.objects.generators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimpleCodeGenerator implements CodeGenerator {
    private String objectName;
    private int lineSize;
    private StringBuilder sb;

    private Method[] allMethods = getClass().getMethods();

    private final int MIN_LINE_SIZE = 50;

    public SimpleCodeGenerator(String objectName, int lineSize) {
        this.objectName = objectName;
        setLineSize(lineSize);
    }


    /**
     * Appends the specified string to character sequence.
     *
     * @param str a string.
     * @return a reference to this object
     */
    protected SimpleCodeGenerator append(String str) {
        sb.append("'").append(str).append("'");
        return this;
    }

    /**
     * Appends the string representation of the int argument to this sequence.
     *
     * @param i an int.
     * @return a reference to this object
     */
    protected SimpleCodeGenerator append(int i) {
        sb.append(i);
        return this;
    }

    /**
     * Appends the string representation of the float argument to this sequence.
     *
     * @param f a float.
     * @return a reference to this object
     */
    protected SimpleCodeGenerator append(float f) {
        sb.append(f);
        return this;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        if (lineSize < MIN_LINE_SIZE) {
            this.lineSize = MIN_LINE_SIZE;
        } else {
            this.lineSize = lineSize;
        }
    }

    public String getObjectName() {
        return objectName;
    }

    public String getGeneratedCode() {
        return separateOnLines(sb, lineSize);
    }


    public void buildStringForCurrentMethod(Object... params) {
        String objectName = getObjectName();
        String methodName = getMethodName();

        sb = new StringBuilder();
        sb.append(objectName).append(".")
                .append(methodName).append("(");

        Method m = getMethodWithName(methodName);
        Type[] gpType;
        if (m != null) {
            gpType = m.getGenericParameterTypes();
        } else {
            return;
        }

        for (int i = 0; i < gpType.length; i++) {
            if (gpType[i].equals(String.class)) {
                append((String) params[i]);
            } else if (gpType[i].equals(int.class)) {
                append((int) params[i]);
            } else if (gpType[i].equals(float.class)) {
                append((float) params[i]);
            }
            if (i != gpType.length - 1) sb.append(",");
        }
        sb.append(");\n");
    }

    public void invokeMethodWithDefaultParams(String methodName) {
        Method m = getMethodWithName(methodName);
        Type[] gpType;
        if (m != null) {
            gpType = m.getGenericParameterTypes();
        } else {
            return;
        }
        Object[] params = new Object[gpType.length];
        for (int i = 0; i < gpType.length; i++) {
            if (gpType[i].equals(String.class)) {
                params[i] = "";
            } else if (gpType[i].equals(int.class)) {
                params[i] = 0;
            } else if (gpType[i].equals(float.class)) {
                params[i] = 0;
            }
        }
        try {
            m.invoke(this, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMethodsNames() {
        List<String> methodsNames = new ArrayList<>();
        for (Method m : allMethods) {
            if (Modifier.isPublic(m.getModifiers()) && !m.getName().equals("getMethodsNames") && !m.getName().equals("invokeMethodWithDefaultParams"))
                methodsNames.add(mGenerator.getName());
        }
        return methodsNames;
    }

    private Method getMethodWithName(String methodName) {
        for (Method m : allMethods) {
            if (!m.getName().equals(methodName)) {
                continue;
            }
            return m;
        }
        return null;
    }
}
