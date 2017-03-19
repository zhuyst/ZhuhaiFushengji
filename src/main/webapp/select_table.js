var xmlHttp;

function showTable(str)
{
    if (str.length==0)
    {
        document.getElementById("txtHint").innerHTML="";
        return
    }
    xmlHttp=GetXmlHttpObject();
    if (xmlHttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return
    }
    var url="http://localhost:8080/Zhuhai_SSM/selectdb";
    var postStr="table=" + str;
    xmlHttp.onreadystatechange=stateChanged;
    xmlHttp.open("POST",url,true);
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.send(postStr);
}

function stateChanged()
{
    if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
    {
        var json = xmlHttp.responseText;
        json = eval(json);

        var str = "<table border='1' align='center' cellpadding='5'>";

        str += "<tr>";
        for(var key in json[0])
        {
            str += "<th>" + key + "</th>";
        }
        str += "</tr>";

        for(var i = 0;i < json.length;i++)
        {
            str += "<tr>";
            for(var key in json[i])
            {
                str += "<td>" + json[i][key] + "</td>";
            }
            str += "</tr>"
        }
        //document.getElementById("txtHint").innerHTML = xmlHttp.responseText
        document.getElementById("txtHint").innerHTML = str;
    }
}

function GetXmlHttpObject()
{
    var xmlHttp=null;
    try
    {
        // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e)
    {
        // Internet Explorer
        try
        {
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e)
        {
            xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}/**
 * Created by zhuyst on 2017/2/7.
 */
