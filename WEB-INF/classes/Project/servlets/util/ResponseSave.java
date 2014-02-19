/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets.util;

/**
 *
 * @author Lance
 */
public class ResponseSave {
    private int record;
    private String message;
    
    public ResponseSave() {
        
    }
    
    public ResponseSave(int record, String message) {
        this.record = record;
        this.message = message;
    }
    
    /**
     * @return the record
     */
    public int getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(int record) {
        this.record = record;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
