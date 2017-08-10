/***********************************************************
 * Software: Keno lottery
 * Module:   exception class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 14.9.2010
 *
 ***********************************************************/
package oh3ebf.lottery.exceptions;

import java.io.*;

public class KenoException extends java.lang.Exception {
    private static final long serialVersionUID = 7526472295622776147L;

    private int id; // a unique id
    private String classname; // the name of the class
    private String method; // the name of the method
    private String message; // a detailed message
    private KenoException previous = null; // the exception which was caught
    private String separator = "\n"; // line separator
  
    /** Creates a new instance of <code>kenoException</code> without detail message.
     *
     * @param id unique id
     * @param classname class name of calling parent
     * @param method name of funtion where called
     * @param message information about exception
     * @param previous the exception which was caught
     *
     */
    
    public KenoException(int id, String classname, String method, String message, KenoException previous) {
        this.id = id;
        this.classname = classname;
        this.method = method;
        this.message = message;
        this.previous = previous;
    }
    
    
    /** Constructs an instance of <code>kenoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     *
     */
    
    public KenoException(String msg) {
        super(msg);
    }
    
    /** Function adds line end to trace back printing
     * 
     * @return trace log printing
     *
     */
    
    public String traceBack() {
        return traceBack("\n");
    }
    
    /** Function prints trace back log
     * 
     * @param sep separator for line ending
     *
     * @return trace back log as string
     *
     */
    
    public String traceBack(String sep) {
        this.separator = sep;
        int level = 0;
        KenoException e = this;
        String text = line("Calling sequence (top to bottom)");
        while (e != null) {
            level++;
            text += line("--level " + level + "--------------------------------------");
            text += line("Class/Method: " + e.classname + "/" + e.method);
            text += line("Id          : " + e.id);
            text += line("Message     : " + e.message);
            e = e.previous;
        }
        return text;
    }
    
    /** Function prints line with specified line end separator
     *
     * @param s string to manipulate
     *
     * @return line with ending
     *
     */
    
    private String line(String s) {
        return s + separator;
    }
}
