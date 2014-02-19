/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.servlets.util;

import Project.BusinessObject;
import java.util.ArrayList;

/**
 *
 * @author Lance
 */
public final class ResponseList {

    private int page;
    private int total;
    private ArrayList<Cell> rows;

    public ResponseList(int page, int total, ArrayList<BusinessObject> records) {
        this.page = page;
        this.total = total;
        this.rows = new ArrayList<Cell>();
        int i = 1;
        for (BusinessObject record : records) {
            this.rows.add(new Cell(i, record));
            i++;
        }
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the rows
     */
    public ArrayList<Cell> getRows() {
        return this.rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(ArrayList<Cell> rows) {
        this.rows = rows;
    }
}

final class Cell {

    private int id;
    private String cell;

    public Cell(int id, BusinessObject cell) {
        this.id = id;
        this.cell = cell.toString();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cell
     */
    public String getCell() {
        return this.cell;
    }

    /**
     * @param cell the cell to set
     */
    public void setCell(String cell) {
        this.cell = cell;
    }
}