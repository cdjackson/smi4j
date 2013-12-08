package com.mod_snmp.SmiParser.ErrorHandler;
import com.mod_snmp.SmiParser.SyntaxTree.Node;
/**
 * Error Exception handler.
 */
public class DebugException extends SmiException {
    public DebugException() {
        super();
    }
    public DebugException(String s) {
        super(s);
    }
    public DebugException(int linenr, String s) {
        super(linenr, s);
    }
    public DebugException(Node item, String s) {
        super(item,s );
    }
    public DebugException(int level, int linenr, String s) {
        super(level, linenr,s );
    }
    public DebugException(int level, Node item, String s) {
        super(level, item,s );
    }
}
