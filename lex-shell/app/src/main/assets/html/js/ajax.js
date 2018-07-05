//import $ from 'jquery';
// 参数为地址，参数，回调，遮罩，消息，同步 ----- 其中回调为数组时，第一个为成功，第二个为完成，错误采用统一方法
var that = this
const ajax = (url,para,callback,mask,msg,sync)=>{
    let param={...para},
        baseUrl='http://47.104.13.159:7666';
        // baseUrl='http://192.168.1.250:7666';


    if(url.indexOf('http')!=0) url=baseUrl+url;
    
    $.ajax({
        contentType: 'application/json',
        async:!sync,
        type:'POST',
        url:url,
        cache:false,
        data:JSON.stringify(param),
        beforeSend(){
            if(mask) popalert.waitstart();
        },
        success(data){
            if(msg) console.log(data)
            if(typeof callback == 'function') callback(data);
            else if(typeof callback == 'object') callback[0](data);
        },
        error(err){
            console.log(err,'err');
            if(typeof callback == 'object' && typeof callback[2] == 'function') callback[2]();
        },
        complete(){
            if(typeof callback == 'object' && typeof callback[1] == 'function') callback[1]();
        }
    });
};

