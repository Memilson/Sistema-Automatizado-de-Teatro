package com.mycompany.mavenproject3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.formula.EvaluationWorkbook;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.CellReferenceType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetVisibility;
import org.apache.poi.ss.usermodel.Workbook;

public class XSSFWorkbook implements Workbook {

    XSSFWorkbook(FileInputStream fis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getActiveSheetIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setActiveSheet(int sheetIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getFirstVisibleTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFirstVisibleTab(int sheetIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSheetOrder(String sheetname, int pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSelectedTab(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSheetName(int sheet, String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSheetName(int sheet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSheetIndex(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSheetIndex(Sheet sheet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sheet createSheet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sheet createSheet(String sheetname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sheet cloneSheet(int sheetNum) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Sheet> sheetIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfSheets() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sheet getSheetAt(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sheet getSheet(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeSheetAt(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Font createFont() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Font findFont(boolean bold, short color, short fontHeight, String name, boolean italic, boolean strikeout, short typeOffset, byte underline) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfFonts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfFontsAsInt() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Font getFontAt(int idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CellStyle createCellStyle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumCellStyles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CellStyle getCellStyleAt(int idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void write(OutputStream stream) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Name getName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends Name> getNames(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends Name> getAllNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Name createName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeName(Name name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int linkExternalWorkbook(String name, Workbook workbook) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPrintArea(int sheetIndex, String reference) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPrintArea(int sheetIndex, int startColumn, int endColumn, int startRow, int endRow) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPrintArea(int sheetIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePrintArea(int sheetIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Row.MissingCellPolicy getMissingCellPolicy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMissingCellPolicy(Row.MissingCellPolicy missingCellPolicy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DataFormat createDataFormat() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addPicture(byte[] pictureData, int format) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends PictureData> getAllPictures() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CreationHelper getCreationHelper() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isHidden() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHidden(boolean hiddenFlag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isSheetHidden(int sheetIx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isSheetVeryHidden(int sheetIx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSheetHidden(int sheetIx, boolean hidden) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SheetVisibility getSheetVisibility(int sheetIx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSheetVisibility(int sheetIx, SheetVisibility visibility) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addToolPack(UDFFinder toolpack) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setForceFormulaRecalculation(boolean value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getForceFormulaRecalculation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SpreadsheetVersion getSpreadsheetVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addOlePackage(byte[] oleData, String label, String fileName, String command) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EvaluationWorkbook createEvaluationWorkbook() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CellReferenceType getCellReferenceType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCellReferenceType(CellReferenceType cellReferenceType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
