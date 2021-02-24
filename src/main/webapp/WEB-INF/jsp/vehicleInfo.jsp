<!DOCTYPE html>
<html>
  <head>
    <style>
#map {
        width: 100%;
        height: 800px;
     }
    </style>
  </head>
  <body>
	<h1>Vehicle Info for ${vehicle.vehicleName}</h1>
	
	<p>The last report for this vehicle was at ${model.position.timeStamp}</p>
	
	<p>It was at lat long ${model.position.latitude}, ${model.position.longitude}</p>
	
	<p>You were served by port ${model.port}</p>

    <div id="map"></div>
    <script>
      function initMap() {
        var myLatLng = {latitude: ${model.position.latitude}, lng: ${model.position.longitude}};
      
        var mapDiv = document.getElementById('map');
        var map = new google.maps.Map(mapDiv, {
            center: myLatLng,
            zoom: 17
        });
        
          var marker = new google.maps.Marker({
    		position: myLatLng,
    		map: map,
    		title: '${model.position.timeStamp}'
  		  });               
      }
      
   
    </script>
    <script async
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDuCKFDFOQBuL8OUwxIzustoTuKp8249z4&callback=initMap">
    </script>
  </body>
</html>