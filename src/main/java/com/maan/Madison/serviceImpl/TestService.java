package com.maan.Madison.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.maan.Madison.request.MotorIntegrationRequest;
import com.maan.Madison.service.MotorIntegrationService;

public class  TestService implements MotorIntegrationService {
	
	@PersistenceContext
	private EntityManager em;
	
	Query query =null;

	@Override
	public Object integration(MotorIntegrationRequest req) {
		try {
			Date lvapprdate;
			Object /* Use -meta option */ lvPolicyNo = "";
			Object /* Use -meta option */ lnproduct= "";
			Object /* Use -meta option */ lvcoreproduct= "";
			String lvcustName= "";;
			Object /* Use -meta option */ lvCustemail= "";
			Object /* Use -meta option */ lvcustResidencyaddr= "";
			Object /* Use -meta option */ lvcustcity= "";
			Object /* Use -meta option */ lvcustpobox= "";
			String lvcustcountry= "";;
			Object /* Use -meta option */ lvcustmobile= "";
			Object /* Use -meta option */ lvcustnrc= "";
			String ldpolicystart="";
			String ldpolicyend="";
			String Piftflexfield1="";
			String Piftflexfield2="";
			String Piftflexfield3="";
			String Piftflexfield4="";
			String Piftflexfield5="";
			String Piftflexfield6="";
			String Piftflexfield7="";
			String Piftflexfield8="";
			String Piftflexfield9="";
			String Piftflexfield10="";
			String Piftflexfield11="";
			String Piftflexfield12="";
			String Piftflexfield13="";
			String Piftflexfield14="";
			String Piftflexfield15="";
			String Piftflexfield16="";
			String Piftflexfield17="";
			String Piftflexfield18="";
			String Piftflexfield19="";
			String Piftflexfield20="";
			String Piftflexfield21="";
			String Piftflexfield22="";
			String Piftflexfield23="";
			String Piftflexfield24="";
			String Piftflexfield25="";
			String Piftflexfield26="";
			String Piftflexfield27="";
			String Piftflexfield28="";
			String Piftflexfield29="";
			String Piftflexfield30="";
			String Piftflexfield31="";
			String Piftflexfield32="";
			String Piftflexfield33="";
			String Piftflexfield34="";
			String Piftflexfield35="";
			String Piftflexfield36="";
			String Piftflexfield37="";
			String Piftflexfield38="";
			String Piftflexfield39="";
			String Piftflexfield40="";
			String Piftflexfield60="";// SQLINES DEMO *** ridhar T on 27032018
			String PIFTQUOTENO="";
			String Lvpiftlevel = "";
			Number Lvpifttransysid = 0;
			Number Lnsrno= 0;
			Number Lvlvlsrcode= 0;
			String Lvuses="";
			String Lvcore_app="";
			String Lvcore_code="";
			String Lvcolor="";
			String Lvbank="";
			String Lvcyl="";
			String Lvchassis_no="";
			String Lvengine_no="";
			String Lvreg_no="";
			String Lvcase="";
			String Lvbody_type_name="";
			String Lvmake_name="";
			String Lvseating_cap_1="";
			String Lvmanufacture_yr="";
			String Lvseating_cap="";
			String Lvagency_repair="";
			String Lvdriver_dob="";
			String Lvstatus="";
			Number Piftlvlsrno = 0;
			Number Z =new BigDecimal(0);
			Number W =new BigDecimal(0);
			Number V =new BigDecimal(0);
			Number O =new BigDecimal(0); // SQLINES DEMO *** SRIDHAR T ON 18012018
			Number P =new BigDecimal(0); // SQLINES DEMO *** SRIDHAR T ON 18012018
			Number Q =new BigDecimal(0); // SQLINES DEMO *** SRIDHAR T ON 18012018
			Number R =new BigDecimal(0); // SQLINES DEMO *** SRIDHAR T ON 18012018
			String LVQUOTE_NO="";// SQLINES DEMO *** SRIDHAR T ON 19012018
			String M_CER_BOOK="";// SQLINES DEMO *** SRIDHAR T ON 23012018
			String M_CER_PREF="";// SQLINES DEMO *** SRIDHAR T ON 23012018
			Number M_CER_NUM;// SQLINES DEMO *** SRIDHAR T ON 23012018
			String lvcustomercode="";
			Number LvappNo=0;
			Object /* Use -meta option */ lvWorkAddress;
			Object /* Use -meta option */ lvcustoccupation;
			Object /* Use -meta option */ lvPassPort;
			Object /* Use -meta option */ lvalternativeMobile;
			Object /* Use -meta option */ lvCusttype;
			Object /* Use -meta option */ lvcomregno;
			Number lnriskid=0;;
			String lvdivisioncode="";
			String lvdefaultCurrency="";
			String lvPremiumcurr="";
			String lvcorePremcode ="";
			Number lncoreExchangeRate;
			Number lnPolicyterm=0;
			Number lnpaymentMode=0;
			String lvcorePayment="";
			String lvliabilityYn="";
			Number lnUsdExchangeRate=0;
			Number lnlcvatCharges =0;;
			Object /* Use -meta option */ lvPolicytypecode;
			Object /* Use -meta option */ lvpolicytypedesc;
			String lvReceiptNO ="";
			String lvReceiptDate ="";
			String lnbankcorecode ="";
			String lvAccountNo ="";
			Number lnoverallPrem =0;
			String Lvfirstname ="";
			String Lvlastname ="";
			String lvpassportno ="";
			String lvlogin ="";
			String lvintgdepcode ="";
			String lvintgcurrcode ="";
			String lvintgprodcode ="";
			String lvaddcoversign ="";
			String lvintgdoccode ="";
			String Lvissueddt ="";
			String lvrsabrokercode ="";
			String LVDSCODE ="";
			
			if( StringUtils.isNotBlank(Lvpiftlevel)) {
				query=em.createNativeQuery("Insert Into Pt_Intg_Flex_Tran ( Pift_Tran_Sys_Id, Pift_Policy_No, PIFT_SR_NO, PIFT_LVL_SR_NO, Pift_Level, Pift_Flex_Field_1, Pift_Flex_Field_2, Pift_Flex_Field_3, Pift_Flex_Field_4, Pift_Flex_Field_5, Pift_Flex_Field_6, Pift_Flex_Field_7, Pift_Flex_Field_8, Pift_Flex_Field_9, Pift_Flex_Field_10, Pift_Flex_Field_11, Pift_Flex_Field_12, Pift_Flex_Field_13, Pift_Flex_Field_14, Pift_Flex_Field_15, Pift_Flex_Field_16, Pift_Flex_Field_17, Pift_Flex_Field_18, Pift_Flex_Field_19, Pift_Flex_Field_20, Pift_Flex_Field_21, Pift_Flex_Field_22, Pift_Flex_Field_23, Pift_Flex_Field_24, Pift_Flex_Field_25, Pift_Flex_Field_26, Pift_Flex_Field_27, Pift_Flex_Field_28, Pift_Flex_Field_29, Pift_Flex_Field_30, Pift_flex_field_31, Pift_flex_field_32, Pift_flex_field_33, Pift_flex_field_34, Pift_flex_field_35, Pift_flex_field_36, Pift_Flex_field_37, Pift_flex_field_38, Pift_flex_field_39, Pift_flex_field_40, Pift_flex_field_60, Pift_Status, Pift_Error_Message, PIFT_QUOTE_NO  ) Values "
						+ "( ?1 , ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27, ?28, ?29, ?30, ?31, ?32, ?33, ?34, ?35, ?36, ?37, ?38, ?39, ?40, ?41, ?42, ?43, ?44, ?45, ?46, '', '', ?47 )");
				query.setParameter(1, Lvpifttransysid);
				query.setParameter(2, lvPolicyNo);
				query.setParameter(3, Lnsrno);
				query.setParameter(4, Piftlvlsrno);
				query.setParameter(5, Lvpiftlevel);
				query.setParameter(6, Piftflexfield1);
				query.setParameter(7, Piftflexfield2);
				query.setParameter(8, Piftflexfield3);
				query.setParameter(9, Piftflexfield4);
				query.setParameter(10, Piftflexfield5);
				query.setParameter(11, Piftflexfield6);
				query.setParameter(12, Piftflexfield7);
				query.setParameter(13, Piftflexfield8);
				query.setParameter(14, Piftflexfield9);
				query.setParameter(15, Piftflexfield10);
				query.setParameter(16, Piftflexfield11);
				query.setParameter(17, Piftflexfield12);
				query.setParameter(18, Piftflexfield13);
				query.setParameter(19, Piftflexfield14);
				query.setParameter(20, Piftflexfield15);
				query.setParameter(21, Piftflexfield16);
				query.setParameter(22, Piftflexfield17);
				query.setParameter(23, Piftflexfield18);
				query.setParameter(24, Piftflexfield19);
				query.setParameter(25, Piftflexfield20);
				query.setParameter(26, Piftflexfield21);
				query.setParameter(27, Piftflexfield22);
				query.setParameter(28, Piftflexfield23);
				query.setParameter(29, Piftflexfield24);
				query.setParameter(30, Piftflexfield25);
				query.setParameter(31, Piftflexfield26);
				query.setParameter(32, Piftflexfield27);
				query.setParameter(33, Piftflexfield28);
				query.setParameter(34, Piftflexfield29);
				query.setParameter(35, Piftflexfield30);
				query.setParameter(36, Piftflexfield31);
				query.setParameter(37, Piftflexfield32);
				query.setParameter(38, Piftflexfield33);
				query.setParameter(39, Piftflexfield34);
				query.setParameter(40, Piftflexfield35);
				query.setParameter(41, Piftflexfield36);
				query.setParameter(42, Piftflexfield37);
				query.setParameter(43, Piftflexfield38);
				query.setParameter(44, Piftflexfield39);
				query.setParameter(45, Piftflexfield40);
				query.setParameter(46, Piftflexfield60);
				query.setParameter(47, PIFTQUOTENO);
				query.executeUpdate();
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 

	

	
	
	
}