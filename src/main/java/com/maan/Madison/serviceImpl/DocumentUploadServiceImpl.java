package com.maan.Madison.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Tuple;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.maan.Madison.controller.DocumentUploadController;
import com.maan.Madison.entity.DocumentUploadDetails;
import com.maan.Madison.entity.DocumentUploadDetailsId;
import com.maan.Madison.entity.HomePositionMaster;
import com.maan.Madison.entity.MailMaster;
import com.maan.Madison.entity.MotorDataDetail;
import com.maan.Madison.repository.DocumentUploadRepository;
import com.maan.Madison.repository.HomePositionMasterRepository;
import com.maan.Madison.repository.MailMasterRepository;
import com.maan.Madison.repository.MotorDataDetailRepository;
import com.maan.Madison.request.BuyPolicyRequest;
import com.maan.Madison.request.DocumentDeleteReq;
import com.maan.Madison.request.DocumentUploadGetReq;
import com.maan.Madison.request.DocumentUploadReq;
import com.maan.Madison.response.CommonResponse;
import com.maan.Madison.response.DocumentUploadGetRes;
import com.maan.Madison.service.DocumentUploadService;
import com.maan.Madison.utilityServiceImpl.CriteriaQueryServiceImpl;
import com.maan.Madison.utilityServiceImpl.SMTPAuthenticator;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class DocumentUploadServiceImpl implements DocumentUploadService{
	
	Logger log =LogManager.getLogger(DocumentUploadController.class);

	@Value("${file.upload.path}")
	private String fileUploadPath;
	@Value("${mailmaster.app.id}")
	private String mailAppId;
	@Autowired
	private DocumentUploadRepository documentUploadRepo;
	@Autowired
	private CriteriaQueryServiceImpl query;
	@Autowired
	private MailMasterRepository mailMasterRepo;
	@Autowired
	private MotorDataDetailRepository motorDataDetailRepository;
	@Autowired
	private HomePositionMasterRepository hpmRepo;
	@Autowired
	private CommonService cs;
	@Override
	public CommonResponse documentUpload(List<DocumentUploadReq> list) {
		CommonResponse res = new CommonResponse();
		log.info("documentUpload request :"+list.size());
		try {
			for (DocumentUploadReq req :list) {
				
				String[] spilt =req.getFileName().split("[.]");
				String file_extenion =spilt[1];
				String file_name=spilt[0];
				
				String file_path =fileUploadPath+file_name+"_"+new Date().getSeconds()+"."+file_extenion;
				
				byte[] decodedImg = Base64.getDecoder().decode(req.getBase64File().getBytes(StandardCharsets.UTF_8));
	                    
				Path destinationFile = Paths.get(file_path);
				Files.write(destinationFile, decodedImg);
				
		        DocumentUploadDetails uploadDetails = DocumentUploadDetails.builder()
		        		.documentId(Long.valueOf(req.getDocumentTypeId()))
		        		.description(StringUtils.isBlank(req.getDescription())?"":req.getDescription())
		        		.fileName(req.getFileName())
		        		.filePathName(file_path)
		        		.productId(Long.valueOf(req.getProductId()))
		        		.quoteNo(Long.valueOf(req.getQuoteNo()))
		        		.status("Y")
		        		.vtypeId(Long.valueOf(req.getVtypeId()))
		        		.uploadTime(new Date())
		        		.build();
		        documentUploadRepo.save(uploadDetails);
			}
	        res.setMessage("SUCCESS");
	        res.setResponse("document upload successfully..!");
		}catch (Exception e) {
			res.setMessage("FAILED");
	        res.setResponse("document upload failed..!");
			e.printStackTrace();
			log.error(e);
		}
		return res;
	}

	@Override
	public CommonResponse getDocumentDetails(DocumentUploadGetReq req) {
		CommonResponse res = new CommonResponse();
		List<DocumentUploadGetRes> response =new ArrayList<DocumentUploadGetRes>();
		try {
			List<Tuple> list =query.getDocumetUploadDetailsByQuoteNo(req);
			if(!CollectionUtils.isEmpty(list)) {
				list.forEach( p->{
					DocumentUploadGetRes uploadGetRes =DocumentUploadGetRes.builder()
							.chassisNo(p.get("chassisNo")==null?"":p.get("chassisNo").toString())
							.description(p.get("documentDesc")==null?"":p.get("documentDesc").toString())
							.documentTypeId(p.get("documentId")==null?"":p.get("documentId").toString())
							.engineNo(p.get("engineNumber")==null?"":p.get("engineNumber").toString())
							.fileName(p.get("fileName")==null?"":p.get("fileName").toString())
							.filePath(p.get("filePathName")==null?"":p.get("filePathName").toString().replace("\\", "//"))
							.registrationNo(p.get("registrationNo")==null?"":p.get("registrationNo").toString())
							.build();
					response.add(uploadGetRes);
				});
				
				res.setMessage("SUCCESS");
				res.setResponse(response);
			}else {
				res.setMessage("FAILED");
				res.setResponse(response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("FAILED");
			res.setResponse(response);
		}
		return res;
	}

	@Override
	public String sendReferalQuoteMail(BuyPolicyRequest req) {
		String response ="Mail sent successfully";
		try {
			MailMaster mail =mailMasterRepo.findByAppId(mailAppId);
			List<Map<String,Object>> list =mailMasterRepo.getMailTemplateMaster();
			Map<String,Object> map =list.get(0);
			
			String template =map.get("MAIL_BODY")==null?"":map.get("MAIL_BODY").toString();
			String subject =map.get("MAIL_SUBJECT")==null?"":map.get("MAIL_SUBJECT").toString();
			String regards =map.get("MAIL_REGARDS")==null?"":map.get("MAIL_REGARDS").toString();
			String mailTo =map.get("EMAIL_TO")==null?"":map.get("EMAIL_TO").toString();
			String mailcc =map.get("EMAIL_CC")==null?"":map.get("EMAIL_CC").toString();
			
			String host =StringUtils.isBlank(mail.getSmtpHost())?"":mail.getSmtpHost();
			String port =mail.getSmtpPort()==null?"":mail.getSmtpPort().toString();
			String userName =StringUtils.isBlank(mail.getSmtpUser())?"":mail.getSmtpUser();
			String password =StringUtils.isBlank(mail.getSmtpPwd())?"":mail.getSmtpPwd();
			String emailFrom =StringUtils.isBlank(mail.getMailCc())?"":mail.getMailCc();
			
			//properties
			Properties prop = new Properties();
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true"); // TLS

			//Authentication	
			javax.mail.Authenticator auth=new SMTPAuthenticator(userName,password);
			Session session = Session.getInstance(prop,auth);
			//session.setDebug(true);
			MimeMessage msg = new MimeMessage(session);
			
			//From mail details
			InternetAddress addressFrom = new InternetAddress(emailFrom, "Madison");
			msg.setFrom(addressFrom);
			
			//to mail details
			int index=0;
			InternetAddress [] addressTo =new InternetAddress[10];
			String[] to =mailTo.split(",");
			for(String t :to) {
				addressTo[index]=new InternetAddress(t.trim().toLowerCase());
				index++;
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			
			//cc mail details
			int ind=0;
			InternetAddress [] addresscc =new InternetAddress[10];;
			String[] cc =mailcc.split(",");
			for(String t :cc) {
				addresscc[ind]=new InternetAddress(t.trim().toLowerCase());
				ind++;
			}
			msg.setRecipients(Message.RecipientType.CC, addresscc);

			
			String mailbody =getMailBody(req.getQuoteNo());
			
			// framing mail body
			String frameMsg =template.replace("{QUOTE_NO}", req.getQuoteNo()).replace("{TABLE}", mailbody);
			String bodyMsg =frameMsg+regards;
			msg.setSubject(subject.replace("{QUOTE_NO}", req.getQuoteNo()));
			msg.setContent(bodyMsg, "text/html");
			
			//send mail
			Transport.send(msg);
			
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response ="Mail sent failed";
		}
		return response;
	}
	
	public String getMailBody(String quoteNo) {
		try {
			String head=
			         "<table width='100%' border='1' align='center'>"
			                + "<tr align='center'>"
			                + "<td><b>Make<b></td>"
			                + "<td><b>Model<b></td>"
			                + "<td><b>EngineNumber<b></td>"
			                + "<td><b>ChassisNo<b></td>"
			                + "<td><b>BodyType<b></td>"
			                + "<td><b>VehicleUsage<b></td>"
			                + "<td><b>VehicleValue<b></td>"
			                + "</tr>";
			
			List<MotorDataDetail> mdd =motorDataDetailRepository.findByQuoteNo(Long.valueOf(quoteNo));
			StringBuilder sb = new StringBuilder(head);
			for(MotorDataDetail d :mdd) {
				 sb.append("<tr>")
				 	.append("<td>")
				    .append(StringUtils.isBlank(d.getMakeName())?"":d.getMakeName())
				       .append("</td>")
				       .append("<td>")
				       .append(StringUtils.isBlank(d.getModelName())?"":d.getModelName())
				       .append("</td>")
				       .append("<td>")
				       .append(StringUtils.isBlank(d.getEngineNumber())?"":d.getEngineNumber())
				       .append("</td>")
				       .append("<td>")
				       .append(StringUtils.isBlank(d.getChassisNo())?"":d.getChassisNo())
				       .append("</td>")
				       .append("<td>")
				       .append(StringUtils.isBlank(d.getBodyName())?"":d.getBodyName())
				       .append("</td>")
				       .append("<td>")
				       .append(StringUtils.isBlank(d.getVehUsageName())?"":d.getVehUsageName())
				       .append("</td>")
				       .append("<td>")
				       .append(d.getSuminsuredValueLocal()==null?"":d.getSuminsuredValueLocal());
				    sb.append("</tr>");
			}
			return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	@Override
	public CommonResponse documentDelete(DocumentDeleteReq req) {
		CommonResponse res =new CommonResponse();
		try {
			DocumentUploadDetailsId id =DocumentUploadDetailsId.builder()
					.documentId(new Long(req.getDocumentTypeId()))
					.productId(new Long(req.getProductId()))
					.quoteNo(new Long(req.getQuoteNo()))
					.vtypeId(new Long(req.getVtypeId()))
					.build();
			documentUploadRepo.deleteById(id);
			res.setMessage("SUCCESS");
			res.setResponse("Document deleted successfully");
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return res;
	}

	@Override
	public CommonResponse getPolicyCertificate(String quoteNo, String vehicleId) {
		CommonResponse res = new CommonResponse();
		Connection conn =null;
		try {
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("%20", " ");
			classpath = classpath.substring(1, classpath.length());

			String imagepath = classpath + "images/";
			
			String certificatePath = cs.getappconstantsProperty().getProperty("madison.policy.certificate.file.path");

			String jasperPath = certificatePath + "/" +quoteNo+ ".pdf";

			String qrcode_common_path =cs.getappconstantsProperty().getProperty("madison.policy.qrcode.file.path");
			String qrcode_jasper_path =qrcode_common_path+quoteNo+".JPG";;
			
			
			List<Map<String,Object>> qrData =motorDataDetailRepository.getCertificateDetails(quoteNo, vehicleId);
			
			String polNo=qrData.get(0).get("POLICY_NO")==null?"":qrData.get(0).get("POLICY_NO").toString();
			String vehRegNo=qrData.get(0).get("VECHICLE_REG_NO")==null?"":qrData.get(0).get("VECHICLE_REG_NO").toString();
			String issueDate=qrData.get(0).get("ISSUE_DATE")==null?"":qrData.get(0).get("ISSUE_DATE").toString();
			String expDate=qrData.get(0).get("EXPIRY_DATE")==null?"":qrData.get(0).get("EXPIRY_DATE").toString();
			String certNo=qrData.get(0).get("CERTIFICATE_NO")==null?"":qrData.get(0).get("CERTIFICATE_NO").toString();
			String tag="MGen ZM";
			String msg=polNo+"\r\n"+vehRegNo+"\r\n"+issueDate+"\r\n"+expDate+"\r\n"+certNo+"\r\n"+tag;
			String loginId=qrData.get(0).get("LOGIN_ID")==null?"":qrData.get(0).get("LOGIN_ID").toString();
			String branchCode=qrData.get(0).get("BRANCH_CODE")==null?"":qrData.get(0).get("BRANCH_CODE").toString();

			String userName =motorDataDetailRepository.getUserName(loginId);
			
			
			generateQRCode(msg,200,200,qrcode_jasper_path);
			
			HashMap<String, Object> jasperParameter = new HashMap<String, Object>();
			jasperParameter.put("Quoteno", quoteNo);
			jasperParameter.put("Pvbranch", branchCode);
			jasperParameter.put("Status", "");
			jasperParameter.put("PvVechicle", vehicleId);
			jasperParameter.put("imagePath", imagepath);
			jasperParameter.put("Pvusername",userName==null?"":userName);
			jasperParameter.put("QRPath", qrcode_common_path);

			conn = getConnection();

			InputStream is = this.getClass().getResourceAsStream("/jasper/PolicyCertificateNew.jrxml");

			JasperReport jr = JasperCompileManager.compileReport(is);

			JasperPrint jp = JasperFillManager.fillReport(jr, jasperParameter, conn);

			JasperExportManager.exportReportToPdfFile(jp, jasperPath);
			
			File file = new File(jasperPath);
			
			
			byte[] bytes = FileUtils.readFileToByteArray(file);

			String encodeToString = Base64.getEncoder().encodeToString(bytes);
			
			res.setMessage("SUCCESS");
			res.setResponse(encodeToString);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("FAILED");
			res.setResponse(null);
		}finally {
			
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	

	private Connection getConnection() {
		Connection connection = null;
		try {

			String connType = cs.getappconstantsProperty().getProperty("connection.type");
			connType = StringUtils.isBlank(connType) ? "spring" : connType;

			if (connType.equalsIgnoreCase("spring"))
				connection = cs.datasourceSpring().getConnection();
			else
				connection = cs.datasourceContext().getConnection();

		} catch (Exception e) {
			log.error(e);
		}
		return connection;
	}
	
	
	@SuppressWarnings("deprecation")
	public void generateQRCode(String text, int width, int height, String filePath) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
			        .lastIndexOf('.') + 1), new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public CommonResponse getPolicySchedule(String quoteNo) {
		CommonResponse res = new CommonResponse();
		Connection conn =null;
		try {
			HomePositionMaster hpm =hpmRepo.findByQuoteNo(Long.valueOf(quoteNo));
			
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("%20", " ");
			classpath = classpath.substring(1, classpath.length());
			String imagepath = classpath + "images/";
			
			String policySchedulePath =cs.getappconstantsProperty().getProperty("madison.policy.schedule.file.path");
			
			String jasper_path=policySchedulePath +quoteNo+ ".pdf";
			
			HashMap<String, Object> jasperParameter = new HashMap<String, Object>();
			jasperParameter.put("Quoteno", quoteNo);
			jasperParameter.put("Pvbranch", hpm.getBranchCode());
			jasperParameter.put("imagePath", imagepath);
			jasperParameter.put("Status", "");
			
			 conn = getConnection();

			InputStream is = this.getClass().getResourceAsStream("/jasper/Schedule.jrxml");

			JasperReport jr = JasperCompileManager.compileReport(is);

			JasperPrint jp = JasperFillManager.fillReport(jr, jasperParameter, conn);

			JasperExportManager.exportReportToPdfFile(jp, jasper_path);
			
			File file = new File(jasper_path);
			
			
			byte[] bytes = FileUtils.readFileToByteArray(file);

			String encodeToString = Base64.getEncoder().encodeToString(bytes);
			
			res.setMessage("SUCCESS");
			res.setResponse(encodeToString);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("FAILED");
			res.setResponse(null);
		}finally {
			
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	@Override
	public CommonResponse getPolicyReceipt(String quoteNo) {
		CommonResponse res = new CommonResponse();
		Connection conn =null;
		try {
			HomePositionMaster hpm =hpmRepo.findByQuoteNo(Long.valueOf(quoteNo));
			
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("%20", " ");
			classpath = classpath.substring(1, classpath.length());
			String imagepath = classpath + "images/";
			
			String receiptPath =cs.getappconstantsProperty().getProperty("madison.policy.receipt.file.path");

			String jasper_path=receiptPath +quoteNo+ ".pdf";

			HashMap<String, Object> jasperParameter = new HashMap<String, Object>();
			jasperParameter.put("Quoteno", quoteNo);
			jasperParameter.put("Pvproduct", hpm.getProductId()==null?"65":hpm.getProductId());
			jasperParameter.put("Pvbranch", StringUtils.isBlank(hpm.getBranchCode())?"01":hpm.getBranchCode());
			jasperParameter.put("imagePath", imagepath);
						
			conn = getConnection();

			InputStream is = this.getClass().getResourceAsStream("/jasper/Receipt.jrxml");

			JasperReport jr = JasperCompileManager.compileReport(is);

			JasperPrint jp = JasperFillManager.fillReport(jr, jasperParameter, conn);

			JasperExportManager.exportReportToPdfFile(jp, jasper_path);
				
			File file = new File(jasper_path);
				
				
			byte[] bytes = FileUtils.readFileToByteArray(file);

			String encodeToString = Base64.getEncoder().encodeToString(bytes);
				
			res.setMessage("SUCCESS");
			res.setResponse(encodeToString);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("FAILED");
			res.setResponse(null);
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	@Override
	public CommonResponse getPolicyDebit(String quoteNo) {
		CommonResponse res = new CommonResponse();
		Connection conn =null;
		try {
			HomePositionMaster hpm =hpmRepo.findByQuoteNo(Long.valueOf(quoteNo));
			
			String classpath = this.getClass().getClassLoader().getResource("").getPath();
			classpath = classpath.replaceAll("%20", " ");
			classpath = classpath.substring(1, classpath.length());
			String imagepath = classpath + "images/";
			
			String debitPath =cs.getappconstantsProperty().getProperty("madison.policy.debit.file.path");

			String jasper_path=debitPath +quoteNo+ ".pdf";

			
			HashMap<String, Object> jasperParameter = new HashMap<String, Object>();
			jasperParameter.put("Quoteno", quoteNo);
			jasperParameter.put("Pvproduct", hpm.getProductId()==null?"65":hpm.getProductId());
			jasperParameter.put("Pvbranch", StringUtils.isBlank(hpm.getBranchCode())?"01":hpm.getBranchCode());
			jasperParameter.put("Status", "");
			jasperParameter.put("imagePath", imagepath);
						
			conn = getConnection();

			InputStream is = this.getClass().getResourceAsStream("/jasper/Receipt.jrxml");

			JasperReport jr = JasperCompileManager.compileReport(is);

			JasperPrint jp = JasperFillManager.fillReport(jr, jasperParameter, conn);

			JasperExportManager.exportReportToPdfFile(jp, jasper_path);
				
			File file = new File(jasper_path);
				
			byte[] bytes = FileUtils.readFileToByteArray(file);

			String encodeToString = Base64.getEncoder().encodeToString(bytes);
				
			res.setMessage("SUCCESS");
			res.setResponse(encodeToString);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			res.setMessage("FAILED");
			res.setResponse(null);
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
}
