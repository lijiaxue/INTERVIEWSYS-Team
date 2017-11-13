<!DOCTYPE html>

<html lang="en">

<body>
<#list cityList as city>
City: ${city.cityName}! <br>
Q:Why I like? <br>
A:${city.description}!
</#list>
<input type="button" value="123"/>
</body>
    <script>
            (function(){
               alert("1")
            })();
    </script>
</html>