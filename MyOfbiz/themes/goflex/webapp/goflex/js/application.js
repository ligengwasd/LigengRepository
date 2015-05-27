function findTableDirOptions(entityName,nameField,valueField){   
	var data;
	$.ajax({url:'findTableDirOptions?entityName='+entityName+'&nameField='+nameField+'&valueField='+valueField,async:false, success:function(e){           
	    if (e != null) { 
	      data = e;                                                            
	          }
	    }});
	return $.parseJSON(data); 
};
function findrefListOptions(referenceValue){   
	var data;
	$.ajax({url:'findRefListEvent?referenceValue='+referenceValue,async:false, success:function(e){           
	    if (e != null) { 
	      data = e;                                                            
	          }
	    }});
	return $.parseJSON(data); 
};


function posturl(url,para){
	$.post(url,para,function(data){
    	window.top.showMsg(data.rtnMsgType,data.rtnMsg);
	})
};

function gridCustomCmd(e,url,data){
	
	
	
}

;(function($){
	/**
	 * ����jquery-1.4.2
	 * ����jquery.json-2.2���ο�http://code.google.com/p/jquery-json/
	 * ���ڽ�form���л���json�������ҿ��Է����л�������
	 */
	$.fn.serializeObjectToJson = function()
	{
	    /**
	     * �˷�������ο���http://css-tricks.com/snippets/jquery/serialize-form-to-json/
	     */
	   var o = {};
	   var a = this.serializeArray();
	   $.each(a, function() {
	       if (o[this.name]) {
	           if (!o[this.name].push) {
	               o[this.name] = [o[this.name]];
	           }
	           o[this.name].push(this.value || '');
	       } else {
	           o[this.name] = this.value || '';
	       }
	   });
	   return o;
	  // return $.toJSON(o);
	};
	/**
	 * ��json��ʽ�������form,����json��key��Ӧform�ڵ�Ԫ��name
	 * ֧��input��radio��select��textarea��ֵ���ֵ
	 * ����jsonΪjson����
	 */
	$.fn.unSerializeObjectFromJson = function(json)
	{
	    if(!json) return;
	    var values=eval('('+json+')');
	    var form=this;
	    /**
	     * �õ�����form�������ݣ��������Ĭ��ֵ��ȫ����գ���Ҫ��Ե�ֵ����磺checkbox��radio����Ϊ��Щ�������ѡ�л᲻�����л���¼�ݴ棬����
	     * �ڷ����л�ʱ���޷�������ܴ���Ĭ��ֵ����Щ�������������һ�����⴦��
	     */
	    var defaultFormJsonValues=form.serializeObjectToJson();
	    $.each(defaultFormJsonValues, function(key, value){
	        var object=form.find("*[name='"+key+"']");
	        if(object.length==1){
	            if(object.attr("type").toLowerCase()=='radio' || object.attr("type").toLowerCase()=='checkbox'){
	                object.attr("checked",false);
	            }
	        }
	    });
	    //������Ҫ���ı�����
	    $.each(values, function(key,value){
	        var object=form.find("*[name='"+key+"']");//�õ�form��ָ��name�Ŀؼ�
	        if(object.length==1){
	            if(object.attr("type").toLowerCase()=='radio' || object.attr("type").toLowerCase()=='checkbox'){
	                if(object.val()==value){
	                    object.attr("checked",true);
	                }
	                return true;
	            }else{
	                object.val(value);
	            }
	        }else if(object.length>1){
	            object.each(function(i){
	                if(object.attr("type").toLowerCase()=='radio' || object.attr("type").toLowerCase()=='checkbox'){
	                    if($.inArray($(this).val(),value)!=-1){
	                        $(this).attr("checked",true);
	                    }else{
	                        $(this).attr("checked",false);
	                    }
	                }else{
	                    $(this).val(value[i]);
	                }
	            });
	        }
	    });

	};
	/**
	 * �õ���ǰ��ĵ�һ��
	 */
	$.getCurrentYearFirstDay=function(format){
	    var date=new Date();
	    var fDate=new Date(date.getFullYear(),0,1,0,0,0,0);
	    return $.getFormateDate(format,fDate);
	};
	/**
	 * �õ���ǰ������һ��
	 */
	$.getCurrentYearLastDay=function(format){
	    var date=new Date();
	    var fDate=new Date(date.getFullYear(),11,31,23,59,59,999);
	    return $.getFormateDate(format,fDate);
	};
	/**
	 * �õ���ǰ�µĵ�һ��
	 */
	$.getCurrentMonthFirstDay=function(format){
	    var date=new Date();
	    var fDate=new Date(date.getFullYear(),date.getMonth(),1,0,0,0,0);
	    return $.getFormateDate(format,fDate);
	};
	/**
	 * �õ���ǰ�µ����һ��
	 */
	$.getCurrentMonthLastDay=function(format){
	    var date=new Date();
	    var fDate=new Date(date.getFullYear(),date.getMonth()+1,0,23,59,59,999);
	    return $.getFormateDate(format,fDate);
	};
	/**
	 * ��ʽ������
	 */
	$.getFormateDate = function(fmt,date){
	    if(!date) date=new Date();
	    var o={   
	    "M+" : date.getMonth()+1, //�·�     
	    "d+" : date.getDate(), //��     
	    "h+" : date.getHours()%12 == 0 ? 12 : date.getHours()%12, //Сʱ
	    "H+" : date.getHours(), //Сʱ
	    "m+" : date.getMinutes(), //��
	    "s+" : date.getSeconds(), //��
	    "q+" : Math.floor((date.getMonth()+3)/3), //����
	    "S" : date.getMilliseconds() //����
	    };
	    var week={
	    "0" : "\u65e5",
	    "1" : "\u4e00",
	    "2" : "\u4e8c",
	    "3" : "\u4e09",
	    "4" : "\u56db",
	    "5" : "\u4e94",
	    "6" : "\u516d"    
	    };
	    if(/(y+)/.test(fmt)){ 
	        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));     
	    }     
	    if(/(E+)/.test(fmt)){
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[date.getDay()+""]);     
	    }     
	    for(var k in o){
	        if(new RegExp("("+ k +")").test(fmt)){
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	        }
	    }
	    return fmt;
	};
	})(jQuery);