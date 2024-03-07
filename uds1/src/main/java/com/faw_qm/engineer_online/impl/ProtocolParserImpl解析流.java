package com.faw_qm.engineer_online.impl;

import com.faw_qm.engineer_online.ProtocolParser;
import com.faw_qm.engineer_online.core.util.FileUtil;
import com.faw_qm.engineer_online.core.util.ProtocolUtil;
import com.faw_qm.engineer_online.define.EcuConfig;
import com.faw_qm.engineer_online.define.SigNal;
import com.faw_qm.engineer_online.define.excel.ParseBean;
import com.faw_qm.engineer_online.define.excel.ParseTypeEnum;
import com.faw_qm.engineer_online.define.protcl.ProtclBean;
import com.faw_qm.engineer_online.define.protcl.ProtclTypeEnum;
import com.faw_qm.engineer_online.exception.InitException;
import com.faw_qm.engineer_online.exception.ProtclParseException;
import com.faw_qm.engineer_online.exception.ReadFileException;
import com.faw_qm.engineer_online.util.EnumUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ProtocolParserImpl解析流 implements ProtocolParser {
    private static ProtocolParserImpl解析流 instance;
    private String filePath;
    private static String dbFilePath;
    private EcuConfig ecuConfig;

    private ProtocolParserImpl解析流() {
    }

    public static ProtocolParserImpl解析流 getInstance() {
        if(instance == null) {
            instance = new ProtocolParserImpl解析流();
        }

        new EnumUtil();
        return instance;
    }

    public void init(String filePath, String ecuType1) throws ProtclParseException {
        this.ecuConfig = EcuConfigFactory.createEcuConfig(ecuType1);
        this.filePath = filePath;
        dbFilePath = this.ecuConfig.getFileName();
    }

    public List parser(String fileName, String[] strInfos) throws InitException, ReadFileException, ProtclParseException {
        if(this.filePath == null) {
            throw new InitException("Not initialized, filePath is null");
        } else {
            FileUtil fileUtil = new FileUtil(this.filePath, fileName);
            if(!fileUtil.isFileExist()) {
                throw new ReadFileException("file is not exist");
            } else {
                try {
                    List e = Format_TxtFile2StructList(fileUtil.readInputStream());
                    new ArrayList();
                    List sigNals = Parser_PMsg2SignalAry(e, strInfos);
                    return sigNals;
                } catch (IOException var6) {
                    throw new ReadFileException(var6);
                }
            }
        }
    }

    public static List Parser_PMsg2SignalAry(List cStructs, String[] strInfos) throws ProtclParseException {
        ArrayList itemAry_parsed = new ArrayList();
        SigNal item_Temp = null;
        SigNal prv_item_Temp = null;

        try {
            SqlliteParser e = new SqlliteParser(dbFilePath);
            String strRequest = "";
            String[] requestInfos = new String[]{"", ""};
            Iterator var9 = cStructs.iterator();

            label397:
            while(true) {
                String strContent;
                String strCmd;
                ResultSet rSet;
                int iRowCount;
                boolean blFind;
                do {
                    do {
                        if(!var9.hasNext()) {
                            return itemAry_parsed;
                        }

                        ProtclBean protclBean = (ProtclBean)var9.next();
                        strContent = protclBean.getContent();
                    } while(strContent.trim().length() < 2);

                    strCmd = strContent.length() > 16?strContent.substring(0, 16):strContent;
                    Object[] params = new Object[0];
                    if(strCmd.indexOf("<!") >= 0 || strCmd.substring(0, 2).equals("7F")) {
                        strRequest = "";
                    }

                    String strSql = "select *,(case when substr(\'" + strCmd + "\',0,2) = substr(Protocol_Request,0,2) then \'Request\' else \'Response\' end) AS WorkType " + "from Protocol where " + (strRequest.length() == 0?"(\'" + strCmd + "\' like Replace(Replace(Replace(replace(Protocol_Request,\'XX\',\'%\'),\' \',\'\'),\'ZZ\',\'\'),\'YY\',\'\') or ":"(Replace(Protocol_Request,\' \',\'\')=\'" + strRequest + "\' and ") + "\'" + strCmd + "\' like Replace(Replace(Replace(replace(Protocol_Response,\'XX\',\'%\'),\' \',\'\'),\'ZZ\',\'\'),\'YY\',\'\'))";
                    rSet = e.executeQuery(strSql, params);

                    for(iRowCount = -1; rSet.next(); iRowCount = rSet.getRow()) {
                        ;
                    }

                    rSet = e.executeQuery(strSql, params);
                    blFind = false;
                } while(rSet == null);

                while(true) {
                    label393:
                    while(true) {
                        if(!rSet.next()) {
                            continue label397;
                        }

                        String[] ProtocolCodes = rSet.getString("Protocol_Code").split("_");
                        String strProtocolCode = ProtocolCodes[0];
                        if(strProtocolCode.equals("DtcInfo") || strProtocolCode.equals("FreezeInfo")) {
                            strInfos[0] = strProtocolCode;
                        }

                        String strParseCode = rSet.getString("Protocol_ParseCode");
                        if(rSet.getString("WorkType").equals("Request")) {
                            if(rSet.getString("Protocol_Request").indexOf("XX") < 0) {
                                strRequest = rSet.getString("Protocol_Request").replace(" ", "");
                            } else {
                                strRequest = "";
                            }
                        } else {
                            strRequest = "";
                        }

                        if(strParseCode.trim().length() == 0) {
                            if(rSet.getString("WorkType").equals("Response")) {
                                prv_item_Temp = new SigNal();
                                prv_item_Temp.setId(requestInfos[0].length() == 0?"null":requestInfos[0]);
                                prv_item_Temp.setName(requestInfos[1].length() == 0?"unrecognized-line":requestInfos[1]);
                                if(strContent.startsWith("7F")) {
                                    prv_item_Temp.setValue(rSet.getString("Protocol_Name"));
                                    prv_item_Temp.setNote(strContent.substring(4, 6));
                                } else {
                                    prv_item_Temp.setNote(rSet.getString("Protocol_Code"));
                                    prv_item_Temp.setValue(rSet.getString("Protocol_Name"));
                                }

                                if(prv_item_Temp.getName() == null) {
                                    prv_item_Temp.setName("unrecognized-line");
                                }

                                itemAry_parsed.add(prv_item_Temp);
                            }
                        } else {
                            int iStart = rSet.getInt("Protocol_DataStart");
                            int iLoop = rSet.getInt("Protocol_DataLoop");
                            if(strContent.length() > iStart) {
                                String strContent1 = strContent.substring(iStart);
                                String strParseFormat = rSet.getString("Protocol_ParseFormat");
                                int iCodeLen = strParseCode.replace("V", "").replace(" ", "").length();
                                String[] parseFormats = new String[]{"G", "H", "I", "J"};
                                String[] parseValues = new String[iCodeLen];
                                String strStatus;
                                if(iLoop == 0) {
                                    for(int var50 = 0; var50 < iCodeLen; ++var50) {
                                        parseValues[var50] = strContent1.substring(var50 * 2, (var50 + 1) * 2);
                                    }

                                    strStatus = strParseCode.replace("V", "").replace(" ", "");

                                    for(int var51 = 0; var51 < iCodeLen; ++var51) {
                                        strStatus = strStatus.replace(parseFormats[var51], parseValues[var51]);
                                    }

                                    String[] var52 = new String[]{""};
                                    List var53;
                                    if(strContent1.length() > iCodeLen * 2) {
                                        var52[0] = strContent1.substring(iCodeLen * 2);
                                        var53 = ParseService(strProtocolCode, strStatus, var52, iLoop, strInfos, requestInfos);
                                        Iterator var57 = var53.iterator();

                                        while(var57.hasNext()) {
                                            SigNal var54 = (SigNal)var57.next();
                                            if(var54 != null && var54.getName() != null && !var54.getValue().equals("<!解析错误>")) {
                                                blFind = true;
                                                itemAry_parsed.add(var54);
                                            }

                                            if(rSet.getRow() == iRowCount && var54.getName() == null && !blFind) {
                                                var54.setName("<!解析错误>");
                                                var54.setNote("协议中不存在");
                                                itemAry_parsed.add(var54);
                                            }
                                        }
                                    } else {
                                        var53 = ParseService(strProtocolCode, strStatus, var52, iLoop, strInfos, requestInfos);
                                        if(var53.size() > 0) {
                                            if(strCmd.indexOf("7F") == 0) {
                                                prv_item_Temp.setNote(strCmd.substring(4, 6));
                                            }

                                            prv_item_Temp = ((SigNal)var53.get(var53.size() - 1)).getName() != null?(SigNal)var53.get(var53.size() - 1):prv_item_Temp;
                                        }
                                    }
                                } else {
                                    strInfos[0] = strProtocolCode;
                                    strStatus = ProtocolCodes.length > 1?ProtocolCodes[1]:"";
                                    String strParseType = rSet.getString("Protocol_ParseType");
                                    String strStatusCode = rSet.getString("Protocol_StatusCode");
                                    String strStautsFormat = rSet.getString("Protocol_StatusFormat");
                                    int strCode;
                                    int strCodeO;
                                    String var55;
                                    if(strParseCode.indexOf(86) > 0) {
                                        strContent1 = strContent.length() > iStart?strContent.substring(iStart):strContent;

                                        while(strContent1.length() > iCodeLen * 2) {
                                            for(strCode = 0; strCode < iCodeLen; ++strCode) {
                                                parseValues[strCode] = strContent1.substring(strCode * 2, (strCode + 1) * 2);
                                            }

                                            var55 = strParseFormat.replace(" ", "");

                                            for(strCodeO = 0; strCodeO < iCodeLen; ++strCodeO) {
                                                var55 = var55.replace(parseFormats[strCodeO], parseValues[strCodeO]);
                                            }

                                            String[] var58 = new String[]{strContent1.substring(iCodeLen * 2)};
                                            List var63 = ParseService(strProtocolCode, var55, var58, iLoop, strInfos, requestInfos);
                                            strContent1 = var58[0];
                                            Iterator var62 = var63.iterator();

                                            while(var62.hasNext()) {
                                                SigNal var61 = (SigNal)var62.next();
                                                if(var61 != null) {
                                                    itemAry_parsed.add(var61);
                                                }
                                            }
                                        }
                                    } else {
                                        strInfos[0] = strProtocolCode;
                                        strContent1 = strContent.length() > iStart?strContent.substring(iStart):strContent;

                                        while(true) {
                                            while(true) {
                                                if(strContent1.length() < iCodeLen * 2) {
                                                    continue label393;
                                                }

                                                for(strCode = 0; strCode < iCodeLen; ++strCode) {
                                                    parseValues[strCode] = strContent1.substring(strCode * 2, (strCode + 1) * 2);
                                                }

                                                var55 = strParseFormat.replace(" ", "");

                                                for(strCodeO = 0; strCodeO < iCodeLen; ++strCodeO) {
                                                    var55 = var55.replace(parseFormats[strCodeO], parseValues[strCodeO]);
                                                }

                                                String var56 = "";

                                                int iStatuscode;
                                                for(iStatuscode = 0; iStatuscode < parseValues.length; ++iStatuscode) {
                                                    var56 = var56 + parseValues[iStatuscode];
                                                }

                                                strContent1 = strContent1.substring(iCodeLen * 2);
                                                iStatuscode = 0;
                                                int strForamtFMI;
                                                if(strStatusCode.trim().length() > 0) {
                                                    for(strForamtFMI = 0; strForamtFMI < iCodeLen; ++strForamtFMI) {
                                                        if(strStatusCode.equals(parseFormats[strForamtFMI])) {
                                                            iStatuscode = (int)(Long.parseLong(parseValues[strForamtFMI], 16) & 255L);
                                                            break;
                                                        }
                                                    }
                                                }

                                                int strSPN2s;
                                                if(strParseType.equals("CODE")) {
                                                    if(strStautsFormat.trim().length() > 0) {
                                                        strForamtFMI = (int)(Long.parseLong(strStautsFormat.split(",")[0], 16) & 255L);
                                                        strSPN2s = Integer.parseInt(strStautsFormat.split("=")[1]);
                                                        if(strStautsFormat.indexOf("!") > 0) {
                                                            if((iStatuscode & strForamtFMI) != strSPN2s) {
                                                                item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                                if(item_Temp != null) {
                                                                    item_Temp.setReserved1(strStatus);
                                                                    item_Temp.setValue(var56);
                                                                    itemAry_parsed.add(item_Temp);
                                                                } else {
                                                                    System.out.println(var55);
                                                                }
                                                            }
                                                        } else if((iStatuscode & strForamtFMI) == strSPN2s) {
                                                            item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                            if(item_Temp != null) {
                                                                item_Temp.setReserved1(strStatus);
                                                                item_Temp.setValue(var56);
                                                                itemAry_parsed.add(item_Temp);
                                                            } else {
                                                                System.out.println(var55);
                                                            }
                                                        }
                                                    } else {
                                                        item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                        if(item_Temp != null) {
                                                            item_Temp.setReserved1(strStatus);
                                                            item_Temp.setValue(var56);
                                                            itemAry_parsed.add(item_Temp);
                                                        } else {
                                                            System.out.println(var55);
                                                        }
                                                    }
                                                } else {
                                                    int iFMIKeepL;
                                                    if(!strParseType.equals("PBCU_4") && !strParseType.equals("PBCU_6")) {
                                                        if(strParseType.equals("FMISPN")) {
                                                            String var59 = strParseFormat.split(",")[0];
                                                            String[] var60 = strParseFormat.split(",")[1].split(" ");
                                                            iFMIKeepL = Integer.parseInt(var59.substring(1, 2));
                                                            String strFMI = var59.substring(0, 1);
                                                            int iAndV = getBinaryFormat(8 - iFMIKeepL, iFMIKeepL);

                                                            int iFMI;
                                                            for(iFMI = 0; iFMI < iCodeLen; ++iFMI) {
                                                                strFMI = strFMI.replace(parseFormats[iFMI], parseValues[iFMI]);
                                                            }

                                                            iFMI = (int)(Long.parseLong(strFMI, 16) & 255L) & iAndV;
                                                            int iSPNKeepR = Integer.parseInt(var60[0].substring(0, 1));
                                                            String strSPN_H = var60[0].substring(1, 2);

                                                            int iSPN_H;
                                                            for(iSPN_H = 0; iSPN_H < iCodeLen; ++iSPN_H) {
                                                                strSPN_H = strSPN_H.replace(parseFormats[iSPN_H], parseValues[iSPN_H]);
                                                            }

                                                            iAndV = getBinaryFormat(0, iSPNKeepR);
                                                            iSPN_H = ((int)(Long.parseLong(strSPN_H, 16) & 255L) & iAndV) >> 8 - iSPNKeepR;
                                                            String strSPN_T = "";
                                                            int strAndV;
                                                            if(var60.length > 1) {
                                                                for(strAndV = 1; strAndV < var60.length; ++strAndV) {
                                                                    strSPN_T = strSPN_T + var60[strAndV];
                                                                }
                                                            }

                                                            for(strAndV = 0; strAndV < iCodeLen; ++strAndV) {
                                                                strSPN_T = strSPN_T.replace(parseFormats[strAndV], parseValues[strAndV]);
                                                            }

                                                            String var64 = strSPN_T.length() % 2 == 0?"":"F";

                                                            int iSPN_T;
                                                            for(iSPN_T = 0; iSPN_T < strSPN_T.length(); ++iSPN_T) {
                                                                var64 = var64 + "F";
                                                            }

                                                            iSPN_T = strSPN_T.length() == 0?0:(int)(Long.parseLong(strSPN_T, 16) & Long.parseLong(var64, 16));
                                                            int iSPN = (iSPN_H << strSPN_T.length() * 4) + iSPN_T;
                                                            strFMI = Integer.toString(iFMI);
                                                            String strSPN = Integer.toString(iSPN);
                                                            if(strStautsFormat.trim().length() > 0) {
                                                                iAndV = Integer.parseInt(strStautsFormat.split(",")[0]);
                                                                int iEquV = Integer.parseInt(strStautsFormat.split("=")[1]);
                                                                if(strStautsFormat.indexOf("!") > 0) {
                                                                    if((iStatuscode & iAndV) != iEquV) {
                                                                        item_Temp = GetDtcByFMI_SPN(strProtocolCode, strFMI, strSPN);
                                                                        if(item_Temp != null) {
                                                                            item_Temp.setReserved1(strStatus);
                                                                            item_Temp.setValue(var56);
                                                                            itemAry_parsed.add(item_Temp);
                                                                        } else {
                                                                            System.out.println("[" + strSPN + "," + strFMI + "]");
                                                                        }
                                                                    }
                                                                } else if((iStatuscode & iAndV) == iEquV) {
                                                                    item_Temp = GetDtcByFMI_SPN(strProtocolCode, strFMI, strSPN);
                                                                    if(item_Temp != null) {
                                                                        item_Temp.setReserved1(strStatus);
                                                                        item_Temp.setValue(var56);
                                                                        itemAry_parsed.add(item_Temp);
                                                                    } else {
                                                                        System.out.println("[" + strSPN + "," + strFMI + "]");
                                                                    }
                                                                }
                                                            } else {
                                                                item_Temp = GetDtcByFMI_SPN(strProtocolCode, strFMI, strSPN);
                                                                if(item_Temp != null) {
                                                                    item_Temp.setReserved1(strStatus);
                                                                    item_Temp.setValue(var56);
                                                                    itemAry_parsed.add(item_Temp);
                                                                } else {
                                                                    System.out.println("[" + strSPN + "," + strFMI + "]");
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        strForamtFMI = Integer.parseInt(strParseType.split("_")[1]);
                                                        var55 = FormatToPBCU(var55, strForamtFMI);
                                                        if(strStautsFormat.trim().length() > 0) {
                                                            strSPN2s = Integer.parseInt(strStautsFormat.split(",")[0]);
                                                            iFMIKeepL = Integer.parseInt(strStautsFormat.split("=")[1]);
                                                            if(strStautsFormat.indexOf("!") > 0) {
                                                                if((iStatuscode & strSPN2s) != iFMIKeepL) {
                                                                    item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                                    if(item_Temp != null) {
                                                                        item_Temp.setReserved1(strStatus);
                                                                        item_Temp.setValue(var56);
                                                                        itemAry_parsed.add(item_Temp);
                                                                    } else {
                                                                        System.out.println(var55);
                                                                    }
                                                                }
                                                            } else if((iStatuscode & strSPN2s) == iFMIKeepL) {
                                                                item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                                if(item_Temp != null) {
                                                                    item_Temp.setReserved1(strStatus);
                                                                    item_Temp.setValue(var56);
                                                                    itemAry_parsed.add(item_Temp);
                                                                } else {
                                                                    System.out.println(var55);
                                                                }
                                                            }
                                                        } else {
                                                            item_Temp = GetDtcByCode(strProtocolCode, var55);
                                                            if(item_Temp != null) {
                                                                item_Temp.setReserved1(strStatus);
                                                                item_Temp.setValue(var56);
                                                                itemAry_parsed.add(item_Temp);
                                                            } else {
                                                                System.out.println(var55);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var49) {
            System.out.println(var49.getMessage());
            throw new ProtclParseException(var49);
        }
    }

    public List getLiveDataList() {
        ArrayList list = new ArrayList();
        SqlliteParser sqlliteParser = new SqlliteParser(dbFilePath);
        String sql = "select * from StreamInfo";
        Object[] params = new Object[0];
        ResultSet rs = sqlliteParser.executeQuery(sql, params);

        try {
            while(rs.next()) {
                ParseBean t = new ParseBean();
                t.setId(rs.getString("PID"));
                t.setNameZh(rs.getString("Name"));
                t.setNameEn(rs.getString("Name_ENG"));
                t.setBeginPosition(rs.getInt("Byte_Start"));
                t.setLength(rs.getInt("Byte_Length"));
                t.setBitStart(rs.getInt("Bit_Start"));
                t.setBitLength(rs.getInt("Bit_Length"));
                t.setOffsets(rs.getInt("Offset"));
                t.setCoefficient((float)rs.getDouble("Coefficient"));
                t.setParseType(ParseTypeEnum.valueOf(rs.getInt("Type")));
                t.setEnumDesc(rs.getString("Enum"));
                t.setUnit(rs.getString("Unit").trim());
                t.setUnitENG(rs.getString("Unit_ENG"));
                t.setValueMin(rs.getDouble("Value_Min"));
                t.setValueMax(rs.getDouble("Value_Max"));
                list.add(t);
            }
        } catch (Throwable var7) {
            var7.printStackTrace();
        }

        return list;
    }

    private static SigNal GetDtcByFMI_SPN(String strProtocolCode, String strFMI, String strSPN) throws ProtclParseException {
        Object[] params = new Object[0];
        SigNal sigNal = null;

        try {
            SqlliteParser e = new SqlliteParser(dbFilePath);
            String strSql = "select * from " + strProtocolCode + " where FMI=" + strFMI + " and SPN=" + strSPN;
            ResultSet rSet = e.executeQuery(strSql, params);
            if(rSet != null) {
                while(rSet.next()) {
                    sigNal = new SigNal();
                    sigNal.setId("[SPN=" + strSPN + ",FMI=" + strFMI + "]");
                    sigNal.setName(rSet.getString("Describe"));
                    sigNal.setNote("读故障");
                }
            } else {
                sigNal = new SigNal();
                sigNal.setId("[SPN=" + strSPN + ",FMI=" + strFMI + "]");
                sigNal.setName("协议中没有故障码为[SPN=" + strSPN + ",FMI=" + strFMI + "]的故障");
                sigNal.setNote("读故障");
            }

            rSet.close();
            e.closeSource();
            return sigNal;
        } catch (Exception var8) {
            System.out.println(var8.getMessage());
            throw new ProtclParseException("解析类型:DtcInfo  " + var8.getMessage());
        }
    }

    private static SigNal GetDtcByCode(String strProtocolCode, String Code) throws ProtclParseException {
        Object[] params = new Object[0];
        SigNal sigNal = null;

        try {
            SqlliteParser e = new SqlliteParser(dbFilePath);
            ResultSet rSet = e.executeQuery("select * from " + strProtocolCode + " where Trim(PCode)=\'" + Code + "\'", params);
            if(rSet != null) {
                while(rSet.next()) {
                    sigNal = new SigNal();
                    sigNal.setId(rSet.getString("PCode"));
                    sigNal.setName(rSet.getString("Describe"));
                    sigNal.setValue(Code);
                    sigNal.setNote("读故障");
                }
            }

            rSet.close();
            e.closeSource();
            return sigNal;
        } catch (Exception var6) {
            throw new ProtclParseException("解析类型:DtcInfo  " + var6.getMessage());
        }
    }

    public static List ParseService(String protocolCode, String code, String[] Content, int iLoop, String[] strInfos, String[] requests) throws ProtclParseException {
        String value = "";
        ArrayList sigNals = new ArrayList();

        try {
            SqlliteParser e = new SqlliteParser(dbFilePath);
            Object[] params = new Object[0];
            ResultSet rSet1 = e.executeQuery("select * from " + protocolCode + " where Trim(pid)=\'" + code + "\'", params);
            int iByte_Begin = 0;
            int iByte_EffLength = 0;
            if(rSet1 != null) {
                label160:
                while(true) {
                    while(true) {
                        if(!rSet1.next()) {
                            break label160;
                        }

                        strInfos[0] = protocolCode;
                        requests[0] = code;
                        requests[1] = rSet1.getString("Name");
                        if(Content[0].length() == 0) {
                            return sigNals;
                        }

                        SigNal sigNal = new SigNal();
                        sigNal.setId(code);
                        sigNal.setName(rSet1.getString("Name"));
                        iByte_Begin = rSet1.getInt("Byte_Start");
                        iByte_EffLength = rSet1.getInt("Byte_Length");
                        int iBit_Start = rSet1.getInt("Bit_Start");
                        int iBit_EffLength = rSet1.getInt("Bit_Length");
                        value = Content[0];
                        if(value.length() == 0) {
                            sigNals.add(sigNal);
                        } else if(value.length() < iByte_Begin * 2 + iByte_EffLength * 2) {
                            sigNal.setValue("<!解析错误>");
                            sigNal.setNote("传入的值错误(Byte)");
                            sigNals.add(sigNal);
                        } else {
                            value = value.substring(iByte_Begin * 2, iByte_Begin * 2 + iByte_EffLength * 2);
                            if(value.length() * 4 < iBit_Start + iBit_EffLength) {
                                sigNal.setValue("<!解析错误>");
                                sigNal.setNote("传入的值错误(Bit)");
                                sigNals.add(sigNal);
                            } else {
                                ParseTypeEnum parseType = ParseTypeEnum.valueOf(Integer.parseInt(rSet1.getString("Type")));
                                int strUnit;
                                String strTrailVer;
                                if(ParseTypeEnum.EnumType.equals(parseType)) {
                                    strUnit = rSet1.getInt("Value_Revert");
                                    int dValue = cutMiddleBit(value, iBit_Start, iBit_EffLength, strUnit);
                                    strTrailVer = rSet1.getString("Enum").trim();
                                    String[] lVer = strTrailVer.split("#");
                                    HashMap map_enumDesc = new HashMap();
                                    String[] var25 = lVer;
                                    int var24 = lVer.length;

                                    String strUnit1;
                                    for(int val = 0; val < var24; ++val) {
                                        strUnit1 = var25[val];
                                        String[] temp = strUnit1.split("\\^");
                                        map_enumDesc.put(temp[0], temp[1]);
                                    }

                                    if(dValue > map_enumDesc.size() - 1) {
                                        sigNal.setValue("<!解析错误>");
                                        sigNal.setNote("枚举Index越界");
                                        sigNals.add(sigNal);
                                        continue;
                                    }

                                    if(map_enumDesc.get(String.valueOf(dValue)) == null) {
                                        System.out.println("解析类型解析错误(" + dValue + ")");
                                    }

                                    strUnit1 = rSet1.getString("Unit");
                                    if(strUnit1.equals("-") || strUnit1.equals(" ") || strUnit1 == null) {
                                        strUnit1 = "";
                                    }

                                    String var34 = dValue + ":" + (String)map_enumDesc.get(String.valueOf(dValue)) + " " + strUnit1;
                                    sigNal.setValue(var34);
                                } else {
                                    String var28;
                                    String var29;
                                    if(ParseTypeEnum.Particular01.equals(parseType)) {
                                        var28 = "0";
                                        var29 = "0";
                                        strTrailVer = "0";
                                        long var30 = tools_HexString2UnsignedLong(value);
                                        var28 = Long.toString(var30 / 10000L);
                                        var30 -= var30 / 10000L * 10000L;
                                        var29 = (var30 / 100L > 10L?"":"0") + Long.toString(var30 / 100L);
                                        var30 -= var30 / 100L * 100L;
                                        strTrailVer = (var30 > 10L?"":"0") + Long.toString(var30);
                                        sigNal.setValue(var28 + "." + var29 + "." + strTrailVer);
                                    } else if(ParseTypeEnum.StringType.equals(parseType)) {
                                        var28 = tools_hexString2AsciiString(value);
                                        var29 = rSet1.getString("Unit");
                                        if(var29 == null) {
                                            sigNal.setValue(var28.trim());
                                        } else {
                                            if(var29.equals("-") || var29.equals(" ")) {
                                                var29 = "";
                                            }

                                            sigNal.setValue(var28.trim() + var29);
                                        }
                                    } else if(!ParseTypeEnum.BCDType.equals(ParseTypeEnum.valueOf(Integer.parseInt(rSet1.getString("Type")))) && !ParseTypeEnum.HEXType.equals(ParseTypeEnum.valueOf(Integer.parseInt(rSet1.getString("Type"))))) {
                                        if(ParseTypeEnum.SignedIntType.equals(parseType)) {
                                            strUnit = ProtocolUtil.converHex8StrToInt(value);
                                            var29 = rSet1.getString("Unit");
                                            if(var29.equals("-") || var29.equals(" ") || var29 == null) {
                                                var29 = "";
                                            }

                                            sigNal.setValue(tools_ToBigMath((double)((float)strUnit * rSet1.getFloat("Coefficient") + rSet1.getFloat("Offset")), 0) + var29);
                                        } else if(ParseTypeEnum.UnsignedIntType.equals(parseType)) {
                                            var28 = rSet1.getString("Type");
                                            if(var28.equals("-") || var28.equals(" ") || var28 == null) {
                                                var28 = "";
                                            }

                                            long var31 = tools_HexString2UnsignedLong(value);
                                            sigNal.setValue(tools_ToBigMath((double)((float)var31 * rSet1.getFloat("Coefficient") + rSet1.getFloat("Offset")), 0) + var28);
                                        } else if(ParseTypeEnum.SignedFloatType.equals(parseType)) {
                                            var28 = rSet1.getString("Unit");
                                            if(var28.equals("-") || var28.equals(" ") || var28 == null) {
                                                var28 = "";
                                            }

                                            float var32 = (float)ProtocolUtil.converHex8StrToLong(value);
                                            sigNal.setValue(tools_ToBigMath((double)(var32 * rSet1.getFloat("Coefficient") + rSet1.getFloat("Offset"))) + var28);
                                        } else if(ParseTypeEnum.UnsignedFloatType.equals(parseType)) {
                                            var28 = rSet1.getString("Unit");
                                            if(var28.equals("-") || var28.equals(" ") || var28 == null) {
                                                var28 = "";
                                            }

                                            double var33 = (double)tools_HexString2UnsignedLong(value);
                                            sigNal.setValue(tools_ToBigMath((double)((float)var33 * rSet1.getFloat("Coefficient") + rSet1.getFloat("Offset"))) + var28);
                                        } else if(!ParseTypeEnum.EnumNull.equals(parseType)) {
                                            throw new ProtclParseException("解析类型解析错误:" + rSet1.getString("Type"));
                                        }
                                    } else {
                                        sigNal.setValue(value);
                                    }
                                }

                                sigNals.add(sigNal);
                            }
                        }
                    }
                }
            }

            Content[0] = Content[0].substring(iByte_Begin * 2 + iByte_EffLength * 2);
            return sigNals;
        } catch (Exception var27) {
            throw new ProtclParseException("解析类型:" + strInfos[0] + "  " + var27.getMessage());
        }
    }

    public static long tools_HexString2UnsignedLong(String data) {
        int iStrLen = data.length();
        if(iStrLen == 0) {
            return 0L;
        } else if(iStrLen > 8) {
            return -1L;
        } else {
            String strAndV = data.length() % 2 == 0?"":"F";

            for(int lAndV = 0; lAndV < iStrLen; ++lAndV) {
                strAndV = strAndV + "F";
            }

            long var5 = Long.parseLong(strAndV, 16);
            return (long)Integer.valueOf(data, 16).byteValue() & var5;
        }
    }

    public static String tools_hexString2AsciiString(String hex) {
        if(hex == null) {
            return "null";
        } else {
            int len = hex.length();
            String ascii = "";

            for(int i = 0; i < len; i += 2) {
                if(i + 2 <= len) {
                    String s = hex.substring(i, i + 2);
                    int n = Integer.parseInt(s, 16);
                    if(n >= 32 && n <= 126) {
                        ascii = ascii + (char)n;
                    }
                }
            }

            return ascii;
        }
    }

    public static String tools_ToBigMath(double dData) {
        return tools_ToBigMath(dData, 3);
    }

    public static String tools_ToBigMath(double dData, int iDceimalPlace) {
        BigDecimal d1 = new BigDecimal(Double.toString(dData));
        BigDecimal d2 = new BigDecimal(Double.toString(1.0D));
        return d1.divide(d2, iDceimalPlace, 4).toString();
    }

    public static String FormatToPBCU(String string, int iKeep) {
        String result = "";
        int dtc = (int)(Long.parseLong(string, 16) & 16777215L);
        char[] prefixes = new char[]{'P', 'C', 'B', 'U'};
        char prefix = prefixes[dtc >> 22 & 3];
        result = prefix + String.format("%06X", new Object[]{Integer.valueOf(dtc & 4194303)});
        return result.substring(0, iKeep + 1);
    }

    private static int getBinaryFormat(int start, int len) {
        int value = 0;

        for(int i = 0; i < 8; ++i) {
            if(i >= start && i < start + len) {
                value += (int)Math.pow(2.0D, (double)(8 - i - 1));
            }
        }

        return value;
    }

    private static int cutMiddleBit(String hexValue, int bitStart, int bitLen, int iRevert) {
        if(hexValue.length() > 32) {
            return -1;
        } else if(bitStart + bitLen > hexValue.length() * 4) {
            return -2;
        } else {
            hexValue = hexValue.length() % 2 == 0?hexValue:"0" + hexValue;
            if(iRevert > 0) {
                String strBins = "";

                for(int strHexs = 0; strHexs < hexValue.length() / 2; ++strHexs) {
                    strBins = hexValue.substring(2 * strHexs, 2 * (strHexs + 1)) + strBins;
                }

                hexValue = strBins;
            }

            String[] var10 = new String[]{"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
            String[] var11 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
            String strBin = "";

            int iResult;
            int iBinLen;
            for(iResult = 0; iResult < hexValue.length(); ++iResult) {
                for(iBinLen = 0; iBinLen < 16; ++iBinLen) {
                    if(hexValue.substring(iResult, iResult + 1).equals(var11[iBinLen])) {
                        strBin = strBin + var10[iBinLen];
                        break;
                    }
                }
            }

            strBin = strBin.substring(strBin.length() - bitStart - bitLen, strBin.length() - bitStart);
            iResult = 0;
            iBinLen = strBin.length();

            for(int i = 0; i < iBinLen; ++i) {
                if(strBin.substring(i, i + 1).equals("1")) {
                    iResult += (int)Math.pow(2.0D, (double)(iBinLen - i - 1));
                }
            }

            return iResult;
        }
    }

    public static List Format_TxtFile2StructList(InputStream is) throws IOException {
        ArrayList lstRet = new ArrayList();
        if(is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String lineTxt = null;
            String strRequest3 = "";

            while((lineTxt = reader.readLine()) != null) {
                try {
                    if(lineTxt.length() > 0) {
                        lstRet.add(Format_ASCII2Struct1(lineTxt, strRequest3));
                    }
                } catch (ProtclParseException var6) {
                    var6.printStackTrace();
                }
            }

            return lstRet;
        } else {
            return null;
        }
    }

    public static ProtclBean Format_ASCII2Struct(String strAscii) throws ProtclParseException {
        String[] a = strAscii.split(" ");
        if(a != null && a.length > 2) {
            String bean1 = a[0].trim();
            String canId = a[1].trim();
            int n = strAscii.indexOf(canId);
            String s = strAscii.substring(n + canId.length());
            s = s.toUpperCase().replace(" ", "");
            int length = ProtocolUtil.converHex8StrToInt(s.substring(0, 4));
            s = s.substring(4, 4 + length * 2);
            ProtclBean protclBean = new ProtclBean();
            protclBean.setTimestamp(bean1);
            protclBean.setCANID(canId.toUpperCase());
            protclBean.setLength(length);
            protclBean.setServiceId(ProtocolUtil.converHex8StrToInt(s.substring(0, 2)));
            protclBean.setContent(s);
            return protclBean;
        } else {
            ProtclBean bean = new ProtclBean();
            bean.setContent(strAscii);
            bean.setProtclType(ProtclTypeEnum.UNDEFINED);
            return bean;
        }
    }

    public static ProtclBean Format_ASCII2Struct1(String strAscii, String strV) throws ProtclParseException {
        String[] a = strAscii.split(" ");
        if(a != null && a.length > 2) {
            String bean1 = a[0].trim();
            String canId = a[1].trim();
            int n = strAscii.indexOf(canId);
            String s = strAscii.substring(n + canId.length());
            s = s.toUpperCase().replace(" ", "");
            int length = ProtocolUtil.converHex8StrToInt(s.substring(0, 4));
            s = s.substring(4, 4 + length * 2);
            ProtclBean protclBean = new ProtclBean();
            protclBean.SetRequest3(strV);
            protclBean.setTimestamp(bean1);
            protclBean.setCANID(canId.toUpperCase());
            protclBean.setLength(length);
            protclBean.setServiceId(ProtocolUtil.converHex8StrToInt(s.substring(0, 2)));
            protclBean.setContent(s);
            return protclBean;
        } else {
            ProtclBean bean = new ProtclBean();
            bean.setContent(strAscii);
            bean.setProtclType(ProtclTypeEnum.UNDEFINED);
            return bean;
        }
    }
}
