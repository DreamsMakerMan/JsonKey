# JsonKey

本项目基于fastjson，快速获取json串中某个key的值。
有两种方式，第一种是根据某一个Key值，获取对应的Value
第二种是根据json的路径（类似于path目录），获取目标的Value


具体使用例子如下：

	String jsonText = "{states:{" 
                + "rect1:{"
                + "type:'start',text:{text:'开始'}, " 
                + "attr:{ x:448, y:105, width:50, height:50}, " 
                + "props:{text:{value:'开始'},temp1:{value:''}," 
                + "temp2:{value:''}"
                + "}}," 
                + "rect2:{"
                + "type:'state',text:{text:'camel_element'}, " 
                + "attr:{ x:431, y:224, width:100, height:50}, props:{text:{value:'camel_element'}," 
                + "temp1:{value:'http://www/baidu.com'}"
                + "}}" 
                + ",rect3:{"
                + "type:'end',text:{text:'结束'}, " 
                + "attr:{ x:454, y:365, width:50, height:50}, props:{text:{value:'结束'}," 
                + "temp1:{value:''},temp2:{value:''}"
                + "}}"
                + "},"
                + "paths:{"
                + "path4:{from:'rect1',to:'rect2', dots:['a','b','c']," 
                + "text:{text:'TO camel_element'},textPos:{x:0,y:-10}, props:{text:{value:''}}"
                + "}," 
                + "path5:{from:'rect2',to:'rect3', dots:[],text:{text:'TO 结束'},textPos:{x:0,y:-10}, " 
                + "props:{text:{value:''}}}"
                + "},"
                + "props:{props:{name:{value:'新建流程'},key:{value:''}," 
                + "desc:{value:''}}}"
                + "}";



	JSONObject obj = JSONObject.parseObject(jsonText);

        /*第一种，根据key值获取*/
        UtilJson.findJsonObjByKey(obj, "dots", new UtilJson.IBingo() {
            @Override
            public void onBingo(Object obj) {
                Print.d(TAG, "onBingo " + obj.toString());
            }
            @Override
            public void onFinished() {
                Print.d(TAG, "onFinished ");
            }
        });
        
        /*第二种，根据path路径获取*/
        UtilJson.findJsonObjByAbsPath__2(obj, "/paths/path4/dots/sdd", new UtilJson.IBingo() {
            @Override
            public void onBingo(Object obj) {
                Print.d(TAG, "AbsPath__2, onBingo " + obj.toString());
            }
            @Override
            public void onFinished() {
                Print.d(TAG, "AbsPath__2, onFinished ");
            }
        });


