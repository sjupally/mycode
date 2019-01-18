/**
 * @author suman.tipparapu
 * *
 */
//111.93.16.251
TRACK_ME = 
{
	 refreshInterval	:  (1 * 1 * 1000)
	,interval			: null
	,autoRefresh		: true
	,directionsService  : null
	,tripId				: null
	,tripRequestTime	: null
	,citizenMobileNumber: null
	,vehicleNumber		: null
	,isShared			: false
	,shareMyRoute		: false
	,appURL				: "https://abhayaotsi.epragathi.org:8099/mobile/"
	,mapConfig			: 
	{
		initialized			: false
		,trackMeId			: null
		,map				: null
		,trackStatus		: null
		,latestGeoLocationId: 0
		,liveStatusRefreshInterval: (1 * 1 * 1000)
		,liveStatusInterval	: null
		,infoWindow			: null
		,geocoder 			: null
		,polylineCoordinates: []
		,markerCoordinates	: []
		,array_marker		: []
		,sourceDestiMarkers : []
		,polyLine			: null
		,strokeColor 		: 'green'
		,strokeOpacity 		: 2
		,strokeWeight 		: 4
		,strokeWeight_highlight: 3
		,mapBounds			:	null
		,startMarkerColor 	: 'green'
		,normalMarkerColor  : '#999999'
		,zIndex_marker		: 1000
		,zIndex_polyline	: 100
		,sourceCoordinates  : null
		,destiCoordinates  : null
		,destinationMarker	: null
		,mapOptions 		:
		{
			center				: null
			,mapTypeId			: google.maps.MapTypeId.ROADMAP
			,mapTypeControlOptions: { }
			,zoom				: 13
			,mapTypeControl		: true
			,navigationControl	: true
			,panControl			: true
			,scaleControl		: true
			,streetViewControl	: true
			,overviewMapControl	: true
			,draggable			: true
			,streetViewControl	: true
			,disableDefaultUI	: true
			,clickableIcons		: false
		}
		,addEvent: function(el, eventType, handler)
		{
			google.maps.event.addListener(el, eventType, handler);
		}
		,highlightPolyline: function(event, polyLine)
		{
			polyLine.orgZIndex = polyLine.zIndex;
			polyLine.setOptions(
			{
				strokeWeight: this.strokeWeight_highlight
				,zIndex	: 500
			});
		}
		,unHighlightPolyline: function(event, polyLine)
		{
			polyLine.setOptions(
			{
				strokeWeight: this.strokeWeight
				,zIndex	: polyLine.orgZIndex
			});
		}
	}
   ,initMap		: function()
	{
	   var me  		= this;
	   $('#endbutton').hide();
	   $('#sharebutton').hide();
	   $('#helpbutton').hide();
	   $('#shareRoute').hide();
	   $('#estimate').hide();

	   var autocomplete2 = new google.maps.places.Autocomplete(document.getElementById('end1'));
	   var autocomplete1 = new google.maps.places.Autocomplete(document.getElementById('source'));
	   autocomplete2.setComponentRestrictions({'country': ['in']});
	   autocomplete1.setComponentRestrictions({'country': ['in']});

	   var url_string = window.location.href;
       var url = new URL(url_string);
       me.vehicleNumber = url.searchParams.get("vehicleNumber");
       me.citizenMobileNumber = url.searchParams.get("citizenMobileNumber");
       me.isShared = url.searchParams.get("isShared");

       me.isTripExist();
       google.maps.Polyline.prototype.getBounds = function(startBounds) 
 	   {
 			if (startBounds) 
 			{
 				var bounds = startBounds;
 			} 
 			else 
 			{
 				var bounds = new google.maps.LatLngBounds();
 			}
 			this.getPath().forEach(function(item, index) {
 				bounds.extend(new google.maps.LatLng(item.lat(), item.lng()));
 			});
 			return bounds;
        };
        $('#endbutton').on('click',function()
        {
        	$('#endTripButtonPopup').modal({ show: true});
        	$("#end-trip-modal-yes-btn").on("click", function()
        	{
        		$.ajax(
				{
		       	  type		  : "POST",
		       	  url	      :  me.appURL + "endTrip?vehicleNumber=" + me.vehicleNumber,
		       	  contentType : "application/json",
		       	  success	  : function(data)
		       	  {
		       		 $('#estimate').empty();
		       		 document.getElementById('end1').value = "";
		       		 $('#startTripButton').prop("disabled", false).css('background-color', '#6742c0');
		       		 me.detachLiveStatusEvent();
		       		 $('#endbutton').hide();
		       		 $('#sharebutton').hide();
	       		     $('#helpbutton').hide();
	       		     $('#shareRoute').hide();
	       		     $('#estimate').hide();
       		         me.isTripExist();

	       		      var url_string = window.location.href;
	       		      var url = new URL(url_string);
	       		      var mobile = url.searchParams.get("mobile");
	       		      if (mobile) window.location.href = url_string + '&tripEnded=tripEnded';
		       	  },
		       	  error: function(data)
		       	  {
		       	  }
		       	});
        	});
	    });
        $('#sharebutton').on('click',function()
        {
        	$.ajax(
			{
	       	  type		  : "GET",
	       	  url	      :  me.appURL + "shareLocation?citizenMobileNumber=" + me.citizenMobileNumber,
	       	  contentType : "application/json",
	       	  success	  : function(data,q)
	       	  {
	       		$('#shareLocationButtonPopup').modal({ show: true});
	       	  },
	       	  error: function(data)
	       	  {
	       	  }
	       	});
	    });
        $('#helpbutton').on('click',function()
        {
        	$('#helpButtonPopup').modal({ show: true});
        	$("#help-button-modal-yes-btn").on("click", function(){
        		$.ajax(
				{
		       	  type		  : "POST",
		       	  url	      :  me.appURL + "distress?citizenMobileNumber=" + me.citizenMobileNumber + "&eventType=WEB",
		       	  contentType : "application/json",
		       	  success	  : function(data)
		       	  {
		       		  $('#helpButtonPoliceAlert').modal({ show: true});
		       	  },
		       	  error: function(data)
		       	  {
		       	  }
		       	});
        	});
	    });
       var sourceSearchBox = new google.maps.places.SearchBox(document.getElementById('source'));
       var destiSearchBox = new google.maps.places.SearchBox(document.getElementById('end1'));
       $('#startTripButton').on('click',function()
       {
    	   if (document.getElementById('end1').value)
		   {
    		   var place = destiSearchBox.getPlaces();
	           var lat = place[0].geometry.location.lat();
	           var lng = place[0].geometry.location.lng();
	           me.mapConfig.destiCoordinates = lat+ ","+lng;
	           me.calcRoute(me.mapConfig.sourceCoordinates,me.mapConfig.destiCoordinates);
	           $('#startButtonShareRoutePopup').modal({ show: true});
	           $("#start-btn-share-route-modal-yes-btn").on("click", function()
	            {
	        	   me.shareMyRoute = true;
	            });
	           $("#start-btn-share-route-modal-no-btn").on("click", function()
	        	{
	        	   me.shareMyRoute = false;
	            });
	           me.initTravel(me.mapConfig.sourceCoordinates,me.mapConfig.destiCoordinates,me.shareMyRoute); 
		   }
	   });
	}
  ,isTripExist : function()
   {
	   var me  = this;
	   $.ajax(
	   {
       	  type	: "GET",
       	  url	: me.appURL + "isTripExist/?vehicleNumber=" + me.vehicleNumber + '&citizenMobileNumber=' + me.citizenMobileNumber,
       	  success: function (data)
       	  {
       		  if (data.isTripExist)
  			  {
	       			$('#estimate').show();
	       			if (me.isShared)
	   				{
	       			  $('#endbutton').hide();
	      			  $('#sharebutton').hide();
	      		      $('#helpbutton').hide();
	      		      $('#shareRoute').hide();
	      		      $('#startTripButton').hide();
	   				}
	       			else
	   				{
	       			  $('#endbutton').show();
	      			  $('#sharebutton').show();
	      		      $('#helpbutton').show();
	      		      $('#shareRoute').show();
	      		      $('#startTripButton').prop("disabled", true).css('background-color', '#777575');
	   				}
       			  me.tripId = data.tripId;
       			  me.intilizeMapOnFirstCall(data.sourceLatLang.split(',')[0], data.sourceLatLang.split(',')[1]);
         		  me.mapConfig.sourceCoordinates = data.sourceLatLang;
         		  me.mapConfig.destiCoordinates = data.destiLatLang;
         		  me.calcRoute(me.mapConfig.sourceCoordinates, data.destiLatLang);

         		  me.reverseGeocode(data.sourceLatLang,1);
         		  me.reverseGeocode(data.destiLatLang,2);
  			  }
       		  else
   			  {
       			 $.ajax(
				 {
		       	  	 type	: "GET",
			       	  url	: me.appURL + "currentLocation/?vehicleNumber="+me.vehicleNumber,
			       	  success: function(data,q)
			       	  {
			       		  if (data == null || data === "")
			  			  {
			  			  	alert('Current location is not available.');
			  			    $('#startTripButton').prop("disabled", true).css('background-color', '#777575');
			  			  	return;
			  			  }
			       		  if (me.isShared)
		       			  {
			       			$('#startTripButton').hide();
		       			  }
			       		  me.intilizeMapOnFirstCall(data.sourceLatLang.split(',')[0], data.sourceLatLang.split(',')[1]);
			       		  me.mapConfig.sourceCoordinates = data.sourceLatLang;
			       		  me.reverseGeocode(data.sourceLatLang,1);
			       	  },
			       	  error: function(data)
			       	  {
			       		  
			       	  }
		       	});
   			  }
       	  },
       	  error: function(data)
       	  {
       	  }
       	});
   }
   ,initTravel : function(source,destination)
   {
	   var me  = this;
	   $('#startTripButton').prop("disabled", true).css('background-color', '#777575');
	   $('#endbutton').show();
	   $('#sharebutton').show();
	   $('#helpbutton').show();
	   $('#shareRoute').show();
	   $('#estimate').show();
	   var data = 
	   {
		   citizenMobileNumber : me.citizenMobileNumber,
		   rcNumber 	 : me.vehicleNumber,
		   sourceLatLang : source,
		   destiLatLang  : destination,
		   shareRoute    : me.shareMyRoute
	   }
	   $.ajax(
		{
       	  type		  : "POST",
       	  url	      :  me.appURL + "initiateTrip",
       	  data		  :  JSON.stringify(data),
       	  contentType : "application/json",
       	  dataType	  : 'json',
       	  success	  : function(data)
       	  {
       		me.tripId = data.code
       	  },
       	  error: function(data)
       	  {
       	  }
       	});
   }
   ,intilizeMapOnFirstCall : function (lat,lng)
   {
	    var me  = this;
	    me.mapConfig.geocoder 	= new google.maps.Geocoder();
		me.mapConfig.infoWindow = new google.maps.InfoWindow({maxWidth: 300});
		me.mapConfig.mapBounds 	= new google.maps.LatLngBounds();
		me.mapConfig.mapOptions.center = new google.maps.LatLng(parseFloat(lat),parseFloat(lng));
		me.mapConfig.mapOptions.mapTypeControlOptions = 
		{
				//google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.SATELLITE, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.TERRAIN
			 //style		: google.maps.MapTypeControlStyle.DROPDOWN_MENU
			 mapTypeIds	: []
		};
		me.mapConfig.map = new google.maps.Map(document.getElementById('map'),me.mapConfig.mapOptions);
		var sourceMarker = me.makeMarker({lat : parseFloat(lat) ,lng : parseFloat(lng)},'images/s.png');
		me.mapConfig.addEvent(sourceMarker, 'click', function(event) {me.getGeocode(sourceMarker);});
		me.mapConfig.sourceDestiMarkers.push(sourceMarker);
   }
   ,calcRoute : function(source,destination)
   {
	   var me = this;
	   me.directionsService  = new google.maps.DirectionsService();
   	   var directionsDisplay = new google.maps.DirectionsRenderer({map: me.mapConfig.map});
       var request = 
		{
           origin					: source,
           destination				: destination,
           provideRouteAlternatives	: true,
           unitSystem				: google.maps.UnitSystem.METRIC,
           travelMode				: google.maps.TravelMode['DRIVING']
       };
       me.directionsService.route(request, function(response, status) 
	   {
           for(var j in  me.mapConfig.polylineCoordinates ) 
		   {
        	   me.mapConfig.polylineCoordinates[j].setMap(null);
           }
           me.mapConfig.polylineCoordinates = [];
           if (status == google.maps.DirectionsStatus.OK) 
			{
				var bounds = new google.maps.LatLngBounds();
				me.clearMarkers();
				var length = response.routes[0].overview_path.length;

				var slatitude = response.routes[0].overview_path[0].lat();
				var slangitude = response.routes[0].overview_path[0].lng();
				me.mapConfig.map.setCenter({lat : slatitude ,lng : slangitude});  

				var sourceMarker = me.makeMarker({lat : slatitude ,lng : slangitude},'images/s.png');
				me.mapConfig.addEvent(sourceMarker, 'click', function(event) {me.getGeocode(sourceMarker);});

				//var dlatitude = response.routes[0].overview_path[length-1].lat();
				//var dlangitude = response.routes[0].overview_path[length-1].lng();
				
				var dlatitude = parseFloat(me.mapConfig.destiCoordinates.split(",")[0]);
				var dlangitude = parseFloat(me.mapConfig.destiCoordinates.split(",")[1]);
				
				var destMarker =  me.makeMarker({lat : dlatitude ,lng : dlangitude},'images/d.png');
				me.mapConfig.addEvent(destMarker, 'click', function(event) {me.getGeocode(destMarker);});

				me.mapConfig.sourceDestiMarkers.push(sourceMarker);
				me.mapConfig.sourceDestiMarkers.push(destMarker);
				var point = response.routes[0].legs[0];
				$('#estimate').empty();
				$('#estimate').append("Estimated Time : " + point.duration.text + ', Distance : ' + point.distance.text);
				for (var i = response.routes.length - 1; i >= 0; i-- ) 
				{
				   var color;
                   if (i == 0)  color = me.mapConfig.startMarkerColor;
                   else  color = me.mapConfig.normalMarkerColor;
                   var line = me.drawPolyline(response.routes[i].overview_path, color);
                   me.mapConfig.polylineCoordinates.push(line);
                   bounds = line.getBounds(bounds);
                   google.maps.event.addListener(line, 'click', function() 
				   {
                       var index = me.mapConfig.polylineCoordinates.indexOf(this);
                       me.highlightRoute(index);
                   });
               }
				me.mapConfig.map.fitBounds(bounds);
           }
       });
       me.attachLiveStatusEvent();
       var url_string = window.location.href;
       var url = new URL(url_string);
       var mobile = url.searchParams.get("mobile");
       if (mobile)
       window.location.href = url_string + '&tripStarted=tripStarted';
   }
   ,detachLiveStatusEvent()
   {
	   var me = this;
	   clearInterval(me.mapConfig.liveStatusInterval)
   }
   ,attachLiveStatusEvent : function ()
   {
	   var me = this;
	   me.mapConfig.liveStatusInterval = setInterval(function() 
	   {
			me.updateLiveStatus();
	   }, me.mapConfig.liveStatusRefreshInterval);
   }
   ,updateLiveStatus : function()
   {
	   var me = this;
	   $.ajax(
		{
       	  type	  : "GET",
       	  url	  : me.appURL + "es/tripTrackingDetails?tripId=" + me.tripId + "&trackId=" + me.mapConfig.latestGeoLocationId ,
       // url	  : me.appURL + "tripTraceDetails?tripId=" + me.tripId + "&traceId=" + me.mapConfig.latestGeoLocationId ,
       	  success : function(data)
       	  {
       		 /*if (data.content != null &&  data.content.length > 0)
   			 {
       			var lastRecord = data.content;
       			me.mapConfig.latestGeoLocationId = data.content[0].sourceId;
       			var reversedRecords = data.content.reverse();
    			var latLngArray = [];
    			for (var i=0; i < reversedRecords.length; i++)
				{
    				latLngArray.push({lat : parseFloat(reversedRecords[i].latitude),lng : parseFloat(reversedRecords[i].langitude)})
				}
    			me.updatePolyline(latLngArray);
   			 }*/
       		 
       		if (data != null &&  data.length > 0)
  			{
      			var lastRecord = data;
      			me.mapConfig.latestGeoLocationId = data[0].id;
      			var reversedRecords = data.reverse();
	   			var latLngArray = [];
	   			for (var i=0; i < reversedRecords.length; i++)
					{
	   				latLngArray.push({lat : parseFloat(reversedRecords[i].latitude),lng : parseFloat(reversedRecords[i].langitude)})
					}
	   			me.updatePolyline(latLngArray);
  			 }
       	  },
       	  error: function(data)
       	  {
       		  console.log(data);
       	  }
       	});
   }
   ,updatePolyline : function(latLngArray)
   {
	    var me = this, i, coordinate;
		me.mapConfig.infoWindow.close();
		me.mapConfig.sourceCoordinates = latLngArray[0].lat +","+ latLngArray[0].lng;
		me.updateMarker(latLngArray);
   }
   ,createMarker : function (position,icon)
   {
	   var me = this;
	   var marker = new google.maps.Marker({
			position	: position,
			icon 		: icon
		});
	  return marker;
   }
   ,updateMarker: function(latLngArray)
	{
		var me = this, markerOption, marker, len, labelText, icon,source;
		for (var i = 0; i < me.mapConfig.array_marker.length;i++ )
		{
			marker = me.mapConfig.array_marker[i];
			marker.setMap(null);
		}
		source = new google.maps.LatLng(parseFloat(me.mapConfig.sourceCoordinates.split(",")[0]),parseFloat(me.mapConfig.sourceCoordinates.split(",")[1]));
		var line = new google.maps.Polyline(
		{
		    path			: latLngArray,
		    strokeColor		: "red",
		    strokeOpacity	: 1.0,
	        strokeWeight	: 3,
	        map				: me.mapConfig.map
		});
		icon = 'images/ic_auto.png';
		icon = {
					path		: "M17.402,0H5.643C2.526,0,0,3.467,0,6.584v34.804c0,3.116,2.526,5.644,5.643,5.644h11.759c3.116,0,5.644-2.527,5.644-5.644 V6.584C23.044,3.467,20.518,0,17.402,0z M22.057,14.188v11.665l-2.729,0.351v-4.806L22.057,14.188z M20.625,10.773 c-1.016,3.9-2.219,8.51-2.219,8.51H4.638l-2.222-8.51C2.417,10.773,11.3,7.755,20.625,10.773z M3.748,21.713v4.492l-2.73-0.349 V14.502L3.748,21.713z M1.018,37.938V27.579l2.73,0.343v8.196L1.018,37.938z M2.575,40.882l2.218-3.336h13.771l2.219,3.336H2.575z M19.328,35.805v-7.872l2.729-0.355v10.048L19.328,35.805z",
					scale		: .7,
				    strokeColor : 'yellow',
				    strokeWeight: .10,
				    fillOpacity : 1,
				    fillColor   : '#404040',
				    offset		: '5%',
				    anchor		: new google.maps.Point(10, 25),
				    rotation	: google.maps.geometry.spherical.computeHeading(new google.maps.LatLng(latLngArray[latLngArray.length-2]), new google.maps.LatLng(latLngArray[latLngArray.length-1])) 
				}
		marker = me.createMarker(new google.maps.LatLng(latLngArray[latLngArray.length-1]),icon);
		marker.setMap(me.mapConfig.map);
		me.mapConfig.array_marker.push(marker);
		//me.mapConfig.map.setCenter(latLngArray[latLngArray.length-1]);  

		var data = {};
		var pointResult;

	   for (var i = 0; i < me.mapConfig.polylineCoordinates.length ; i++)
	   {
		   coordinate =  new google.maps.LatLng(latLngArray[0]);
			var line = me.mapConfig.polylineCoordinates[i];
			//var pointResult = me.isLocationOnEdge(coordinate,line,300);
			var pointResult = google.maps.geometry.poly.isLocationOnEdge(coordinate, line, 0.001) ; 
			if (!pointResult) 
			{
			  console.log("The location is not on route!");

			  data = {};
			  data.citizenMobileNumber = me.citizenMobileNumber;
			  data.rcNumber = me.vehicleNumber;
			  data.location = latLngArray[0].lat + "," + latLngArray[0].lng;
			}
	   }
	   if (!pointResult) 
	   {
		  $.ajax(
			{
	       	  type	  : "POST",
	       	  url	  : me.appURL + "routeDeviation",
	       	  contentType: 'application/json',
	       	  data	  : JSON.stringify(data),
	       	  success : function(data)
	       	  {
	       		
	       	  },
	       	  error: function(data)
	       	  {
	       	  }
	       	});
		}
	}
   ,highlightRoute : function (index)
   {
	   var me = this;
	   for (var j in  me.mapConfig.polylineCoordinates ) 
		{
		   if (j == index)  color = mapConfig.startMarkerColor;
           else  color = mapConfig.normalMarkerColor;
           me.mapConfig.polylineCoordinates[j].setOptions({strokeColor: color});
       }
   }
   ,drawPolyline	: function (path,color)
   {
	   var me = this;
	   var line = new google.maps.Polyline(
		{
            path			: path,
            strokeColor		: color,
            strokeOpacity	: me.mapConfig.strokeOpacity,
            strokeWeight	: me.mapConfig.strokeWeight
        });
        line.setMap(me.mapConfig.map);
        return line;
   }
   ,makeMarker : function(position,icon)
   {
	   var me = this;
	   var marker = new google.maps.Marker({
			position	: position,
			icon 		: icon
		});
	  marker.setMap(me.mapConfig.map);
	  return marker;
   }
   ,clearMarkers : function ()
   {
	   var me = this;
	   for (var i = 0; i < me.mapConfig.sourceDestiMarkers.length; i++) {
			me.mapConfig.sourceDestiMarkers[i].setMap(null);
		}
   }
   ,getGeocode: function(marker)
	{
		var me = this, coordinate, manualAttributes, content = "", location;
		coordinate = marker.getPosition();
		me.mapConfig.geocoder.geocode({'latLng': coordinate},
		function(results, status)
		{
			content += '<center style="font-weight: bold; color: #F75850;">[ ' + coordinate.lat() + ' , ' + coordinate.lng() +' ]</center><br style="line-height: 0px">';
			if (status == google.maps.GeocoderStatus.OK)
			{
				if (results[0])
				{
					location = results[0].formatted_address;
				}
				else
				{
					location = 'No Result';
				}
			}
			else
			{
				location = 'Geocoder failed due to - ' + status;
			}
			content += '<span style="line-height:14px; font-weight: bold;">Location: </span>' + location;
			me.mapConfig.infoWindow.setContent(content);
			me.mapConfig.infoWindow.open(me.mapConfig.map, marker);
		});
	}
   ,isLocationOnEdge : function(location,polyline,toleranceInMeters) 
    {
	    for (var leg of polyline.getPath().b)
	    {
	        if (google.maps.geometry.spherical.computeDistanceBetween(location,leg) >= toleranceInMeters)
	        {
	            return false;
	        }
	    } 
	    return true;
	}
   ,reverseGeocode: function(latLng,type)
	{
		var me = this, location;
		var type= type;
		latLng = new google.maps.LatLng(parseFloat(latLng.split(",")[0]),parseFloat(latLng.split(",")[1]));
		me.mapConfig.geocoder.geocode({'latLng': latLng},
		function(results, status)
		{
			if (status == google.maps.GeocoderStatus.OK)
			{
				if (results[0])
				{
					location = results[0].formatted_address;
					if (type === 1 || type == '1')
						document.getElementById('source').value = location;
					else
	         		  document.getElementById('end1').value = location;
				}
				else
				{
					location = 'No Result';
				}
			}
			else
			{
				location = 'Geocoder failed due to - ' + status;
			}
		});
	}
}