<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search</title>
<link href="images/icon.ico" rel="shortcut icon">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="content/styles.css" rel="stylesheet" />
<link href="css-jsp/ui-lightness/jquery-ui-1.8.23.custom.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.8.2jsp.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.customjsp.js"></script>
<script type="text/javascript" src="js/jqueryjsp.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
<script type="text/javascript" src="js/date_time.js"></script>
</script>
<script>
$(function() {
$( "#tabs" ).tabs();
});
//==========================================Query Processing for Wikipedia=========================================//

		function success_fn() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace("","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikipedia/select?q="+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true";

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
// === response ==== //
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> </br>';
				}

				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'" target="_blank">'+  val.url +'</a>'+'</li>';
//===== highlighting end ==== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										//alert(tempArray[i].text);
										x = x + "&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
							output += '<br />';							
						}
//===== highlighting end ==== //
					});
				}
				
				

				
				output +='</ul>';
				$('#update').html(output);

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
			success_fn();
			return false;
		
	});
	
//==========================================Query Processing for Wikivoyage=========================================//
	
		function success_fn2() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace(" ","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikivoyage/select?q="+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true";

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
// === response ==== //
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> </br>';
				}

				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'"  target="_blank">'+  val.url +'</a>'+'</li>';
						if(val.text !== undefined)
							output += '<li class="details">'+ val.text +'</a>'+'</li>';
						
//===== highlighting start ==== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										x = x + "&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
							output += '<br />';							
						}
//===== highlighting end ==== //
					});
				}
				
				
				output +='</ul>';
				$('#update2').html(output);

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
		success_fn2();
			return false;
	});


//==========================================Query Processing for Wikinews=========================================//

	function success_fn3() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace("","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikinews/select?q="+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true";

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
		
// === response ==== //
		
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> </br>';
				}


				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';	
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'"  target="_blank">'+  val.url +'</a>'+'</li>';
				
// ===== highlighting start ===== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										x = x + "&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
							output += '<br />';							
						}
//===== highlighting end ==== //
					});
				}
				
				output +='</ul>';
				$('#update3').html(output);

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
			success_fn3();
			return false;
		});
	
</script>

<!==========================================HTML Start=======================================================>

</head>
<body>
<div align="center">

	<div id="header">
    	<div class="container">
            <a target="_blank" href="http://www.buffalo.edu"><div class="logo"></div></a>
            <div class="dt">
                <span id="date_time"></span>
                <script type="text/javascript">window.onload = date_time('date_time');</script>
            </div>
    	</div><!-- end of container -->
    </div><!-- end of header -->

    <div id='cssmenu'>
    <div class="container">
        <ul>
           <li><a href='index.html'><span>Home</span></a></li>
            <li class='last'><a href='http://localhost/phpsqlajax_map_v3.html'><span>Map</span></a></li>
           <li><a href='about1.html'><span>About Project</span></a></li>
           <li class='last'><a href='contact.html'><span>Contact Us</span></a></li>
           <li class='last'><a href='faq.html'><span>FAQ</span></a></li>
        </ul>
        	
	<div class="search1">
    	<div class="container">
        	<div id="search-bar">
          
            <form method="get" action="result.jsp" id="form1">
           <input type="text" name="query" class="search" id="hello" value="Search here" onFocus="if(this.value == 'Search here') {this.value = '';this.style.color='black';}" >
            <input type="submit" class="button" value="Search" name="submit_btn" id="submit_btn">
            
            </form>
            </div><!--end of search bar-->
            <div id="space"></div>
        </div><!--end of container-->
    </div><!--search1-->
    </div><!--end of container-->
    </div><!--end of cssmenu-->
         
    
    <!-- Content -->
    <div class="container">
    <div class="content">
    <form name="form1" >
    	<input type="hidden" name="query" value="<% out.println(request.getParameter("query")); %>">
    </form> 
    	<h2>Results for: <span><% out.println(request.getParameter("query")); %></span></h2>
		<div id="tabs">
            <ul>
                <li><a href="#tabs-1" id="one">Wikipedia </a></li>
                <li><a href="#tabs-2" id="two">Wikivoyage</a></li>
                <li><a href="#tabs-3" id="three">Wikipedia News</a></li>
            </ul>
            <div id="tabs-1">
            	<div id="update"></div>
            </div>
            <div id="tabs-2">
            	<div id="update2"></div>
            </div>
            <div id="tabs-3">
            	<div id="update3"></div>
            </div>
        </div>
    </div>
    </div>
    
    <!-- Footer -->
    <div id="footer">
    <div class="container">
        <div class="footer_nav">
            <span>Copyright &copy; 2013,<a href="http://www.buffalo.edu/">University at Buffalo.</a> All rights reserved    |   <a href="http://www.buffalo.edu/ubit/policies/it-policies-a-to-z/privacy.html">Privacy </a></span>
        </div>
        <div class="footer_nav _center">
            <a target="_blank" href="https://www.facebook.com/universityatbuffalo"><img src="images/s1.png"></a>
            <a target="_blank" href="https://twitter.com/UBCommunity"><img src="images/s2.png"></a>
        </div>
        <div class="footer_nav _right">
        	<a href="faq.html">FAQ</a>
        </div>
    </div>
	</div> <!-- end of Footer -->       

</div>
</body>
</html>
