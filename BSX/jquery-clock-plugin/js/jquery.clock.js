/* Copyright (c) 2009 José Joaquín Núñez (josejnv@gmail.com) http://joaquinnunez.cl/blog/
 * Licensed under GPL (http://www.opensource.org/licenses/gpl-2.0.php)
 * Use only for non-commercial usage.
 *
 * Version : 0.1
 *
 * Requires: jQuery 1.2+
 */
var myS=0;
var myM=1;
var myH=3;

(function(jQuery)
{
  jQuery.fn.clock = function(options)
  {
    var defaults = {
      offset: '+0',
      type: 'analog'
    };
    var _this = this;
    var opts = jQuery.extend(defaults, options);

    setInterval( function() {
      var seconds = jQuery.calcTime().getSeconds();
      if(opts.type=='analog')
      {
        var sdegree = seconds * 6;
        var srotate = "rotate(" + sdegree + "deg)";
        jQuery(_this).find(".sec").css({"-moz-transform" : srotate, "-webkit-transform" : srotate});
      }
      else
      {
        jQuery(_this).find(".sec").html(seconds);
      }
	  var hours = jQuery.calcTime().getHours();
      var mins = jQuery.calcTime().getMinutes();
      if(opts.type=='analog')
      {
        var hdegree = hours * 30 + (mins / 2);
        var hrotate = "rotate(" + hdegree + "deg)";
        jQuery(_this).find(".hour").css({"-moz-transform" : hrotate, "-webkit-transform" : hrotate});
      }
      else
      {
        jQuery(_this).find(".hour").html(hours+':');
      }
      var meridiem = hours<12?'AM':'PM';
      jQuery(_this).find('.meridiem').html(meridiem);
	  
	  
	  var mins = jQuery.calcTime().getMinutes();
      if(opts.type=='analog')
      {
        var mdegree = mins * 6;
        var mrotate = "rotate(" + mdegree + "deg)";        
        jQuery(_this).find(".min").css({"-moz-transform" : mrotate, "-webkit-transform" : mrotate});                
      }
      else
      {
        jQuery(_this).find(".min").html(mins+':');
      }
    }, 60000 );
  }
})(jQuery);


jQuery.calcTime = function() {

  // create Date object for current location
  //d=new Date();
 //d = new Date(2011, 11, 18, 11, 12, 30, 999);
//d.setDate("October 13, 2011 11:13:00");
  // convert to msec
  // add local time zone offset
  // get UTC time in msec
  //utc = d.getTime() + (d.getTimezoneOffset() * 60000);
//utc = d.getTime() ;
  // create new Date object for different city
  // using supplied offset
  //nd = new Date(utc + (3600000*offset));
  	
  
	nd = new Date();
	
	alert(nd.getTime())
	//nd = new Date("October 13, 2011 11:13:00");
  // return time as a string
  return ( nd);
};
