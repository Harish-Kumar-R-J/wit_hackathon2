package docs;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.ibm.cloud.objectstorage.AmazonServiceException;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.GetObjectRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectMetadata;
import com.ibm.cloud.objectstorage.services.s3.model.PutObjectRequest;
import com.ibm.cloud.objectstorage.services.s3.model.S3Object;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import com.ibm.cloud.objectstorage.util.IOUtils;

    public class CosExample
    {
        private static String COS_ENDPOINT = "s3.jp-tok.cloud-object-storage.appdomain.cloud"; // eg "https://s3.us.cloud-object-storage.appdomain.cloud"
        private static String COS_API_KEY_ID = "py3tnZFJmUcni_wmx3EFEuuMVBpE_N7NSct1Aj9nQxkF"; // eg "0viPHOY7LbLNa9eLftrtHPpTjoGv6hbLD1QalRXikliJ"
        private static String COS_AUTH_ENDPOINT = "https://iam.cloud.ibm.com/identity/token";
        private static String COS_SERVICE_CRN = "crn:v1:bluemix:public:cloud-object-storage:global:a/68e3eef8c511465d91059147f6fab99c:b6ac60bb-eeb9-4801-bf92-063a29ad9003::"; // "crn:v1:bluemix:public:cloud-object-storage:global:a/<CREDENTIAL_ID_AS_GENERATED>:<SERVICE_ID_AS_GENERATED>::"
        private static String COS_BUCKET_LOCATION = "jp-tok"; // eg "us"
        
        public static void main(String[] args)
        {
            String bucketName = "cloud-object-storage-7f-cos-standard-80y";  // eg my-unique-bucket-name
            String newBucketName = "my-other-unique-bucket-name22222222222222222222222222222"; // eg my-other-unique-bucket-name
            String apiKey = "py3tnZFJmUcni_wmx3EFEuuMVBpE_N7NSct1Aj9nQxkF"; // eg "W00YiRnLW4k3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
            String serviceInstanceId = "crn:v1:bluemix:public:cloud-object-storage:global:a/68e3eef8c511465d91059147f6fab99c:b6ac60bb-eeb9-4801-bf92-063a29ad9003::"; // eg "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
            String endpointUrl =  "s3.jp-tok.cloud-object-storage.appdomain.cloud"; // this could be any service endpoint

            String storageClass = "Smart Tier";
            String location = "jp-tok"; // not an endpoint, but used in a custom function below to obtain the correct URL
            	
            
                SDKGlobalConfiguration.IAM_ENDPOINT = COS_AUTH_ENDPOINT;

                AmazonS3 _cos = null;
                try {
                    _cos = createClient(COS_API_KEY_ID, COS_SERVICE_CRN, COS_ENDPOINT, COS_BUCKET_LOCATION);
                } catch (SdkClientException sdke) {
                    System.out.printf("SDK Error: %s\n", sdke.getMessage());
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            
            
            
                createfunc(bucketName, _cos);
//                System.out.println(_cos.getUrl("cloud-object-storage-7f-cos-standard-80y", "load.jpg"));
//                System.out.println("Current time: " + LocalDateTime.now());
            AmazonS3 cosClient = createClient(apiKey, serviceInstanceId, endpointUrl, location);
            
            }
            
            public static void select(String bucketName, AmazonS3 _cos)             
            {Scanner sc= new Scanner(System.in);
            String input = "";
            List<String>path = new ArrayList<String>();
            List<String>filename = new ArrayList<String>();
//            String path = null, filename = null;
//            System.out.println("1) Check entries \n2) Clear cloud \n3) Delete \n4) Read\n5) Update\n6) Clear\n7) Insert prod\n8) List your products");
            System.out.println("1) insert products\n2) Profile\n3) List all products\n4) Delete\n5) LogOut");
            input = sc.nextLine();
            
            
            
//            if(input.equals("4"))
//            {String th = null, th1;
//            th1 = "empssss"; 
//      		put(_cos, th, th1, bucketName);}
            
            if(input.equals("4"))
            {List<String> items = new ArrayList<>();
            items.add("emailid");
            items.add("address");
            items.add("contact");
            items.add("p_description");
            items.add("price");
            items.add("prod_name");
            delete(bucketName, items, _cos);}
            
            
            else if(input.equals("3"))
            {List<String> items = Arrays.asList("prod_name", "contact", "address", "price", "p_description", "emailid");
        	display(bucketName, items, _cos);}
            
            else if(input.equals("1"))
            {List<String> items = Arrays.asList("address", "contact", "p_description", "price", "prod_name", "emailid");
        	insert2(bucketName, items, _cos);}
            
            else if(input.equals("2"))
            {List<String> items = Arrays.asList("emailid", "address", "contact", "p_description", "price", "prod_name");
        	listproducts(bucketName, items, _cos);}
            
            else if(input.equals("5"))
            {List<String> itemname = Arrays.asList("userid", "password", "cname.txt", "p_no.txt", "l_address.txt");
            logout(bucketName,itemname, _cos);}
            
            else
            {System.out.println("Invalid option");
            select(bucketName, _cos);}}

            
            
            public static void logout(String bucketName,List<String>itemname , AmazonS3 _cos)
            {PrintWriter pw;
			try {
				pw = new PrintWriter("login.txt");
				pw.write("");
	            pw.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			login(bucketName, itemname, _cos);}
            
            public static void display(String bucketName, List<String> items, AmazonS3 _cos)
            {String things = "";
            List<String> prod_name = new ArrayList<>();
            List<String> address = new ArrayList<>();
            List<String> contact = new ArrayList<>();
            List<String> price = new ArrayList<>();
            List<String> p_discp = new ArrayList<>();
            List<String> email = new ArrayList<>();
            String everything = "";
            try(FileInputStream inputStream = new FileInputStream("login.txt"))      
            {everything = IOUtils.toString(inputStream);}
            catch (IOException ignored) {}
            String emails = readafile(bucketName, "emailid", _cos);
            
            List<String> email2 = new ArrayList<>();
            for(String e: emails.split("#"))
            {if(!e.equals(""))
        	{email2.add(e);}}
            
            int cnt = -1;
        	for(int i=0;i<items.size();i++)
    		{cnt = -1;
            things = readafile(bucketName, items.get(i), _cos);
        	for(String e: things.split("###"))
            {if(!e.equals(""))
    		{cnt+=1;}
    		if(items.get(i).equals("prod_name") && !e.equals("") && (!email2.get(cnt).equals(everything)))
            {prod_name.add(e);}
            else if(items.get(i).equals("address") && !e.equals("") && (!email2.get(cnt).equals(everything)))
            {address.add(e);}
            else if(items.get(i).equals("contact") && !e.equals("") && (!email2.get(cnt).equals(everything)))
            {contact.add(e);}
            else if(items.get(i).equals("price") && !e.equals("") && (!email2.get(cnt).equals(everything)))
            {price.add(e);}
            else if(items.get(i).equals("p_description") && !e.equals("") && (!email2.get(cnt).equals(everything)))
            {p_discp.add(e);}}}
        	
        	if(prod_name.size()!=0)
        	{for(int i=0;i<prod_name.size();i++)
        	{System.out.println("Product Name: " + prod_name.get(i));
        	System.out.println("Price: ₹" + price.get(i));
        	System.out.println("Address: " + address.get(i));
        	System.out.println("Contact: " + contact.get(i));
        	System.out.println("Product description: " + p_discp.get(i) + "\n\n");}}
        	else
        	{System.out.println("No products available\n\n");}
        	select(bucketName, _cos);}
            
            
            public static void listproducts(String bucketName, List<String> items, AmazonS3 _cos)
            {String everything = "";
            try(FileInputStream inputStream = new FileInputStream("login.txt"))      
            {everything = IOUtils.toString(inputStream);}
            catch (IOException ignored) {}
            
            List<String> cname = new ArrayList<>();
            List<String> p_no = new ArrayList<>();
            List<String> p_address = new ArrayList<>();
            
            List<String> ite = Arrays.asList("l_address.txt", "cname.txt", "p_no.txt");
            for(int i=0;i<ite.size();i++)
            {String out = readafile(bucketName, ite.get(i), _cos);
        	for(String e: out.split("###"))
            {if(ite.get(i).equals("l_address.txt") && !e.equals(""))
            {p_address.add(e);}
            else if(ite.get(i).equals("cname.txt") && !e.equals(""))
            {cname.add(e);}
            else if(ite.get(i).equals("p_no.txt") && !e.equals(""))
            {p_no.add(e);}}}
            
//            System.out.println(p_address);
//            System.out.println(cname);
//            System.out.println(p_no);
            
            String out = readafile(bucketName, "userid", _cos);
            int cnt=0;
    		for(String e: out.split("###"))
    		{if(!e.equals(""))
			{if(e.equals(everything))
    		{System.out.println("Company name: " + cname.get(cnt));
    		System.out.println("address     : " + p_address.get(cnt));
			System.out.println("Phone no    : " + p_no.get(cnt));
			System.out.println("Email       : " + everything);
			System.out.println("\n\n");}
			cnt+=1;}}
            
            
            String emailid = readafile(bucketName, "emailid", _cos);
            List<String> prod_name = new ArrayList<>();
            List<String> address = new ArrayList<>();
            List<String> contact = new ArrayList<>();
            List<String> price = new ArrayList<>();
            List<String> p_discp = new ArrayList<>();
            List<String> email = new ArrayList<>();
            for(String e: emailid.split("###"))
            {if(!e.equals(""))
    		{email.add(e);}}
            
            for(int i=0;i<items.size();i++)
            {for(String e: readafile(bucketName, items.get(i), _cos).split("###"))
            	{if(items.get(i).equals("address") && (!e.equals("")))
            		{address.add(e);}
            	else if(items.get(i).equals("contact") && (!e.equals("")))
            		{contact.add(e);}
            	else if(items.get(i).equals("p_description") && (!e.equals("")))
            		{p_discp.add(e);}
            	else if(items.get(i).equals("price") && (!e.equals("")))
            		{price.add(e);}
            	else if(items.get(i).equals("prod_name") && (!e.equals("")))
            		{prod_name.add(e);}}}
                        
            for(int i=0;i<prod_name.size();i++)
            {if(email.get(i).equals(everything.toString()))
            {System.out.println("Product Name       : " + prod_name.get(i));
        	System.out.println( "Price              : " + price.get(i));
        	System.out.println( "Address            : " + address.get(i));
        	System.out.println( "Contact            : " + contact.get(i));
        	System.out.println( "Product description: " + p_discp.get(i) + "\n\n");
        	System.out.println("     --------------------------------------    \n\n");}}
            select(bucketName, _cos);}
        	
            
        
        public static void createfunc(String bucketName, AmazonS3 _cos)
        {
        	File a = new File("prod_name.txt");
        	File b = new File("address.txt");
        	File c = new File("contact.txt");
        	File d = new File("price.txt");
        	File e = new File("p_description.txt");
        	File userid = new File("userid.txt");
        	File password = new File("password.txt");
        	File f = new File("l_address.txt");
        	File g = new File("cname.txt");
        	File h = new File("p_no.txt");
        	
        try {a.createNewFile();
        	b.createNewFile();
        	c.createNewFile();
        	d.createNewFile();
        	e.createNewFile();
        	userid.createNewFile();
    		password.createNewFile();
    		f.createNewFile();
    		g.createNewFile();
    		h.createNewFile();} catch (IOException e1) {e1.printStackTrace();}
        
        List<String> itemname = Arrays.asList("userid", "password", "cname.txt", "p_no.txt", "l_address.txt");
        login(bucketName, itemname, _cos);
        }
        
        
        public static void login(String bucketName, List<String>itemname, AmazonS3 _cos)
        {

        String everything = "";
        try(FileInputStream inputStream = new FileInputStream("login.txt"))      
            {everything = IOUtils.toString(inputStream);}
        catch (IOException ignored) {}
        
                
        if(everything.equals(""))
        {System.out.println("***GRAB SCRAP***\n1) Login\n2) Create a new account\n");
    	String inp = "", user_id, pwd;
        Scanner sc = new Scanner(System.in);
        inp = sc.next();
        if(inp.equals("1"))
        {System.out.println("Enter MailId and Password");
        List<String> u_p = new ArrayList<>();
        user_id = sc.nextLine();
        u_p.add(sc.nextLine());
    	u_p.add(sc.nextLine());
    	List<String> mailid = new ArrayList<>();
    	List<String> password = new ArrayList<>();
    	
    	String str = "";
    	
    	for(int i=0;i<2;i++)
    	{str = readafile(bucketName, itemname.get(i), _cos);
//    	System.out.println(str);
    	if(itemname.get(i).equals("userid"))
    	{for(String x: str.split("###"))
    	{if(!x.equals(""))
    	{mailid.add(x);}}}
    	
    	else if(itemname.get(i).equals("password"))
    	{for(String x: str.split("###"))
    	{if(!x.equals(""))
    	{password.add(x);}}}}
    	
    	done:
        for(int i=0;i<mailid.size();i++)
        {if(u_p.get(0).equals(mailid.get(i)) && u_p.get(1).equals(password.get(i)))
        {
    	try{File logg = new File("login.txt");
        FileWriter fw = new FileWriter(logg.getCanonicalPath());
        fw.write(u_p.get(0));fw.close();}catch(IOException ignored) {}
    	select(bucketName, _cos);
        break done;}
        else if(i==mailid.size()-1)
    	{System.out.println("invalid credentials");
    	List<String> itemname2 = Arrays.asList("userid", "password", "cname.txt", "p_no.txt", "l_address.txt");
    	login(bucketName, itemname2, _cos);}}}
    	
        if(inp.equals("2"))
        {System.out.println("Enter Details");
    	List<String> u_p = new ArrayList<>();
    	u_p.clear();
    	String inpt = sc.nextLine();
    	System.out.println("Enter Mailid");
//    	if(checkemail(sc.nextLine())!=0)
    	u_p.add(checkemail(sc.nextLine()));
    	System.out.println("Enter password");
    	u_p.add(sc.nextLine());
    	System.out.println("Company name");
    	u_p.add(check_mandatory("prod_name", sc.nextLine()));
    	System.out.println("Phone number");
    	u_p.add(check_mandatory("contact_info", sc.nextLine()));
    	System.out.println("address");
    	u_p.add(sc.nextLine());
//    	System.out.println(u_p.get(0) + "   " + u_p.get(1));
	   	File userid = new File("userid.txt");
    	File password = new File("password.txt");
    	File c_name = new File("cname.txt");
    	File p_no = new File("p_no.txt");
    	File l_address = new File("l_address.txt");
		
//    	System.out.println(itemname + "   llllllllllllll");
    	System.out.println("Creating Login...\n");
    	String out = "";
    	try {for(int i=0;i<itemname.size();i++)
    	{out = readafile(bucketName, itemname.get(i), _cos);
//        System.out.println(out + "  " + itemname.get(i));
		if(itemname.get(i).equals("userid"))
		{
		FileWriter fwu = new FileWriter(userid.getCanonicalPath());
		fwu.write(out.toString() + "###" + u_p.get(0));
		fwu.close();
		_cos.putObject(bucketName, "userid",new File(userid.getCanonicalPath()));
		PrintWriter pwu = new PrintWriter(userid.getCanonicalPath());
		pwu.write("");
		pwu.close();}
		
		else if(itemname.get(i).equals("cname.txt"))
		{
//		System.out.println("inside userid out: " + u_p.get(0));
		FileWriter fwu = new FileWriter(c_name.getCanonicalPath());
		fwu.write(out.toString() + "###" + u_p.get(2));
		fwu.close();
		_cos.putObject(bucketName, "cname.txt",new File(c_name.getCanonicalPath()));
		PrintWriter pwu = new PrintWriter(c_name.getCanonicalPath());
		pwu.write("");
		pwu.close();}	
		
		else if(itemname.get(i).equals("p_no.txt"))
		{
//		System.out.println("inside userid out: " + u_p.get(0));
		FileWriter fwu = new FileWriter(p_no.getCanonicalPath());
		fwu.write(out.toString() + "###" + u_p.get(3));
		fwu.close();
		_cos.putObject(bucketName, "p_no.txt",new File(p_no.getCanonicalPath()));
		PrintWriter pwu = new PrintWriter(p_no.getCanonicalPath());
		pwu.write("");
		pwu.close();}	
		
		else if(itemname.get(i).equals("l_address.txt"))
		{
//		System.out.println("inside userid out: " + u_p.get(0));
		FileWriter fwu = new FileWriter(l_address.getCanonicalPath());
		fwu.write(out.toString() + "###" + u_p.get(4));
		fwu.close();
		_cos.putObject(bucketName, "l_address.txt",new File(l_address.getCanonicalPath()));
		PrintWriter pwu = new PrintWriter(l_address.getCanonicalPath());
		pwu.write("");
		pwu.close();}	
		
		else if(itemname.get(i).equals("password"))
		{
//		System.out.println("inside password");
		FileWriter fwp = new FileWriter(password.getCanonicalPath());
        fwp.write(out + "###" + u_p.get(1));
        fwp.close();
		_cos.putObject(bucketName, "password",new File(password.getCanonicalPath()));}
		PrintWriter pwu = new PrintWriter(password.getCanonicalPath());
		pwu.write("");
		pwu.close();}
    	try{File logg = new File("login.txt");
        FileWriter fw = new FileWriter(logg.getCanonicalPath());
        fw.write(u_p.get(0));fw.close();}catch(IOException ignored) {}
    	select(bucketName, _cos);}
    	catch (IOException e) {e.printStackTrace();}}}
        else
        {select(bucketName, _cos);}}
        
        
        
        public static String check_mandatory(String field, String inp)
        {if(field.equals("prod_name"))
        {if(!inp.trim().equals(""))
        {return inp;}
        else
        	{System.out.println("invalid prod_name\nRe-enter prod_name");
        	Scanner sc = new Scanner(System.in);
        	String xyz = sc.nextLine();
        	return check_mandatory("prod_name", xyz);}
        }
        
        else if(field.equals("contact_info"))
        {if(inp.matches("-?\\d+(\\.\\d+)?") && (inp.length()==10 || inp.length()==11))
        {return inp;}
        else 
    	{System.out.println("invalid contact_info\nRe-enter contact_info");
    	Scanner sc = new Scanner(System.in);
    	String xyz = sc.nextLine();
    	return check_mandatory("contact_info", xyz);}}
    	
        else if(field.equals("address"))
        {if(!inp.trim().equals(""))
    	{return inp;}
        else
    	{System.out.println("invalid address\nRe-enter address");
    	Scanner sc = new Scanner(System.in);
    	String xyz = sc.nextLine();
    	return check_mandatory("address", xyz);}}
        
        else if(field.equals("price"))
        {if(inp.matches("-?\\d+(\\.\\d+)?") && !inp.equals("") && Integer.parseInt(inp)>=0)
        {return inp;}
        else
    	{System.out.println("invalid price tag\nRe-enter price tag");
    	Scanner sc = new Scanner(System.in);
    	String xyz = sc.nextLine();
    	return check_mandatory("price", xyz);}}
        
        return "";
        }
        
        public static void insert2(String bucketName, List<String> itemName, AmazonS3 _cos)
        {String path = "";
        Scanner sc = new Scanner(System.in);
        S3Object item = null;
	   	 System.out.println("Enter product name: ");
	   	 String p_name = check_mandatory("prod_name", sc.nextLine());
	   	 System.out.println("Enter contact: ");
	   	 String c_info = check_mandatory("contact_info", sc.nextLine());
	   	 System.out.println("Enter address: ");
	   	 String add = check_mandatory("address", sc.nextLine());
	   	 System.out.println("Enter price tag: ");
	   	 String price = check_mandatory("price", sc.nextLine());
	   	 System.out.println("Enter description: ");
	   	 String desc = sc.nextLine();
	   	 if(desc.equals(""))
	   	 {desc = "-";}
	   	 System.out.println("Entering values...");
	   	 
	   	File fp_name = new File("prod_name.txt");
    	File fadd= new File("address.txt");
    	File fc_info = new File("contact.txt");
    	File f_price = new File("price.txt");
    	File f_desc = new File("p_description.txt");
    	File f_email = new File("emailid.txt");
    	
    	
//    	"address", "contact", "p_description", "price", "prod_name", "emailid"
    	
    	
    	FileWriter ffw;
    	
    	//clearing all the contents of the local file
//		try {
//			ffw = new FileWriter(ffile.getCanonicalPath());
//			ffw.write("");} catch (IOException e1) {e1.printStackTrace();}
    	
    	String email = "";
    	
        try(FileInputStream inputStream = new FileInputStream("login.txt"))      
        {email = IOUtils.toString(inputStream);}
        catch (IOException ignored) {}
    	
        try
        {for(int i=0;i<itemName.size();i++)
    	{item = _cos.getObject(new GetObjectRequest(bucketName, itemName.get(i)));
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        InputStreamReader in = new InputStreamReader(item.getObjectContent());
            
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);}
        
        
        
        if(itemName.get(i).equals("prod_name")) {
//    	product name
        FileWriter fw_p_name = new FileWriter(fp_name.getCanonicalPath());
        fw_p_name.write(out.toString() + "###" + p_name);
        fw_p_name.close();
        _cos.putObject(bucketName, "prod_name",new File(fp_name.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fp_name.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(itemName.get(i).equals("emailid"))
        {FileWriter fw_email = new FileWriter(f_email.getCanonicalPath());
        fw_email.write(out.toString() + "###" + email);
        fw_email.close();
        _cos.putObject(bucketName, "emailid",new File(f_email.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_email.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(itemName.get(i).equals("price")) {
//		price
        FileWriter fw_price = new FileWriter(f_price.getCanonicalPath());
        fw_price.write(out.toString() + "###" + price);
        fw_price.close();
        _cos.putObject(bucketName, "price",new File(f_price.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_price.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(itemName.get(i).equals("contact")) {
//		contact info
        FileWriter fwc_info = new FileWriter(fc_info.getCanonicalPath());
        fwc_info.write(out.toString() + "###" + c_info);
        fwc_info.close();
        _cos.putObject(bucketName, "contact",new File(fc_info.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fc_info.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(itemName.get(i).equals("p_description")) {
//		description
        FileWriter fw_desc = new FileWriter(f_desc.getCanonicalPath());
        fw_desc.write(out.toString() + "###" + desc);
        fw_desc.close();
        _cos.putObject(bucketName, "p_description",new File(f_desc.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_desc.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(itemName.get(i).equals("address")) {
//		address
        FileWriter fw_add = new FileWriter(fadd.getCanonicalPath());
        fw_add.write(out.toString() + "###" + add);
        fw_add.close();
        _cos.putObject(bucketName, "address",new File(fadd.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fadd.getCanonicalPath());
        pw.write("");
        pw.close();}}}
        catch(IOException e) {e.printStackTrace();}
        System.out.println("Values entered!");
        select(bucketName, _cos);}
        
        
        public static void put(AmazonS3 cosClient, String path, String filename, String bucketName)
        {
        	
        	File a = new File("prod_name.txt");
        	File b = new File("address.txt");
        	File c = new File("contact.txt");
        	File d = new File("price.txt");
        	File e = new File("p_description.txt");
        	File f = new File("userid.txt");
        	File g = new File("password.txt");
        	File h = new File("emailid.txt");
        	File i = new File("l_address.txt");
        	File j = new File("p_no.txt");
        	File k = new File("cname.txt");
        	
        	try {
        		System.out.println("Clearing...");
				cosClient.putObject(bucketName,"prod_name",new File(a.getCanonicalPath()));
				cosClient.putObject(bucketName,"address",new File(b.getCanonicalPath()));
				cosClient.putObject(bucketName,"contact",new File(c.getCanonicalPath()));
				cosClient.putObject(bucketName,"price",new File(d.getCanonicalPath()));
				cosClient.putObject(bucketName,"p_description",new File(e.getCanonicalPath()));
				cosClient.putObject(bucketName,"userid",new File(f.getCanonicalPath()));
				cosClient.putObject(bucketName,"password",new File(g.getCanonicalPath()));
				cosClient.putObject(bucketName,"emailid",new File(h.getCanonicalPath()));
				cosClient.putObject(bucketName,"l_address.txt",new File(i.getCanonicalPath()));
				cosClient.putObject(bucketName,"p_no.txt",new File(j.getCanonicalPath()));
				cosClient.putObject(bucketName,"cname.txt",new File(k.getCanonicalPath()));
				System.out.println("Cleared");} 
        	catch (AmazonServiceException e1) {e1.printStackTrace();} 
        	catch (SdkClientException e1) {e1.printStackTrace();} 
        	catch (IOException e1) {e1.printStackTrace();}
        	
    	}

        
        public static AmazonS3 createClient(String apiKey, String serviceInstanceId, String endpointUrl, String location)
        {
            AWSCredentials credentials = new BasicIBMOAuthCredentials(apiKey, serviceInstanceId);
            ClientConfiguration clientConfig = new ClientConfiguration()
                    .withRequestTimeout(5000)
                    .withTcpKeepAlive(true);

            return AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(new EndpointConfiguration(endpointUrl, location))
                    .withPathStyleAccessEnabled(true)
                    .withClientConfiguration(clientConfig)
                    .build();
        }


        
        
        private static String readafile(String bucketName, String itemName, AmazonS3 _cos)
        {final StringBuilder out = new StringBuilder();
        	try {
        	S3Object item;
            item = _cos.getObject(new GetObjectRequest(bucketName, itemName));
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            
            InputStreamReader in = new InputStreamReader(item.getObjectContent());

            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);}
                        
            }catch(IOException ignored) {}
        	return out.toString();}
        
        public static void delete(String bucketName, List<String> items, AmazonS3 _cos)
        {String everything = "";
        try(FileInputStream inputStream = new FileInputStream("login.txt"))      
        {everything = IOUtils.toString(inputStream);}
        catch (IOException ignored) {}
        
        String emailid = readafile(bucketName, "emailid", _cos);
        List<String> prod_name = new ArrayList<>();
        List<String> address = new ArrayList<>();
        List<String> contact = new ArrayList<>();
        List<String> price = new ArrayList<>();
        List<String> p_discp = new ArrayList<>();
        List<String> email = new ArrayList<>();
        for(String e: emailid.split("###"))
        {if(!e.equals(""))
		{email.add(e);}}
        
        for(int i=0;i<items.size();i++)
        {for(String e: readafile(bucketName, items.get(i), _cos).split("###"))
        	{if(items.get(i).equals("address") && (!e.equals("")))
        		{address.add(e);}
        	else if(items.get(i).equals("contact") && (!e.equals("")))
        		{contact.add(e);}
        	else if(items.get(i).equals("p_description") && (!e.equals("")))
        		{p_discp.add(e);}
        	else if(items.get(i).equals("price") && (!e.equals("")))
        		{price.add(e);}
        	else if(items.get(i).equals("prod_name") && (!e.equals("")))
        		{prod_name.add(e);}}}
        
        List<Integer> delete_range = new ArrayList<>();
        delete_range.clear();
        int range = 0;
        for(int i=0;i<prod_name.size();i++)
        {if(email.get(i).equals(everything.toString()))
        {System.out.println(i+1 + ") " + "Product Name: " + prod_name.get(i));
        delete_range.add(i+1);
    	System.out.println("   Price: " + price.get(i));
    	System.out.println("   Address: " + address.get(i));
    	System.out.println("   Contact: " + contact.get(i));
    	System.out.println("   Product description: " + p_discp.get(i) + "\n\n");
    	System.out.println("     --------------------------------------    \n\n");
    	range = i+1;}}
        
        System.out.println("Enter the product number\nEnter any other key to go back");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
//        System.out.println(range + "   range");
        
        if(num.matches("-?\\d+(\\.\\d+)?"))
        {if(Integer.parseInt(num)>=1 && Integer.parseInt(num)<=range && delete_range.contains(Integer.parseInt(num)))
        {System.out.println("Deleting prod_no " + (num) + "...");
        String daddress = address.get(Integer.parseInt(num)-1), dcontact = contact.get(Integer.parseInt(num)-1), ddiscp = p_discp.get(Integer.parseInt(num)-1), dprod_name = prod_name.get(Integer.parseInt(num)-1), dpice = price.get(Integer.parseInt(num)-1);
        
//        System.out.println(daddress + "\n" + dcontact + "\n" + ddiscp + "\n" + dprod_name + "\n" + dpice);
        
        
        String things = "";
        List<String> p_name = new ArrayList<>();
        List<String> paddress = new ArrayList<>();
        List<String> pcontact = new ArrayList<>();
        List<String> pprice = new ArrayList<>();
        List<String> pdiscp = new ArrayList<>();
        List<String> pemail = new ArrayList<>();
        items.add("emailid");
    	for(int i=0;i<items.size();i++)
        {things = readafile(bucketName, items.get(i), _cos);
        for(String e: things.split("###"))
        {
		if(items.get(i).equals("prod_name") && !e.equals(""))
        {p_name.add(e);}
        else if(items.get(i).equals("address") && !e.equals(""))
        {paddress.add(e);}
        else if(items.get(i).equals("contact") && !e.equals(""))
        {pcontact.add(e);}
        else if(items.get(i).equals("price") && !e.equals(""))
        {pprice.add(e);}
        else if(items.get(i).equals("p_description") && !e.equals(""))
        {pdiscp.add(e);}
        else if(items.get(i).equals("emailid") && !e.equals(""))
        {pemail.add(e);}}}
        
        prod_name.clear();
        address.clear();
        contact.clear();
        price.clear();
        p_discp.clear();
        email.clear();
        
//        System.out.println("1111111111111111111");
//        System.out.println(p_name);
//        System.out.println(paddress);
//        System.out.println(pcontact);
//        System.out.println(pprice);
//        System.out.println(pdiscp);
//        System.out.println(pemail);
        
	   	File fp_name = new File("prod_name.txt");
    	File fadd= new File("address.txt");
    	File fc_info = new File("contact.txt");
    	File f_price = new File("price.txt");
    	File f_desc = new File("p_description.txt");
    	File f_email = new File("emailid.txt");
    	
//    	System.out.println(prod_name.size() + "   222222222222222" +  "   " + p_name.size());
//    	System.out.println(p_name.get(i) + " " + paddress.get(i) + " " + pcontact.get(i) + " " + pprice.get(i) + " " + pdiscp.get(i) + " " + pemail.get(i) + " " + everything);    	
        
        for(int i=0;i<p_name.size();i++)
        {if(!(p_name.get(i).equals(dprod_name) && paddress.get(i).equals(daddress) && pcontact.get(i).equals(dcontact) && pprice.get(i).equals(dpice) && pdiscp.get(i).equals(ddiscp) && pemail.get(i).equals(everything)))
    	{prod_name.add(p_name.get(i) + "###");
    	address.add(paddress.get(i) + "###");
    	contact.add(pcontact.get(i) + "###");
    	price.add(pprice.get(i) + "###");
    	p_discp.add(pdiscp.get(i) + "###");
    	email.add(pemail.get(i) + "###");}}
        
        if(p_name.size()==1)
        {FileWriter fw_p_name;
		try {fw_p_name = new FileWriter(fp_name.getCanonicalPath());
	        fw_p_name.write("");
	        fw_p_name.close();
	        _cos.putObject(bucketName, "prod_name",new File(fp_name.getCanonicalPath()));
	        PrintWriter pw = new PrintWriter(fp_name.getCanonicalPath());
	        pw.write("");
	        pw.close();
	        
	        FileWriter fw_email = new FileWriter(f_email.getCanonicalPath());
	        fw_email.write("");
	        fw_email.close();
	        _cos.putObject(bucketName, "emailid",new File(f_email.getCanonicalPath()));
	        PrintWriter pw2 = new PrintWriter(f_email.getCanonicalPath());
	        pw2.write("");
	        pw2.close();
	        
	        FileWriter fw_price = new FileWriter(f_price.getCanonicalPath());
	        fw_price.write("");
	        fw_price.close();
	        _cos.putObject(bucketName, "price",new File(f_price.getCanonicalPath()));
	        PrintWriter pw3 = new PrintWriter(f_price.getCanonicalPath());
	        pw3.write("");
	        pw3.close();
	        
	        FileWriter fwc_info = new FileWriter(fc_info.getCanonicalPath());
	        fwc_info.write("");
	        fwc_info.close();
	        _cos.putObject(bucketName, "contact",new File(fc_info.getCanonicalPath()));
	        PrintWriter pw4 = new PrintWriter(fc_info.getCanonicalPath());
	        pw4.write("");
	        pw4.close();
	        
	        FileWriter fw_desc = new FileWriter(f_desc.getCanonicalPath());
	        fw_desc.write("");
	        fw_desc.close();
	        _cos.putObject(bucketName, "p_description",new File(f_desc.getCanonicalPath()));
	        PrintWriter pw5 = new PrintWriter(f_desc.getCanonicalPath());
	        pw5.write("");
	        pw5.close();
	        
	        FileWriter fw_add = new FileWriter(fadd.getCanonicalPath());
	        fw_add.write("");
	        fw_add.close();
	        _cos.putObject(bucketName, "address",new File(fadd.getCanonicalPath()));
	        PrintWriter pw6 = new PrintWriter(fadd.getCanonicalPath());
	        pw6.write("");
	        pw6.close();
		} catch (IOException ignored) {}}
        

        
    	
    	
//        System.out.println(p_name);
//        System.out.println(paddress);
//        System.out.println(pcontact);
//        System.out.println(pprice);
//        System.out.println(pdiscp);
//        System.out.println(pemail);
    	
    	for(int i=0;i<items.size();i++)
        {for(int j=0;j<prod_name.size();j++)
		{try{if(items.get(i).equals("prod_name")) {
//    	product name
        FileWriter fw_p_name = new FileWriter(fp_name.getCanonicalPath());
        fw_p_name.write(prod_name.get(j));
        fw_p_name.close();
        _cos.putObject(bucketName, "prod_name",new File(fp_name.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fp_name.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(items.get(i).equals("emailid"))
        {FileWriter fw_email = new FileWriter(f_email.getCanonicalPath());
        fw_email.write(email.get(j));
        fw_email.close();
        _cos.putObject(bucketName, "emailid",new File(f_email.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_email.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(items.get(i).equals("price")) {
//		price
        FileWriter fw_price = new FileWriter(f_price.getCanonicalPath());
        fw_price.write(price.get(j));
        fw_price.close();
        _cos.putObject(bucketName, "price",new File(f_price.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_price.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(items.get(i).equals("contact")) {
//		contact info
        FileWriter fwc_info = new FileWriter(fc_info.getCanonicalPath());
        fwc_info.write(contact.get(j));
        fwc_info.close();
        _cos.putObject(bucketName, "contact",new File(fc_info.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fc_info.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(items.get(i).equals("p_description")) {
//		description
        FileWriter fw_desc = new FileWriter(f_desc.getCanonicalPath());
        fw_desc.write(p_discp.get(j));
        fw_desc.close();
        _cos.putObject(bucketName, "p_description",new File(f_desc.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(f_desc.getCanonicalPath());
        pw.write("");
        pw.close();}
        
        else if(items.get(i).equals("address")) {
//		address
        FileWriter fw_add = new FileWriter(fadd.getCanonicalPath());
        fw_add.write(address.get(j));
        fw_add.close();
        _cos.putObject(bucketName, "address",new File(fadd.getCanonicalPath()));
        PrintWriter pw = new PrintWriter(fadd.getCanonicalPath());
        pw.write("");
        pw.close();}}
        catch(IOException e) {e.printStackTrace();}}}
    	select(bucketName, _cos);}
        
        
        else
        {System.out.println("Invalid choice");
    	select(bucketName, _cos);}}
        else
        {select(bucketName, _cos);}}

        
        public static String checkemail(String abc)
        {if(abc.contains("@") && !abc.contains(" "))
        {return abc;}
        else{
    	System.out.println("Invalid emailid\nRe-enter emailid");
    	Scanner sc = new Scanner(System.in);
    	String xyz = sc.nextLine();
    	return checkemail(xyz);}}
        
        
        public static int check_login()
        {String everything = "";
    	try(FileInputStream inputStream = new FileInputStream("login.txt"))      
        {everything = IOUtils.toString(inputStream);}
        catch (IOException ignored) {}
		return 1;}
        
        
    }
