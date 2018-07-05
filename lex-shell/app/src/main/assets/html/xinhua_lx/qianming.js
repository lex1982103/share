    testAnySign(100010)
            document.onreadystatechange = setAlertTitle;

            var apiInstance;
            var fileData;
            var ocrCapture;
            
			var DATA_CANNOT_PARSED = "10003"; //输入数据项无法解析
			var SERVICE_SYSTEM_EXCEPTION = "10011"; //服务系统异常错误
			var RECOGNITION_RESULT_EMPTY = "10100"; //识别结果为空
			var CONNECTION_SERVICE_TIMEOUT = "10101"; //连接识别服务超时
			var CONNECTION_RECOGNITION_EXCEPTION = "10102"; //连接识别服务异常
			var SUCCESS = "0"; //识别成功
			var RECOGNITION_FALSE = "-1";//识别错误
            function setAlertTitle()
            {
                // document.title = "返回结果";
            }

            
			//配置模板数据
          testSetTemplateData();
            function testSetTemplateData()
            {

               // var formData = "{\"bjcaxssrequest\":{\"submitinfo\":[{\"username\":\"测星雨\",\"identitycardnbr\":\"320902198901191012\"},{\"username\":\"测星雨123\",\"identitycardnbr\": \"320902198901191012\"}]}}";

                var formData = "PGh0bWw+PGhlYWQ+PHRpdGxlPjwvdGl0bGU+PG1ldGEgaHR0cC1lcXVpdj0nQ29udGVudC1UeXBlJyBjb250ZW50PSd0ZXh0L2h0bWw7IGNoYXJzZXQ9VVRGLTgnIC8+PC9oZWFkPjxib2R5PjxkaXY+PGRpdj48bGFiZWw+a2V5d29yZDo8L2xhYmVsPjwvZGl2PjxkaXY+PGxhYmVsPuWIl+WQjTLvvJo8L2xhYmVsPjwvZGl2PjxkaXY+PGxhYmVsPuWIl+WQjTPvvJo8L2xhYmVsPjwvZGl2PjwvZGl2PjwvYm9keT48L2h0bWw+";
               //文件数据
//  		    var formData = fileData;
                var businessId = "123123";//集成信手书业务的唯一标识

                var template_serial = "4000";//用于生成PDF的模板ID


                var res;

                //配置JSON格式签名原文
                /**
                 * 设置表单数据，每次业务都需要set一次
                 * @param template_type 签名所用的模板类型
                 * @param contentUtf8Str 表单数据，类型为Utf8字符串
                 * @param businessId 业务工单号
                 * @param template_serial 模板序列号
                 * @returns {*} 是否设置成功
                 */
                res = apiInstance.setTemplate(TemplateType.HTML,formData,businessId,template_serial);

                if(res)
                {
                 console.log("setTemplateData success");
                    return res;
                }
                else
                {
                    console.log("setTemplateData error");
                    return res;
                }
            }
            
            //选择文件
			function  handleFiles(files)
			{
    			if(files.length)
    			{
        			var file = files[0];
        			var reader = new FileReader();

        			reader.onload = function FileReaderOnload() {
            		var buffer = reader.result;
            		var uint8Array = new Uint8Array(reader.result);
            		var bufStr = "";
            		var bufarray = Base64.encodeUint8Array(uint8Array);
            		bufStr = bufarray;
            		fileData = bufStr;
        			};
        			reader.readAsArrayBuffer(file);
    			}
			}
			
          	//添加单签签名框
            function testAddSignatureObj(objId)
            {

                var context_id = objId;
                var signer = new Signer("李明", "11011111111");

                /**
                 * 根据坐标定位签名方式
                 * @param left 签名图片最左边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param top 签名图片顶边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param right 签名图片最右边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param bottom 签名图片底边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param pageNo 签名在PDF中的页码，从1开始
                 * @param unit 坐标系单位，目前支持"dp"和"pt"
                 * @constructor
                 */
                if(objId == 20){
             		var signerRule = new SignRule_XYZ(100.0, 110.1, 180.2, 50.3, 1, "pt");
                }else{
             		var signerRule = new SignRule_XYZ(100.0, 110.1, 180.2, 50.3, 1, "pt");
                }


				/**
				 * 关键字定位方式，寻找PDF中的关键字，根据关键字位置放置签名图片
				 * @param keyword 关键字
				 * @param keyWordAlignMethod 签字图片和关键字位置关系：等于1时，签字图片和关键字矩形重心重合
				 *                            等于2时，签字图片位于关键字正下方，中心线对齐；等于3时，签字图片位于关键字正右方，中心线对齐；
				 *                            等于4时，签字图片左上角和关键字右下角重合，可能额外附加偏移量，详见构造函数的offset参数
				 * @param keyWordOffset 当keyWordAlignMethod非零时，额外附加的偏移量，单位pt
				 * @param pageNo 寻找关键字的PDF起始页码
				 * @param KWIndex KWIndex 第几个关键字
				 * @constructor
				 */
		      //    var signerRule = new SignRule_KeyWord("甲方签字",2,0,1,1);
				//var signerRule = new SignRule_KeyWordV2("签名算法",50,0,1,1);

                /**
                 *根据关键字定位签名位置
                 * @param keyWord 关键字字面值
                 * @param xOffset X轴偏移量，适配关键字和规则
                 * @param yOffset Y轴偏移量，适配关键字和规则
                 * @param pageNo 签名在PDF中的页码，第几页查找关键字，正数为正序，当是负数为倒序
                 * @param KWIndex KWIndex 第几个关键字
                 * @constructor
                 */
//                var signerRule = new SignRule_KeyWord("签名算法",100,100,1,1);

                /**
                 * 关键字定位方式，寻找PDF中的关键字，根据关键字位置放置签名图片
                 * @param keyword 关键字
                 * @param keyWordAlignMethod 签字图片和关键字位置关系：等于0时，签字图片和关键字矩形重心重合
                 *                            等于1时，签字图片位于关键字正下方，中心线对齐；等于2时，签字图片位于关键字正右方，中心线对齐；
                 *                            等于3时，签字图片左上角和关键字右下角重合，可能额外附加偏移量，详见构造函数的offset参数
                 * @param keyWordOffset 当keyWordAlignMethod非零时，额外附加的偏移量，单位pt
                 * @param pageNo 寻找关键字的PDF起始页码
                 * @param KWIndex KWIndex 第几个关键字
                 * @constructor
                 */
//                var signerRule = new SignRule_KeyWordV2("关键字", "0", 10, 1,1);


                var signatureConfig = new SignatureConfig(signer,signerRule);
//                   1:时间在上、2：时间在下、3：时间在右
                var timeTag = new TimeTag(1,"yyMMdd hh:mm;ss");
                signatureConfig.timeTag = timeTag;
				signatureConfig.singleWidth = 200;
				signatureConfig.singleHeight = 200;
				signatureConfig.title = "请投保人李明签字";
				signatureConfig.penColor = "#FF0000";
                signatureConfig.isTSS = false;//是否开始时间戳服务
                signatureConfig.signatureImgRatio = 3.0;
                signatureConfig.nessesary = false;
                signatureConfig.isdistinguish = false;
                signatureConfig.ocrCapture = ocrCapture;
                var res = apiInstance.addSignatureObj(context_id,signatureConfig);
                console.log(apiInstance)
                if(res)
                {
                    console.log("addSignatureObj "+context_id+" success");
                    return res;
                }
                else
                {
                    console.log("addSignatureObj "+context_id+" error");
                    return res;
                }
            }


            //添加批签签名框
            function testAddCommentObj(objId)
            {

                var context_id = objId;
                var signer = new Signer("李明", "11011111111");

                /**
                 * 根据坐标定位签名方式
                 * @param left 签名图片最左边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param top 签名图片顶边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param right 签名图片最右边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param bottom 签名图片底边坐标值，相对于PDF当页最左下角(0,0)点，向上和向右分别为X轴、Y轴正方向
                 * @param pageNo 签名在PDF中的页码，从1开始
                 * @param unit 坐标系单位，目前支持"dp"和"pt"
                 * @constructor
                 */
                var signerRule = new SignRule_XYZ(84.0, 523.0, 400.0, 477.0, 1, "dp");



               /**
 				* 关键字定位方式，寻找PDF中的关键字，根据关键字位置放置签名图片
 				* @param keyword 关键字
 				* @param keyWordAlignMethod 签字图片和关键字位置关系：等于1时，签字图片和关键字矩形重心重合
 				*                           等于2时，签字图片位于关键字正下方，中心线对齐；等于3时，签字图片位于关键字正右方，中心线对齐；
 				*                            等于4时，签字图片左上角和关键字右下角重合，可能额外附加偏移量，详见构造函数的offset参数
 				* @param keyWordOffset 当keyWordAlignMethod非零时，额外附加的偏移量，单位pt
 				* @param pageNo 寻找关键字的PDF起始页码
 				* @param KWIndex KWIndex 第几个关键字
 				* @constructor
 				*/
//              var signerRule = new SignRule_KeyWord("默认",4,0,1,1);


                var commentConfig = new CommentConfig(signer,signerRule);
                commentConfig.commitment = "本人已阅读保险条款、产品说明书和投保提示书，了解本产品的特点和保单利益的不确定性。";
                commentConfig.mass_word_height = 50;
                commentConfig.mass_word_width = 50;
                commentConfig.mass_words_in_single_line = 4;
				commentConfig.penColor = "#FF0000";
                commentConfig.nessesary = false;
                commentConfig.isdistinguish = false;
                commentConfig.ocrCapture = ocrCapture;
                commentConfig.mass_dlg_type = CommentInputType.WhiteBoard;
                var res = apiInstance.addCommentObj(context_id,commentConfig);
                if(res)
                {
                    console.log("addCommentObj "+context_id+" success");
                    return res;
                }
                else
                {
                    console.log("addCommentObj "+context_id+" error");
                    return res;
                }
            }


            //demo总入口
            function testAnySign(channel)
            {
                var res;
                
                //识别回调接口
                var identify_callback = function(errCode){
                	if(errCode == SUCCESS){
                		return;
                	}
                  	if(errCode == DATA_CANNOT_PARSED) {
                		console.log("输入数据项无法解析！");
					} else if(errCode == SERVICE_SYSTEM_EXCEPTION) {
                		console.log("服务系统异常错误！");
					} else if(errCode == RECOGNITION_RESULT_EMPTY) {
                		console.log("识别结果为空！");
					} else if(errCode == CONNECTION_SERVICE_TIMEOUT) {
                		console.log("连接识别服务超时！");
					} else if(errCode == CONNECTION_RECOGNITION_EXCEPTION) {
                		console.log("连接识别服务异常！");
					} else if(errCode == RECOGNITION_FALSE) {
                		console.log("书写错误！");
					}else{
                		console.log(errCode);
					}
                }
                
                var callback = function(context_id,context_type,val)
                {
                    var isT = sessionStorage.getItem('ist');
        			document.getElementById("other").style.display = "block";
                	if(context_type == CALLBACK_TYPE_START_RECORDING || context_type == CALLBACK_TYPE_STOP_RECORDING)
                	{
                		return;
                	}
                   
                    if(context_type == CALLBACK_TYPE_SIGNATURE && isT == '0' )
                    {
                        //签名回显
                        document.getElementById("xss_20").src = "data:image/png;base64," + val;
                        var aImg=document.getElementById("xss_20");
                        for(var i=0;i<aImg.length;i++){
                            aImg[i].style.height="1500";
                            aImg[i].style.width="1500";
                        }
                    }
                    else if(context_type == CALLBACK_TYPE_SIGNATURE && isT == '2' )
                    {
                        //签名回显
                        document.getElementById("xss_21").src = "data:image/png;base64," + val;
                        var aImg=document.getElementById("xss_21");
                        for(var i=0;i<aImg.length;i++){
                            aImg[i].style.height="1500";
                            aImg[i].style.width="1500";
                        }
                    }
                    else if(context_type == CALLBACK_TYPE_COMMENTSIGN)
                    {
                    	//签名回显
                        document.getElementById("xss_21").src = "data:image/png;base64," + val;
                        var aImg=document.getElementById("xss_21");
                        for(var i=0;i<aImg.length;i++){
                            aImg[i].style.height="250";
                            aImg[i].style.width="250";
                        }
                    	
                    }
					
                    setAlertTitle();
                    // console.log("收到浏览器回调：" + "context_id：" + context_id + " context_type：" + context_type + " value：" + val);
                };//测试回调，将回调数据显示

                ////////////////////////////////////////////////
                
                //设置签名算法，默认为RSA，可以设置成SM2
    			EncAlgType.EncAlg = "RSA";
    
                apiInstance = new AnySignApi();
//                var channel = "10010";//渠道号，由信手书提供，请咨询项目经理
                //初始化签名接口
                res = apiInstance.initAnySignApi(callback,channel);

                if(!res){
                	console.log("init error");
                }else{

                }
                ////////////////////////////////////////////////
                
                //开启识别
                ocrCapture = new OCRCapture();
                ocrCapture.text = "a";
				ocrCapture.IPAdress = "http://192.168.126.32:11203/HWR/RecvServlet";
				ocrCapture.appID = "123";
				ocrCapture.count = 5;
				ocrCapture.language = Language.CHS;
				ocrCapture.resolution = 80;
				ocrCapture.serviceID = "999999";

				setIdentifyCallBack(identify_callback);
				
                ///////////////////////////////////////////////

                //注册单字签字对象20
                res = testAddSignatureObj(20);
                if(!res){
                    console.log("testAddSignatureObj error");
					return;
                }else{

                }

                res = testAddCommentObj(30);
                if(!res){
                    console.log("testAddCommentObj error");
					return;
                }else{

                }

                ////////////////////////////////////////////////

                //注册一个单位签章

                var signer = new Signer("小明","110xxxxxx");
                /**
                 * 使用服务器规则配置签名
                 * @param tid 服务器端生成的配置规则
                 * @constructor
                 */
                var signerRule = new SignRule_Tid("111");
                var cachet_config = new CachetConfig(signer, signerRule, false);

                res = apiInstance.addCachetObj(cachet_config);
//              ////////////////////////////////////////////////
//
//              if(!res){
//                  console.log("addCachetObj error");
//              }else{
//
//              }
                ////////////////////////////////////////////////

                //将配置提交
                res = apiInstance.commitConfig();

                if(res){
                	console.log("Init ALL 提交配置成功");
                }else{
                	console.log("Init ALL 提交配置失败");
                }

                ////////////////////////////////////////////////
            
            }
            
            function testIsReadyToUpload()
            {
                console.log("testIsReadyToUpload :" + apiInstance.isReadyToUpload());
            }

            //生成签名加密数据
            function testGenData()
            {
                var res = document.getElementById('result');

                try
                {
                    res.value = apiInstance.getUploadDataGram();
                    console.log("value"+res.value);
                }
                catch(err)
                {
                    console.log(err);
                }
            }

            //弹出签名框签名
            function testPopupDialog(context_id)
            {
            	if(!apiInstance){
                    console.log("信手书接口没有初始化");
                    return;
            	}
                switch (apiInstance.showSignatureDialog(context_id))
                {
                    case RESULT_OK:
                    // console.log(document.getElementById("other"))
        				document.getElementById("other").style.display = "none";
                        break;
                    case EC_API_NOT_INITED:
                        console.log("信手书接口没有初始化");
                        break;
                    case EC_WRONG_CONTEXT_ID:
                        console.log("没有配置相应context_id的签字对象");
                        break;
                }
            }
            
            function setIdentifyCallBack(callback){
            	if(!apiInstance){
                    console.log("信手书接口没有初始化");
                    return;
            	}
            	apiInstance.setIdentifyCallBack(callback);
            }

            //弹出批注签名框
            function testCommentDialog(context_id)
            {
            	if(!apiInstance){
                    console.log("信手书接口没有初始化");
                    return;
            	}
                switch (apiInstance.showCommentDialog(context_id))
                {
                    case RESULT_OK:
        				document.getElementById("other").style.display = "none";
                        break;
                    case EC_API_NOT_INITED:
                        console.log("信手书接口没有初始化");
                        break;
                    case EC_WRONG_CONTEXT_ID:
                        console.log("没有配置相应context_id的签字对象");
                    case EC_COMMENT_ALREADY_SHOW:
                        console.log("批注签名框已弹出，请勿重复操作！");
                }

            }

            //获取签名api版本信息
            function testGetVersion()
            {
                console.log(apiInstance.getVersion());
            }

            //获取设备操作系统信息
            function testGetOsInfo()
            {
                console.log(apiInstance.getOSInfo());
                console.log(navigator.userAgent);
                console.log(window.__wxjs_is_wkwebview);
            }
            
            //jane
            function testAddEvidence(result)
            {

                console.log(apiInstance.addEvidence(20,result,DataFormat.IMAGE_JPEG,BioType.PHOTO_SIGNER_IDENTITY_CARD_BACK,0));
            }

        
